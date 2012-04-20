package Units;

import java.util.ArrayList;

/**
 * @author thaodv-bkit
 * @version 1.0
 * @created 05-Apr-2011 2:35:52 PM
 */
public class Phrase {

    private int phraseLen;
    private int id_phrase;
    private String phraseContent;
    
    
    /**
     * vi tri bat dau cua cum tu trong file am thanh
     */
    private int startIndex;
    /**
     * vi tri ket thuc cua cum tu trong file am thanh
     */
    private int endIndex;

    public Phrase() {
    }

    /**
     * @return the numberOfSyllables
     */
    public int getNumberOfSyllables() {
        return getPhraseLen();
    }

    /**
     * @param numberOfSyllables the numberOfSyllables to set
     */
    public void setNumberOfSyllables(int numberOfSyllables) {
        this.setPhraseLen(numberOfSyllables);
    }



    /**
     * @return the startIndex
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * @param startIndex the startIndex to set
     */
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * @return the endIndex
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * @param endIndex the endIndex to set
     */
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    

    /**
     * @return the id_phrase
     */
    public int getId_phrase() {
        return id_phrase;
    }

    /**
     * @param id_phrase the id_phrase to set
     */
    public void setId_phrase(int id_phrase) {
        this.id_phrase = id_phrase;
    }

    /**
     * @return the phraseLen
     */
    public int getPhraseLen() {
        return phraseLen;
    }

    /**
     * @param phraseLen the phraseLen to set
     */
    public void setPhraseLen(int phraseLen) {
        this.phraseLen = phraseLen;
    }

    /**
     * @return the phraseContent
     */
    public String getPhraseContent() {
        return phraseContent;
    }

    /**
     * @param phraseContent the phraseContent to set
     */
    public void setPhraseContent(String phraseContent) {
        this.phraseContent = " "+phraseContent.trim()+" ";
    }
}
