package hu.thepocok.kockapp.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yashovardhan99.timeit.Stopwatch;

import java.util.HashMap;
import java.util.Map;

import hu.thepocok.kockapp.R;
import hu.thepocok.kockapp.persistence.database.ResultDatabase;
import hu.thepocok.kockapp.persistence.entity.Result;

public class TimerActivity extends AppCompatActivity {
    private final String apiURL = "http://thepocok.freeddns.org:3092/records";
    private Button leftBtn;
    private Button rightBtn;
    TextView timer;

    private boolean isLeftTouched = false;
    private boolean isRightTouched = false;
    private boolean isTimerStarted = false;

    private Stopwatch stopwatch;

    private ResultDatabase resultDatabase;

    private RequestQueue queue;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        resultDatabase = ResultDatabase.getDatabase(this);
        queue = Volley.newRequestQueue(this);

        stopwatch = new Stopwatch();
        timer = findViewById(R.id.elapsed_time_view);
        stopwatch.setTextView(timer);

        leftBtn = findViewById(R.id.button_left);
        rightBtn = findViewById(R.id.button_right);

        leftBtn.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                isLeftTouched = true;
                leftBtn.setBackgroundColor(getColor(R.color.yellow));

                if (isLeftTouched && isRightTouched && isTimerStarted) {
                    stopTimer();
                }

                return true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (isLeftTouched && isRightTouched && !isTimerStarted) {
                    startTimer();
                }

                leftBtn.setBackgroundColor(getColor(R.color.white));
                isLeftTouched = false;
                return false;
            }

            return false;
        });

        rightBtn.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                isRightTouched = true;
                rightBtn.setBackgroundColor(getColor(R.color.yellow));

                if (isLeftTouched && isRightTouched && isTimerStarted) {
                    stopTimer();
                    askToSaveResult();
                } else if (isLeftTouched && isRightTouched && !isTimerStarted) {
                    timer.setText(formatElapsedTime(0));
                }

                return true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (isLeftTouched && isRightTouched && !isTimerStarted) {
                    startTimer();
                }

                rightBtn.setBackgroundColor(getColor(R.color.white));
                isRightTouched = false;
                return false;
            }

            return false;
        });
    }

    private void askToSaveResult() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Congratulations!")
                .setMessage("Do you want to save your result?")
                .setPositiveButton("Yes, it was a 2x2 cube", (dialogInterface, i) -> {
                    Result result = new Result();
                    result.cubeSize = 2;
                    result.time = stopwatch.getElapsedTime();

                    resultDatabase.resultDao().insert(result);
                    saveResultToRemoteDatabase(2, stopwatch.getElapsedTime());
                })
                .setNegativeButton("Yes, it was a 3x3 cube", (dialogInterface, i) -> {
                    Result result = new Result();
                    result.cubeSize = 3;
                    result.time = stopwatch.getElapsedTime();

                    resultDatabase.resultDao().insert(result);
                    saveResultToRemoteDatabase(3, stopwatch.getElapsedTime());
                })
                .setNeutralButton("No", (dialogInterface, i) -> {})
                .show();
    }

    private void startTimer() {
        if (!stopwatch.isStarted()) {
            stopwatch.start();
        }
        isTimerStarted = true;
    }

    private void stopTimer() {
        if (stopwatch.isStarted()) {
            stopwatch.stop();
        }

        timer.setText(formatElapsedTime(stopwatch.getElapsedTime()));

        // Necessary to make sure, that the timer will not restart when releasing the buttons
        isLeftTouched = false;
        isRightTouched = false;
        isTimerStarted = false;
    }

    private String formatElapsedTime(long elapsedTime){
        int minutes = (int) (elapsedTime / (60 * 1000) % 60);
        int seconds = (int) ((elapsedTime / 1000) % 60);
        int milliseconds = (int) ((elapsedTime % 1000) / 10);

        StringBuilder sb = new StringBuilder();

        if (minutes != 0) {
            sb.append((minutes < 10) ? "0" : "");
            sb.append(minutes);
            sb.append(":");
        }

        sb.append((seconds < 10) ? "0" : "");
        sb.append(seconds);
        sb.append(".");

        sb.append((milliseconds < 10) ? "0" : "");
        sb.append(milliseconds);

        return sb.toString();
    }

    public void saveResultToRemoteDatabase(int cubeSize, long result) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                apiURL,
                response -> Toast.makeText(getApplicationContext(), "Result saved successfully!", Toast.LENGTH_SHORT).show(),
                error -> {
                    Log.d("REQUEST", error.toString());
                    Toast.makeText(getApplicationContext(), "Could not save result to remote database!", Toast.LENGTH_SHORT).show();
                }
        ) {
            @SuppressLint("HardwareIds")
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("device_id", android.provider.Settings.Secure.getString(
                        getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID));
                params.put("cube_size", String.valueOf(cubeSize));
                params.put("result", String.valueOf(result));
                return params;
            }
        };

        queue.add(request);
    }
}