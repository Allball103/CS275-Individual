package com.example.a275del3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a275del3.dummy.MatchupChart;
import com.example.a275del3.dummy.MatchupChartDatabase;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.ref.WeakReference;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_creation);
//
//
//    }
public class CreationActivity extends AppCompatActivity {

    private TextInputEditText et_title,et_content;
    private MatchupChartDatabase muDatabase;
    private MatchupChart.Chart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        muDatabase = MatchupChartDatabase.getInstance(com.example.a275del3.CreationActivity.this);
        Button button = findViewById(R.id.but_save);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // create worker thread to insert data into database
                new InsertTask(com.example.a275del3.CreationActivity.this,chart).execute();
            }
        });

    }

    private void setResult(MatchupChart.Chart chart, int flag){
        setResult(flag,new Intent().putExtra("chart",chart.id));
        finish();
    }

    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<CreationActivity> activityReference;
        private MatchupChart.Chart chart;

        // only retain a weak reference to the activity
        InsertTask(CreationActivity context, MatchupChart.Chart chart) {
            activityReference = new WeakReference<>(context);
            this.chart = chart;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            activityReference.get().muDatabase.getNoteDao().insert(chart);
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(chart,1);
            }
        }

    }

}

