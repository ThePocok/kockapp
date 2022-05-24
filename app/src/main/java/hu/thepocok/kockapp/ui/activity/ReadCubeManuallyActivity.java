package hu.thepocok.kockapp.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import hu.thepocok.kockapp.ui.view.CubeFacePreviewView;
import pl.droidsonroids.gif.GifImageView;

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
    private GifImageView gifOverlay;

    private Button prevBtn;
    private Button nextBtn;
    private CubeFacePreviewView facePreviewView;
    private Color previousFace;
    private Color currentFaceToSet;
    private Color nextFace;

    private Face whiteFace = null;
    private Face redFace = null;
    private Face greenFace = null;
    private Face orangeFace = null;
    private Face blueFace = null;
    private Face yellowFace = null;

    private Cube cube;
    private Cube solvedCube;
    private int dimensions;

    private ArrayList<Color> tileColors;

    @SuppressLint("ClickableViewAccessibility")
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
        gifOverlay = findViewById(R.id.gif_overlay);

        facePreviewView = findViewById(R.id.cube_face_preview_view);

        whiteBtn.setOnClickListener(l -> selectColor(Color.WHITE));
        redBtn.setOnClickListener(l -> selectColor(Color.RED));
        greenBtn.setOnClickListener(l -> selectColor(Color.GREEN));
        orangeBtn.setOnClickListener(l -> selectColor(Color.ORANGE));
        blueBtn.setOnClickListener(l -> selectColor(Color.BLUE));
        yellowBtn.setOnClickListener(l -> selectColor(Color.YELLOW));

        previousFace = Color.YELLOW;
        currentFaceToSet = Color.WHITE;
        nextFace = Color.RED;

        prevBtn = findViewById(R.id.prev_face_button);
        prevBtn.setOnClickListener(l -> setFace(true, false, true));

        nextBtn = findViewById(R.id.next_face_button);
        nextBtn.setOnClickListener(l -> setFace(false, true, true));
        setPrevAndNextButtonColors();

        facePreviewView.setOnTouchListener((view, click) -> {
            Color clickedFace = facePreviewView.getClickedFace(click.getX(), click.getY());
            Log.d(TAG, "Clicked color: " + clickedFace);
            setFace(false, false, false);
            setFaceColorVariables(clickedFace);
            setPrevAndNextButtonColors();
            resetCubeContainer(clickedFace);
            return false;
        });

        Intent intent = getIntent();
        dimensions = intent.getIntExtra("cubeDimensions", 0);
        facePreviewView.setCubeDimensions(dimensions);

        whiteFace = (Face) intent.getSerializableExtra("whiteFace");
        redFace = (Face) intent.getSerializableExtra("redFace");
        greenFace = (Face) intent.getSerializableExtra("greenFace");
        orangeFace = (Face) intent.getSerializableExtra("orangeFace");
        blueFace = (Face) intent.getSerializableExtra("blueFace");
        yellowFace = (Face) intent.getSerializableExtra("yellowFace");
        facePreviewView.setAllFaces(whiteFace, redFace, greenFace, orangeFace, blueFace, yellowFace);

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

    private void setPrevAndNextButtonColors() {
        prevBtn.setBackgroundColor(android.graphics.Color.rgb(previousFace.redValue, previousFace.greenValue, previousFace.blueValue));
        nextBtn.setBackgroundColor(android.graphics.Color.rgb(nextFace.redValue, nextFace.greenValue, nextFace.blueValue));
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
            row.removeAllViews();

            for (int j = 0; j < dimensions; j++) {
                Button tile = new Button(this);
                Color color = tileColors.get(i * dimensions + j);

                Drawable drawable;
                switch (color) {
                    case WHITE:
                        drawable = ResourcesCompat.getDrawable(res, R.drawable.cube_tile_white, getTheme());
                        break;
                    case RED:
                        drawable = ResourcesCompat.getDrawable(res, R.drawable.cube_tile_red, getTheme());
                        break;
                    case GREEN:
                        drawable = ResourcesCompat.getDrawable(res, R.drawable.cube_tile_green, getTheme());
                        break;
                    case ORANGE:
                        drawable = ResourcesCompat.getDrawable(res, R.drawable.cube_tile_orange, getTheme());
                        break;
                    case BLUE:
                        drawable = ResourcesCompat.getDrawable(res, R.drawable.cube_tile_blue, getTheme());
                        break;
                    case YELLOW:
                        drawable = ResourcesCompat.getDrawable(res, R.drawable.cube_tile_yellow, getTheme());
                        break;
                    default:
                        drawable = ResourcesCompat.getDrawable(res, R.drawable.cube_tile_empty, getTheme());
                        break;
                }

                tile.setBackground(drawable);
                tile.setLayoutParams(new LinearLayout.LayoutParams(row.getHeight(), row.getHeight()));

                int finalI = i;
                int finalJ = j;
                tile.setOnClickListener(l -> {
                    Drawable d;
                    switch (selectedColor) {
                        case WHITE:
                            d = ResourcesCompat.getDrawable(res, R.drawable.cube_tile_white, getTheme());
                            break;
                        case RED:
                            d = ResourcesCompat.getDrawable(res, R.drawable.cube_tile_red, getTheme());
                            break;
                        case GREEN:
                            d = ResourcesCompat.getDrawable(res, R.drawable.cube_tile_green, getTheme());
                            break;
                        case ORANGE:
                            d = ResourcesCompat.getDrawable(res, R.drawable.cube_tile_orange, getTheme());
                            break;
                        case BLUE:
                            d = ResourcesCompat.getDrawable(res, R.drawable.cube_tile_blue, getTheme());
                            break;
                        case YELLOW:
                            d = ResourcesCompat.getDrawable(res, R.drawable.cube_tile_yellow, getTheme());
                            break;
                        default:
                            d = ResourcesCompat.getDrawable(res, R.drawable.cube_tile_empty, getTheme());
                            break;
                    }

                    l.setBackground(d);
                    tileColors.set(finalI * dimensions + finalJ, selectedColor);
                });

                row.addView(tile);
            }
            row.setGravity(Gravity.CENTER);
        }
    }

    private void setFace(boolean reverse, boolean shouldOpenSolution, boolean shouldShowArrow) {
        ArrayList<Color> colors = (ArrayList<Color>) tileColors.clone();
        Face face;
        if (dimensions == 2) {
            Layer firstLayer = new Layer(colors.get(0), colors.get(1));
            Layer secondLayer = new Layer(colors.get(2), colors.get(3));
            face = new Face(currentFaceToSet, firstLayer, secondLayer);
        } else {
            Layer firstLayer = new Layer(colors.get(0), colors.get(1), colors.get(2));
            Layer secondLayer = new Layer(colors.get(3), colors.get(4), colors.get(5));
            Layer thirdLayer = new Layer(colors.get(6), colors.get(7), colors.get(8));
            face = new Face(currentFaceToSet, firstLayer, secondLayer, thirdLayer);
        }

        if (currentFaceToSet.equals(Color.WHITE)) {
            whiteFace = face;
            Log.d(TAG, "Colors assigned to white face");
            facePreviewView.setFace(Color.WHITE, whiteFace);

            if (reverse) {
                setFaceColorVariables(Color.YELLOW);
            } else {
                setFaceColorVariables(Color.RED);
            }

            if (shouldShowArrow) {
                displayArrowGif(180);
            }
        } else if (currentFaceToSet.equals(Color.RED)) {
            redFace = face;
            Log.d(TAG, "Colors assigned to red face");
            facePreviewView.setFace(Color.RED, redFace);

            if (reverse) {
                setFaceColorVariables(Color.WHITE);
            } else {
                setFaceColorVariables(Color.GREEN);
            }

            if (shouldShowArrow) {
                displayArrowGif(270);
            }
        } else if (currentFaceToSet.equals(Color.GREEN)) {
            greenFace = face;
            Log.d(TAG, "Colors assigned to green face");
            facePreviewView.setFace(Color.GREEN, greenFace);

            if (reverse) {
                setFaceColorVariables(Color.RED);
            } else {
                setFaceColorVariables(Color.ORANGE);
            }

            if (shouldShowArrow) {
                displayArrowGif(270);
            }
        } else if (currentFaceToSet.equals(Color.ORANGE)) {
            orangeFace = face;
            Log.d(TAG, "Colors assigned to orange face");
            facePreviewView.setFace(Color.ORANGE, orangeFace);

            if (reverse) {
                setFaceColorVariables(Color.GREEN);
            } else {
                setFaceColorVariables(Color.BLUE);
            }


            if (shouldShowArrow) {
                displayArrowGif(270);
            }
        } else if (currentFaceToSet.equals(Color.BLUE)) {
            blueFace = face;
            Log.d(TAG, "Colors assigned to blue face");
            facePreviewView.setFace(Color.BLUE, blueFace);

            if (reverse) {
                setFaceColorVariables(Color.ORANGE);
            } else {
                setFaceColorVariables(Color.YELLOW);
            }

            if (shouldShowArrow) {
                displayTwoArrowGifs(270, 180);
            }
        } else if (currentFaceToSet.equals(Color.YELLOW)) {
            yellowFace = face;
            Log.d(TAG, "Colors assigned to yellow face");
            facePreviewView.setFace(Color.YELLOW, yellowFace);

            if (allFacesSet() && !reverse) {
                currentFaceToSet = Color.EMPTY;
            } else {
                if (reverse) {
                    setFaceColorVariables(Color.BLUE);
                } else {
                    setFaceColorVariables(Color.WHITE);
                }
            }
        }

        setPrevAndNextButtonColors();

        if (currentFaceToSet.equals(Color.EMPTY) && shouldOpenSolution) {
            if (dimensions == 2) {
                cube = new CubeTwo(whiteFace, redFace, greenFace, orangeFace, blueFace, yellowFace);
            } else {
                cube = new CubeThree(whiteFace, redFace, greenFace, orangeFace, blueFace, yellowFace);
            }

            Log.d(TAG, "Cube created");
            Log.d(TAG, cube.toString());

            if (checkCubeValidity()) {
                Intent intent = new Intent(this, CubeSolutionActivity.class);
                intent.putExtra("cube", cube);
                intent.putExtra("solvedCube", solvedCube);
                startActivity(intent);
            }
        }
    }

    private void setFaceColorVariables(Color currentFace) {
        ArrayList<Color> faces = getPreviousAndNextFaces(currentFace);
        this.previousFace = faces.get(0);
        this.currentFaceToSet = faces.get(1);
        this.nextFace = faces.get(2);

        resetCubeContainer(faces.get(1));
    }

    private boolean allFacesSet() {
        return whiteFace != null
                && redFace != null
                && greenFace != null
                && orangeFace != null
                && blueFace != null
                && yellowFace != null;
    }

    private boolean checkCubeValidity() {
        if (!cube.isValidCube()) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Invalid cube")
                    .setMessage("Do you want to review the faces or reread the whole cube?")
                    .setPositiveButton("Review", (dialogInterface, i) -> {
                        currentFaceToSet = Color.YELLOW;
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

            return false;
        } else {
            try {
                solvedCube = cube.duplicate();
                solvedCube.solve();
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
                return false;
            }
            Log.d(TAG, solvedCube.getSolutionString());
        }

        return true;
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
        tileColors = new ArrayList<>();
        for (int i = 0; i < dimensions * dimensions; i++) {
            tileColors.add(Color.WHITE);
        }

        previousFace = Color.YELLOW;
        currentFaceToSet = Color.WHITE;
        nextFace = Color.RED;
        setPrevAndNextButtonColors();

        setTiles();
        facePreviewView.clearFaces();
    }

    private ArrayList<Color> getPreviousAndNextFaces(Color color) {
        ArrayList<Color> colors = new ArrayList<>();

        switch (color) {
            case WHITE:
                colors.add(Color.YELLOW);
                colors.add(Color.WHITE);
                colors.add(Color.RED);
                break;
            case RED:
                colors.add(Color.WHITE);
                colors.add(Color.RED);
                colors.add(Color.GREEN);
                break;
            case GREEN:
                colors.add(Color.RED);
                colors.add(Color.GREEN);
                colors.add(Color.ORANGE);
                break;
            case ORANGE:
                colors.add(Color.GREEN);
                colors.add(Color.ORANGE);
                colors.add(Color.BLUE);
                break;
            case BLUE:
                colors.add(Color.ORANGE);
                colors.add(Color.BLUE);
                colors.add(Color.YELLOW);
                break;
            case YELLOW:
                colors.add(Color.BLUE);
                colors.add(Color.YELLOW);
                colors.add(Color.WHITE);
                break;
        }

        return colors;
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

    public void displayArrowGif(int rotationDegree) {
        Thread gifOverlayThread = new Thread(() -> {
            runOnUiThread(() -> {
                gifOverlay.bringToFront();
                gifOverlay.setRotation(rotationDegree);
                gifOverlay.setImageResource(R.drawable.arrow);
                gifOverlay.setColorFilter(android.graphics.Color.BLACK);
            });

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(() -> {
                cubeContainer.bringToFront();
                gifOverlay.setImageResource(0);
            });
        });
        gifOverlayThread.start();
    }

    public void displayTwoArrowGifs(int rotationDegree1, int rotationDegree2) {
        Thread gifThreadHandler = new Thread(() -> {
            displayArrowGif(rotationDegree1);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            displayArrowGif(rotationDegree2);
        });
        gifThreadHandler.start();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        Intent intent = getIntent();
        if (intent.getIntExtra("comingFromCamera", 0) == 0) {
            resetCube();
        }
    }
}