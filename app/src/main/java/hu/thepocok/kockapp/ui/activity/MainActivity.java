package hu.thepocok.kockapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;

import hu.thepocok.kockapp.R;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView cubeByCameraBtn = findViewById(R.id.cube_by_camera);
        cubeByCameraBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ReadCubeFromCameraActivity.class);
            startActivity(intent);
        });

        ImageView cube3Manually = findViewById(R.id.cube_3_manually);
        cube3Manually.setOnClickListener(view -> {
            Intent intent = new Intent(this, ReadCubeManuallyActivity.class);
            intent.putExtra("cubeDimensions", 3);
            startActivity(intent);
        });

        ImageView cube2Manually = findViewById(R.id.cube_2_manually);
        cube2Manually.setOnClickListener(view -> {
            Intent intent = new Intent(this, ReadCubeManuallyActivity.class);
            intent.putExtra("cubeDimensions", 2);
            startActivity(intent);
        });
    }

    static {
        if (OpenCVLoader.initDebug()) {
            Log.d(TAG, "OpenCV is initialized");
        }
        else {
            Log.d(TAG, "OpenCV is not initialized");
        }
    }
}