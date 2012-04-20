/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XML_Processing;

import Units.Syllable;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author thaodv-bkit
 */
public class SyllableAnalysis {

    FileInput fi;
    ArrayList<String> listLines;
    private ArrayList<Syllable> sylDic = new ArrayList<Syllable>();
    ArrayList<String> eachLine;
    Syllable syllable;
    static String[] firstCons = {"NG", "th", "tr", "c", "NJ", "f", "X", "z", "z", "G", "k", "d", "t", "b", "m", "n", "l", "s", "ss", "h", "v", "zr", "p"};
    static String[] firstConType = {"CSN", "CSU", "CSU", "CSU", "CSN", "CFU", "CFU", "CFV", "CFV", "CFV", "CSU", "CSV", "CSU", "CSV", "CSN", "CSN", "CFL", "CFU", "CFU", "CFU", "CFV", "CFV", "CSU"};
    static String semiVowel = "w";
    static String semiVowelType = "SV";
    static String[] vowels = {"M7", "uo", "ie", "M", "7", "o", "e", "E", "7X", "aX", "a", "EX", "aX", "O", "OX", "u", "i"};//17
    static String[] vowelTypes = {"VM", "VB", "VF", "VM", "VM", "VB", "VF", "VF", "VMC", "VMC", "VM", "VFC", "VMC", "VB", "VBC", "VB", "VF"};//17
    static String[] lastPhns = {"p", "t", "k", "m", "n", "NG", "w", "j"};//8
    static String[] lastPhnTypes = {"CSU", "CSU", "CSU", "CSN", "CSN", "CSN", "SV", "SV"};//8
    ////
    String[] csPhoneme =            {"7X", "EX", "aX", "OX", "ie", "M7", "uo", "NG", "th", "tr", "NJ", "ss", "zr"};
    //csPhoneme la dang sampa
    String[] csPhonemeConverted =   {"Z", "A", "B", "Q", "I", "U", "Y", "N", "T", "R", "J", "S", "r"};
    //coPhoneme la dang trong CSDL

    public SyllableAnalysis() {
        fi = new FileInput(new File("PronunDic.dic"));
        listLines = fi.getListLines();
        ReadSylTransDic();
        for (int i = 0; i < getSylDic().size(); i++) {
            Syllable syllab = getSylDic().get(i);
            //System.out.println(" "+syllab.getSylName()+" "+syllab.getIntialPhoneme()+" "+syllab.getMiddlePhoneme()+" "+syllab.getNucleusPhoneme()+" "+syllab.getFinalPhoneme());
            setHalfSyl(syllab);
        }
    }

    private void ReadSylTransDic() {
        for (int i = 0; i < listLines.size(); i++) {
            eachLine = new ArrayList<String>();
            int beginIndex = 0, endIndex = 0;
            while (endIndex != -1) {
                endIndex = listLines.get(i).indexOf(" ", beginIndex);
                if (endIndex == -1) {
                    break;
                }
                eachLine.add(listLines.get(i).substring(beginIndex, endIndex));
                beginIndex = endIndex + 1;
            }
            eachLine.add(listLines.get(i).substring(beginIndex));// am vi cuoi cung
//            syllable = new Syllable();
//            syllable.setSylName(eachLine.get(0));
            getSylDic().add(SetPhonemes(eachLine));
        }
//        for (int i = 0; i < sylDic.size(); i++) {
//            Syllable sss = sylDic.get(i);
//            System.out.print(new DelimitingSign(sss.getSylName()).getStrResult());
//            for (int j = 1; j < eachLine.size(); j++) {
//                System.out.print(" " + eachLine.get(j));
//            }
//            System.out.println(""); // doan code nay dung de tach dau am tiet trong tu dien phien am
//        }
    }

    public Syllable SetPhonemes(ArrayList<String> eachLine) {
        Syllable syl = new Syllable();
        syl.setSylName(eachLine.get(0));
        eachLine.remove(0);
        //kiem tra am vi dau tien co phai la firstCons ko? Neu dung thi remove khoi eachLine
        //kiem tra am vi cuoi cung co phai la lastPhoneme ko? Neu dung thi remove khoi eachLine
        //kiem tra am vi dau tien trong eachLine(da remove firstCons) co phai la semiVowel ko?
        //kiem tra cac am vi con lai (chac chan con lai 1 am vi) co phai la vowels ko?
        for (int i = 0; i < firstCons.length; i++) {
            if (eachLine.get(0).compareTo(firstCons[i]) == 0) {
                syl.setIntialPhoneme(eachLine.get(0));
                syl.setInitialType(firstConType[i]);
                eachLine.remove(0);
                break;
            }
        }
        ////////////////////////////
        for (int i = 0; i < lastPhns.length; i++) {
            if (eachLine.get(eachLine.size() - 1).compareTo(lastPhns[i]) == 0) {
                syl.setFinalPhoneme(lastPhns[i]);
                syl.setFinalType(lastPhnTypes[i]);
                eachLine.remove(eachLine.size() - 1);
                break;
            }
        }
        ////////////////////////////
        if (eachLine.get(0).compareTo(semiVowel) == 0) {
            syl.setMiddlePhoneme(eachLine.get(0));
            syl.setMiddleType(semiVowelType);
            eachLine.remove(0);
        }
        //////////////////////////////
        for (int i = 0; i < vowels.length; i++) {
            if (eachLine.get(0).compareTo(vowels[i]) == 0) {
                syl.setNucleusPhoneme(vowels[i]);
                syl.setNucleusType(vowelTypes[i]);
                eachLine.remove(0);
                break;
            }
        }
        //////////////////////////////
        if (eachLine.size() > 0) {
            System.out.println("oi troi oi, loi roi. line 118 SylTransDic");
        }
        ////////////////////////////
        return syl;
    }
    ////////////


