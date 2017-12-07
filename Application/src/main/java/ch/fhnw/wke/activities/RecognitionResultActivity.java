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
        List<Float> scores = Data.jsonRecognitionResult.getScores();
        List<String> classes = Data.jsonRecognitionResult.getClasses();
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        textView1.setText(String.valueOf(scores.get(0) + "\nclass: " + classes.get(0)));
        textView2.setText(String.valueOf(scores.get(1) + "\nclass: " + classes.get(1)));
        textView3.setText(String.valueOf(scores.get(2) + "\nclass: " + classes.get(2)));
        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
        ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView1.setImageBitmap(Util.base64ToBitmap(Data.jsonRecognitionResult.getImages().get(0)));
        imageView2.setImageBitmap(Util.base64ToBitmap(Data.jsonRecognitionResult.getImages().get(1)));
        imageView3.setImageBitmap(Util.base64ToBitmap(Data.jsonRecognitionResult.getImages().get(2)));
    }

}