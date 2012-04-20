/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XML_Processing;

import Units.Sentence;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author thaodv-bkit
 */
public abstract class XML_Reader {

    private XMLInputFactory xMLInputFactory;
    private XMLStreamReader xMLStreamReader;
    private FileInputStream fileInputStream;
    private ArrayList<Sentence> allSentences = new ArrayList<Sentence>();
    
    ////////////////////////////////////////////////////////////////////////////

    public XML_Reader(File inputFile) {
        try {
             fileInputStream = new FileInputStream(inputFile);
        } catch (Exception e) {
        }

    }
    ////////////////////////////////////////////////////////////////////////////

    public XML_Reader(String str) {
        try {
            fileInputStream = new FileInputStream(new File(str));
        } catch (Exception e) {
        }

    }
    ////////////////////////////////////////////////////////////////////////////
//        System.out.println(xMLStreamReader.CHARACTERS);//4
//        System.out.println(xMLStreamReader.COMMENT);//5
//        System.out.println(xMLStreamReader.END_DOCUMENT);//8
//        System.out.println(xMLStreamReader.END_ELEMENT);//2
//        System.out.println(xMLStreamReader.START_DOCUMENT);//7
//        System.out.println(xMLStreamReader.START_ELEMENT);//1
//        System.out.println(xMLStreamReader.NAMESPACE);//13

    // Doc cac thong tin chi tiet
    public abstract void ReadDetails();
    public abstract void ReadSentenceDetails();
    public abstract void ReadPhraseDetails();

    /**
     * @return the xMLStreamReader
     */
    public XMLStreamReader getxMLStreamReader() {
        return xMLStreamReader;
    }

    /**
     * 
     */
    public void setxMLStreamReader() {
        xMLInputFactory = XMLInputFactory.newInstance();
        try {
            xMLStreamReader = xMLInputFactory.createXMLStreamReader(fileInputStream, "utf-8");
        } catch (XMLStreamException ex) {
            Logger.getLogger(XML_Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the allSentences
     */
    public ArrayList<Sentence> getAllSentences() {
        return allSentences;
    }

    /**
     * @param allSentences the allSentences to set
     */
    public void setAllSentences(ArrayList<Sentence> allSentences) {
        this.allSentences = allSentences;
    }

    /**
     * @param indexOfSentence index of sentence to be returned
     */
    public Sentence getSentenceAt(int indexOfSentence){
        return allSentences.get(indexOfSentence);
    }
    
    /**
     * 
     * @param str
     * @return
     */
    public int StrToInt(String str) {
        return Integer.parseInt(str);
    }
}
