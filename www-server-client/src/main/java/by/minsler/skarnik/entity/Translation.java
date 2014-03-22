package by.minsler.skarnik.entity;

/**
 * Class to encapsulate information about word and translation for it.
 *
 * @author Dzmitry Misiuk
 */
public class Translation {

    private int id;
    private String word;
    private String translation;

    public Translation(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