    ////////
    private void setHalfSyl(Syllable syllable) {
        StringBuilder leftHalfSyl = new StringBuilder();
        leftHalfSyl.append("_");
        StringBuilder rightHalfSyl = new StringBuilder();
        String init, middle, nucleus, finalPh;
        init = syllable.getInitialPhoneme();
        middle = syllable.getMiddlePhoneme();
        nucleus = syllable.getNucleusPhoneme();
        finalPh = syllable.getFinalPhoneme();
        for (int i = 0; i < csPhoneme.length; i++) {
            if (init == null) {
            } else if (init.compareTo(csPhoneme[i]) == 0) {
                init = csPhonemeConverted[i];
            }
            if (middle == null) {
            } else if (middle.compareTo(csPhoneme[i]) == 0) {
                middle = csPhonemeConverted[i];
            }
            if (nucleus == null) {
            } else if (nucleus.compareTo(csPhoneme[i]) == 0) {
                nucleus = csPhonemeConverted[i];
            }
            if (finalPh == null) {
            } else if (finalPh.compareTo(csPhoneme[i]) == 0) {
                finalPh = csPhonemeConverted[i];
            }
        }
        if(init!=null) leftHalfSyl.append(init);
        if(middle!=null) leftHalfSyl.append(middle);
        if(nucleus!=null) {
            leftHalfSyl.append(nucleus);
            rightHalfSyl.append(nucleus);
        }
        if(finalPh!=null) rightHalfSyl.append(finalPh);
        rightHalfSyl.append("_");
        syllable.setLeftHalfSyl(leftHalfSyl.toString());
        syllable.setRightHalfSyl(rightHalfSyl.toString());
        //System.out.println(leftHalfSyl + "::" + rightHalfSyl);
    }

    public static void main(String[] args) {
        SyllableAnalysis sylTransDic = new SyllableAnalysis();
        sylTransDic.getTypeOfPhonemeInHalfSylDB();
        System.out.println("");
//        sylTransDic.ReadSylTransDic();
//        for (int i = 0; i < sylTransDic.getSylDic().size(); i++) {
//            Syllable syllab = sylTransDic.getSylDic().get(i);
//            //System.out.println(" "+syllab.getSylName()+" "+syllab.getIntialPhoneme()+" "+syllab.getMiddlePhoneme()+" "+syllab.getNucleusPhoneme()+" "+syllab.getFinalPhoneme());
//            sylTransDic.setHalfSyl(syllab);
//        }

    }

    /**
     * @return the sylDic
     */
    public ArrayList<Syllable> getSylDic() {
        return sylDic;
    }
    
    // lay loai cua am vi duoc ki hieu trong HalfSylDB
    public void getTypeOfPhonemeInHalfSylDB(){
        ArrayList<String> phonemes = new ArrayList<String>();
        ArrayList<String> phonemesType = new ArrayList<String>();
        for (int i = 0; i < firstCons.length; i++) {
            phonemesType.add(firstConType[i]);
            if(firstCons[i].length()==1){
                phonemes.add(firstCons[i]);
            }else {
                for (int j = 0; j < csPhoneme.length; j++) {
                    if(firstCons[i].compareTo(csPhoneme[j])==0){
                        phonemes.add(csPhonemeConverted[j]);
                    }
                }
            }
        }
        for (int i = 0; i < vowels.length; i++) {
            phonemesType.add(vowelTypes[i]);
            if(vowels[i].length()==1){
                phonemes.add(vowels[i]);
            }else {
                for (int j = 0; j < csPhoneme.length; j++) {
                    if(vowels[i].compareTo(csPhoneme[j])==0){
                        phonemes.add(csPhonemeConverted[j]);
                    }
                }
            }
        }
        ///////////////
        //in ra am vi va loai tuong ung
        for (int i = 0; i < phonemes.size(); i++) {
            System.out.print("*"+phonemes.get(i)+"*,\t");
            System.out.println("*"+phonemesType.get(i)+"*,");
        }
        /*
         *
         * {"N","T","R","c","J","f","X","z","z","G","k","d","t","b","m","n","l","s","S","h","v","r","p","U","Y","I","M","7","o","e","E","Z","B","a","A","B","O","Q","u","i","w","j"}
         * {"CSN","CSU","CSU","CSU","CSN","CFU","CFU","CFV","CFV","CFV","CSU","CSV","CSU","CSV","CSN","CSN","CFL","CFU","CFU","CFU","CFV","CFV","CSU","VM","VB","VF","VM","VM","VB","VF","VF","VMC","VMC","VM","VFC","VMC","VB","VBC","VB","VF","SV","SV"}
         */
    }
}
