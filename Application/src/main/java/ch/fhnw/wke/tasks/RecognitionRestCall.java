package ch.fhnw.wke.tasks;

import android.graphics.Bitmap;
import android.util.Log;

import ch.fhnw.wke.Configuration;
import ch.fhnw.wke.json.JSONImage;
import ch.fhnw.wke.json.JSONRecognitionResult;
import ch.fhnw.wke.util.Util;

public class RecognitionRestCall extends RestCall<Bitmap, Void, JSONRecognitionResult> {

    @Override
    protected JSONRecognitionResult doInBackground(Bitmap... bitmaps) {
        try {
            Bitmap bitmap = bitmaps[0];
            JSONRecognitionResult jsonRecognitionResult = getRestTemplate().postForObject(Configuration.REST_ENDPOINT_RECOGNITION,
                    new JSONImage(0, "", Util.bitmapToBase64(bitmap)), JSONRecognitionResult.class);
            return jsonRecognitionResult;
        } catch (Exception e) {
            Log.e("RecognitionTask", e.getMessage(), e);
        }
        return null;
    }

}