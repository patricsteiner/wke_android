package ch.fhnw.wke;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

public class RecognitionActivity extends AppCompatActivity implements PictureTakerTaskListener {

    private Camera2RawFragment mCamera2RawFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition);
        if (null == savedInstanceState) {
            mCamera2RawFragment = Camera2RawFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.cameraContainer, mCamera2RawFragment)
                    .commit();
        }
    }

    static final int REQUEST_VIDEO_CAPTURE = 1;

    private void takeVideo() {
        new ReviewActivity.HttpRequestTask().execute();
        /*
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            for (Bitmap frame : extractFrames(videoUri)) ReviewActivity.addBitmap(frame);
            Intent reviewIntent = new Intent(this, ReviewActivity.class);
            startActivity(reviewIntent);
        }
    }

    private ArrayList<Bitmap> extractFrames(Uri videoUri) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(getBaseContext(), videoUri);
        ArrayList<Bitmap> frames = new ArrayList<>();
        Bitmap frame, previous = mediaMetadataRetriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST);
        int time = 1;
        int sameCounter = 0;
        while (sameCounter < 5) {
            frame = mediaMetadataRetriever.getFrameAtTime(time++ * 1000 * 300, MediaMetadataRetriever.OPTION_CLOSEST);
            if (frame.sameAs(previous)) sameCounter++;
            else { frames.add(frame); sameCounter = 0; previous = frame; }
        }
        return frames;
    }

    public void recognize(View view) {
        final ProgressDialog dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Trying to recognize... please wait");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                showRecognitionResult();
            }
        }, 2000);

    }

    private void showRecognitionResult() {
        Intent intent = new Intent(this, RecognitionResultActivity.class);
        startActivity(intent);
    }

    public void addNewPart(View view) {
        new AlertDialog.Builder(this)
                .setMessage("Press OK when machine part is in box, motor & lights are on and phone is in holder")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //takePictures();
                        takeVideo();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onPicturesTaken() {
        Intent intent = new Intent(this, ReviewActivity.class);
        startActivity(intent);
    }

    private void takePictures() {
        PictureTakerTask pictureTakerTask = new PictureTakerTask(this);
        pictureTakerTask.execute(mCamera2RawFragment);
    }


    static class PictureTakerTask extends AsyncTask<Camera2RawFragment, Void, Boolean> {

        PictureTakerTaskListener pictureTakerTaskListener;

        public PictureTakerTask(PictureTakerTaskListener pictureTakerTaskListener) {
            this.pictureTakerTaskListener = pictureTakerTaskListener;
        }

        @Override
        protected Boolean doInBackground(Camera2RawFragment... camera2RawFragments) {
            Camera2RawFragment camera2RawFragment = camera2RawFragments[0];
            for (int i = 0; i < 10; i++) {
                camera2RawFragment.takePicture();
                SystemClock.sleep(400);
            }
            camera2RawFragment.finishedCaptureLocked();
            SystemClock.sleep(2000);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            pictureTakerTaskListener.onPicturesTaken();
        }

    }

}

interface PictureTakerTaskListener {
    void onPicturesTaken();
}
