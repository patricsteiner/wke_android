package ch.fhnw.wke.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.wonderkiln.camerakit.CameraKitEventListenerAdapter;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraView;

import static com.wonderkiln.camerakit.CameraKit.Constants.FLASH_OFF;
import static com.wonderkiln.camerakit.CameraKit.Constants.FOCUS_CONTINUOUS;
import static com.wonderkiln.camerakit.CameraKit.Constants.METHOD_STILL;

public class CaptureTask extends AbstractAsyncTask<CameraView, Void, Bitmap> {

    private Bitmap bitmap;

    @Override
    protected Bitmap doInBackground(CameraView... cameraViews) {
        CameraView cameraView = cameraViews[0];
        cameraView.setJpegQuality(80);
        cameraView.setMethod(METHOD_STILL);
        cameraView.setCropOutput(false);
        cameraView.setFocus(FOCUS_CONTINUOUS);
        cameraView.setFlash(FLASH_OFF);
        cameraView.captureImage();

        cameraView.addCameraKitListener(new CameraKitEventListenerAdapter() {
            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                byte[] bytes = cameraKitImage.getJpeg();
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
            }
        });

        while (bitmap == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

}
