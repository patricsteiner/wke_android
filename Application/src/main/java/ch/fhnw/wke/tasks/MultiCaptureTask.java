package ch.fhnw.wke.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.wonderkiln.camerakit.CameraKitEventListenerAdapter;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraView;

import java.util.ArrayList;
import java.util.List;

import ch.fhnw.wke.Config;

import static com.wonderkiln.camerakit.CameraKit.Constants.FLASH_OFF;
import static com.wonderkiln.camerakit.CameraKit.Constants.FOCUS_CONTINUOUS;
import static com.wonderkiln.camerakit.CameraKit.Constants.METHOD_STILL;

public class MultiCaptureTask extends AbstractAsyncTask<CameraView, Integer, List<Bitmap>> {

    private final int totalPictures = Config.PICTURES_TO_TAKE_FOR_NEW_WORKPIECE;
    private int picturesTaken = 0;
    private List<Bitmap> bitmaps = new ArrayList<>(totalPictures);

    @Override
    protected List<Bitmap> doInBackground(CameraView... cameraViews) {
        CameraView cameraView = cameraViews[0];
        cameraView.setJpegQuality(60);
        cameraView.setMethod(METHOD_STILL);
        cameraView.setCropOutput(false);
        cameraView.setFocus(FOCUS_CONTINUOUS);
        cameraView.setFlash(FLASH_OFF);
        cameraView.captureImage();

        cameraView.addCameraKitListener(new CameraKitEventListenerAdapter() {
            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                byte[] bytes = cameraKitImage.getJpeg();
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
                bitmaps.add(bitmap);
                if (++picturesTaken < totalPictures) {
                    cameraView.captureImage();
                    publishProgress((int)(((double) picturesTaken / totalPictures * 100)));
                }
            }
        });

        while (picturesTaken < totalPictures) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        publishProgress(100);
        return bitmaps;
    }

}
