package ch.fhnw.wke.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ch.fhnw.wke.Camera2BasicFragment;
import ch.fhnw.wke.Camera2RawFragment;
import ch.fhnw.wke.R;
import ch.fhnw.wke.tasks.PictureTakerTask;
import ch.fhnw.wke.tasks.RecognitionRestCall;
import ch.fhnw.wke.util.Data;

public class RecognitionActivity extends AppCompatActivity {

    private Camera2RawFragment mCamera2RawFragment;
    private Camera2BasicFragment mCamera2BasicFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition);
        if (null == savedInstanceState) {
            mCamera2RawFragment = Camera2RawFragment.newInstance();
            mCamera2BasicFragment = Camera2BasicFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.cameraContainer, mCamera2RawFragment)
                    .commit();
        }
    }

//    static final int REQUEST_VIDEO_CAPTURE = 1;
//
//    private void takeVideo() {
//        //new ReviewActivity.HttpRequestTask().execute();
//        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
//            Uri videoUri = intent.getData();
//            for (Bitmap frame : extractFrames(videoUri)) ReviewActivity.addBitmap(frame);
//            Intent reviewIntent = new Intent(this, ReviewActivity.class);
//            startActivity(reviewIntent);
//        }
//    }
//
//    private ArrayList<Bitmap> extractFrames(Uri videoUri) {
//        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
//        mediaMetadataRetriever.setDataSource(getBaseContext(), videoUri);
//        ArrayList<Bitmap> frames = new ArrayList<>();
//        Bitmap frame, previous = mediaMetadataRetriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST);
//        int time = 1;
//        int sameCounter = 0;
//        while (sameCounter < 5) {
//            frame = mediaMetadataRetriever.getFrameAtTime(time++ * 1000 * 300, MediaMetadataRetriever.OPTION_CLOSEST);
//            if (frame.sameAs(previous)) sameCounter++;
//            else {
//                frames.add(frame);
//                sameCounter = 0;
//                previous = frame;
//            }
//        }
//        return frames;
//    }

    public void initiateRecognition(View view) {
        mCamera2RawFragment.takePicture(); // TODO might throw exception, so wrap in try/catch maybe?
        SystemClock.sleep(2000);
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Trying to recognize... please wait");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        RecognitionRestCall recognitionRestCall = new RecognitionRestCall();
        recognitionRestCall.setOnPostExecuteAction(recognitionResultData -> {
            Data.recognitionResultData = recognitionResultData;
            progressDialog.dismiss();
            Intent intent = new Intent(this, RecognitionResultActivity.class);
            startActivity(intent);
        });
        recognitionRestCall.execute(Data.imageToBeRecognized);
    }


    public void initiateNewWorkpiece(View view) {
        new AlertDialog.Builder(this)
                .setMessage("Press OK when machine part is in box, motor & lights are on and phone is in holder")
                .setPositiveButton(android.R.string.ok, (dialogInterface, x) -> {
                    Data.imagesToBeAdded = new ArrayList<>();
                    PictureTakerTask pictureTakerTask = new PictureTakerTask();
                    pictureTakerTask.setOnPostExecuteAction(Void -> {
                        Intent intent = new Intent(this, ReviewActivity.class);
                        startActivity(intent);
                    });
                    pictureTakerTask.execute(mCamera2RawFragment);
                })
                .setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

}