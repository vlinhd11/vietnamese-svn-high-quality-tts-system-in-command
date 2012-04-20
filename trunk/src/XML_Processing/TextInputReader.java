/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XML_Processing;

import Units.LevelPhrase;
import Units.Sentence;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author thaodv_bkit
 */
public class TextInputReader extends XML_Reader {

    private XMLStreamReader xMLStreamReader;
    private String nameOfElement;
    private Sentence sentence;
    private LevelPhrase levelPhrase;
    private ArrayList<Integer> level;
    int id_levelPhrase;

    public TextInputReader(String str) {
        super(str);
        try {
            setxMLStreamReader();
            xMLStreamReader = getxMLStreamReader();
            ReadDetails();
        } catch (Exception e) {
        }
    }
    ///////////////////////////

    public TextInputReader(File file) {
        super(file);
        try {
            setxMLStreamReader();
            xMLStreamReader = getxMLStreamReader();
            ReadDetails();
        } catch (Exception e) {
        }
    }

    @Override
    public void ReadDetails() {

        try {
            while (xMLStreamReader.hasNext()) {
                Integer eventType = xMLStreamReader.next();
//                if (eventType.equals(XMLEvent.START_ELEMENT)) {
//                    System.out.println("START_ELEMENT " + xMLStreamReader.getName() + " " + xMLStreamReader.getAttributeCount());
//                } else if (eventType.equals(XMLEvent.CHARACTERS)) {
//                    System.out.println("CHARACTERS " + xMLStreamReader.getText() + " ");
//                    //ko thay in ra gi
                if (eventType.equals(XMLEvent.START_ELEMENT)) {
                    //neu element la "sentence" thi tao moi 1 cau, getSentenceContent
                    //neu element la "parse" thi ko lam j ca
                    //neu element la cac phrase thi doc phrase
                    nameOfElement = xMLStreamReader.getName().toString();

                    if (nameOfElement.compareTo("sentence") == 0) {
                        ReadSentenceDetails();
                        addSilsToSen("SILS");
                    } else if (nameOfElement.compareTo("parse") == 0) {
                        ReadParseDetails();
                    } else if (nameOfElement.compareTo("root") == 0) {
                    } else if (nameOfElement.compareTo("punc") == 0) {
                        this.addSilToSen("SIL");
                    } else {
                        ReadPhraseDetails();
                    }
                } else if (eventType.equals(XMLEvent.CHARACTERS)) {
                } else if (eventType.equals(XMLEvent.END_ELEMENT)) {
                    // so sanh
                    nameOfElement = xMLStreamReader.getName().toString();
                    //////////
                    if (nameOfElement.compareTo("sentence") == 0) {
                        this.setLevelOfPhrase();
                        //this.addSubLevel();
                        //this.setLevelOfPhrase();//set lai level sau khi da add them subLevel
                        this.addSilsToSen("SILS");
                        this.getAllSentences().add(sentence);
                    } else if (nameOfElement.compareTo("root") == 0) {
                        //this.addSilsToSen("SILS");
                    }
                } else if (eventType.equals(XMLEvent.END_DOCUMENT)) {
                } else {
                    System.out.println("Don't know this event");
                }
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(TextInputReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    ///////////

    public void ReadSentenceDetails() {
        id_levelPhrase = 0;
        sentence = new Sentence();
        level = new ArrayList<Integer>();
        sentence.setiDofFile(StrToInt(xMLStreamReader.getAttributeValue(0)));
        try {
            xMLStreamReader.next();
            sentence.setSenContent(xMLStreamReader.getText().trim());
        } catch (XMLStreamException ex) {
            Logger.getLogger(TextInputReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /////////////

    public void ReadParseDetails() {
        xMLStreamReader.getAttributeValue(0);
    }
    ////////////

    public void ReadPhraseDetails() {
        levelPhrase = new LevelPhrase();
        levelPhrase.setId_phrase(id_levelPhrase++);
        int lv = StrToInt(xMLStreamReader.getAttributeValue(0));
        levelPhrase.setLevel(lv);
        try {
            xMLStreamReader.next();
            levelPhrase.setPhraseContent(xMLStreamReader.getText().trim());
            levelPhrase.setSyllableIn();
        } catch (XMLStreamException ex) {
            Logger.getLogger(TextInputReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        sentence.getLevelPhrases().add(levelPhrase);
        level.add(lv);
    }
    //////////////

    public void addSilsToSen(String str) {
        levelPhrase = new LevelPhrase();
        levelPhrase.setId_phrase(id_levelPhrase++);
        levelPhrase.setLevel(1);
        levelPhrase.setPhraseContent(str);
        levelPhrase.setSyllableIn();
        sentence.getLevelPhrases().add(levelPhrase);
        level.add(1);
    }
    //////////

    public void addSilToSen(String str) {
        levelPhrase = new LevelPhrase();
        levelPhrase.setId_phrase(id_levelPhrase++);
        int lv = StrToInt(xMLStreamReader.getAttributeValue(0));
        levelPhrase.setLevel(lv);
        levelPhrase.setPhraseContent(str);
        levelPhrase.setSyllableIn();
        sentence.getLevelPhrases().add(levelPhrase);
        level.add(lv);
    }

    private void setLevelOfPhrase() {
        //1221122233
        int currentLevel = 1;
        int currentLevelIndex = 0;

        while (true) {
            boolean isFound = false;
            for (int i = 0; i < level.size(); i++) {
                if ((int) level.get(i) == currentLevel) {
                    //neu phan tu hien tai co gia tri = currentLevel thi cap nhat lai vi tri cua currentLevel
                    currentLevelIndex = i;
                    isFound = true;
                    for (int j = currentLevelIndex + 1; j < level.size(); j++) {
                        //ke tu vi tri hien tai, neu
                        int sub = (int) level.get(j) - currentLevel;
                        if (sub == 1) {
                            sentence.getLevelPhrases().get(currentLevelIndex).addIndexOfSubLevel(j);
                        } else if (sub <= 0) {
                            break;
                        }
                    }
                }
            }

            if (!isFound) {
                sentence.setMaxLevelOfLevellPhrase(currentLevel - 1);
                //System.out.println("max: "+sentence.getMaxLevelOfLevelPhrase());
                break;
            }
            currentLevel++;
        }
    }
    ////////////////////////////////

    public void addSubLevel() {
        ArrayList<LevelPhrase> lps = sentence.getLevelPhrases();
        int size = lps.size();
        for (int i = size - 1; i >= 0; i--) {
            //kiem tra phrase thu i co subLevel va Len >=2 hay ko
            //neu khong co subLevel va Len >=2 thi tao them cac LevelPhrase
            //add LevelPhrase vao vi tri sau vi tri thu i
            //thiet lap subLevelIndex cua phrase thu i la index cua cac
            //thiet lap lai maxLevel cua sentence
            int phraseLen = lps.get(i).getPhraseLen();
            ArrayList<String> syllableIn = lps.get(i).getSyllableIn();
            if ((!lps.get(i).haveSubLevel()) && (phraseLen > 1)) {
                for (int j = 0; j < phraseLen; j++) {
                    LevelPhrase lvphs = new LevelPhrase();
                    lvphs.setId_phrase(id_levelPhrase++);
                    int m = lps.get(i).getLevel() + 1;
                    lvphs.setLevel(m);
                    if (m > sentence.getMaxLevelOfLevelPhrase()) {
                        sentence.setMaxLevelOfLevellPhrase(m);
                    }
                    lvphs.setPhraseContent(syllableIn.get(j).toString());
                    //lps.get(i).addIndexOfSubLevel(i + j+1);
                    sentence.getLevelPhrases().add(i + j + 1, lvphs);
                }
            }

        }
    }

    public void printDetails() {
        for (int i = 0; i < this.getAllSentences().size(); i++) {
            ArrayList<LevelPhrase> levelPhrases = this.getAllSentences().get(i).getLevelPhrases();
            for (int j = 0; j < levelPhrases.size(); j++) {
                System.out.println(i + " : " + j + " : " + levelPhrases.get(j).getPhraseContent() + " : " + levelPhrases.get(j).getLevel());
            }
        }
    }

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        String textDBLocation = System.getProperty("user.dir") + "\\result2.xml";
        TextInputReader textInputReader = new TextInputReader(textDBLocation);
        //textInputReader.printDetails();
    }
}
