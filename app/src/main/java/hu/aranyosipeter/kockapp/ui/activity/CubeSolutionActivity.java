package hu.aranyosipeter.kockapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.stream.Collectors;

import hu.aranyosipeter.kockapp.R;
import hu.aranyosipeter.kockapp.model.cube.Cube;
import hu.aranyosipeter.kockapp.model.cube.component.Color;
import hu.aranyosipeter.kockapp.model.cube.component.Layer;
import hu.aranyosipeter.kockapp.model.cube.util.Orientation;
import hu.aranyosipeter.kockapp.model.exception.InvalidOrientationException;
import hu.aranyosipeter.kockapp.model.exception.UnsolvableCubeException;
import hu.aranyosipeter.kockapp.model.move.Move;
import hu.aranyosipeter.kockapp.model.move.Reorientation;
import hu.aranyosipeter.kockapp.model.move.Rotation;

public class CubeSolutionActivity extends AppCompatActivity {
    public final String TAG = "CubeSolutionActivity";
    private WebView webView;
    private TextView stageName;
    private LinearLayout moves;

    private Cube cube;
    private Cube solvedCube;

    private int currentSection = 0;

    private int currentMoveInSection = 0;
    private int maxMovesInSection = 0;
    private boolean shouldBeReset = false;

    private final String[] cubeThreeStageNames = new String[] {
            "white_cross_yellow_face",
            "white_cross_white_face",
            "white_edges",
            "middle_layer",
            "yellow_cross_yellow_face",
            "yellow_edges",
            "reposition_yellow_edges",
            "reposition_yellow_middles"
    };

