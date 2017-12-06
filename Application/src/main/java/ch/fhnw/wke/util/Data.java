package ch.fhnw.wke.util;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import ch.fhnw.wke.json.JSONRecognitionResult;

public class Data {

    public static JSONRecognitionResult jsonRecognitionResult;
    public static Bitmap imageToBeRecognized;
    public static List<Bitmap> imagesToBeAdded = new ArrayList<>();

}
