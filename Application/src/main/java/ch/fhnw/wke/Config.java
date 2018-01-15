package ch.fhnw.wke;

public class Config {

    public static final int    PICTURES_TO_TAKE_FOR_NEW_WORKPIECE   = 20;
    public static final String REST_BASE_URL                        = "http://192.168.1.116:8888/";
    public static final String REST_ENDPOINT_RECOGNIZE_WORKPIECE    = REST_BASE_URL + "recognize_workpiece";
    public static final String REST_ENDPOINT_NEW_WORKPIECE_ID       = REST_BASE_URL + "new_workpiece_id";
    public static final String REST_ENDPOINT_ADD_WORKPIECE_IMAGE    = REST_BASE_URL + "add_workpiece_image";
    public static final String REST_ENDPOINT_TRANSFER_LEARNING      = REST_BASE_URL + "initiate_transfer_learning";

}