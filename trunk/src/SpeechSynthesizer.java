
import java.io.*;
import javax.xml.stream.XMLStreamException;
import support.PreProcessing;
import support.UTF8ToAnsiUtils;
import support.Utility;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lelightwin
 */
public class SpeechSynthesizer {

    private UnitConcatJNI ucjni;
    private String sentence;

    public SpeechSynthesizer() {
    }

    public SpeechSynthesizer(String input) throws FileNotFoundException, IOException, XMLStreamException, UnsupportedEncodingException, InterruptedException {

        init();
        String inputXML = createInputXMLFile(input);
        selectUnitAndConductSpeechSynthesizer(inputXML);
    }

    /**
     * @function inital set-up
     */
    private void init() {
        sentence = "";
    }

    /**
     * @function create the input XML for the Unit Concat
     * @param input
     * @return String
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws XMLStreamException 
     */
    private String createInputXMLFile(String input) throws UnsupportedEncodingException, FileNotFoundException, IOException, XMLStreamException {
        String name = Utility.getFileName(input, true);
        BufferedReader brf = new BufferedReader(new InputStreamReader(new FileInputStream(input), "utf-8"));
        String s = "";
        String str = "";
        boolean firstLine = true;

        while ((str = brf.readLine()) != null) {
            if (firstLine) {
                s = UTF8ToAnsiUtils.removeUTF8BOM(str);
                firstLine = false;
            } else {
                s = s.concat(" " + str);
            }
        }
        brf.close();
        s = s.trim();

        String tokenStr = "";
        PreProcessing.init();
        String outputToken = System.getProperty("user.dir") + "/mediate files/" + name + ".tok";
        BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputToken), "utf-8"));
        tokenStr = PreProcessing.vntoken.segment(s).toLowerCase();

        //p1: store the tokens ArrayList for the SemiSyllableSynthesizer
        String[] strArr = tokenStr.split(" ");
        sentence = "";
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = strArr[i].replace("_", " ");
            sentence = sentence.concat(" " + strArr[i]).trim();
        }
        //p1: End

        bfw.write(tokenStr);
        bfw.close();

        String xmlResult = System.getProperty("user.dir") + "/mediate files/" + name + ".xml";
        PreProcessing.run(tokenStr, xmlResult);

        return xmlResult;
    }

    /**
     * @function process the two main works :
    + unit concat
    + synthesize and play the output sound
     * @param inputXML
     * @throws XMLStreamException
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws IOException 
     */
    private void selectUnitAndConductSpeechSynthesizer(String inputXML) throws XMLStreamException, FileNotFoundException, UnsupportedEncodingException, IOException, InterruptedException {
        ucjni = new UnitConcatJNI(new File(inputXML));
        String reSy = System.getProperty("user.dir") + "/mediate files/reSynthesized.txt";
        BufferedReader brf = new BufferedReader(new FileReader(reSy));
        String str = brf.readLine();
        if (str.equals("true")) {
            SemiSyllableSynthesizer sSSynthesizer = new SemiSyllableSynthesizer(sentence);
            sSSynthesizer.assemblyAndOperation("mediate files/SelectedLPhrs.txt");
        }
        brf.close();
        ucjni.concatUni();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, XMLStreamException, UnsupportedEncodingException, InterruptedException {
        String arg;
        if (args.length == 0) {
            arg = "E:/work/version 4.1/test.txt";
            System.out.println("using default arg \""+arg+"\"");
        } else {
            arg = args[0];
        }
        SpeechSynthesizer speech = new SpeechSynthesizer(arg);
    }
}