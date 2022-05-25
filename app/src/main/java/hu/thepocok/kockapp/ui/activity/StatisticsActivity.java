package hu.thepocok.kockapp.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hu.thepocok.kockapp.R;
import hu.thepocok.kockapp.persistence.database.ResultDatabase;
import hu.thepocok.kockapp.persistence.entity.Result;

public class StatisticsActivity extends AppCompatActivity {
    private static final String API_URL = "http://thepocok.freeddns.org:3092/records";
    private ResultDatabase resultDatabase;

    private TextView resultTwo;
    private TextView resultThree;

    private TextView rankTwo;
    private TextView rankThree;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        resultDatabase = ResultDatabase.getDatabase(this);
        queue = Volley.newRequestQueue(this);

        TextView title = findViewById(R.id.statistics_title);
        title.setText(R.string.statistics_title);

        TextView labelTwo = findViewById(R.id.best_2_label);
        labelTwo.setText(R.string.best_2);

        TextView labelThree = findViewById(R.id.best_3_label);
        labelThree.setText(R.string.best_3);

        TextView rankTwoLabel = findViewById(R.id.rank_2_label);
        rankTwoLabel.setText(R.string.rank_2);

        TextView rankThreeLabel = findViewById(R.id.rank_3_label);
        rankThreeLabel.setText(R.string.rank_3);

        resultTwo = findViewById(R.id.best_2_result);
        resultThree = findViewById(R.id.best_3_result);
        rankTwo = findViewById(R.id.rank_2);
        rankTwo.setText(R.string.fetching_result);
        rankThree = findViewById(R.id.rank_3);
        rankThree.setText(R.string.fetching_result);

        setResults();

        Button deleteBtn = findViewById(R.id.delete);
        deleteBtn.setText(R.string.delete_statistics);
        deleteBtn.setOnClickListener(l -> {
            resultDatabase.resultDao().deleteAll();
            deleteFromRemoteDatabase();

            setResults();
        });
    }

    private void setResults() {
        ArrayList<Result> bestOfCubeTwo = (ArrayList<Result>) resultDatabase.resultDao().getBestOfSize(2);
        ArrayList<Result> bestOfCubeThree = (ArrayList<Result>) resultDatabase.resultDao().getBestOfSize(3);

        if (bestOfCubeTwo.size() != 0) {
            resultTwo.setText(formatTime(bestOfCubeTwo.get(0).time));
            getRankFromRemoteDatabase(2 ,bestOfCubeTwo.get(0).time);

            saveResultToRemoteDatabase(2, bestOfCubeTwo.get(0).time);
        } else {
            resultTwo.setText(R.string.no_result);
            rankTwo.setText(R.string.no_result);
        }

        if (bestOfCubeThree.size() != 0) {
            resultThree.setText(formatTime(bestOfCubeThree.get(0).time));
            getRankFromRemoteDatabase(3 ,bestOfCubeThree.get(0).time);

            saveResultToRemoteDatabase(3, bestOfCubeThree.get(0).time);
        } else {
            resultThree.setText(R.string.no_result);
            rankThree.setText(R.string.no_result);
        }
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

    public void getRankFromRemoteDatabase(int cubeSize, long result) {
        String completedURL = API_URL + "?cube_size=" + cubeSize + "&result=" + result;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                completedURL,
                response -> {
                    if (cubeSize == 2) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            rankTwo.setText(String.valueOf(obj.getInt("rank") + 1));
                        } catch (JSONException e) {
                            rankTwo.setText(R.string.cannot_retrieve_information);
                        }

                    } else if (cubeSize == 3) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            rankThree.setText(String.valueOf(obj.getInt("rank") + 1));
                        } catch (JSONException e) {
                            rankThree.setText(R.string.cannot_retrieve_information);
                        }
                    }
                },
                error -> {
                    Log.d("REQUEST", error.toString());
                    rankTwo.setText(R.string.could_not_connect);
                    rankThree.setText(R.string.could_not_connect);
                }
        );

        queue.add(request);
    }

    @SuppressLint("HardwareIds")
    private void deleteFromRemoteDatabase() {
         String completedURL = API_URL + "/" + android.provider.Settings.Secure.getString(
                getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        StringRequest request = new StringRequest(
                Request.Method.GET,
                completedURL,
                response -> Toast.makeText(this, "Records deleted successfully", Toast.LENGTH_SHORT).show(),
                error -> {
                    Log.d("REQUEST", error.toString());
                    rankTwo.setText(R.string.could_not_connect);
                    rankThree.setText(R.string.could_not_connect);
                }
        );

        queue.add(request);
    }

    private String formatTime(long time){
        int minutes = (int) (time / (60 * 1000) % 60);
        int seconds = (int) ((time / 1000) % 60);
        int milliseconds = (int) ((time % 1000) / 10);

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
}