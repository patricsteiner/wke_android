package ch.fhnw.wke.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

import ch.fhnw.wke.R;

public class RecognitionResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition_result);
        List<Float> scores = RecognitionActivity.jsonRecognitionResult.getScores();
        List<String> classes = RecognitionActivity.jsonRecognitionResult.getClasses();
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        textView1.setText(String.valueOf(scores.get(0) + "\nclass: " + classes.get(0)));
        textView2.setText(String.valueOf(scores.get(1) + "\nclass: " + classes.get(1)));
        textView3.setText(String.valueOf(scores.get(2) + "\nclass: " + classes.get(2)));
    }

}