package ch.fhnw.wke.tasks;

import android.graphics.Bitmap;
import android.util.Log;

import ch.fhnw.wke.Config;
import ch.fhnw.wke.dto.ImageData;
import ch.fhnw.wke.dto.WorkpieceIdData;
import ch.fhnw.wke.util.Util;

public class ImageAdderTask extends RestCall<Bitmap, Void, Void> {

    @Override
    protected Void doInBackground(Bitmap... bitmaps) {
        try {
            WorkpieceIdData workpieceIdData = getRestTemplate().getForObject(Config.REST_ENDPOINT_NEW_WORKPIECE_ID, WorkpieceIdData.class);
            for (int i = 0; i < bitmaps.length; i++) {
                Bitmap bitmap = bitmaps[i];
                if (bitmap == null) continue;
                getRestTemplate().postForObject(Config.REST_ENDPOINT_ADD_WORKPIECE_IMAGE_,
                        new ImageData(i, workpieceIdData.getWorkpieceId(), Util.bitmapToBase64(bitmap)), WorkpieceIdData.class);
            }
        } catch (Exception e) {
            Log.e("ImageAdderTask", e.getMessage(), e);
        }
        return null;
    }

}