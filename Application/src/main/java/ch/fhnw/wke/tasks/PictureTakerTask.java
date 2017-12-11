package ch.fhnw.wke.tasks;

import ch.fhnw.wke.Camera2RawFragment;

public class PictureTakerTask extends ExtendedAsyncTask<Camera2RawFragment, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Camera2RawFragment... camera2RawFragments) {
        Camera2RawFragment camera2RawFragment = camera2RawFragments[0];
        //for (int i = 0; i < 30; i++) {
            camera2RawFragment.takePicture();
            //SystemClock.sleep(500); // TODO there has to be a better way
        //}
        //SystemClock.sleep(2000);
        return true;
    }

}