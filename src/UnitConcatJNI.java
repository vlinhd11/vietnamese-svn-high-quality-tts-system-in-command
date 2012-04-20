

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jh
 */
import UnitSelection.UnitSelection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author thaodv_bkit
 */
public class UnitConcatJNI {

    private String[] fileNames;
    private UnitSelection unitSelection;
    private String name = "";
    private String nameWithoutEx = "";

    private native void UnitConcatenative(String fileLocation, String nameOfWavFile);

    static {
        System.load(System.getProperty("user.dir")+"/LowLevelJNI.dll");
    }

    public UnitConcatJNI() throws XMLStreamException, FileNotFoundException {
    }

    public UnitConcatJNI(File inputXMLFile) throws XMLStreamException, FileNotFoundException, UnsupportedEncodingException, IOException {
        // get the name of input XMl File
        name = inputXMLFile.getName();
        nameWithoutEx = name.substring(0,name.lastIndexOf("."));
        //process the main work using the name which was obtained above
        unitSelection = new UnitSelection(inputXMLFile);
    }
    
    public void concatUni(){
        UnitConcatenative(unitSelection.getPathFile(), nameWithoutEx);
        //UnitConcatenative("mediate files/SelectedLPhrs.txt", "test");
    }
}