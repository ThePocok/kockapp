package hu.thepocok.kockapp.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.YuvImage;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.features2d.ORB;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import hu.thepocok.kockapp.R;
import hu.thepocok.kockapp.model.cube.component.Color;
import hu.thepocok.kockapp.model.cube.component.Face;
import hu.thepocok.kockapp.model.cube.component.Layer;
import pl.droidsonroids.gif.GifImageView;

public class ReadCubeFromCameraActivity extends AppCompatActivity {
    private static final String TAG = "ReadCubeFromCameraActivity";
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int ANALYSISTRESHOLD = 1000;

    private ImageAnalysis imageAnalysis;

    private ArrayList<Color> tileColors = new ArrayList<>();
    private final Object tileColorsLock = new Object();
    private long colorsLastProcessedTime = 0L;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView previewView;
    private CubeTileOverlayView cubeTileOverlayView;

    private Button sizeChangeBtn;
    private boolean isTwoTimesTwo = false;

    private GifImageView gifOverlay;

    private CubeFacePreviewView facePreviewView;

    private Color currentFaceToSet;
    private Face whiteFace = null;
    private Face redFace = null;
    private Face greenFace = null;
    private Face orangeFace = null;
    private Face blueFace = null;
    private Face yellowFace = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_cube_from_camera);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Button captureBtn = findViewById(R.id.capture_face_button);
        captureBtn.setOnClickListener(l -> setFace());

        sizeChangeBtn = findViewById(R.id.cube_size_selector);
        sizeChangeBtn.setOnClickListener(l -> changeCubeSize());

        previewView = findViewById(R.id.previewView);
        cubeTileOverlayView = findViewById(R.id.cube_tile_overlay);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        gifOverlay = findViewById(R.id.gif_overlay);

        facePreviewView = findViewById(R.id.cube_face_preview_view);
        facePreviewView.setOnTouchListener((view, click) -> {
            Color clickedFace = facePreviewView.getClickedFace(click.getX(), click.getY());
            Log.d(TAG, "Clicked color: " + clickedFace);
            currentFaceToSet = clickedFace;
            return false;
        });
        currentFaceToSet = Color.WHITE;

        synchronized (tileColorsLock) {
            for (int i = 0; i < 9; i++) {
                tileColors.add(Color.EMPTY);
            }
        }

        if (checkCameraPermission()) {
            Log.d(TAG, "Camera permission granted");
        } else {
            requestCameraPermission();
        }

        imageAnalysis = setImageAnalysis();

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, e.toString());
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindPreview(ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis, preview);
    }


    private ImageAnalysis setImageAnalysis() {
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setImageQueueDepth(24)
                .build();

        imageAnalysis.setAnalyzer(AsyncTask.THREAD_POOL_EXECUTOR, image -> {
            Bitmap bitmap = imageToBitmap(image);

            if (!cubeTileOverlayView.isRotationDegreeSet()) {
                cubeTileOverlayView.setRotationDegree(image.getImageInfo().getRotationDegrees());
            }

            if(bitmap != null && System.currentTimeMillis() - colorsLastProcessedTime > ANALYSISTRESHOLD) {
                Mat mat = new Mat();
                Utils.bitmapToMat(bitmap, mat);

                Mat hsvImage = new Mat();
                Imgproc.cvtColor(mat, hsvImage, Imgproc.COLOR_RGB2HSV);

                Point matCenterPoint = new Point(hsvImage.width() / 2.0f, hsvImage.height() / 2.0f);

                int arrayLength = (isTwoTimesTwo) ? 4 : 9;

                for (int i = 0; i < arrayLength; i++) {
                    Point[] points = cubeTileOverlayView.getAnalyzableSquareCoordinates(hsvImage.width(), hsvImage.height(), matCenterPoint, i);

                    Point topLeft = points[0];
                    Point bottomRight = points[1];

                    Rect rect = new Rect(topLeft, bottomRight);

                    Mat hsvSubMat = hsvImage.submat(rect);
                    Scalar hsvValues = Core.mean(hsvSubMat);
                    Log.d("ReadColor", hsvValues.toString());

                    //Bitmap bmp = Bitmap.createBitmap(bitmap, (int) points[0].x, (int) points[0].y, (int) (points[1].x - points[0].x), (int) (points[1].y - points[0].y));

                    synchronized (tileColorsLock) {
                        tileColors.set((int) points[2].y, getColorFromHSV(hsvValues));
                    }
                    Log.d("ReadColor", getColorFromHSV(hsvValues).stringValue);
                }

                mat.release();
                hsvImage.release();

                colorsLastProcessedTime = System.currentTimeMillis();
                setTilesToDraw();
            }

            image.close();
        });

        return imageAnalysis;
    }

    private void setTilesToDraw() {
        synchronized (tileColorsLock) {
            cubeTileOverlayView.setTileColors(tileColors);
        }
    }

    private Bitmap imageToBitmap(ImageProxy image) {
        ByteBuffer yBuffer = image.getPlanes()[0].getBuffer();
        ByteBuffer uBuffer = image.getPlanes()[1].getBuffer();
        ByteBuffer vBuffer = image.getPlanes()[2].getBuffer();

        int ySize = yBuffer.remaining();
        int uSize = uBuffer.remaining();
        int vSize = vBuffer.remaining();

        byte[] nv21= new byte[ySize + uSize + vSize];
        yBuffer.get(nv21, 0, ySize);
        vBuffer.get(nv21, ySize, vSize);
        uBuffer.get(nv21, ySize + vSize, uSize);

        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, image.getWidth(), image.getHeight(), null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new android.graphics.Rect(0, 0, image.getWidth(), image.getHeight()), 50, outputStream);
        byte[] bytes = outputStream.toByteArray();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public void setFace() {
        ArrayList<Color> colors;
        synchronized (tileColorsLock) {
            colors = (ArrayList<Color>) tileColors.clone();
        }
        Face face;

        // Due to some miscalculations, the cube in the array is mirrored to the y axis
        if (isTwoTimesTwo) {
            Layer firstLayer = new Layer(colors.get(1), colors.get(0));
            Layer secondLayer = new Layer(colors.get(3), colors.get(2));
            face = new Face(firstLayer, secondLayer);
        } else {
            Layer firstLayer = new Layer(colors.get(2), colors.get(1), colors.get(0));
            Layer secondLayer = new Layer(colors.get(5), colors.get(4), colors.get(3));
            Layer thirdLayer = new Layer(colors.get(8), colors.get(7), colors.get(6));
            face = new Face(firstLayer, secondLayer, thirdLayer);
        }


        if (currentFaceToSet.equals(Color.WHITE)) {
            whiteFace = face;
            Toast.makeText(this, "Colors assigned to white face",
                    Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Colors assigned to white face");
            facePreviewView.setFace(Color.WHITE, whiteFace);

            currentFaceToSet = Color.RED;
            displayArrowGif(0);
        } else if (currentFaceToSet.equals(Color.RED)) {
            redFace = face;
            Toast.makeText(this, "Colors assigned to red face",
                    Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Colors assigned to red face");
            facePreviewView.setFace(Color.RED, redFace);

            currentFaceToSet = Color.GREEN;
            displayArrowGif(90);
        } else if (currentFaceToSet.equals(Color.GREEN)) {
            greenFace = face;
            Toast.makeText(this, "Colors assigned to green face",
                    Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Colors assigned to green face");
            facePreviewView.setFace(Color.GREEN, greenFace);

            currentFaceToSet = Color.ORANGE;
            displayArrowGif(90);
        } else if (currentFaceToSet.equals(Color.ORANGE)) {
            orangeFace = face;
            Toast.makeText(this, "Colors assigned to orange face",
                    Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Colors assigned to orange face");
            facePreviewView.setFace(Color.ORANGE, orangeFace);

            currentFaceToSet = Color.BLUE;
            displayArrowGif(90);
        } else if (currentFaceToSet.equals(Color.BLUE)) {
            blueFace = face;
            Toast.makeText(this, "Colors assigned to blue face",
                    Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Colors assigned to blue face");
            facePreviewView.setFace(Color.BLUE, blueFace);

            currentFaceToSet = Color.YELLOW;
            displayTwoArrowGifs(90, 0);
        } else if (currentFaceToSet.equals(Color.YELLOW)) {
            yellowFace = face;
            Toast.makeText(this, "Colors assigned to yellow face",
                    Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Colors assigned to yellow face");
            facePreviewView.setFace(Color.YELLOW, yellowFace);

            if (allFacesSet()) {
                currentFaceToSet = Color.EMPTY;
            } else {
                currentFaceToSet = Color.WHITE;
            }
        }

        if (currentFaceToSet.equals(Color.EMPTY)) {
            Intent intent = new Intent(this, ReadCubeManuallyActivity.class);
            intent.putExtra("cubeDimensions", whiteFace.getDimensions());
            intent.putExtra("whiteFace", whiteFace);
            intent.putExtra("redFace", redFace);
            intent.putExtra("greenFace", greenFace);
            intent.putExtra("orangeFace", orangeFace);
            intent.putExtra("blueFace", blueFace);
            intent.putExtra("yellowFace", yellowFace);

            currentFaceToSet = Color.WHITE;

            whiteFace = null;
            redFace = null;
            greenFace = null;
            orangeFace = null;
            blueFace = null;
            yellowFace = null;
            startActivity(intent);
        }
    }

    private boolean allFacesSet() {
        return whiteFace != null
                && redFace != null
                && greenFace != null
                && orangeFace != null
                && blueFace != null
                && yellowFace != null;
    }

    public void displayArrowGif(int rotationDegree) {
        Thread gifOverlayThread = new Thread(() -> {
            runOnUiThread(() -> {
                gifOverlay.setRotation(rotationDegree);
                gifOverlay.setImageResource(R.drawable.arrow);
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(() -> gifOverlay.setImageResource(0));
        });
        gifOverlayThread.start();
    }

    public void displayTwoArrowGifs(int rotationDegree1, int rotationDegree2) {
        Thread gifThreadHandler = new Thread(() -> {
            displayArrowGif(rotationDegree1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            displayArrowGif(rotationDegree2);
        });
        gifThreadHandler.start();
    }

    private Color getColorFromHSV(Scalar hsvValues) {
        if (inBetween(hsvValues, new Scalar(0, 70, 50), new Scalar(9, 255, 255)) ||
                inBetween(hsvValues, new Scalar(160, 70, 50), new Scalar(180, 255, 255))) {
            return Color.RED;
        } else if (inBetween(hsvValues, new Scalar(10, 100, 20), new Scalar(24, 255, 255))) {
            return Color.ORANGE;
        } else if (inBetween(hsvValues, new Scalar(25, 100, 100), new Scalar(40, 255, 255))) {
            return Color.YELLOW;
        } else if (inBetween(hsvValues, new Scalar(41, 50, 70), new Scalar(75, 255, 255))) {
            return Color.GREEN;
        } else if (inBetween(hsvValues, new Scalar(90, 50, 70), new Scalar(130, 255, 255))) {
            return Color.BLUE;
        } else if (inBetween(hsvValues, new Scalar(0, 0, 180), new Scalar(180, 75, 255))) {
            return Color.WHITE;
        }

        return Color.EMPTY;
    }

    private boolean inBetween(Scalar hsvValues, Scalar lowerBound, Scalar upperBound) {
        return (hsvValues.val[0] >= lowerBound.val[0] && hsvValues.val[0] <= upperBound.val[0]) &&
                (hsvValues.val[1] >= lowerBound.val[1] && hsvValues.val[1] <= upperBound.val[1]) &&
                (hsvValues.val[2] >= lowerBound.val[2] && hsvValues.val[2] <= upperBound.val[2]);
    }

    private void changeCubeSize() {
        isTwoTimesTwo = !isTwoTimesTwo;

        synchronized (tileColorsLock) {
            tileColors.clear();
            if (isTwoTimesTwo) {
                sizeChangeBtn.setText("2x2");
                for (int i = 0; i < 4; i++) {
                    tileColors.add(Color.EMPTY);
                }
            } else {
                sizeChangeBtn.setText("3x3");
                for (int i = 0; i < 9; i++) {
                    tileColors.add(Color.EMPTY);
                }
            }
        }

        facePreviewView.clearFaces();
        facePreviewView.setCubeDimensions((isTwoTimesTwo) ? 2 : 3);
        currentFaceToSet = Color.WHITE;

        cubeTileOverlayView.setTwoTimesTwo(isTwoTimesTwo);

        whiteFace = null;
        redFace = null;
        greenFace = null;
        orangeFace = null;
        blueFace = null;
        yellowFace = null;
    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    private void showPermissionAskingDialog(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        showPermissionAskingDialog("You need to allow access permissions",
                                (dialog, which) -> requestCameraPermission());
                    }
                }
                break;
        }
    }
}