/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UnitSelection;

import Units.*;
import XML_Processing.DelimitingSign;
import XML_Processing.SyllableAnalysis;
import XML_Processing.TextDBReader;
import XML_Processing.TextInputReader;
import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import javax.xml.stream.XMLStreamException;
import support.PreProcessing;

/**
 *
 * @author thaodv-bkit
 */
public class UnitSearching {

    /**
     * @return the allSenInTextDB
     */
    //static TextInputReader textInputReader = new TextInputReader(System.getProperty("user.dir") + "\\sentest\\sen7.xml");
    TextInputReader textInputReader;
    static TextDBReader textDBReader = new TextDBReader(System.getProperty("user.dir") + "/data/Text_DB_Creator.xml");
    static SyllableAnalysis sylTransDic = new SyllableAnalysis();
    private static ArrayList<Sentence> allSenInTextDB;
    ArrayList<Sentence> allSenInTextInput;
    ArrayList<Syllable> sylDic;
    ArrayList<HalfSyl> allHalfSyls;
    //static HalfSylDBReader halfSylDBReader = new HalfSylDBReader();
    Stack<Integer> indexesOfLP;
    private ArrayList<LevelPhrase> foundLPhs = new ArrayList<LevelPhrase>();
    private String reSynthesized;

    public UnitSearching() {
    }
    ////////////////////////////////

    public UnitSearching(File inputTextFile) throws XMLStreamException, FileNotFoundException, UnsupportedEncodingException, IOException {
        //textDBReader = new TextDBReader(System.getProperty("user.dir") + "\\Text_DB_Creator.xml");
        //textInputReader = new TextInputReader(System.getProperty("user.dir") + "\\result7_fixed.xml");
        allSenInTextDB = textDBReader.getAllSentences();
        textInputReader = new TextInputReader(inputTextFile);
        allSenInTextInput = textInputReader.getAllSentences();
        sylDic = sylTransDic.getSylDic();
        reSynthesized = "true";
        //textInputReader.printDetails();
        //allHalfSyls = halfSylDBReader.getAllHalfSyls();
        searchTextInput();
        setPosInSenOfPhrs();
    }
    /////////////////////////////////

    public static ArrayList<Sentence> getAllSenInTextDB() {
        return allSenInTextDB;
    }

    private void searchTextInput() throws XMLStreamException, FileNotFoundException, UnsupportedEncodingException, IOException {
        for (int i = 0; i < allSenInTextInput.size(); i++) {
            searchSentenceByLP(allSenInTextInput.get(i));
        }
    }

    private void addOutputGWtoFile(String str1, String str2, ArrayList<String> strs) {
        if (!strs.isEmpty()) {
            //System.out.println(str2);
            String string = strs.get(strs.size() - 1);
            if (string.endsWith(str1)) {
                strs.set(strs.size() - 1, string + str2.substring(str2.lastIndexOf(" ")));
            } else {
                strs.add(str2);
            }
        } else {
            strs.add(str2);
        }
    }

