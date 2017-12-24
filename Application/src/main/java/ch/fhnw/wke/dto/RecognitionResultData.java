package ch.fhnw.wke.dto;

import java.util.List;

public class RecognitionResultData {

    private List<Float> scores;
    private List<String> classes;
    private List<String> images;

    public RecognitionResultData() {
    }

    public RecognitionResultData(List<Float> scores, List<String> classes, List<String> images) {
        this.scores = scores;
        this.classes = classes;
        this.images = images;
    }

    public List<Float> getScores() {
        return scores;
    }

    public void setScores(List<Float> scores) {
        this.scores = scores;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
    
}