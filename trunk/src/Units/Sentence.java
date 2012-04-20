package Units;

import java.util.ArrayList;

/**
 * @author thaodv-bkit
 * @version 1.0
 * @created 05-Apr-2011 2:41:52 PM
 */
public class Sentence {

    private ArrayList<SylPhrase> sylPhrases = new ArrayList<SylPhrase>();
    private ArrayList<LevelPhrase> levelPhrases = new ArrayList<LevelPhrase>();
    private int iDofSentence;
    private int iDofFile;
    private String carryingFile;
    private String senContent;
    private int maxLevelOfPhrase;

    public void Sentence() {
    }
    ///////////////////////////////////

    /**
     *
     * @param level level of LevelPhrase to get
     * @return An ArrayList of LevelPhrase at level lv
     */
    public ArrayList<LevelPhrase> getPhraseByLevel(int lv) {
        ArrayList<LevelPhrase> phrases = new ArrayList<LevelPhrase>();
        for (int i = 0; i < levelPhrases.size(); i++) {
            if (levelPhrases.get(i).getLevel() == lv) {
                phrases.add(levelPhrases.get(i));
            }
        }
        return phrases;
    }

    /*
     * @param level level of LevelPhrase to get
     * @return an array of indexes of LevelPhrase at lv
     */
    public ArrayList<Integer> getIndexesOfPhraseByLevel(int lv) {
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0; i < levelPhrases.size(); i++) {
            if (levelPhrases.get(i).getLevel() == lv) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    /**
     * @return the syllablesInSen
     */
    /**
     * @return the sylPhrases
     */
    public ArrayList<SylPhrase> getSylPhrases() {
        return sylPhrases;
    }

    /**
     * @return the IDofSentence
     */
    public int getIDofSentence() {
        return iDofSentence;
    }

    /**
     * @param IDofSentence the IDofSentence to set
     */
    public void setIDofSentence(int IDofSentence) {
        this.iDofSentence = IDofSentence;
    }

    /**
     * @return the carryingFile
     */
    public String getCarryingFile() {
        return carryingFile;
    }

    /**
     * @param carryingFile the carryingFile to set
     */
    public void setCarryingFile(String carryingFile) {
        this.carryingFile = carryingFile;
    }

    /**
     * @return the senContent
     */
    public String getSenContent() {
        return senContent;
    }

    /**
     * 
     */
    public void setSenContent() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < sylPhrases.size(); i++) {
            sb.append(sylPhrases.get(i).getPhraseContent()).append(" ");
        }
        this.setSenContent(" " + sb.toString().trim() + " ");

    }

    /**
     * @return the iDofFile
     */
    public int getiDofFile() {
        return iDofFile;
    }

    /**
     * @param iDofFile the iDofFile to set
     */
    public void setiDofFile(int iDofFile) {
        this.iDofFile = iDofFile;
    }

    /**
     * @return the levelPhrases
     */
    public ArrayList<LevelPhrase> getLevelPhrases() {
        return levelPhrases;
    }

    /**
     * @param senContent the senContent to set
     */
    public void setSenContent(String senContent) {
        this.senContent = senContent;
    }

    /**
     * @return the maxLevelOfSylPhrase
     */
    public int getMaxLevelOfLevelPhrase() {
        return maxLevelOfPhrase;
    }

    /**
     * @param maxLevelOfSylPhrase the maxLevelOfSylPhrase to set
     */
    public void setMaxLevelOfLevellPhrase(int maxLevelOfSylPhrase) {
        this.maxLevelOfPhrase = maxLevelOfSylPhrase;
    }
}
