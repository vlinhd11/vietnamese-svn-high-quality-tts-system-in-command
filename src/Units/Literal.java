/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Units;

/**
 *
 * @author big-ant
 */
public class Literal {
    private String chars;
    private String transcriptions;
    private String type;

    public Literal(String c, String tr, String t) {
        chars =c;
        transcriptions = tr;
        type = t;
    }

    /**
     * @return the chars
     */
    public String getChars() {
        return chars;
    }

    /**
     * @return the transcriptions
     */
    public String getTranscriptions() {
        return transcriptions;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
}
