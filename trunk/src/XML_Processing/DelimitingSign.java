package XML_Processing;


import Units.SignedVowel;
import java.util.HashMap;
//import letter.Literal;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author victoria
 */
public class DelimitingSign {
    static final SignVowelReader SIGN_VOWEL_READER = new SignVowelReader();
    private HashMap sw = SIGN_VOWEL_READER.getSvw();
    //private HashMap fc = MainFrame.reader.getFc();
    private int[] intArrayResult;
    private String strResult;
    private int sign;
    private char[] c;
    private int len = 0;

    
////////////////////////////////////////////////////////////

    public DelimitingSign(String s1) {
        String s = s1.toLowerCase();
        len = s.length();
        c = s.toCharArray();

        intArrayResult = new int[s.length() + 1];
        int dau = 1;
        for (int i = 0; i < s.length(); i++) {
            SignedVowel sv = getSignedVowel(s.charAt(i));
            if (sv != null) {
                intArrayResult[i] = 1;
                if (dau < sv.getSign()) {
                    dau = sv.getSign();
                }
                c[i] = sv.getOutput();
            }
//            else {
//                Literal l = getSingleConsonant(s.substring(i, i+1));
//                if (l != null) {
//                    intArrayResult[i] = 0;
//                } else {
//                    intArrayResult[i] = 9;
//                }
//            }
        }
        setSign(dau);
        intArrayResult[s.length()] = dau;
        strResult = new String(c);
        this.resetArrayResult();
    }

    /**
     *@ xu ly truong hop dac biet cua 'qu' va 'gi'
     */

    public void resetArrayResult() {
        if ((strResult.length() >= 3)) {
            // kiem tra co phai la qu hay ko
            if ((strResult.substring(0, 2).compareTo("qu") == 0)) {
                intArrayResult[1] = 0; // qu tro thanh 00
            } else if (strResult.substring(0, 2).compareTo("gi") == 0) {
                // kiem tra co phai la gi hay ko               
                if (intArrayResult[2] == 0) {
                    // neu sau gi la mot phu am thi chuyen g -> d
                    strResult = strResult.replace('g', 'd');
                } else {
                    // sau gi la mot nguyen am thi xet gi la am dau
                    intArrayResult[1] = 0;
                }
            }
        } else if (strResult.compareTo("gi") == 0) {
            strResult = "di";
        }
    }
    ////////////
    public SignedVowel getSignedVowel(char c) {
        return (SignedVowel) sw.get(new Character(c));
    }

//    public Literal getSingleConsonant(String str) {
//        return (Literal) fc.get(str);
//    }
    /////////////

    /**
     * @return the length of input String
     */
    public int getLen() {
        return len;
        // len la length cua xau dua vao
    }

    /**
     * @return the intArrayResult
     */
    public int[] getIntArrayResult() {
        return intArrayResult;
    }

    /**
     * @return the strResult
     */
    public String getStrResult() {
        return strResult;
    }

    /**
     * @return the sign of SignedVowel
     */
    public int getSign() {
        return sign;
    }

    /**
     * @param s the sign to set
     */
    public void setSign(int s) {
        this.sign = s;
    }

   //
}
