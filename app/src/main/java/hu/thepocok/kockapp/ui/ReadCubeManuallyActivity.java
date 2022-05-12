package hu.thepocok.kockapp.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import hu.thepocok.kockapp.R;
import hu.thepocok.kockapp.model.cube.Cube;
import hu.thepocok.kockapp.model.cube.CubeThree;
import hu.thepocok.kockapp.model.cube.CubeTwo;
import hu.thepocok.kockapp.model.cube.component.Color;
import hu.thepocok.kockapp.model.cube.component.Face;
import hu.thepocok.kockapp.model.cube.component.Layer;
import hu.thepocok.kockapp.model.exception.UnsolvableCubeException;

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

    private CubeFacePreviewView facePreviewView;
    private Color currentFaceToSet;

    private Face whiteFace = null;
    private Face redFace = null;
    private Face greenFace = null;
    private Face orangeFace = null;
    private Face blueFace = null;
    private Face yellowFace = null;

    private Cube cube;
    private int dimensions;

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

        facePreviewView = findViewById(R.id.cube_face_preview_view);

        whiteBtn.setOnClickListener(l -> selectColor(Color.WHITE));
        redBtn.setOnClickListener(l -> selectColor(Color.RED));
        greenBtn.setOnClickListener(l -> selectColor(Color.GREEN));
        orangeBtn.setOnClickListener(l -> selectColor(Color.ORANGE));
        blueBtn.setOnClickListener(l -> selectColor(Color.BLUE));
        yellowBtn.setOnClickListener(l -> selectColor(Color.YELLOW));

        Button nextButton = findViewById(R.id.next_face_button);

        nextButton.setOnClickListener(l -> setFace());

        facePreviewView.setOnTouchListener((view, click) -> {
            Color clickedFace = facePreviewView.getClickedFace(click.getX(), click.getY());
            Log.d(TAG, "Clicked color: " + clickedFace);
            currentFaceToSet = clickedFace;
            resetCubeContainer(clickedFace);
            return false;
        });
        currentFaceToSet = Color.WHITE;

        Intent intent = getIntent();
        dimensions = intent.getIntExtra("cubeDimensions", 0);
        facePreviewView.setCubeDimensions(dimensions);

        whiteFace = (Face) intent.getSerializableExtra("whiteFace");
        redFace = (Face) intent.getSerializableExtra("redFace");
        greenFace = (Face) intent.getSerializableExtra("greenFace");
        orangeFace = (Face) intent.getSerializableExtra("orangeFace");
        blueFace = (Face) intent.getSerializableExtra("blueFace");
        yellowFace = (Face) intent.getSerializableExtra("yellowFace");

        tileColors = new ArrayList<>();

        if (whiteFace != null) {
            tileColors.addAll(whiteFace.getAllColors());
        } else {
            for (int i = 0; i < dimensions * dimensions; i++) {
                tileColors.add(Color.WHITE);
            }
        }

        cubeContainer.post(this::setTiles);
    }

    private void resetCubeContainer(Color color) {
        Face face = null;
        switch (currentFaceToSet) {
            case WHITE:
                face = whiteFace;
                break;
            case RED:
                face = redFace;
                break;
            case GREEN:
                face = greenFace;
                break;
            case ORANGE:
                face = orangeFace;
                break;
            case BLUE:
                face = blueFace;
                break;
            case YELLOW:
                face = yellowFace;
                break;
        }

        tileColors.clear();
        if (face != null) {
            tileColors.addAll(face.getAllColors());
        } else {
            for (int i = 0; i < dimensions * dimensions; i++) {
                tileColors.add(color);
            }
        }
        setTiles();
    }

    private void setTiles() {
        Resources res = getResources();
        for (int i = 0; i < dimensions; i++) {
            LinearLayout row = (LinearLayout) cubeContainer.getChildAt(i);
            row.setMinimumHeight(cubeContainer.getHeight() / 3);
            Log.d(TAG, String.valueOf(row.getHeight()));
            row.removeAllViews();
            for (int j = 0; j < dimensions; j++) {
                Button tile = new Button(this);
                Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.cube_tile, getTheme());
                Color color = tileColors.get(i * dimensions + j);
                if (color != Color.WHITE) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        drawable.setColorFilter(new BlendModeColorFilter(
                                android.graphics.Color.rgb(color.redValue, color.greenValue, color.blueValue),
                                BlendMode.SRC_ATOP));
                    } else {
                        drawable.setColorFilter(android.graphics.Color.rgb(color.redValue, color.greenValue, color.blueValue),
                                PorterDuff.Mode.SRC_ATOP);
                    }
                }

                tile.setBackground(drawable);
                tile.setLayoutParams(new LinearLayout.LayoutParams(row.getHeight(), row.getHeight()));

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
                    tileColors.set(finalI * dimensions + finalJ, selectedColor);
                });

                row.addView(tile);
            }
            row.setGravity(Gravity.CENTER);
        }
    }

    private void setFace() {
        ArrayList<Color> colors = (ArrayList<Color>) tileColors.clone();
        Face face;
        if (dimensions == 2) {
            Layer firstLayer = new Layer(colors.get(0), colors.get(1));
            Layer secondLayer = new Layer(colors.get(2), colors.get(3));
            face = new Face(firstLayer, secondLayer);
        } else {
            Layer firstLayer = new Layer(colors.get(0), colors.get(1), colors.get(2));
            Layer secondLayer = new Layer(colors.get(3), colors.get(4), colors.get(5));
            Layer thirdLayer = new Layer(colors.get(6), colors.get(7), colors.get(8));
            face = new Face(firstLayer, secondLayer, thirdLayer);
        }

        if (currentFaceToSet.equals(Color.WHITE)) {
            whiteFace = face;
            Log.d(TAG, "Colors assigned to white face");
            currentFaceToSet = Color.RED;
            resetCubeContainer(Color.RED);
        } else if (currentFaceToSet.equals(Color.RED)) {
            redFace = face;
            Log.d(TAG, "Colors assigned to red face");
            currentFaceToSet = Color.GREEN;
            resetCubeContainer(Color.GREEN);
        } else if (currentFaceToSet.equals(Color.GREEN)) {
            greenFace = face;
            Log.d(TAG, "Colors assigned to green face");
            currentFaceToSet = Color.ORANGE;
            resetCubeContainer(Color.ORANGE);
        } else if (currentFaceToSet.equals(Color.ORANGE)) {
            orangeFace = face;
            Log.d(TAG, "Colors assigned to orange face");
            currentFaceToSet = Color.BLUE;
            resetCubeContainer(Color.BLUE);
        } else if (currentFaceToSet.equals(Color.BLUE)) {
            blueFace = face;
            Log.d(TAG, "Colors assigned to blue face");
            currentFaceToSet = Color.YELLOW;
            resetCubeContainer(Color.YELLOW);
        } else if (currentFaceToSet.equals(Color.YELLOW)) {
            yellowFace = face;
            Log.d(TAG, "Colors assigned to yellow face");
            if (allFacesSet()) {
                currentFaceToSet = Color.EMPTY;
            } else {
                currentFaceToSet = Color.WHITE;
                resetCubeContainer(Color.WHITE);
            }
        }

        if (currentFaceToSet.equals(Color.EMPTY)) {
            if (dimensions == 2) {
                cube = new CubeTwo(whiteFace, redFace, greenFace, orangeFace, blueFace, yellowFace);
            } else {
                cube = new CubeThree(whiteFace, redFace, greenFace, orangeFace, blueFace, yellowFace);
            }

            Log.d(TAG, "Cube created");
            Log.d(TAG, cube.toString());

            checkCubeValidity();

            //TODO link to new activity
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

    private void checkCubeValidity() {
        if (!cube.isValidCube()) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Invalid cube")
                    .setMessage("Do you want to review the faces or reread the whole cube?")
                    .setPositiveButton("Review", (dialogInterface, i) -> {

                    })
                    .setNegativeButton("Reread manually", (dialogInterface, i) -> {
                        resetCube();
                        resetCubeContainer(Color.WHITE);
                    })
                    .setNeutralButton("Reread by camera", (dialogInterface, i) -> {
                        Intent intent = new Intent(ReadCubeManuallyActivity.this, ReadCubeFromCameraActivity.class);
                        startActivity(intent);
                    })
                    .show();
        } else {
            try {
                cube.solve();
            } catch (UnsolvableCubeException e) {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Unsolvable cube")
                        .setMessage("Unfortunately, we cannot solve this cube!")
                        .setPositiveButton("Review", (dialogInterface, i) -> {

                        })
                        .setNegativeButton("Reread manually", (dialogInterface, i) -> {
                            resetCube();
                            resetCubeContainer(Color.WHITE);
                        })
                        .setNeutralButton("Reread by camera", (dialogInterface, i) -> {
                            Intent intent = new Intent(ReadCubeManuallyActivity.this, ReadCubeFromCameraActivity.class);
                            startActivity(intent);
                        })
                        .show();
            }
            Log.d(TAG, cube.getSolutionString());
        }
    }

    private void resetCube() {
        currentFaceToSet = Color.WHITE;
        whiteFace = null;
        redFace = null;
        greenFace = null;
        orangeFace = null;
        blueFace = null;
        yellowFace = null;
        cube = null;

        setTiles();
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