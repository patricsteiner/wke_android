package ch.fhnw.wke.json;

public class JSONImage {

    private int imageNumber;
    private String workpieceId;
    private String image;


    public JSONImage() {

    }

    public JSONImage(int imageNumber, String workpieceId, String image) {
        this.imageNumber = imageNumber;
        this.workpieceId = workpieceId;
        this.image = image;
    }

    public int getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }

    public String getWorkpieceId() {
        return workpieceId;
    }

    public void setWorkpieceId(String workpieceId) {
        this.workpieceId = workpieceId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}