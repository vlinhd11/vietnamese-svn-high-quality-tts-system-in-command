/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XML_Processing;

import Units.Sentence;
import Units.Syllable;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author thaodv_bkit
 */
// tu ngay 14/04/2011 ko can dung class nay nua
public class XMLTextDBSylReader extends XML_Reader {

    private XMLStreamReader xMLStreamReader;
    private ArrayList<Sentence> allSentencesInText = new ArrayList<Sentence>();
    Sentence sentence;
    int fileID;
    String fileName;
    Syllable syllable;
    private ArrayList<Syllable> sylArray = new ArrayList<Syllable>();

    public XMLTextDBSylReader(String str) throws XMLStreamException, FileNotFoundException {
        super(str);
        setxMLStreamReader();
        xMLStreamReader = getxMLStreamReader();
    }
    /////////////////////////////////////

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
                    // neu eventType =  START_ELEMENT thi kiem tra, neu Name = File thi ReadFile,
                    //      neu Name = root thi bo qua, neu Name = Syllable thi ReadSyl
                    // neu eventType = CHARACTERS thi bo qua
                    // neu eventType = END_ELEMENT thi kiem tra, neu Name = Syllable thi bo qua
                    //      neu Name = File thi ket thuc file
                    //      neu Name = sentence thi ket thuc cau
                    if (xMLStreamReader.getName().toString().compareTo("File") == 0) {
                        ReadFileDetails();
                    } else if (xMLStreamReader.getName().toString().compareTo("sentence") == 0) {
                        ReadSentenceDetails();
                    } else if (xMLStreamReader.getName().toString().compareTo("Syllable") == 0) {
                        ReadSylDetails();
                    } else if (xMLStreamReader.getName().toString().compareTo("root") == 0) {
                        continue;
                    } else {
                        System.out.println("DON'T KNOW THIS START_ELEMENT");
                    }
                } else if (eventType.equals(XMLEvent.END_ELEMENT)) {
                    if (xMLStreamReader.getName().toString().compareTo("sentence") == 0) {
                        getAllSentencesInText().add(sentence);
                    }
                } else if (eventType.equals(XMLEvent.CHARACTERS)) {
                    continue;
                } else if (eventType.equals(XMLEvent.END_DOCUMENT)) {
                    continue;
                } else {
                    System.out.println(eventType);
                    System.out.println("DON'T KNOW THIS EVENT");
                }

            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(XMLTextDBSylReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            xMLStreamReader.close();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XMLTextDBSylReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        ////////////////////////////////////////////////////////////////////////
        // in ra ket qua
//        for (int i = 0; i < allSentencesInText.size(); i++) {
//            int sizeOfSen = allSentencesInText.get(i).getSyllablesInSen().size();
//            for (int j = 0; j < sizeOfSen; j++) {
//                getSylArray().add(allSentencesInText.get(i).getSyllablesInSen().get(j));
//            }
//        }
        System.out.println(getSylArray().size());
    }
    ////////////////////////////////////////////////////////////////////////////

    public void ReadFileDetails() {
        fileID = StrToInt((xMLStreamReader.getAttributeValue(0)));
        fileName = xMLStreamReader.getAttributeValue(1);
        // System.out.println(fileID + " " + fileName);

    }
    ////////////////////////////////////////////////////////////////////////////

    public void ReadSentenceDetails() {
        sentence = new Sentence();
        sentence.setCarryingFile(fileName);
        sentence.setIDofSentence(StrToInt(xMLStreamReader.getAttributeValue(0)));
    }
    ////////////////////////////////////////////////////////////////////////////

    public void ReadSylDetails() {
        syllable = new Syllable();
        syllable.setIDofSyllable(StrToInt(xMLStreamReader.getAttributeValue(0)));
        syllable.setSylName(xMLStreamReader.getAttributeValue(1));
        syllable.setStartIndex(StrToInt(xMLStreamReader.getAttributeValue(2)));
        syllable.setEndIndex(StrToInt(xMLStreamReader.getAttributeValue(3)));
        sylArray.add(syllable);
    }
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    public void ReadListOfSyllable(ArrayList<String> ListLines) {
        //Name	Tone	Length	Position	PhraseLen	Pos/Len	NumOfPhone
        //LeftSyl	L-Tone	RighSyl	R-Tone	CarryingFile	Initial	Middle
        //Nucleus	Final	LeftPhone	RightPhone	InitialType
        //MiddleType	NucleusType	FinalType	LeftPhoneType	RightPhoneType
        //Energy
        int indexOfSyl = 0;
        boolean setSyllable;
        for (int i = 1; i < ListLines.size(); i++, indexOfSyl++) {
            StringTokenizer tokenizer = new StringTokenizer(ListLines.get(i));
            while (tokenizer.hasMoreTokens()) {
                String name = tokenizer.nextToken();//
                int tone = StrToInt(tokenizer.nextToken());//
                int length = StrToInt(tokenizer.nextToken());
                int position = StrToInt(tokenizer.nextToken());
                int PhraseLen = StrToInt(tokenizer.nextToken());//
                String posPlen = tokenizer.nextToken();
                int numOfPhone = StrToInt(tokenizer.nextToken());//
                String LSyl = tokenizer.nextToken();
                int LTone = StrToInt(tokenizer.nextToken());
                String RSyl = tokenizer.nextToken();
                int RTone = StrToInt(tokenizer.nextToken());
                String carryingFile = tokenizer.nextToken();//
                String initial = tokenizer.nextToken();//
                String middle = tokenizer.nextToken();//
                String nucleus = tokenizer.nextToken();//
                String finalPh = tokenizer.nextToken();//
                //Nucleus	Final	LeftPhone	RightPhone	InitialType	
                //MiddleType	NucleusType	FinalType	LeftPhoneType	RightPhoneType
                //Energy
                String lPhone = tokenizer.nextToken();
                String rPhone = tokenizer.nextToken();
                String initialType = tokenizer.nextToken();//
                String middleTYpe = tokenizer.nextToken();//
                String nucleusType = tokenizer.nextToken();//
                String finalType = tokenizer.nextToken();//
                String lPhoneType = tokenizer.nextToken();
                String rPhoneType = tokenizer.nextToken();
                float energy = (Float.parseFloat(tokenizer.nextToken()));//
                //System.out.println("energy "+energy);

                setSyllable = getSylArray().get(indexOfSyl).setSyllable(name, tone, PhraseLen, numOfPhone, carryingFile, initial, middle, nucleus, finalPh, initialType, middleTYpe, nucleusType, finalType, energy, LSyl, RSyl, LTone, RTone, lPhone, rPhone, lPhoneType, rPhoneType);
                if (setSyllable == false) {
                    System.out.println("break:" + indexOfSyl + " " + i);
                }


            }

        }

    }

    /*
     * public static void main(String[] args) { try { XMLTextDBSylReader
     * xMLTextDBSylReader = new XMLTextDBSylReader("Text_DB_Syllable.xml");
     * xMLTextDBSylReader.ReadDetails();
     *
     *
     * FileInput f = new FileInput("ListOfSyllable.tdd");
     * xMLTextDBSylReader.ReadListOfSyllable(f.getListLines());
     *
     *
     * } catch (XMLStreamException ex) {
     * Logger.getLogger(XMLTextDBSylReader.class.getName()).log(Level.SEVERE,
     * null, ex); } catch (FileNotFoundException ex) {
     * Logger.getLogger(XMLTextDBSylReader.class.getName()).log(Level.SEVERE,
     * null, ex); }
     *
     *
     * }
     */
    /**
     * @return the allSentencesInText
     */
    public ArrayList<Sentence> getAllSentencesInText() {
        return allSentencesInText;
    }

    /**
     * @return the sylArray
     */
    public ArrayList<Syllable> getSylArray() {
        return sylArray;
    }

    /**
     * @param sylArray the sylArray to set
     */
    public void setSylArray(ArrayList<Syllable> sylArray) {
        this.sylArray = sylArray;
    }

    @Override
    public void ReadPhraseDetails() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
