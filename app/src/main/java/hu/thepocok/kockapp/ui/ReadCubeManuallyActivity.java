package hu.thepocok.kockapp.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

import hu.thepocok.kockapp.R;
import hu.thepocok.kockapp.model.cube.CubeThree;
import hu.thepocok.kockapp.model.cube.component.Color;
import hu.thepocok.kockapp.model.cube.component.Face;
import hu.thepocok.kockapp.model.cube.component.Layer;

public class ReadCubeManuallyActivity extends AppCompatActivity {
    private static final String TAG = "ReadCubeManually";

    private Color selectedColor = Color.WHITE;

    private Button whiteBtn;
    private Button redBtn;
    private Button greenBtn;
    private Button orangeBtn;
    private Button blueBtn;
    private Button yellowBtn;

    private LinearLayout cubeContainer;

    private Face whiteFace = null;
    private Face redFace = null;
    private Face greenFace = null;
    private Face orangeFace = null;
    private Face blueFace = null;
    private Face yellowFace = null;

    private CubeThree cube;

    private ArrayList<Color> tileColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_cube_manually);

        whiteBtn = findViewById(R.id.white_button);
        redBtn = findViewById(R.id.red_button);
        greenBtn = findViewById(R.id.green_button);
        orangeBtn = findViewById(R.id.orange_button);
        blueBtn = findViewById(R.id.blue_button);
        yellowBtn = findViewById(R.id.yellow_button);

        cubeContainer = findViewById(R.id.cube_container);

        setEmptyTiles();

        whiteBtn.setOnClickListener(l -> selectColor(Color.WHITE));
        redBtn.setOnClickListener(l -> selectColor(Color.RED));
        greenBtn.setOnClickListener(l -> selectColor(Color.GREEN));
        orangeBtn.setOnClickListener(l -> selectColor(Color.ORANGE));
        blueBtn.setOnClickListener(l -> selectColor(Color.BLUE));
        yellowBtn.setOnClickListener(l -> selectColor(Color.YELLOW));

        Button nextButton = findViewById(R.id.next_face_button);

        nextButton.setOnClickListener(l -> setFace());

        tileColors = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            tileColors.add(Color.WHITE);
        }
    }

    private void setEmptyTiles() {
        Resources res = getResources();
        for (int i = 0; i < 3; i++) {
            LinearLayout row = (LinearLayout) cubeContainer.getChildAt(i);
            row.removeAllViews();
            for (int j = 0; j < 3; j++) {
                //int tileSize = cubeContainer.getMeasuredHeight();
                //Log.d(TAG, String.valueOf(tileSize));

                Button tile = new Button(this);
                Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.cube_tile, getTheme());
                tile.setBackground(drawable);
                tile.setLayoutParams(new LinearLayout.LayoutParams(150, 150));

                int finalI = i;
                int finalJ = j;
                tile.setOnClickListener(l -> {
                    Drawable d = ResourcesCompat.getDrawable(res, R.drawable.cube_tile, getTheme());
                    if (selectedColor != Color.WHITE) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            d.setColorFilter(new BlendModeColorFilter(
                                    android.graphics.Color.rgb(selectedColor.redValue, selectedColor.greenValue, selectedColor.blueValue),
                                    BlendMode.SRC_ATOP));
                        } else {
                            d.setColorFilter(android.graphics.Color.rgb(selectedColor.redValue, selectedColor.greenValue, selectedColor.blueValue),
                                    PorterDuff.Mode.SRC_ATOP);
                        }
                    }

                    l.setBackground(d);
                    tileColors.set(finalI * 3 + finalJ, selectedColor);
                });

                row.addView(tile);
            }
            row.setGravity(Gravity.CENTER);
        }
    }

    private void setFace() {
        ArrayList<Color> colors = (ArrayList<Color>) tileColors.clone();
        Layer firstLayer = new Layer(colors.get(0), colors.get(1), colors.get(2));
        Layer secondLayer = new Layer(colors.get(3), colors.get(4), colors.get(5));
        Layer thirdLayer = new Layer(colors.get(6), colors.get(7), colors.get(8));

        if (whiteFace == null) {
            whiteFace = new Face(firstLayer, secondLayer, thirdLayer);
            Log.d(TAG, "Colors assigned to white face");
        } else if (redFace == null) {
            redFace = new Face(firstLayer, secondLayer, thirdLayer);
            Log.d(TAG, "Colors assigned to red face");
        } else if (greenFace == null) {
            greenFace = new Face(firstLayer, secondLayer, thirdLayer);
            Log.d(TAG, "Colors assigned to green face");
        } else if (orangeFace == null) {
            orangeFace = new Face(firstLayer, secondLayer, thirdLayer);
            Log.d(TAG, "Colors assigned to orange face");
        } else if (blueFace == null) {
            blueFace = new Face(firstLayer, secondLayer, thirdLayer);
            Log.d(TAG, "Colors assigned to blue face");
        } else if (yellowFace == null) {
            yellowFace = new Face(firstLayer, secondLayer, thirdLayer);
            Log.d(TAG, "Colors assigned to yellow face");
        }

        if (yellowFace != null) {
            cube = new CubeThree(whiteFace, redFace, greenFace, orangeFace, blueFace, yellowFace);
            Log.d(TAG, "Cube created");
            Log.d(TAG, cube.toString());

            checkCubeValidity();

            //TODO link to new activity
        }

        tileColors.clear();
        for (int i = 0; i < 9; i++) {
            tileColors.add(Color.WHITE);
        }
        setEmptyTiles();
    }

    private void checkCubeValidity() {
        if (!cube.isValidCube()) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Invalid cube")
                    .setMessage("Do you want to review the faces or reread the whole cube?")
                    .setPositiveButton("Review", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setNegativeButton("Reread", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            resetCube();
                        }
                    })
                    .show();
        }
    }

    private void resetCube() {
        whiteFace = null;
        redFace = null;
        greenFace = null;
        orangeFace = null;
        blueFace = null;
        yellowFace = null;
        cube = null;

        setEmptyTiles();
    }

    private void selectColor(Color color) {
        switch (selectedColor) {
            case WHITE:
                whiteBtn.setHighlightColor(android.graphics.Color.TRANSPARENT);
                break;
            case RED:
                redBtn.setHighlightColor(android.graphics.Color.TRANSPARENT);
                break;
            case GREEN:
                greenBtn.setHighlightColor(android.graphics.Color.TRANSPARENT);
                break;
            case ORANGE:
                orangeBtn.setHighlightColor(android.graphics.Color.TRANSPARENT);
                break;
            case BLUE:
                blueBtn.setHighlightColor(android.graphics.Color.TRANSPARENT);
                break;
            case YELLOW:
                yellowBtn.setHighlightColor(android.graphics.Color.TRANSPARENT);
                break;
        }

        selectedColor = color;

        switch (color) {
            case WHITE:
                whiteBtn.setHighlightColor(android.graphics.Color.MAGENTA);
                break;
            case RED:
                redBtn.setHighlightColor(android.graphics.Color.MAGENTA);
                break;
            case GREEN:
                greenBtn.setHighlightColor(android.graphics.Color.MAGENTA);
                break;
            case ORANGE:
                orangeBtn.setHighlightColor(android.graphics.Color.MAGENTA);
                break;
            case BLUE:
                blueBtn.setHighlightColor(android.graphics.Color.MAGENTA);
                break;
            case YELLOW:
                yellowBtn.setHighlightColor(android.graphics.Color.MAGENTA);
                break;
        }
    }


}