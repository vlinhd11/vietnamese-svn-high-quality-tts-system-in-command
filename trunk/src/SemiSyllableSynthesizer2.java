
import java.io.*;
import java.util.ArrayList;
import support.FileCopier;
import support.Utility;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author lelightwin
 */
public class SemiSyllableSynthesizer2 {

    private ArrayList<String> wordNotFound;
    private ArrayList<Integer> indexNotFound;
    private ArrayList<String> wordHasFound;
    private ArrayList<String> wordNotFoundHasSSS;
    private String wordGroupInputFilePath;

    public SemiSyllableSynthesizer2(String fileInput) throws IOException {
        wordNotFound = new ArrayList<String>();
        indexNotFound = new ArrayList<Integer>();
        wordHasFound = new ArrayList<String>();
        wordNotFoundHasSSS = new ArrayList<String>();
        wordGroupInputFilePath = fileInput;
        setUpData();
    }

    /**
     * @function read data from file
     *
     * @throws IOException
     */
    private void setUpData() throws IOException {
        String wnfFile = System.getProperty("user.dir") + "/mediate files/wordNotFound.txt";
        String idxFile = System.getProperty("user.dir") + "/mediate files/indexOfWordNotFound.idx";
        String selectedPhrs = System.getProperty("user.dir") + "/mediate files/SelectedLPhrs.txt";

        // <editor-fold desc="read wordNotFound from file">
        BufferedReader bfr1 = new BufferedReader(new InputStreamReader(new FileInputStream(wnfFile), "utf-8"));
        String data1 = "";
        while ((data1 = bfr1.readLine()) != null) {
            if (!data1.equals("")) {
                wordNotFound.add(data1);
            }
        }
        bfr1.close();
        // </editor-fold>

        // <editor-fold desc="read index from file">
        BufferedReader bfr2 = new BufferedReader(new InputStreamReader(new FileInputStream(idxFile), "utf-8"));
        String data2 = "";
        while ((data2 = bfr2.readLine()) != null) {
            if (!data2.equals("")) {
                indexNotFound.add(Integer.parseInt(data2));
            }
        }
        bfr2.close();
        // </editor-fold>

        // <editor-fold desc="read words which have been synthesized from file">
        BufferedReader bfr3 = new BufferedReader(new InputStreamReader(new FileInputStream(selectedPhrs), "utf-8"));
        String data3 = "";
        while ((data3 = bfr3.readLine()) != null) {
            if (!data3.equals("")) {
                wordHasFound.add(data3);
            }
        }
        bfr3.close();
        // </editor-fold>
    }

    /**
     * @function synthesize word from file and form a wav file
     */
    private void createWavFromfile(String file, String outputSoundFile) throws InterruptedException, IOException {

        // <editor-fold desc="create and copy wav file to corpus dir">
        String f0 = "210";
        String rate = "0.9";
        String command1 = "TTSiRobo.exe \"mediate files\"/wordGroupInput/" + file + " " + f0 + " " + rate;

        Runtime rt = Runtime.getRuntime();
        Process pr1 = rt.exec(command1);
        pr1.waitFor();

        FileCopier.run(System.getProperty("user.dir")+"/mediate files/wordGroupInput/"+file + ".wav", outputSoundFile);
        // </editor-fold>

        // <editor-fold desc="extract information about word from file which has been SSS">
        String tempFile = System.getProperty("user.dir") + "/temp.txt";
        BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(tempFile), "utf-8"));
        String data = "";
        //get the index of word in file
        int i = Integer.parseInt(file.substring(file.lastIndexOf("/") + 1));
        String word = wordNotFound.get(i);
        boolean check = false;

        while ((data = bfr.readLine()) != null) {
            String syl = data.substring(data.lastIndexOf(" ") + 1);
            if (Utility.deleteSign(word).equals(Utility.deleteSign(syl))) {
                String str = i + ".wav " + data;
                wordNotFoundHasSSS.add(str);
                check = true;
                break;
            }
        }

        if (check == false) {
            wordNotFoundHasSSS.add("NULL");
        }
        bfr.close();
        // </editor-fold>
    }

    /**
     * @function do main work
     */
    public void createOutput() throws InterruptedException, IOException {

        // <editor-fold desc="synthesize all the words not found">
        for (int i = 0; i < wordNotFound.size(); i++) {
            createWavFromfile(
                    ""+ i,
                    System.getProperty("user.dir") + "/wav/FormOfSPESIALS/" + i + ".wav");
        }
        // </editor-fold>

        // <editor-fold desc="add word-not-found which has been synthesized to wordHasFound">
        for (int i = 0; i < wordNotFoundHasSSS.size(); i++) {
            String string = wordNotFoundHasSSS.get(i);
            wordHasFound.add(indexNotFound.get(i) + i, "1 " + string);
        }
        // </editor-fold>

        // <editor-fold desc="save result to file">
        BufferedWriter bfw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(wordGroupInputFilePath), "utf-8"));
        for (int i = 0; i < wordHasFound.size(); i++) {
            String str = wordHasFound.get(i);
            bfw1.write(str);
            bfw1.newLine();
        }
        bfw1.close();
        // </editor-fold>
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SemiSyllableSynthesizer2 sss = new SemiSyllableSynthesizer2(System.getProperty("user.dir") + "/mediate files/SelectedLPhrs.txt");
        sss.createOutput();
    }
}
