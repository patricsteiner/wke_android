package ch.fhnw.wke.tasks;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ch.fhnw.wke.Camera2RawFragment;

public class PictureTakerTask extends AbstractAsyncTask<Camera2RawFragment, Void, Void> {

    @Override
    protected Void doInBackground(Camera2RawFragment... camera2RawFragments) {
        Camera2RawFragment camera2RawFragment = camera2RawFragments[0];
        int delayBetweenPictures = 500;
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        scheduledExecutorService.scheduleAtFixedRate(camera2RawFragment::takePicture, delayBetweenPictures, delayBetweenPictures, TimeUnit.MILLISECONDS);
        try {
            Thread.sleep(20 * delayBetweenPictures);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scheduledExecutorService.shutdown();
        try {
            scheduledExecutorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
