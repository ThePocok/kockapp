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

import hu.thepocok.kockapp.model.cube.Cube;
import hu.thepocok.kockapp.model.cube.component.Color;
import hu.thepocok.kockapp.model.cube.component.Layer;
import hu.thepocok.kockapp.model.move.Move;

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

        loadHtml();

        Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            clickPlayButton();
        });

        t.start();

    }

    private void loadHtml() {
         String htmlData = "<html>\n" +
                "    <script src=\"AnimCube3.js\"></script>\n" +
                "\n" +
                "    <body>\n" +
                "        <div class=\"cube\" style=\"width: 100%; height: 100%;\">\n" +
                "            <script> AnimCube3(\"" + getParametersForAnimCube() + "\") </script>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>";

        Log.d(TAG, htmlData);
        webView.loadDataWithBaseURL("file:///android_asset/js/", htmlData, "text/html; charset=utf-8", "UTF-8", null);
    }

    private String getParametersForAnimCube() {
        StringBuilder sb = new StringBuilder();

        sb.append("edit=0&");
        sb.append("bgcolor=ffffff&");
        sb.append("position=llluuu&");
        // 0w 1r 2g 3o 4b 5y
        sb.append("colors=ffffffb71234009b480046adff5800ffd500&");
        sb.append("facelets=" + mapCubeToFaceletString());
        // F - green face clockwise
        // U - white face clockwise
        // R - red face clockwise
        // L - orange face clockwise
        // B - blue face clockwise
        // D - yellow face clockwise
        sb.append("move=F F' U U' R R' L L' B B' D D'&");

        return sb.toString();
    }

    private String mapCubeNotationToAnimCube(Move move) {

        return null;
    }

    private String mapCubeToFaceletString() {
        StringBuilder sb = new StringBuilder();

        // White face
        for (int i = 0; i < cube.getDimensions(); i++) {
            Layer layer = cube.getFace(Color.WHITE).getNthColumn(i);

            for (Color color : layer.getDataSet()) {
                sb.append(mapColorToFaceletString(color));
            }
        }

        // Yellow face
        for (int i = cube.getDimensions() - 1; i >= 0; i--) {
            Layer layer = cube.getFace(Color.YELLOW).getNthRow(i);

            for (Color color : layer.getDataSet()) {
                sb.append(mapColorToFaceletString(color));
            }
        }

        // Green face
        for (int i = 0; i < cube.getDimensions(); i++) {
            Layer layer = cube.getFace(Color.GREEN).getNthColumn(i);

            for (Color color : layer.getDataSet()) {
                sb.append(mapColorToFaceletString(color));
            }
        }

        // Blue face
        for (int i = 0; i < cube.getDimensions(); i++) {
            Layer layer = cube.getFace(Color.BLUE).getNthColumn(i);

            for (Color color : layer.getDataSet()) {
                sb.append(mapColorToFaceletString(color));
            }
        }

        // Orange face
        for (int i = 0; i < cube.getDimensions(); i++) {
            Layer layer = cube.getFace(Color.ORANGE).getNthRow(i).reverse();

            for (Color color : layer.getDataSet()) {
                sb.append(mapColorToFaceletString(color));
            }
        }

        // Red face
        for (int i = 0; i < cube.getDimensions(); i++) {
            Layer layer = cube.getFace(Color.RED).getNthColumn(i);

            for (Color color : layer.getDataSet()) {
                sb.append(mapColorToFaceletString(color));
            }
        }

        sb.append('&');
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