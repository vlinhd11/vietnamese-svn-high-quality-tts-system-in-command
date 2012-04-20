/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XML_Processing;

import Units.SignedVowel;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author thaodv_bkit
 */
public class SignVowelReader {

    private static final String path = System.getProperty("user.dir") + "\\SignVowel.xml";
    private Document doc;
    private HashMap svw = new HashMap();
    private ArrayList<SignedVowel> signVowel = new ArrayList();

    public SignVowelReader(){
        try {
            File file = new File(path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = (Document) db.parse(file);
            doc.getDocumentElement().normalize();
            this.ReadElement("SignVowel", "input", "output", "sign");
        } catch (Exception e) {
        }
    }

    private void ReadElement(String tagName, String elementName1, String elementName2, String elementName3) {

        NodeList nodeLst = doc.getElementsByTagName(tagName);
        for (int i = 0; i < nodeLst.getLength(); i++) {
            Node fstNode = nodeLst.item(i);
            if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                Element fstElmnt = (Element) fstNode;

                NodeList NodeList1 = fstElmnt.getElementsByTagName(elementName1);
                Element Element1 = (Element) NodeList1.item(0);
                NodeList Node1 = Element1.getChildNodes();
                String s1 = ((Node) Node1.item(0)).getNodeValue();
                //System.out.println("First Name : " + ((Node) Node1.item(0)).getNodeValue());

                NodeList NodeList2 = fstElmnt.getElementsByTagName(elementName2);
                Element Element2 = (Element) NodeList2.item(0);
                NodeList Node2 = Element2.getChildNodes();
                String s2 = ((Node) Node2.item(0)).getNodeValue();
                //System.out.println("Last Name : " + ((Node) Node2.item(0)).getNodeValue());

                NodeList NodeList3 = fstElmnt.getElementsByTagName(elementName3);
                Element Element3 = (Element) NodeList3.item(0);
                NodeList Node3 = Element3.getChildNodes();
                String s3 = ((Node) Node3.item(0)).getNodeValue();
                //////////////////////////////
                getSvw().put(new Character(s1.charAt(0)),
                        new SignedVowel(s1.charAt(0),
                        s2.charAt(0),
                        Integer.valueOf(s3).intValue()));
                getSignVowel().add(new SignedVowel(s1.charAt(0),
                        s2.charAt(0),
                        Integer.valueOf(s3).intValue()));
            }
        }
    }

    /**
     * @return the signVowel
     */
    public ArrayList<SignedVowel> getSignVowel() {
        return signVowel;
    }

    /**
     * @return the svw
     */
    public HashMap getSvw() {
        return svw;
    }
}
