/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Units;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author thaodv-bkit
 */
public class SylPhrase extends Phrase {
    private ArrayList<Syllable> syllablesInPh = new ArrayList<Syllable>();
    private Syllable leftSylOfSP, rightSylOfSP;//am tiet ben trai va ben phai cua SP

    public SylPhrase() {
    }

    public void setPhraseLen() {
        int len = 0;
        
        for (int i = 0; i < syllablesInPh.size(); i++) {
            String name = syllablesInPh.get(i).getSylName();
            if ((name.compareTo("SIL") == 0) || (name.compareTo("SILS") == 0) || (name.compareTo("SILP") == 0)) {
            } else {
                len++;
            }
        }
        this.setPhraseLen(len);
    }
    /*
     * dung cho SylPhrase
     */
    public void setContent(){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < syllablesInPh.size(); i++) {
            sb.append(syllablesInPh.get(i).getSylName()).append(" ");
        }
        this.setPhraseContent(sb.toString());
    }
    /**
     * @return the syllablesInPh
     */
    public ArrayList<Syllable> getSyllablesInPh() {
        return syllablesInPh;
    }

    /**
     * @param syllablesInPh the syllablesInPh to set
     */
    public void setSyllablesInPh(ArrayList<Syllable> syllablesInPh) {
        this.syllablesInPh = syllablesInPh;
    }
    
    /*
     *@param indexOfSpace vi tri dau cach tim thay LevelPhrase
     *@return id cua syllable tai vi tri dau cach truyen vao
     */
    public int getSylIDbyIndexOfSpace(int indexOfSpace){      
        int countTokens = new StringTokenizer(this.getPhraseContent().substring(0, indexOfSpace)).countTokens();
        return countTokens;
    }

    /**
     * @return the leftSylOfSP
     */
    public Syllable getLeftSylOfSP() {
        return leftSylOfSP;
    }

   
//    public void setLeftSylOfSP() {
//        //luu y truong hop dau phrase, dau sentence, dau file
//        //kiem tra xem co phai la dau phrase hay ko; neu am tiet dau co id = 0 thi la dau phrase
//        if(syllablesInPh.get(0).getIDofSyllable()!=0){
//            //neu ko phai dau cua phrase thi lam binh thuong
//            leftSylOfSP = syllablesInPh.
//        }
//    }

    /**
     * @return the rightSylOfSP
     */
    public Syllable getRightSylOfSP() {
        return rightSylOfSP;
    }

    /**
     * @param leftSylOfSP the leftSylOfSP to set
     */
    public void setLeftSylOfSP(Syllable leftSylOfSP) {
        this.leftSylOfSP = leftSylOfSP;
    }

    /**
     * @param rightSylOfSP the rightSylOfSP to set
     */
    public void setRightSylOfSP(Syllable rightSylOfSP) {
        this.rightSylOfSP = rightSylOfSP;
    }

}
