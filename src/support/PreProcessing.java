/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import vn.hus.nlp.tokenizer.VietTokenizer;

/**
 *
 * @author lelightwin
 */
public class PreProcessing {

    public static VietTokenizer vntoken;
    private static ArrayList<String> puncArr;

    public static void init() {
        vntoken = new VietTokenizer();

        String[] punc = {",", "...", ";", ":", "?", "!", ".", "-"};
        puncArr = new ArrayList<String>();
        puncArr.addAll(Arrays.asList(punc));
    }

    public static ArrayList<String> words(String s) throws FileNotFoundException {
        ArrayList<String> result = new ArrayList<String>();
        String[] tokens = vntoken.segment(s).split(" ");

        for (int i = 0; i < tokens.length; i++) {
            String string = tokens[i];
            String[] check = string.split("_");
            for (int j = 0; j < check.length; j++) {
                String string1 = check[j];
                result.add(string1);
            }
        }
        return result;
    }

    private static void writeXMLOutput(String[] tokens, XMLStreamWriter writer) throws XMLStreamException {
        String s = "";
        for (int i = 0; i < tokens.length; i++) {
            s = s.concat(tokens[i] + " ");
        }
        s = s.trim();
        writer.writeStartDocument();
        writer.writeStartElement("root");
        writer.writeStartElement("sentence");
        writer.writeAttribute("id", "1");
        writer.writeCharacters(s);
        writer.writeStartElement("parse");
        writer.writeAttribute("id", "1");
        for (int i = 0; i < tokens.length; i++) {
            String string = tokens[i];

            if (!puncArr.contains(tokens[i])) {
                writer.writeStartElement("W");
                writer.writeAttribute("level", "1");
                String[] syls = string.split(" ");
                writer.writeCharacters(string);

                if (syls.length > 1) {
                    for (int j = 0; j < syls.length; j++) {
                        String string1 = syls[j];
                        writer.writeStartElement("T");
                        writer.writeAttribute("level", "2");
                        writer.writeCharacters(string1);
                        writer.writeEndElement();
                    }
                }
                writer.writeEndElement();
            } else {
                writer.writeStartElement("punc");
                writer.writeAttribute("level", "1");
                writer.writeCharacters(string);
                writer.writeEndElement();
            }
        }
        writer.writeEndElement();
        writer.writeEndElement();
        writer.writeEndElement();
        writer.writeEndDocument();
    }

    public static void run(String tokenStr, String outputPathFile) throws XMLStreamException, FileNotFoundException, IOException {
        String s = tokenStr;
        XMLOutputFactory factory;
        XMLStreamWriter writer;
        factory = XMLOutputFactory.newInstance();
        writer = factory.createXMLStreamWriter(new FileOutputStream(outputPathFile), "utf-8");

        String[] strs = s.split(" ");
        for (int i = 0; i < strs.length; i++) {
            strs[i] = strs[i].replaceAll("_", " ");
        }
        writeXMLOutput(strs, writer);
        writer.close();
    }

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException, IOException {
    }
}
