package ch.fhnw.wke.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.wonderkiln.camerakit.CameraView;

import ch.fhnw.wke.R;
import ch.fhnw.wke.tasks.CaptureTask;
import ch.fhnw.wke.tasks.MultiCaptureTask;
import ch.fhnw.wke.tasks.RecognitionRestCall;
import ch.fhnw.wke.util.Data;

public class RecognitionActivity extends AppCompatActivity {

    private CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition);
        cameraView = findViewById(R.id.camera);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    public void initiateRecognition(View view) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Trying to recognize... please wait");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        CaptureTask captureTask = new CaptureTask();
        RecognitionRestCall recognitionRestCall = new RecognitionRestCall();
        captureTask.setOnPostExecuteAction(recognitionRestCall::execute);
        recognitionRestCall.setOnPostExecuteAction(recognitionResultData -> {
            Data.recognitionResultData = recognitionResultData;
            progressDialog.dismiss();
            Intent intent = new Intent(this, RecognitionResultActivity.class);
            startActivity(intent);
        });
        captureTask.execute(cameraView);
    }

    public void initiateNewWorkpiece(View view) {
        Toast toast = Toast.makeText(this, "", Toast.LENGTH_LONG);
        new AlertDialog.Builder(this)
                .setMessage("Press OK when workpiece is in box, motor & lights are on and phone is in holder")
                .setPositiveButton(android.R.string.ok, (dialogInterface, x) -> {
                    MultiCaptureTask multiCaptureTask = new MultiCaptureTask();
                    multiCaptureTask.setOnProgressUpdateAction(progress -> {
                        toast.setText(progress + "% captured");
                        toast.show();
                    });
                    multiCaptureTask.setOnPostExecuteAction(bitmaps -> {
                        Data.imagesToBeAdded = bitmaps;
                        Intent intent = new Intent(this, ReviewActivity.class);
                        startActivity(intent);
                    });
                    multiCaptureTask.execute(cameraView);
                })
                .setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

}