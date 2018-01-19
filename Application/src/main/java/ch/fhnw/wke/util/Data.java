package ch.fhnw.wke.util;

import android.graphics.Bitmap;

import java.util.Collections;
import java.util.List;

import ch.fhnw.wke.dto.RecognitionResultData;
import ch.fhnw.wke.dto.WorkpieceIdData;

public class Data {

    public static RecognitionResultData recognitionResultData;
    public static List<Bitmap> imagesToBeAdded = Collections.emptyList();
    public static WorkpieceIdData workpieceIdData;
    public static int imagesAdded;

}