    private final String[] cubeTwoStageNames = new String[] {
            "white_red_blue",
            "white_orange_green",
            "white_orange_green",
            "white_red_green",
            "yellow_tiles_upwards",
            "swap_incorrect",
    };

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cube_solution);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        cube = (Cube) getIntent().getSerializableExtra("cube");
        solvedCube = (Cube) getIntent().getSerializableExtra("solvedCube");

        stageName = findViewById(R.id.stage_name);
        if (cube.getDimensions() == 2) {
            stageName.setText(getResources().getIdentifier(cubeTwoStageNames[currentSection], "string", getPackageName()));
        } else if (cube.getDimensions() == 3) {
            stageName.setText(getResources().getIdentifier(cubeThreeStageNames[currentSection], "string", getPackageName()));
        }

        moves = findViewById(R.id.moves);

        Button prevStageBtn = findViewById(R.id.prev_stage);
        prevStageBtn.setOnClickListener(this::previousStage);
        Button nextStageBtn = findViewById(R.id.next_stage);
        nextStageBtn.setOnClickListener(this::nextStage);

        Button playBtn = findViewById(R.id.play_button);
        playBtn.setOnClickListener(this::animateSteps);

        Button prevStepBtn = findViewById(R.id.previous_step_button);
        prevStepBtn.setOnClickListener(this::previousStep);

        Button nextStepBtn = findViewById(R.id.next_step_button);
        nextStepBtn.setOnClickListener(this::nextStep);

        webView = findViewById(R.id.cube_model);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("WebView", consoleMessage.message());
                return true;
            }
        });

        webView.setOnTouchListener((view, motionEvent) -> false);

        loadHtml(0);

        moves.post(() -> fillUpViewWithMoves(currentSection));
    }

    private void previousStage(View view) {
        if (currentSection == 0) {
            return;
        }

        currentSection--;
        loadHtml(currentSection);
        fillUpViewWithMoves(currentSection);
        if (cube.getDimensions() == 2) {
            stageName.setText(getResources().getIdentifier(cubeTwoStageNames[solvedCube.getIDFromSection(currentSection)],
                    "string", getPackageName()));
        } else if (cube.getDimensions() == 3) {
            stageName.setText(getResources().getIdentifier(cubeThreeStageNames[solvedCube.getIDFromSection(currentSection)],
                    "string", getPackageName()));
        }
    }

    private void nextStage(View view) {
        if (currentSection == solvedCube.getSectionCount() - 1) {
            return;
        }

        currentSection++;
        loadHtml(currentSection);
        fillUpViewWithMoves(currentSection);
        if (cube.getDimensions() == 2) {
            stageName.setText(getResources().getIdentifier(cubeTwoStageNames[solvedCube.getIDFromSection(currentSection)],
                    "string", getPackageName()));
        } else if (cube.getDimensions() == 3) {
            stageName.setText(getResources().getIdentifier(cubeThreeStageNames[solvedCube.getIDFromSection(currentSection)],
                    "string", getPackageName()));
        }
    }

    private void animateSteps(View view) {
        resetCube();
        webView.evaluateJavascript("clickPlayButton()", null);
        shouldBeReset = true;
    }

    private void resetCube() {
        webView.evaluateJavascript("clickResetButton()", null);
        shouldBeReset = false;
        currentMoveInSection = 0;
    }

    public void nextStep(View view) {
        if (shouldBeReset) {
            resetCube();
        }

        if (currentMoveInSection == maxMovesInSection) {
            nextStage(null);
            return;
        }

        currentMoveInSection++;
        webView.evaluateJavascript("clickNextStepButton()", null);
    }

    public void previousStep(View view) {
        if (shouldBeReset) {
            resetCube();
        }

        if (currentMoveInSection == 0) {
            previousStage(null);
            return;
        }

        currentMoveInSection--;
        webView.evaluateJavascript("clickPreviousStepButton()", null);
    }

    private void loadHtml(int section) {
         String htmlData = "<html>\n" +
                "    <script src=\"AnimCube" + cube.getDimensions() + ".js\"></script>\n" +
                "    <script src=\"controls.js\"></script>\n" +
                "\n" +
                "    <body>\n" +
                "        <div id=\"cube\" style=\"width: 100%; height: 100%;\">\n" +
                "            <script> AnimCube" + cube.getDimensions() + "(\"" + getParametersForAnimCube(section) + "\") </script>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>";

        Log.d(TAG, htmlData);
        webView.loadDataWithBaseURL("file:///android_asset/js/", htmlData, "text/html; charset=utf-8", "UTF-8", null);
    }

    private String getParametersForAnimCube(int section) {
        StringBuilder sb = new StringBuilder();

        sb.append("edit=0&");
        sb.append("speed=20&");
        sb.append("bgcolor=ffffff&");
        sb.append("position=llluuuuuu&");
        sb.append("colors=ffffffb71234009b480046adff5800ffd500&");
        try {
            sb.append("facelets=" + mapCubeToFaceletString(section) + "&");
        } catch (InvalidOrientationException e) {
            // This should never happen
        }
        sb.append("move=" + mapCubeSolutionToAnimCubeMoves(section).replace(" ", " . ") + "&");

        return sb.toString();
    }

    private String mapCubeSolutionToAnimCubeMoves(int section) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Move> solutionSection = solvedCube.getSolutionSection(section);

        for (int i = 0; i < solutionSection.size() - 1; i++) {
            try {
                sb.append(mapCubeNotationToAnimCubeNotation(solutionSection.get(i)));
            } catch (InvalidOrientationException e) {
                e.printStackTrace(); // This will never occur
            }
            sb.append(" ");
        }

        try {
            sb.append(mapCubeNotationToAnimCubeNotation(solutionSection.get(solutionSection.size() - 1)));
        } catch (InvalidOrientationException e) {
            e.printStackTrace(); // This will never occur
        } catch (ArrayIndexOutOfBoundsException e) {
            // This will only occur if the solution string is empty
            // This case nothing should be done
        }

        return sb.toString();
    }

    private void fillUpViewWithMoves(int section) {
        final int movesPerRow = 7;

        moves.removeAllViews();

        int rowHeight = Math.min((moves.getMeasuredHeight() - movesPerRow * 14) / 4, (moves.getMeasuredWidth() - movesPerRow * 14) / movesPerRow);
        String[] solutionSection = mapCubeSolutionToAnimCubeMoves(section).split(" ");

        currentMoveInSection = 0;
        maxMovesInSection = solutionSection.length;

        int rowCount = solutionSection.length / movesPerRow;
        if (solutionSection.length % movesPerRow != 0) {
            rowCount++;
        }

        for (int i = 0; i < rowCount; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.setMinimumHeight(rowHeight);

            for (int j = i * movesPerRow; j < Math.min((i + 1 ) * movesPerRow, solutionSection.length); j++) {
                ImageView imageView = new ImageView(this);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setAdjustViewBounds(true);
                imageView.setMaxHeight(rowHeight);
                imageView.setMaxWidth(rowHeight);
                imageView.setPadding(7, 7, 7, 7);


                // F - green face clockwise
                // U - white face clockwise
                // R - red face clockwise
                // L - orange face clockwise
                // B - blue face clockwise
                // D - yellow face clockwise
                switch (solutionSection[j]) {
                    case "U":
                        imageView.setImageResource(R.drawable.turn_u);
                        break;
                    case "U'":
                        imageView.setImageResource(R.drawable.turn_u_);
                        break;
                    case "F":
                        imageView.setImageResource(R.drawable.turn_l);
                        break;
                    case "F'":
                        imageView.setImageResource(R.drawable.turn_l_);
                        break;
                    case "R":
                        imageView.setImageResource(R.drawable.turn_f);
                        break;
                    case "R'":
                        imageView.setImageResource(R.drawable.turn_f_);
                        break;
                    case "B":
                        imageView.setImageResource(R.drawable.turn_r);
                        break;
                    case "B'":
                        imageView.setImageResource(R.drawable.turn_r_);
                        break;
                    case "L":
                        imageView.setImageResource(R.drawable.turn_b);
                        break;
                    case "L'":
                        imageView.setImageResource(R.drawable.turn_b_);
                        break;
                    case "D":
                        imageView.setImageResource(R.drawable.turn_d);
                        break;
                    case "D'":
                        imageView.setImageResource(R.drawable.turn_d_);
                        break;
                    case "X":
                        imageView.setImageResource(R.drawable.turn_x);
                        break;
                    case "X'":
                        imageView.setImageResource(R.drawable.turn_x_);
                        break;
                    case "Y":
                        imageView.setImageResource(R.drawable.turn_y);
                        break;
                    case "Y'":
                        imageView.setImageResource(R.drawable.turn_y_);
                        break;
                    case "Z":
                        imageView.setImageResource(R.drawable.turn_z);
                        break;
                    case "Z'":
                        imageView.setImageResource(R.drawable.turn_z_);
                        break;
                }

                linearLayout.addView(imageView);
            }
            moves.addView(linearLayout);
        }
    }

    private String mapCubeNotationToAnimCubeNotation(Move move) throws InvalidOrientationException {
        if (move instanceof Rotation) {
            String key = ((Rotation) move).getKey();

            // F - green face clockwise
            // U - white face clockwise
            // R - red face clockwise
            // L - orange face clockwise
            // B - blue face clockwise
            // D - yellow face clockwise
            switch (key) {
                case "F":
                    return "R";
                case "F'":
                    return "R'";
                case "R":
                    return "B";
                case "R'":
                    return "B'";
                case "L":
                    return "F";
                case "L'":
                    return "F'";
                case "B":
                    return "L";
                case "B'":
                    return "L'";
                default:
                    return key;
            }
        }

        if (move instanceof Reorientation) {
            Orientation currentOrientation = ((Reorientation) move).getPreviousOrientation().duplicate();
            Orientation targetOrientation = ((Reorientation) move).getOrientation();

            ArrayList<String> rotations = new ArrayList<>();

            while (! currentOrientation.getFaceFront().equals(targetOrientation.getFaceUp()) &&
                    ! currentOrientation.getFaceFront().equals(targetOrientation.getFaceFront())) {
                rotations.add("Z'");
                currentOrientation.setOrientation(currentOrientation.getFaceUp(), currentOrientation.getFaceLeft());
            }

            if (currentOrientation.getFaceFront().equals(targetOrientation.getFaceFront())) {
                if (targetOrientation.getFaceUp().equals(currentOrientation.getFaceLeft())) {
                    rotations.add("X");
                } else if (targetOrientation.getFaceUp().equals(currentOrientation.getFaceRight())) {
                    rotations.add("X'");
                } else if (targetOrientation.getFaceUp().equals(currentOrientation.getFaceDown())) {
                    rotations.add("X");
                    rotations.add("X");
                }
            } else {
                rotations.add("Y'");
                currentOrientation.setOrientation(currentOrientation.getFaceFront(), currentOrientation.getFaceDown());

                while (!currentOrientation.getFaceFront().equals(targetOrientation.getFaceFront())) {
                    rotations.add("Z'");
                    currentOrientation.setOrientation(currentOrientation.getFaceUp(), currentOrientation.getFaceLeft());
                }
            }

            for (int i = 0; i < rotations.size() - 2; i++) {
                if (rotations.get(i).equals(rotations.get(i+1)) && rotations.get(i+1).equals(rotations.get(i+2))) {
                    if (rotations.get(i).endsWith("'")) {
                        rotations.set(i, rotations.get(i).substring(0, 1));
                    } else {
                        rotations.set(i, rotations.get(i) + "'");
                    }

                    rotations.remove(i+2);
                    rotations.remove(i+1);
                }
            }

            return rotations.stream().collect(Collectors.joining(" "));
        }

        return null;
    }

    private String mapCubeToFaceletString(int section) throws InvalidOrientationException{
        //TODO add reorientation to the beginning of every section
        Cube initialState = cube.duplicate();
        try {
            initialState.solveBySolutionArray(solvedCube.getSolutionBeforeSection(section));
        } catch (UnsolvableCubeException e) {
            // This will never occur
        }
        StringBuilder sb = new StringBuilder();

        // Top face
        for (int i = 0; i < initialState.getDimensions(); i++) {
            Layer layer = initialState
                    .getFaceWithCurrentOrientation(initialState.getOrientation().getFaceUp())
                    .getNthColumn(i);

            for (Color color : layer.getDataSet()) {
                sb.append(mapColorToFaceletString(color));
            }
        }

        // Bottom face
        for (int i = initialState.getDimensions() - 1; i >= 0; i--) {
            Layer layer = initialState
                    .getFaceWithCurrentOrientation(initialState.getOrientation().getFaceDown())
                    .getNthRow(i);

            for (Color color : layer.getDataSet()) {
                sb.append(mapColorToFaceletString(color));
            }
        }

        // Left face
        for (int i = 0; i < initialState.getDimensions(); i++) {
            Layer layer = initialState
                    .getFaceWithCurrentOrientation(initialState.getOrientation().getFaceLeft())
                    .getNthColumn(i);

            for (Color color : layer.getDataSet()) {
                sb.append(mapColorToFaceletString(color));
            }
        }

        // Right face
        for (int i = 0; i < initialState.getDimensions(); i++) {
            Layer layer = initialState
                    .getFaceWithCurrentOrientation(initialState.getOrientation().getFaceRight())
                    .getNthColumn(i);

            for (Color color : layer.getDataSet()) {
                sb.append(mapColorToFaceletString(color));
            }
        }

        // Back face
        for (int i = 0; i < initialState.getDimensions(); i++) {
            Layer layer = initialState
                    .getFaceWithCurrentOrientation(initialState.getOrientation().getFaceBack())
                    .getNthRow(i)
                    .reverse();

            for (Color color : layer.getDataSet()) {
                sb.append(mapColorToFaceletString(color));
            }
        }

        // Front face
        for (int i = 0; i < initialState.getDimensions(); i++) {
            Layer layer = initialState
                    .getFaceWithCurrentOrientation(initialState.getOrientation().getFaceFront())
                    .getNthColumn(i);

            for (Color color : layer.getDataSet()) {
                sb.append(mapColorToFaceletString(color));
            }
        }
        return sb.toString();
    }

    private char mapColorToFaceletString(Color color) {
        switch (color) {
            case WHITE:
                return '0';
            case RED:
                return '1';
            case GREEN:
                return '2';
            case ORANGE:
                return '4';
            case BLUE:
                return '3';
            case YELLOW:
                return '5';
        }

        return '_';
    }
}