    /**
     *
     */
    public void searchSentenceByLP(Sentence s) throws XMLStreamException, FileNotFoundException, UnsupportedEncodingException, IOException {
        // lay chi so cua cac LevelPhrase co level = 1
        // tim kiem bat dau tu LevelPhrase co chi so la phan tu dau tien trong indexes
        // doi voi LevelPhrase ko tim thay thi kiem tra LP do co subLevel hay ko?
        // neu LP do co subLevel thi thay LP do = cac SubLevelPhrase cua LP do neu co subLP
        // neu LP do ko co subLevel thi chuyen sang LP tiep theo
        // remove gia tri trong indexes cua LP do
        // add chi so cua SubLP cua LP do vao stack indexes tai vi tri cua LP do
        // tim kiem ket thuc khi indexesOfLP empty
        String words = s.getSenContent();

        indexesOfLP = new Stack<Integer>();
        int topOfStack;
        boolean isFoundLevelPhrase;
        ArrayList<Integer> indexesOfLevelPhrase = s.getIndexesOfPhraseByLevel(1);
        //// chuyen indexesOfLevelPhrase tu Array sang Stack
        addIndexesOfLPtoStack(indexesOfLevelPhrase);
        // in ra indexes ban dau
        //System.out.println("indexesOfLP : "+indexesOfLP.toString());
        // bat dau tim kiem LP
        ArrayList<String> strArr = PreProcessing.words(s.getSenContent());
        ArrayList<String> ogf = new ArrayList<String>();
        ArrayList<Integer> idnf = new ArrayList<Integer>();
        ArrayList<String> wnf = new ArrayList<String>();

        while (!indexesOfLP.isEmpty()) {
            topOfStack = (int) indexesOfLP.pop();
            LevelPhrase topLP = s.getLevelPhrases().get(topOfStack);

            isFoundLevelPhrase = searchLevelPhrase(topLP);
            if (!isFoundLevelPhrase) {
                //neu ko tim thay LP
                if (topLP.haveSubLevel()) {
                    //System.out.println("IndexesOfSubLevel of Top "+topLP.getIndexesOfSubLevel().toString());
                    addIndexesOfLPtoStack(topLP.getIndexesOfSubLevel());
                    //System.out.println("indexesOfLP : "+indexesOfLP.toString());
                    //System.out.println("ko thay: " + topLP.getPhraseContent()+" :va da add subLevel de tim kiem");
                } else {
                    String outputGWtoFile = "";
                    String word = topLP.getPhraseContent().trim();
                    int index = strArr.indexOf(word);
                    if (index == 0) {
                        outputGWtoFile = "SILS " + strArr.get(index) + " " + strArr.get(index + 1);
                        strArr.remove(index);
                    } else if (index == (strArr.size() - 1)) {
                        outputGWtoFile = strArr.get(index - 1) + " " + strArr.get(index) + " SILS";
                        strArr.remove(index);
                    } else if (index != -1) {
                        outputGWtoFile = strArr.get(index - 1) + " " + strArr.get(index) + " " + strArr.get(index + 1);
                        strArr.remove(index);
                    }
                    addOutputGWtoFile(word, outputGWtoFile, ogf);
                    idnf.add(getFoundLPhs().size());
                    wnf.add(word);
//                    //neu ko tim thay LP va LP ko co subLevel de tim kiem
//                    //topLP.setIsFound(2);
//                    //tim kiem trong tu dien am tiet va ban am tiet                    
//                    if (!checkSyllableInSylDic(topLP)) {
//                        System.out.println("ko thay: " + topLP.getPhraseContent() + " :va LP nay ko co subLevel ");
//                        topLP.setPhraseContent("SILS");
////                        getFoundLPhs().add(topLP);
//                    } else {
//                        //searchSyllableInHalfSylDB(topLP);
//                    }
//                    getFoundLPhs().add(topLP);
//                    //System.out.println("so luong LP tim thay" + getFoundLPhs().size());
                }
            } else {
                //neu tim thay LP
                topLP.setIsFound(1);
                getFoundLPhs().add(topLP);
            }
        }
        ////////////////////////////////////////////////////////////////////
        if (wnf.isEmpty()) {
            reSynthesized = "false";
        } else {

            String dir = System.getProperty("user.dir") + "/mediate files/";
            // <editor-fold desc="save index of wordNotFound">
            String file1 = dir + "indexOfWordNotFound.idx";
            BufferedWriter bfw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1), "utf-8"));
            for (int i = 0; i < idnf.size(); i++) {
                Integer index = idnf.get(i);
                bfw1.write(index+"");
                bfw1.newLine();
            }
            bfw1.close();
            // </editor-fold>

            // <editor-fold desc="save word not found">
            String file2 = dir + "wordNotFound.txt";
            BufferedWriter bfw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2), "utf-8"));
            for (int i = 0; i < wnf.size(); i++) {
                String str = wnf.get(i);
                bfw2.write(str);
                bfw2.newLine();
            }
            bfw2.close();
            // </editor-fold>

            // <editor-fold desc="save word group which are needed to be semi-syllable-synthesized">
            String dir3 = dir + "wordGroupInput/";
            for (int i = 0; i < ogf.size(); i++) {
                String str = ogf.get(i);
                String fileName = dir3 + "" + i;
                BufferedWriter bfw3 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
                bfw3.write(str);
                bfw3.close();
            }
            // </editor-fold>
        }

        String rsFile = System.getProperty("user.dir") + "/mediate files/reSynthesized.txt";
        PrintWriter pw = new PrintWriter(rsFile);
        pw.print(reSynthesized);
        pw.close();

    }

    /**
     *
     */
    public void addIndexesOfLPtoStack(ArrayList<Integer> indexesOfLevelPhrase) {
        for (int i = indexesOfLevelPhrase.size() - 1; i >= 0; i--) {
            indexesOfLP.push((int) indexesOfLevelPhrase.get(i));
        }
    }

    /**
     *
     */
    public boolean searchLevelPhrase(LevelPhrase levelPhrase) {
        String phContentToSearch = (" " + levelPhrase.getPhraseContent().trim() + " ");
        int indexFound = 0;
        boolean isFound = false;
        int sylID;
        for (int i = 0; i < getAllSenInTextDB().size(); i++) {
            ArrayList<SylPhrase> sylPhrasesInSen = getAllSenInTextDB().get(i).getSylPhrases();
            for (int j = 0; j < sylPhrasesInSen.size(); j++) {
                // tim kiem phContentToSearch trong SylPhraseContent
                // khi tim kiem lan dau thay
                // va subString con lai co length > phContentToSearch.length
                // thi tiep tuc tim kiem trong subString con lai
                String phrsContent = sylPhrasesInSen.get(j).getPhraseContent();
//                indexFound = Utility.indexOfWithoutSign(phContentToSearch, phrsContent);
                indexFound = phrsContent.indexOf(phContentToSearch);

//                if (indexFound >= 0) {
//                    isFound = true;
//                    sylID = sylPhrasesInSen.get(j).getSylIDbyIndexOfSpace(indexFound);
//                    System.out.println("tim thay: " + phContentToSearch + ": tai syllable co ID =: " + sylID+ " :tai phrase thu: "+j+": cua cau thu: " + i);
//                    System.out.println(phContentToSearch + " - " + sylID);
//                    phrsContent = " " + phrsContent.substring(indexFound + phContentToSearch.length()).trim() + " ";
//                }
                while (indexFound >= 0 && phrsContent.length() >= phContentToSearch.length()) {
//                    indexFound = Utility.indexOfWithoutSign(phContentToSearch, phrsContent);
                    indexFound = phrsContent.indexOf(phContentToSearch);

                    if (indexFound == -1) {
                        break;
                    } else if (indexFound >= 0) {
                        if (phContentToSearch.trim().compareTo("SIL") == 0 || phContentToSearch.trim().compareTo("SILS") == 0 || phContentToSearch.trim().compareTo("SILP") == 0) {
                            if (levelPhrase.getFoundIndexes1().size() == 1) {
                                break;
                            }
                        }
                        isFound = true;
                        sylID = sylPhrasesInSen.get(j).getSylIDbyIndexOfSpace(indexFound);
                        levelPhrase.getFoundIndexes1().add(i);
                        levelPhrase.getFoundIndexes2().add(j);
                        levelPhrase.getFoundIndexes3().add(sylID);
                        levelPhrase.getFoundIndexes4().add(sylID + levelPhrase.getPhraseLen() - 1);

                        //System.out.println("tim thay: " + phContentToSearch + ": tai syllable co ID =: " + sylID+ " :tai phrase thu: "+j+": cua cau thu: " + i);
                        //System.out.println(phContentToSearch + " : " + sylID);
                        phrsContent = " " + phrsContent.substring(indexFound + phContentToSearch.length()).trim() + " ";
                        if (levelPhrase.getFirstSylInLPhrs() == null) {
                            levelPhrase.setFirstSylInLPhrs(sylPhrasesInSen.get(j).getSyllablesInPh().get(sylID));
                            levelPhrase.setLastSylInLPhrs(sylPhrasesInSen.get(j).getSyllablesInPh().get(sylID + levelPhrase.getPhraseLen() - 1));
                        }

                    }
                }
            }
        }
        return isFound;
    }

    /**
     * @return the foundLPhs
     */
    public ArrayList<LevelPhrase> getFoundLPhs() {
        return foundLPhs;
    }

    private void setPosInSenOfPhrs() {
        int posToSet = 0;
        for (int i = 0; i < getFoundLPhs().size(); i++) {
            String phraseContent = getFoundLPhs().get(i).getPhraseContent();
            if ((phraseContent.compareTo("SIL") == 0) || (phraseContent.compareTo("SILS") == 0)) {
                posToSet = 0;
            } else {
                getFoundLPhs().get(i).setPosInSen(posToSet);
                posToSet += getFoundLPhs().get(i).getPhraseLen();
            }
        }
    }

    private boolean checkSyllableInSylDic(LevelPhrase topLP) {
        DelimitingSign delimittedSignLP = new DelimitingSign(topLP.getPhraseContent());
        String strResult = delimittedSignLP.getStrResult().trim();
        //topLP.getFirstSylInLPhrs().setSylTone(delimittedSignLP.getSign());
        for (int i = 0; i < sylDic.size(); i++) {
            //System.out.println("tim kiem "+topLP.getPhraseContent()+"::"+sylDic.get(i).getSylName());
            if (strResult.compareTo(sylDic.get(i).getSylName()) == 0) {
                topLP.setFirstSylInLPhrs(sylDic.get(i));
                topLP.getFirstSylInLPhrs().setSylTone(delimittedSignLP.getSign());
                //System.out.println("da tim thay trong ban am tiet");
                topLP.setIsFound(2);
                return true;
            }
        }
        topLP.setIsFound(0);
        return false;
    }

    private void searchSyllableInHalfSylDB(LevelPhrase topLP) {
        for (int i = 0; i < allHalfSyls.size(); i++) {
            if (topLP.getFirstSylInLPhrs().getLeftHalfSyl().compareTo(allHalfSyls.get(i).getHalfSylName()) == 0) {
                //System.out.println("tim thay " + topLP.getFirstSylInLPhrs().getLeftHalfSyl() + " tai vi tri " + i);
                topLP.getFoundIndexes1().add(i);
            }
            if (topLP.getFirstSylInLPhrs().getRightHalfSyl().compareTo(allHalfSyls.get(i).getHalfSylName()) == 0) {
                //System.out.println("tim thay " + topLP.getFirstSylInLPhrs().getRightHalfSyl() + " tai vi tri " + i);
                topLP.getFoundIndexes2().add(i);
            }
        }
    }

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
    }
}
