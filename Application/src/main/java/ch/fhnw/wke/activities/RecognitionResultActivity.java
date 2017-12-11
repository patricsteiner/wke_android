package ch.fhnw.wke.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.fhnw.wke.R;
import ch.fhnw.wke.util.Data;
import ch.fhnw.wke.util.Util;

public class RecognitionResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition_result);
        if (Data.recognitionResultData != null) {
            List<Float> scores = Data.recognitionResultData.getScores();
            List<String> classes = Data.recognitionResultData.getClasses();
            TextView textView1 = findViewById(R.id.textView1);
            TextView textView2 = findViewById(R.id.textView2);
            TextView textView3 = findViewById(R.id.textView3);
            textView1.setText(String.valueOf(scores.get(0) + "\nclass: " + classes.get(0)));
            textView2.setText(String.valueOf(scores.get(1) + "\nclass: " + classes.get(1)));
            textView3.setText(String.valueOf(scores.get(2) + "\nclass: " + classes.get(2)));
            ImageView imageView1 = findViewById(R.id.imageView1);
            ImageView imageView2 = findViewById(R.id.imageView2);
            ImageView imageView3 = findViewById(R.id.imageView3);
            imageView1.setImageBitmap(Util.base64ToBitmap(Data.recognitionResultData.getImages().get(0)));
            imageView2.setImageBitmap(Util.base64ToBitmap(Data.recognitionResultData.getImages().get(1)));
            imageView3.setImageBitmap(Util.base64ToBitmap(Data.recognitionResultData.getImages().get(2)));
        }
    }

}