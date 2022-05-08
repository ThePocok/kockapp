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
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import hu.thepocok.kockapp.R;
import hu.thepocok.kockapp.model.cube.component.Color;
import hu.thepocok.kockapp.model.cube.component.Face;
import hu.thepocok.kockapp.model.cube.component.Layer;

public class ReadCubeFromCameraActivity extends AppCompatActivity {
    private static final String TAG = "ReadCubeFromCameraActivity";
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int ANALYSISTRESHOLD = 1000;
    public static final int TILESIZE = 100;

    private ImageAnalysis imageAnalysis;

    private ArrayList<Color> tileColors = new ArrayList<>();
    private long colorsLastProcessedTime = 0L;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView previewView;
    private CubeTileOverlayView cubeTileOverlayView;

    private Button sizeChangeBtn;
    private boolean isTwoTimesTwo = false;

    private Point[] cubeThreePieceOffset = new Point[]{
            new Point(-1, -1), new Point(-1, 0), new Point(-1, 1),
            new Point(0, -1), new Point(0, 0), new Point(0, 1),
            new Point(1, -1), new Point(1, 0), new Point(1, 1)
    };

    private Point[] cubeTwoPieceOffset = new Point[]{
            new Point(-1, -1), new Point(-1, 0),
            new Point(0, -1), new Point(0, 0)
    };

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

        for (int i = 0; i < cubeThreePieceOffset.length; i++) {
            tileColors.add(Color.EMPTY);
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

            if(bitmap != null && System.currentTimeMillis() - colorsLastProcessedTime > ANALYSISTRESHOLD) {
                Mat mat = new Mat();
                Utils.bitmapToMat(bitmap, mat);

                Mat hsvImage = new Mat();
                Imgproc.cvtColor(mat, hsvImage, Imgproc.COLOR_RGB2HSV);

                tileColors.clear();
                Point matCenterPoint = new Point(hsvImage.width() / 2, hsvImage.height() / 2);

                int arrayLength = (isTwoTimesTwo) ? cubeTwoPieceOffset.length : cubeThreePieceOffset.length;

                for (int i = 0; i < arrayLength; i++) {
                    Point point = (isTwoTimesTwo) ? cubeTwoPieceOffset[i] : cubeThreePieceOffset[i];

                    Point topLeft = new Point(point.x * TILESIZE + matCenterPoint.x - 50, point.y * TILESIZE + matCenterPoint.y - 50);
                    Point bottomRight = new Point(point.x * TILESIZE + matCenterPoint.x + 50, point.y * TILESIZE + matCenterPoint.y + 50);

                    Rect rect = new Rect(topLeft, bottomRight);
                    Mat hsvSubMat = hsvImage.submat(rect);
                    Scalar hsvValues = Core.mean(hsvSubMat);

                    tileColors.add(getColorFromHSV(hsvValues));
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
        cubeTileOverlayView.setTileColors(tileColors);
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
        ArrayList<Color> colors = (ArrayList<Color>) tileColors.clone();
        Face face;

        if (isTwoTimesTwo) {
            Layer firstLayer = new Layer(colors.get(0), colors.get(1));
            Layer secondLayer = new Layer(colors.get(2), colors.get(3));
            face = new Face(firstLayer, secondLayer);
        } else {
            Layer firstLayer = new Layer(colors.get(0), colors.get(1), colors.get(2));
            Layer secondLayer = new Layer(colors.get(3), colors.get(4), colors.get(5));
            Layer thirdLayer = new Layer(colors.get(6), colors.get(7), colors.get(8));
            face = new Face(firstLayer, secondLayer, thirdLayer);
        }


        if (whiteFace == null) {
            whiteFace = face;
            Log.d(TAG, "Colors assigned to white face");
        } else if (redFace == null) {
            redFace = face;
            Log.d(TAG, "Colors assigned to red face");
        } else if (greenFace == null) {
            greenFace = face;
            Log.d(TAG, "Colors assigned to green face");
        } else if (orangeFace == null) {
            orangeFace = face;
            Log.d(TAG, "Colors assigned to orange face");
        } else if (blueFace == null) {
            blueFace = face;
            Log.d(TAG, "Colors assigned to blue face");
        } else if (yellowFace == null) {
            yellowFace = face;
            Log.d(TAG, "Colors assigned to yellow face");

            //TODO link to new activity
            Intent intent = new Intent(this, ReadCubeManuallyActivity.class);
            intent.putExtra("cubeDimensions", whiteFace.getDimensions());
            intent.putExtra("whiteFace", whiteFace);
            intent.putExtra("redFace", redFace);
            intent.putExtra("greenFace", greenFace);
            intent.putExtra("orangeFace", orangeFace);
            intent.putExtra("blueFace", blueFace);
            intent.putExtra("yellowFace", yellowFace);
            startActivity(intent);
        }
    }

    private Color getColorFromHSV(Scalar hsvValues) {
        if (inBetween(hsvValues, new Scalar(0, 70, 50), new Scalar(9, 255, 255)) ||
                inBetween(hsvValues, new Scalar(170, 70, 50), new Scalar(180, 255, 255))) {
            return Color.RED;
        } else if (inBetween(hsvValues, new Scalar(10, 100, 20), new Scalar(24, 255, 255))) {
            return Color.ORANGE;
        } else if (inBetween(hsvValues, new Scalar(25, 100, 100), new Scalar(35, 255, 255))) {
            return Color.YELLOW;
        } else if (inBetween(hsvValues, new Scalar(36, 50, 70), new Scalar(89, 255, 255))) {
            return Color.GREEN;
        } else if (inBetween(hsvValues, new Scalar(90, 50, 70), new Scalar(128, 255, 255))) {
            return Color.BLUE;
        } else if (inBetween(hsvValues, new Scalar(0, 0, 200), new Scalar(180, 55, 255))) {
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

        if (isTwoTimesTwo) {
            sizeChangeBtn.setText("2x2");
        } else {
            sizeChangeBtn.setText("3x3");
        }

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