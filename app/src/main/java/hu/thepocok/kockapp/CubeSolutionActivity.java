package hu.thepocok.kockapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class CubeSolutionActivity extends AppCompatActivity {
    public final String TAG = "CubeSolutionActivity";
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cube_solution);

        webView = findViewById(R.id.cube_model);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("WebView", consoleMessage.message());
                return true;
            }
        });

        webView.setOnTouchListener((view, motionEvent) -> true);

        loadHtml();

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

        return sb.toString();
    }
}