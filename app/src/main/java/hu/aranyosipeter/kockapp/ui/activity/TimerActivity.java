package hu.aranyosipeter.kockapp.ui.activity;

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

import hu.aranyosipeter.kockapp.R;
import hu.aranyosipeter.kockapp.persistence.database.ResultDatabase;
import hu.aranyosipeter.kockapp.persistence.entity.Result;

public class TimerActivity extends AppCompatActivity {
    private final String API_URL = "http://thepocok.freeddns.org:3092/records";
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
                    askToSaveResult(stopwatch.getElapsedTime());
                }  else if (isLeftTouched && isRightTouched && !isTimerStarted) {
                    timer.setText(formatElapsedTime(0));
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
                    askToSaveResult(stopwatch.getElapsedTime());
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

    private void askToSaveResult(long elapsedTime) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.congratulations_title)
                .setMessage(getResources().getString(R.string.your_result) + formatElapsedTime(elapsedTime) + getResources().getString(R.string.want_to_save))
                .setPositiveButton(R.string.yes_2, (dialogInterface, i) -> {
                    Result result = new Result();
                    result.cubeSize = 2;
                    result.time = stopwatch.getElapsedTime();

                    resultDatabase.resultDao().insert(result);
                    saveResultToRemoteDatabase(2, stopwatch.getElapsedTime());
                })
                .setNegativeButton(R.string.yes_3, (dialogInterface, i) -> {
                    Result result = new Result();
                    result.cubeSize = 3;
                    result.time = stopwatch.getElapsedTime();

                    resultDatabase.resultDao().insert(result);
                    saveResultToRemoteDatabase(3, stopwatch.getElapsedTime());
                })
                .setNeutralButton(R.string.no, (dialogInterface, i) -> {})
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
                API_URL,
                response -> Toast.makeText(getApplicationContext(), R.string.saved, Toast.LENGTH_SHORT).show(),
                error -> {
                    Log.d("REQUEST", error.toString());
                    Toast.makeText(getApplicationContext(), R.string.not_saved, Toast.LENGTH_SHORT).show();
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