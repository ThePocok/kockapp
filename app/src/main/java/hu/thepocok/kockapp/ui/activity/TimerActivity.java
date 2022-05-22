package hu.thepocok.kockapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

import com.yashovardhan99.timeit.Stopwatch;

import hu.thepocok.kockapp.R;

public class TimerActivity extends AppCompatActivity {
    private Button leftBtn;
    private Button rightBtn;

    private boolean isLeftTouched = false;
    private boolean isRightTouched = false;
    private boolean isTimerStarted = false;

    Stopwatch stopwatch;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        stopwatch = new Stopwatch();
        stopwatch.setTextView(findViewById(R.id.elapsed_time_view));

        leftBtn = findViewById(R.id.button_left);
        rightBtn = findViewById(R.id.button_right);

        leftBtn.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                isLeftTouched = true;

                if (isLeftTouched && isRightTouched && !isTimerStarted) {
                    leftBtn.setBackgroundColor(getColor(R.color.yellow));
                } else if (isLeftTouched && isRightTouched && isTimerStarted) {
                    stopwatch.stop();
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

                if (isLeftTouched && isRightTouched && !isTimerStarted) {
                    rightBtn.setBackgroundColor(getColor(R.color.yellow));
                } else if (isLeftTouched && isRightTouched && isTimerStarted) {
                    stopwatch.stop();
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
    }
}