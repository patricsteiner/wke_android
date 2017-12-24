package ch.fhnw.wke;

public class Config {


    public static final int    PICTURES_TO_TAKE_FOR_NEW_WORKPIECE   = 20;
    public static final String REST_BASE_URL                        = "http://192.168.1.116:8888/";
    public static final String REST_ENDPOINT_RECOGNITION            = REST_BASE_URL + "";
    public static final String REST_ENDPOINT_NEW_WORKPIECE_ID       = REST_BASE_URL + "new_workpiece_id";
    public static final String REST_ENDPOINT_ADD_WORKPIECE_IMAGE_   = REST_BASE_URL + "add_workpiece_image";

}