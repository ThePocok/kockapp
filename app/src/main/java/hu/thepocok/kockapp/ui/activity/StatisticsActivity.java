package hu.thepocok.kockapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

        TextView labelTwo = findViewById(R.id.best_2_label);
        labelTwo.setText("Best time for 2x2 cube");

        TextView labelThree = findViewById(R.id.best_3_label);
        labelThree.setText("Best time for 3x3 cube");

        TextView rankTwoLabel = findViewById(R.id.rank_2_label);
        rankTwoLabel.setText("Rank in 2x2 cube");

        TextView rankThreeLabel = findViewById(R.id.rank_3_label);
        rankThreeLabel.setText("Rank in 3x3 cube");

        resultTwo = findViewById(R.id.best_2_result);
        resultThree = findViewById(R.id.best_3_result);
        rankTwo = findViewById(R.id.rank_2);
        rankTwo.setText("Fetching result...");
        rankThree = findViewById(R.id.rank_3);
        rankThree.setText("Fetching result...");

        setResults();

        Button deleteBtn = findViewById(R.id.delete);
        deleteBtn.setOnClickListener(l -> {
            resultDatabase.resultDao().deleteAll();

            setResults();
        });
    }

    private void setResults() {
        ArrayList<Result> bestOfCubeTwo = (ArrayList<Result>) resultDatabase.resultDao().getBestOfSize(2);
        ArrayList<Result> bestOfCubeThree = (ArrayList<Result>) resultDatabase.resultDao().getBestOfSize(3);

        if (bestOfCubeTwo.size() != 0) {
            resultTwo.setText(String.valueOf(bestOfCubeTwo.get(0).time));
            getRankFromRemoteDatabase(2 ,bestOfCubeTwo.get(0).time);
        } else {
            resultTwo.setText("No result yet");
            rankTwo.setText("No result yet");
        }

        if (bestOfCubeThree.size() != 0) {
            resultThree.setText(String.valueOf(bestOfCubeThree.get(0).time));
            getRankFromRemoteDatabase(3 ,bestOfCubeThree.get(0).time);
        } else {
            resultThree.setText("No result yet");
            rankThree.setText("No result yet");
        }
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
                            rankTwo.setText("Cannot retrieve information from database");
                        }

                    } else if (cubeSize == 3) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            rankThree.setText(String.valueOf(obj.getInt("rank") + 1));
                        } catch (JSONException e) {
                            rankThree.setText("Cannot retrieve information from database");
                        }                    }
                },
                error -> {
                    Log.d("REQUEST", error.toString());
                    rankTwo.setText("Could not connect to database");
                    rankThree.setText("Could not connect to database");
                }
        );

        queue.add(request);
    }
}