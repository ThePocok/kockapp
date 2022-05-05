package hu.thepocok.kockapp.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCamera2View;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;

import hu.thepocok.kockapp.R;
import hu.thepocok.kockapp.model.cube.component.Color;
import hu.thepocok.kockapp.model.cube.component.Face;
import hu.thepocok.kockapp.model.cube.component.Layer;

public class ReadCubeFromCameraActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private static final String TAG = "ReadCubeFromCameraActivity";
    private static final int PERMISSION_REQUEST_CODE = 100;

    private JavaCameraView camera;
    private BaseLoaderCallback baseLoaderCallback;

    Mat frame;
    ArrayList<Color> tileColors = new ArrayList<>();
    private long colorsLastProcessedTime = 0L;

    private Point[] cubeThreePieceOffset = new Point[]{
            new Point(-1, -1), new Point(-1, 0), new Point(-1, 1),
            new Point(0, -1), new Point(0, 0), new Point(0, 1),
            new Point(1, -1), new Point(1, 0), new Point(1, 1)
    };

    private Point[] cubeTwoPieceOffset = new Point[]{
            new Point(-1, -1), new Point(-1, 1),
            new Point(1, -1), new Point(1, 1)
    };
    private final int TILESIZE = 100;

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

        Button captureBtn =  findViewById(R.id.capture_face_button);
        captureBtn.setOnClickListener(e -> setFace());

        for (int i = 0; i < cubeThreePieceOffset.length; i++) {
            tileColors.add(Color.EMPTY);
        }

        if (checkCameraPermission()) {
            Log.d(TAG, "Camera permission granted");

            initCubeScanning();
        } else {
            requestCameraPermission();
        }
    }

    private void initCubeScanning() {
        camera = findViewById(R.id.javaCameraView);

        camera.setVisibility(SurfaceView.VISIBLE);
        camera.setCameraPermissionGranted();
        camera.setCvCameraViewListener(this);
        camera.setMaxFrameSize(640, 480);
        camera.enableView();

        baseLoaderCallback = new BaseLoaderCallback(this) {
            @Override
            public void onManagerConnected(int status) {
                switch (status) {
                    case LoaderCallbackInterface.SUCCESS:
                        Log.d(TAG, "openCV loaded successfully");
                        camera.enableView();
                        break;
                    default:
                        super.onManagerConnected(status);
                        Log.d(TAG, "Something went wrong during openCV initialization");
                        break;
                }
            }
        };
    }

    public void setFace() {
        ArrayList<Color> colors = (ArrayList<Color>) tileColors.clone();
        Layer firstLayer = new Layer(colors.get(0), colors.get(1), colors.get(2));
        Layer secondLayer = new Layer(colors.get(3), colors.get(4), colors.get(5));
        Layer thirdLayer = new Layer(colors.get(6), colors.get(7), colors.get(8));

        if (whiteFace == null) {
            whiteFace = new Face(firstLayer, secondLayer, thirdLayer);
        } else if (redFace == null) {
            redFace = new Face(firstLayer, secondLayer, thirdLayer);
        } else if (greenFace == null) {
            greenFace = new Face(firstLayer, secondLayer, thirdLayer);
        } else if (orangeFace == null) {
            orangeFace = new Face(firstLayer, secondLayer, thirdLayer);
        } else if (blueFace == null) {
            blueFace = new Face(firstLayer, secondLayer, thirdLayer);
        } else if (yellowFace == null) {
            yellowFace = new Face(firstLayer, secondLayer, thirdLayer);
            //TODO link to new activity
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        frame = inputFrame.rgba();
        Mat frameT = frame.t();
        Core.flip(frameT, frameT, 1);
        Imgproc.resize(frameT, frameT, frame.size());

        // Check cube every half seconds
        if (System.currentTimeMillis() - colorsLastProcessedTime > 2000) {
            Mat hsvImage = new Mat();
            Imgproc.cvtColor(frameT, hsvImage, Imgproc.COLOR_RGB2HSV);

            tileColors = new ArrayList<>();
            Point matCenterPoint = new Point(frameT.width() / 2, frameT.height() / 2);

            for (Point point : cubeThreePieceOffset) {
                Point topLeft = new Point(point.x * TILESIZE + matCenterPoint.x - 50, point.y * TILESIZE + matCenterPoint.y - 50);
                Point bottomRight = new Point(point.x * TILESIZE + matCenterPoint.x + 50, point.y * TILESIZE + matCenterPoint.y + 50);

                Rect rect = new Rect(topLeft, bottomRight);
                Mat hsvSubMat = hsvImage.submat(rect);
                Scalar hsvValues = Core.mean(hsvSubMat);

                tileColors.add(getColorFromHSV(hsvValues));
            }

            colorsLastProcessedTime = System.currentTimeMillis();
            hsvImage.release();
        }

        for (int i = 0; i < cubeThreePieceOffset.length; i++) {
            Point point = cubeThreePieceOffset[i];

            Point matCenterPoint = new Point(frameT.width() / 2, frameT.height() / 2);

            Point topLeft = new Point(point.x * TILESIZE + matCenterPoint.x - 50, point.y * TILESIZE + matCenterPoint.y - 50);
            Point bottomRight = new Point(point.x * TILESIZE + matCenterPoint.x + 50, point.y * TILESIZE + matCenterPoint.y + 50);

            Scalar scalar = new Scalar(tileColors.get(i).redValue, tileColors.get(i).greenValue, tileColors.get(i).blueValue);
            Imgproc.rectangle(frameT, topLeft, bottomRight, scalar, 5);
        }

        //Core.inRange(hsvImage, new Scalar(0, 70, 50), new Scalar(10, 255, 255), filtered); //RED
        //Core.inRange(hsvImage, new Scalar(170, 70, 50), new Scalar(180, 255, 255), filtered); //RED
        //Core.inRange(hsvImage, new Scalar(10, 100, 20), new Scalar(25, 255, 255), filtered); //ORANGE
        //Core.inRange(hsvImage, new Scalar(20, 100, 100), new Scalar(40, 255, 255), filtered); //YELLOW
        //Core.inRange(hsvImage, new Scalar(36, 50, 70), new Scalar(89, 255, 255), filtered); //GREEN
        //Core.inRange(hsvImage, new Scalar(90, 50, 70), new Scalar(128, 255, 255), filtered); //BLUE
        //Core.inRange(hsvImage, new Scalar(0, 0, 200), new Scalar(180, 55, 255), filtered); //WHITE

        return frameT;
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

                    // main logic
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