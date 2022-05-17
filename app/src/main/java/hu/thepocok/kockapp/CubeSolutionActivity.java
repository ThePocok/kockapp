package hu.thepocok.kockapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.util.ArrayList;

import hu.thepocok.kockapp.model.cube.Cube;
import hu.thepocok.kockapp.model.cube.component.Color;
import hu.thepocok.kockapp.model.cube.component.Layer;
import hu.thepocok.kockapp.model.cube.util.Orientation;
import hu.thepocok.kockapp.model.exception.InvalidOrientationException;
import hu.thepocok.kockapp.model.exception.UnsolvableCubeException;
import hu.thepocok.kockapp.model.move.Move;
import hu.thepocok.kockapp.model.move.Reorientation;
import hu.thepocok.kockapp.model.move.Rotation;

public class CubeSolutionActivity extends AppCompatActivity {
    public final String TAG = "CubeSolutionActivity";
    private WebView webView;

    private Cube cube;
    private Cube solvedCube;

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cube_solution);

        cube = (Cube) getIntent().getSerializableExtra("cube");
        solvedCube = (Cube) getIntent().getSerializableExtra("solvedCube");

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

        Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(this::nextStep);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(this::previousStep);
        });

        //t.start();

    }

    public void nextStep() {
        webView.evaluateJavascript("clickNextStepButton()", null);
    }

    public void previousStep() {
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
        sb.append("bgcolor=ffffff&");
        sb.append("position=llluuuuuuu&");
        // 0w 1r 2g 3o 4b 5y
        sb.append("colors=ffffffb71234009b480046adff5800ffd500&");
        sb.append("facelets=" + mapCubeToFaceletString(section) + "&");
        // F - green face clockwise
        // U - white face clockwise
        // R - red face clockwise
        // L - orange face clockwise
        // B - blue face clockwise
        // D - yellow face clockwise
        sb.append("move=" + mapCubeSolutionToAnimCubeMoves(section) + "&");

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

    private String mapCubeNotationToAnimCubeNotation(Move move) throws InvalidOrientationException {
        if (move instanceof Rotation) {
            String key = ((Rotation) move).getKey();

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
            Orientation previousOrientation = ((Reorientation) move).getPreviousOrientation().duplicate();
            Orientation orientation = ((Reorientation) move).getOrientation();

            StringBuilder sb = new StringBuilder();

            if (previousOrientation.getFaceUp().equals(orientation.getOppositeColor(orientation.getFaceUp()))) {
                sb.append("Y Y ");
                previousOrientation.setOrientation(previousOrientation.getFaceDown(), previousOrientation.getFaceBack());
            } else if (! previousOrientation.getFaceUp().equals(orientation.getFaceUp())) {
                if (previousOrientation.getFaceUp().equals(orientation.getFaceFront())) {
                    sb.append("Y' ");
                    previousOrientation.setOrientation(previousOrientation.getFaceFront(), previousOrientation.getFaceDown());
                } else if (previousOrientation.getFaceUp().equals(orientation.getFaceLeft())) {
                    sb.append("X' ");
                    previousOrientation.setOrientation(previousOrientation.getFaceLeft(), previousOrientation.getFaceDown());
                } else if (previousOrientation.getFaceUp().equals(orientation.getFaceBack())) {
                    sb.append("Y ");
                    previousOrientation.setOrientation(previousOrientation.getFaceBack(), previousOrientation.getFaceDown());
                } else {
                    sb.append("X ");
                    previousOrientation.setOrientation(previousOrientation.getFaceRight(), previousOrientation.getFaceDown());
                }
            }

            if (! previousOrientation.getFaceFront().equals(orientation.getFaceFront())) {
                if (previousOrientation.getFaceFront().equals(orientation.getFaceRight())) {
                    sb.append("Z'");
                } else if (previousOrientation.getFaceFront().equals(orientation.getFaceLeft())) {
                    sb.append("Z");
                } else {
                    sb.append("Z Z");
                }
            }

            return sb.toString();
        }

        return null;
    }

    private String mapCubeToFaceletString(int section) {
        Cube initialState = cube.duplicate();
        try {
            initialState.solveBySolutionArray(solvedCube.getSolutionBeforeSection(section));
        } catch (UnsolvableCubeException e) {
            // This will never occur
        }
        StringBuilder sb = new StringBuilder();

        // White face
        for (int i = 0; i < initialState.getDimensions(); i++) {
            Layer layer = initialState.getFace(Color.WHITE).getNthColumn(i);

            for (Color color : layer.getDataSet()) {
                sb.append(mapColorToFaceletString(color));
            }
        }

        // Yellow face
        for (int i = initialState.getDimensions() - 1; i >= 0; i--) {
            Layer layer = initialState.getFace(Color.YELLOW).getNthRow(i);

            for (Color color : layer.getDataSet()) {
                sb.append(mapColorToFaceletString(color));
            }
        }

        // Green face
        for (int i = 0; i < initialState.getDimensions(); i++) {
            Layer layer = initialState.getFace(Color.GREEN).getNthColumn(i);

            for (Color color : layer.getDataSet()) {
                sb.append(mapColorToFaceletString(color));
            }
        }

        // Blue face
        for (int i = 0; i < initialState.getDimensions(); i++) {
            Layer layer = initialState.getFace(Color.BLUE).getNthColumn(i);

            for (Color color : layer.getDataSet()) {
                sb.append(mapColorToFaceletString(color));
            }
        }

        // Orange face
        for (int i = 0; i < initialState.getDimensions(); i++) {
            Layer layer = initialState.getFace(Color.ORANGE).getNthRow(i).reverse();

            for (Color color : layer.getDataSet()) {
                sb.append(mapColorToFaceletString(color));
            }
        }

        // Red face
        for (int i = 0; i < initialState.getDimensions(); i++) {
            Layer layer = initialState.getFace(Color.RED).getNthColumn(i);

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

    private void clickPlayButton() {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis() + 100;
        float x = webView.getWidth() / 2.0f;
        float y = webView.getHeight() * 0.90f;

        int metaState = 0;
        MotionEvent motionEvent = MotionEvent.obtain(
                downTime,
                eventTime,
                MotionEvent.ACTION_UP,
                x,
                y,
                metaState
        );

        webView.dispatchGenericMotionEvent(motionEvent);
        Log.d(TAG, "Clicked");
    }
}