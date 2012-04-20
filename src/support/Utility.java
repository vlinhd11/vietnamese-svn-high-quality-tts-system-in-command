package support;


import Constant.VietLetters;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lelightwin
 */
public class Utility {

    /**
     * 
     * @param str
     * @return the fileName except the extension
     */
    private static String deleteExtension(String str) {
        int index = str.lastIndexOf(".");
        if (index == -1) {
            return str;
        } else {
            return str.substring(0, index);
        }
    }

    /**
     * 
     * @param str
     * @return the file Name of the file path denote as str
     */
    public static String getFileName(String str, boolean ex) {
        int index = str.lastIndexOf("/");
        if (index == -1) {
            index = str.lastIndexOf("\\");
        }
        if (index != -1) {
            String name = str.substring(index + 1);
            if (ex) {
                return deleteExtension(name);
            } else {
                return name;
            }
        } else {
            if (ex) {
                return deleteExtension(str);
            } else {
                return str;
            }
        }
    }
    
        /**
     * 
     * @param str
     * @return the unsign string of word
     */
    public static String deleteSign(String word) {
        String result = word;
        for (int i = 0; i < result.length(); i++) {
            String[] vLetters = VietLetters.viLETTERS;
            char ch = result.charAt(i);
            for (int j = 0; j < vLetters.length; j++) {
                String string = vLetters[j];
                int index = string.indexOf(ch);
                if ((index != -1) && (index != 0)) {
                    result = result.replace(ch, string.charAt(0));
                }
            }
        }
        return result;
    }

    /**
     * 
     * @param needToSearch
     * @param motherStr
     * @return the index of the first needToSearh's same-without-sign words
     */
    public static int indexOfWithoutSign(String needToSearch, String motherStr) {
        int index = motherStr.indexOf(needToSearch);
        if (index!=-1){
            return index;
        } else if (motherStr.length() > needToSearch.length()) {

            String input1 = deleteSign(needToSearch);
            String input2 = deleteSign(motherStr);
            return input2.indexOf(input1);
        }
        return -1;
    }

    public static int indexOfWord1(String word, ArrayList<String> strArr){
        for (int i = 0; i < strArr.size(); i++) {
            String string = strArr.get(i);
            String w = string.substring(string.lastIndexOf(" ")).trim();
            if (deleteSign(w).equals(deleteSign(word))){
                return i;
            }
        }
        return -1;
    }

    public static int indexOfWord2(String word, ArrayList<String> strArr){
        for (int i = 0; i < strArr.size(); i++) {
            String string = strArr.get(i);
            if (deleteSign(string.trim()).equals(deleteSign(word.trim()))){
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        XMLOutputFactory factory;
        XMLStreamWriter writer;
        factory = XMLOutputFactory.newInstance();
        writer = factory.createXMLStreamWriter(new FileOutputStream("E:/test.txt"), "UTF-8");
        writer.writeStartElement("title");
        writer.writeCharacters("Nếu còn có ngày mai");
        writer.writeEndElement();
        writer.close();
    }
}
