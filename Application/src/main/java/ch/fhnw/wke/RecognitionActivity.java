package ch.fhnw.wke;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RecognitionActivity extends AppCompatActivity implements PictureTakerTaskListener {

    private Camera2BasicFragment mCamera2BasicFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition);
        if (null == savedInstanceState) {
            mCamera2BasicFragment = Camera2BasicFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.cameraContainer, mCamera2BasicFragment)
                    .commit();
        }
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
                        takePictures();
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
        mCamera2BasicFragment.unlockFocus(); //TODO is it smart to do here? usually it is done after every pic, i just removed it and added it here instead
    }

    private void takePictures() {
        PictureTakerTask pictureTakerTask = new PictureTakerTask(this);
        pictureTakerTask.execute(mCamera2BasicFragment);
    }


    static class PictureTakerTask extends AsyncTask<Camera2BasicFragment, Void, Boolean> {

        PictureTakerTaskListener pictureTakerTaskListener;

        public PictureTakerTask(PictureTakerTaskListener pictureTakerTaskListener) {
            this.pictureTakerTaskListener = pictureTakerTaskListener;
        }

        @Override
        protected Boolean doInBackground(Camera2BasicFragment... camera2BasicFragments) {
            Camera2BasicFragment camera2BasicFragment = camera2BasicFragments[0];
            for (int i = 0; i < 20; i++) {
                camera2BasicFragment.showToast(String.format("i = %d", i + 1));
                camera2BasicFragment.takePicture();
                SystemClock.sleep(400);
            }
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
