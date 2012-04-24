
import java.io.*;
import java.util.ArrayList;
import support.FileCopier;
import support.Utility;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lelightwin
 */
public class SemiSyllableSynthesizer {

    private ArrayList<String> wordHasFound;
    private ArrayList<Integer> indexNotFound;
    private ArrayList<String> wordNotFoundHasSSS;
    private ArrayList<String> wordNotFound;
    private ArrayList<String> temp;
    private String sentence;

    public SemiSyllableSynthesizer(String sentence) throws IOException, InterruptedException {
        this.sentence = sentence;
        init();
        createSemiSyllbaleSound(System.getProperty("user.dir")+"/wav/FormOfSPESIALS/wordGroupNeedToSSS.wav");
    }

    /**
     * @function initial set-up
     */
    private void init() {
        wordHasFound = new ArrayList<String>();
        indexNotFound = new ArrayList<Integer>();
        wordNotFoundHasSSS = new ArrayList<String>();
        wordNotFound = new ArrayList<String>();
        temp = new ArrayList<String>();
    }

    /**
     * @function conduct the HOA SUNG semiSyllable synthesizer for the words which were not found
     * @param none
     * @throws IOException 
     * @throws InterruptedException
     */
    private void createSemiSyllbaleSound(String outputSoundFile) throws IOException, InterruptedException {
        String f0 = "210";
        String rate = "0.9";
        String command1 = "TTSiRobo.exe wordGroupNeedToSSS.txt " + f0 + " " + rate;

        Runtime rt = Runtime.getRuntime();
        Process pr1 = rt.exec(command1);
        pr1.waitFor();

        FileCopier.run("wordGroupNeedToSSS.txt.wav", outputSoundFile);
    }

    /**
     * 
     */
    private boolean check(String str1, String str2) {
        String com1 = Utility.deleteSign(str1.trim());
        String com2 = Utility.deleteSign(str2.trim().substring(str2.lastIndexOf(" ") + 1));
        return com1.equals(com2);
    }

    /**
     * @function get data in inputSelectedPhrsFile, temp.txt and store it in wordNotFoundHasSSS and wordHasFound
     * @param inputSelectedPhrsFile
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private void setUpAOData(String inputSelectedPhrsFile) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        BufferedReader bfr1 = new BufferedReader(new InputStreamReader(new FileInputStream(inputSelectedPhrsFile), "UTF-8"));
        BufferedReader bfr2 = new BufferedReader(new InputStreamReader(new FileInputStream("mediate files/indexOfWordNotFound.idx"), "UTF-8"));
        BufferedReader bfr3 = new BufferedReader(new InputStreamReader(new FileInputStream("mediate files/wordNotFound.txt"), "UTF-8"));
        BufferedReader bfr4 = new BufferedReader(new InputStreamReader(new FileInputStream("temp.txt"), "UTF-8"));

        String str1 = "";
        String str2 = "";
        String str3 = "";
        String str4 = "";

        // <editor-fold desc="read data from file">
        // read words which has been found from file
        while ((str1 = bfr1.readLine()) != null) {
            wordHasFound.add(str1.trim());
        }

        // read indexes of words which hasn't been found from file
        while ((str2 = bfr2.readLine()) != null) {
            indexNotFound.add(Integer.parseInt(str2));
        }

        // read words which hasn't been found from file
        while ((str3 = bfr3.readLine()) != null) {
            wordNotFound.add(str3.trim());
        }

        // read the semi-syllable synthesizer's output from file
        while ((str4 = bfr4.readLine()) != null) {
            String word = str4.substring(str4.lastIndexOf(" ") + 1);
            int index = Utility.indexOfWord2(word, wordNotFound);
            if (index != -1) {
                wordNotFoundHasSSS.add(str4.trim());
            } 
        }
        // </editor-fold>

        // <editor-fold desc="process words can not be synthesized">
        ArrayList<Integer> canNotSynthesized = new ArrayList<Integer>();
        for (int i = 0; i < wordNotFound.size(); i++) {
            String string = wordNotFound.get(i);
            int index = Utility.indexOfWord1(string, wordNotFoundHasSSS);
            if (index == -1) {
                canNotSynthesized.add(i);
            }
        }
        for (int i = 0; i < canNotSynthesized.size(); i++) {
            Integer icns = canNotSynthesized.get(i);
            wordNotFoundHasSSS.add(icns, "null");
        }

        for (int i = 0; i < wordNotFoundHasSSS.size(); i++) {
            String string = wordNotFoundHasSSS.get(i);
        }
        // </editor-fold>
        bfr1.close();
        bfr2.close();
        bfr3.close();
        bfr4.close();
    }

    /**
     * @function use the HOA SUNG result for refining the input file for the speech synthesizer
     */
    public void assemblyAndOperation(String inputSelectedPhrsFile) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        setUpAOData(inputSelectedPhrsFile);
        for (int i = 0; i < wordNotFoundHasSSS.size(); i++) {
            String string = wordNotFoundHasSSS.get(i);
//            System.out.println(indexNotFound.size()+"-"+wordNotFoundHasSSS.get(i));
            wordHasFound.add(indexNotFound.get(i) + i, "1 wordGroupNeedToSSS.wav " + string);
        }

        BufferedWriter bfr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(inputSelectedPhrsFile), "UTF-8"));
        for (int i = 0; i < wordHasFound.size() - 1; i++) {
            String string = wordHasFound.get(i);
            bfr.write(string);
            bfr.newLine();
        }
        bfr.write(wordHasFound.get(wordHasFound.size() - 1));
        bfr.close();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
    }
}
