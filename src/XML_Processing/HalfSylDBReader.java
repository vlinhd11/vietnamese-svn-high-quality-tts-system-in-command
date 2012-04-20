/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XML_Processing;

import Units.HalfSyl;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author thaodv_bkit
 */
public class HalfSylDBReader {

    ArrayList<HalfSyl> allHalfSyls = new ArrayList<HalfSyl>();
    String[] phonemes = {"N", "T", "R", "c", "J", "f", "X", "z", "z", "G", "k", "d", "t", "b", "m", "n", "l", "s", "S", "h", "v", "r", "p", "U", "Y", "I", "M", "7", "o", "e", "E", "Z", "B", "a", "A", "B", "O", "Q", "u", "i", "w", "j"};
    String[] phonemeTypes = {"CSN", "CSU", "CSU", "CSU", "CSN", "CFU", "CFU", "CFV", "CFV", "CFV", "CSU", "CSV", "CSU", "CSV", "CSN", "CSN", "CFL", "CFU", "CFU", "CFU", "CFV", "CFV", "CSU", "VM", "VB", "VF", "VM", "VM", "VB", "VF", "VF", "VMC", "VMC", "VM", "VFC", "VMC", "VB", "VBC", "VB", "VF", "SV", "SV"};

    /**
     * @return the allHalfSyls
     */
    public ArrayList<HalfSyl> getAllHalfSyls() {
        return allHalfSyls;
    }
    ArrayList<String> listLines;
    HalfSyl halfSyl;

    public HalfSylDBReader() {
        listLines = new FileInput("halfSylInfosDB2.txt").getListLines();
        readEachLine();
    }
//UnitName bDeleted wTranPoint dwDipLen UnitType bTone bLeftTone
//bRightTone dwLowFEnergy dwHighFEnergy cLeftUnitName cRightUnitName

    private void readEachLine() {
        for (int i = 0; i < listLines.size(); i++) {
            halfSyl = new HalfSyl();
            StringTokenizer stk = new StringTokenizer(listLines.get(i));
            halfSyl.setHalfSylIndex(i);
            halfSyl.setHalfSylName(stk.nextToken());
            halfSyl.setbDeleted(StrToInt(stk.nextToken().trim()));
            halfSyl.setwTranPoint(StrToInt(stk.nextToken().trim()));
            halfSyl.setDwDipLen(StrToInt(stk.nextToken().trim()));
            halfSyl.setUnitType(StrToInt(stk.nextToken().trim()));
            halfSyl.setTone(StrToInt(stk.nextToken().trim()));
            halfSyl.setLeftTone(StrToInt(stk.nextToken().trim()));
            halfSyl.setRightTone(StrToInt(stk.nextToken().trim()));
            halfSyl.setLowEnergy(StrToInt(stk.nextToken().trim()));
            halfSyl.setHighEnergy(StrToInt(stk.nextToken().trim()));
            halfSyl.setLeftUnitName(stk.nextToken().trim());
            halfSyl.setRightUnitName(stk.nextToken().trim());
            setAdjacentType(halfSyl, halfSyl.getLeftUnitName(), halfSyl.getRightUnitName());
            getAllHalfSyls().add(halfSyl);
        }
    }

    /*
     * @param str
     * @return
     */
    public int StrToInt(String str) {
        return Integer.parseInt(str);
    }

    public static void main(String[] args) {
        new HalfSylDBReader();
//        System.out.println("kek");
    }

    private void setAdjacentType(HalfSyl halfsyl, String leftUnitName, String rightUnitName) {
        String phonemeToSet = "";
        if ((leftUnitName.compareTo("SIL") == 0) || (leftUnitName.compareTo("SILS") == 0) || (leftUnitName.compareTo("SILP") == 0)) {
            halfsyl.setLeftPhnmType("NUL");
        } else if (leftUnitName.charAt(0) == '_') {
            phonemeToSet = leftUnitName.substring(1, 2);
        } else {
            int indexOf = leftUnitName.indexOf("_");
            phonemeToSet = leftUnitName.substring(indexOf - 1, indexOf);
        }
        for (int i = 0; i < phonemes.length; i++) {
            if (phonemeToSet.compareTo(phonemes[i]) == 0) {
                halfsyl.setLeftPhnmType(phonemeTypes[i]);
                break;
            }
        }
        //////////////
        String phonemeToSet2 = "";
        if ((rightUnitName.compareTo("SIL") == 0) || (rightUnitName.compareTo("SILS") == 0) || (rightUnitName.compareTo("SILP") == 0)) {
            halfsyl.setRightPhnmType("NUL");
        } else if (rightUnitName.charAt(0) == '_') {
            phonemeToSet2 = rightUnitName.substring(1, 2);
        } else {
            int indexOf = rightUnitName.indexOf("_");
            if(indexOf == -1) indexOf = rightUnitName.length();
            phonemeToSet2 = rightUnitName.substring(indexOf - 1, indexOf);
        }
        for (int i = 0; i < phonemes.length; i++) {
            if (phonemeToSet2.compareTo(phonemes[i]) == 0) {
                halfsyl.setRightPhnmType(phonemeTypes[i]);
                break;
            }
        }
    }
}


