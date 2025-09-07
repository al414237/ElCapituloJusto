package es.sanchoo.capitulojusto.auxiliares;

public class Panel {
    private String image;
    private int difficulty;
    private int rightChapter;

    public Panel(String image, int rightChapter, int difficulty){
        this.image = image;
        this.difficulty = difficulty;
        this.rightChapter = rightChapter;
    }

    public String getImage() {
        return image;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getRightChapter() {
        return rightChapter;
    }
}
