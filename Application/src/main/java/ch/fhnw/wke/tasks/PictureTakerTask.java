package ch.fhnw.wke.tasks;

import android.os.SystemClock;

import ch.fhnw.wke.Camera2RawFragment;

public class PictureTakerTask extends ExtendedAsyncTask<Camera2RawFragment, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Camera2RawFragment... camera2RawFragments) {
        Camera2RawFragment camera2RawFragment = camera2RawFragments[0];
        for (int i = 0; i < 10; i++) {
            camera2RawFragment.takePicture();
            SystemClock.sleep(400);
        }
        SystemClock.sleep(5000);
        return true;
    }

}