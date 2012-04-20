/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XML_Processing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author thaodv-bkit
 */
public class XML_Creator {

    String fileName = "Text_DB.xml";
    XMLOutputFactory xof;
    XMLStreamWriter xmlStrWr;
    FileWriter fileWriter;
    int idOfSentence;
    int idOfSyllable;

    public XMLStreamWriter getXtw() {
        return xmlStrWr;
    }

    public XML_Creator() throws XMLStreamException {
        idOfSentence = 1;
        idOfSyllable = 1;
        xof = XMLOutputFactory.newInstance();
        xmlStrWr = null;
        try {
            fileWriter = new FileWriter(fileName);
            xmlStrWr = xof.createXMLStreamWriter(fileWriter);
        } catch (IOException ex) {
            Logger.getLogger(XML_Creator.class.getName()).log(Level.SEVERE, null, ex);
        }


        xmlStrWr.flush();
        xmlStrWr.close();
    }
    /////////

    public void WriteXML(String str) throws XMLStreamException {
        StringTokenizer strTk = new StringTokenizer(str.trim());
        ArrayList<String> tokenList = new ArrayList();
        for (int i = 0; i < strTk.countTokens(); i++) {
            //System.out.println(strTk.nextToken());
            tokenList.add(strTk.nextToken());
        }
        System.out.println(strTk.countTokens());
        int i = 0;
        while (i < tokenList.size()) {
        }

    }
    /////

    public void WriteXML(ArrayList<String> listLines, File file, int fileIndex) throws XMLStreamException {

        boolean isStartParaghrp = false; // cho biet SILP dang doc co phai la bat dau cua doan van hay ko?
        boolean isEndParaghrp = false;
        xmlStrWr = xof.createXMLStreamWriter(fileWriter);
        {
            xmlStrWr.writeStartElement("File");
            xmlStrWr.writeAttribute("Id", Integer.toString(fileIndex));
            xmlStrWr.writeAttribute("File_Name", file.getName());


            for (int i = 0; i < listLines.size(); i++) {
                StringTokenizer strTkLine = new StringTokenizer(listLines.get(i));
                {
                    String SylName = strTkLine.nextToken(); // token dau tien la ten cua am tiet
                    String StartIndex = strTkLine.nextToken();
                    String EndIndex = strTkLine.nextToken();
                    ////////////////////////////////////////////////////////////

                    //kiem tra xem co phai bat dau file hay ko?
                    //Bat dau 1 cau: xuat hien SILS || xuat hien SILP nhung ko phai o vi tri cuoi cung
                    // xuat hien SILS: ==> chac chan la phan tach cau
                    // xuat hien SILP: ==> StartIndex == 0 thi la bat dau cau, StartIndex!=0 thi la ket thuc
                    ////////////////////////////////////////////////////////////
                    // kiem tra xem co phai bat dau file hay ko?
                    if (StartIndex.compareTo("0") == 0) {
                        isStartParaghrp = true;
                    } else {
                        isStartParaghrp = false;
                    }
                    ////////////////////////////////////////////////////////////
                    // kiem tra xem co phai ket thuc file hay ko?
                    if (i == (listLines.size() - 1)) {
                        isEndParaghrp = true;
                    } else {
                        isEndParaghrp = false;
                    }
                    ////////////////////////////////////////////////////////////
                    if (isStartParaghrp == true) {
                        xmlStrWr.writeStartElement("sentence");
                        xmlStrWr.writeAttribute("id", ((Integer) idOfSentence++).toString());
                        this.WriteSyl(SylName, StartIndex, EndIndex);
                    } else if (isEndParaghrp == true) {
                        this.WriteSyl(SylName, StartIndex, EndIndex);
                        xmlStrWr.writeEndElement();
                    } else if (SylName.compareTo("SILS") == 0) {
                        this.WriteSyl(SylName, StartIndex, EndIndex);
                        xmlStrWr.writeEndElement();
                        xmlStrWr.writeStartElement("sentence");
                        xmlStrWr.writeAttribute("id", ((Integer) idOfSentence++).toString());
                        //this.WriteSyl(SylName, StartIndex, EndIndex);
                    } else {
                        this.WriteSyl(SylName, StartIndex, EndIndex);
                    }
                    ////////////////////////////////////////////////////////////



//                    if (SylName.compareTo("SILP") == 0) {
//                        isStartParaghrp = !isStartParaghrp;
//                        if (isStartParaghrp == true) {
//                            xmlStrWr.writeStartElement("sentence");
//                            xmlStrWr.writeAttribute("id", ((Integer) idOfSentence++).toString());
//                            this.WriteSyl(SylName, StartIndex, EndIndex);
//                        } else {
//                            this.WriteSyl(SylName, StartIndex, EndIndex);
//                            xmlStrWr.writeEndElement();
//                        }
//                    } else {
//                        if (SylName.compareTo("SILS") == 0) {
//                            this.WriteSyl(SylName, StartIndex, EndIndex);
//                            xmlStrWr.writeEndElement();
//                            xmlStrWr.writeStartElement("sentence");
//                            xmlStrWr.writeAttribute("id", ((Integer) idOfSentence++).toString());
//                            this.WriteSyl(SylName, StartIndex, EndIndex);
//                        } else {
//                            this.WriteSyl(SylName, StartIndex, EndIndex);
//                        }
//                    }
                    ////////////////////////////////////////
                    //////////////////
                }
            }
            xmlStrWr.writeEndElement();
        }
        xmlStrWr.flush();
        xmlStrWr.close();
    }
    ////////////////////////////////////////////////////////////////////////////

    public void WriteSyl(String SylName, String StartIndex, String EndIndex) throws XMLStreamException {
        {
            xmlStrWr.writeStartElement("Syllable");
            ////////////////////////////////////////
            {
                xmlStrWr.writeAttribute("Syl_ID", ((Integer)idOfSyllable++).toString());
                xmlStrWr.writeAttribute("Syllable_Name", SylName);
//                xmlStrWr.writeStartElement("Syllable_Name");
//                xmlStrWr.writeCharacters(SylName);
//                xmlStrWr.writeEndElement();
            }
            ///////////////////////////////////////
            {
                xmlStrWr.writeAttribute("Start_Index", StartIndex);
//                xmlStrWr.writeStartElement("Start_Index");
//                xmlStrWr.writeCharacters(StartIndex);
//                xmlStrWr.writeEndElement();
            }
            ///////////////////////////////////////
            {
                xmlStrWr.writeAttribute("End_Index", EndIndex);
//                xmlStrWr.writeStartElement("End_Index");
//                xmlStrWr.writeCharacters(EndIndex);
//                xmlStrWr.writeEndElement();
            }
            ///////////////////////////////////////
            xmlStrWr.writeEndElement();
        }
    }
}
