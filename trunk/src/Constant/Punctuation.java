/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Constant;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author lelightwin
 */
public class Punctuation {
    public static final String[] alls = {".",",","?","-"};
    
    public static ArrayList<String> getAlls(){
        ArrayList<String> result = new ArrayList<String>();
        result.addAll(Arrays.asList(alls));
        return result;
    }
}
