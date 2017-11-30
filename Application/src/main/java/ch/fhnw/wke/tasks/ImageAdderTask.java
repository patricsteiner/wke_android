package ch.fhnw.wke.tasks;

import android.graphics.Bitmap;
import android.util.Log;

import ch.fhnw.wke.Configuration;
import ch.fhnw.wke.json.JSONImage;
import ch.fhnw.wke.json.JSONWorkpieceId;
import ch.fhnw.wke.util.Util;

public class ImageAdderTask extends RestCall<Bitmap, Void, Void> {

    @Override
    protected Void doInBackground(Bitmap... bitmaps) {
        try {
            JSONWorkpieceId jsonWorkpieceId = getRestTemplate().getForObject(Configuration.REST_ENDPOINT_NEW_WORKPIECE_ID, JSONWorkpieceId.class);
            for (int i = 0; i < bitmaps.length; i++) {
                Bitmap bitmap = bitmaps[i];
                if (bitmap == null) continue;
                getRestTemplate().postForObject(Configuration.REST_ENDPOINT_ADD_WORKPIECE_IMAGE_,
                        new JSONImage(i, jsonWorkpieceId.getWorkpieceId(), Util.bitmapToBase64(bitmap)), JSONWorkpieceId.class);
            }
        } catch (Exception e) {
            Log.e("ImageAdderTask", e.getMessage(), e);
        }
        return null;
    }

}