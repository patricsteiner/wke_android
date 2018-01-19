package ch.fhnw.wke.tasks;

import android.graphics.Bitmap;
import android.util.Log;

import ch.fhnw.wke.Config;
import ch.fhnw.wke.dto.ImageData;
import ch.fhnw.wke.dto.WorkpieceIdData;
import ch.fhnw.wke.util.Data;
import ch.fhnw.wke.util.Util;

public class ImageAdderRestCall extends AbstractRestCall<Bitmap, Integer, WorkpieceIdData> {

    private WorkpieceIdData workpieceIdData;

    public ImageAdderRestCall(WorkpieceIdData workpieceIdData) {
        this.workpieceIdData = workpieceIdData;
    }

    @Override
    protected WorkpieceIdData doInBackground(Bitmap... bitmaps) {
        try {
            if (workpieceIdData == null){
                workpieceIdData = getRestTemplate().getForObject(Config.REST_ENDPOINT_NEW_WORKPIECE_ID, WorkpieceIdData.class);
            }
            for (int i = 0; i < bitmaps.length; i++) {
                Bitmap bitmap = bitmaps[i];
                getRestTemplate().postForObject(Config.REST_ENDPOINT_ADD_WORKPIECE_IMAGE,
                        new ImageData(i + Data.imagesAdded, workpieceIdData.getWorkpieceId(), Util.bitmapToBase64(bitmap)), WorkpieceIdData.class);
                publishProgress((int)(((double) i / bitmaps.length) * 100));
                bitmaps[i].recycle();
                bitmaps[i] = null;
            }
            Data.imagesAdded += bitmaps.length;
            publishProgress(100);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), e.getMessage(), e);
        }
        return workpieceIdData;
    }

}