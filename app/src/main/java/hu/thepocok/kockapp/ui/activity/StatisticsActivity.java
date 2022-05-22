package hu.thepocok.kockapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import hu.thepocok.kockapp.R;
import hu.thepocok.kockapp.persistence.database.ResultDatabase;
import hu.thepocok.kockapp.persistence.entity.Result;

public class StatisticsActivity extends AppCompatActivity {
    private ResultDatabase resultDatabase;

    private TextView resultTwo;
    private TextView resultThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        resultDatabase = ResultDatabase.getDatabase(this);

        TextView labelTwo = findViewById(R.id.best_2_label);
        labelTwo.setText("Best time for 2x2 cube");

        TextView labelThree = findViewById(R.id.best_3_label);
        labelThree.setText("Best time for 3x3 cube");

        resultTwo = findViewById(R.id.best_2_result);
        resultThree = findViewById(R.id.best_3_result);

        setResults();

        Button deleteBtn = findViewById(R.id.delete);
        deleteBtn.setOnClickListener(l -> {
            resultDatabase.resultDao().deleteAll();

            setResults();
        });
    }

    private void setResults() {
        ArrayList<Result> bestOfCubeTwo = (ArrayList<Result>) resultDatabase.resultDao().getBestOfSize(2);
        resultTwo.setText((bestOfCubeTwo.size() == 0) ? "No result yet" : String.valueOf(bestOfCubeTwo.get(0).time));

        ArrayList<Result> bestOfCubeThree = (ArrayList<Result>) resultDatabase.resultDao().getBestOfSize(3);
        resultThree.setText((bestOfCubeThree.size() == 0) ? "No result yet" : String.valueOf(bestOfCubeThree.get(0).time));
    }
}