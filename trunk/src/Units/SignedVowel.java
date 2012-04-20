package Units;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lelightwin
 */
public class SignedVowel {

    private char input = 0;
    private char output = 0;
    private int sign = 0;

    public SignedVowel(char c, char k, int d) {
        this.input = c;
        this.output = k;
        this.sign = d;
    }

    /**
     * @return the codau
     */
    public char getInput() {
        return input;
    }

    /**
     * @return the khongdau
     */
    public char getOutput() {
        return output;
    }

    /**
     * @return the dau
     */
    public int getSign() {
        return sign;
    }

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object o){
        SignedVowel s = (SignedVowel)o;
        return (s.input==input);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.input;
        return hash;
    }
}
