package Units;

/**
 * @author thaodv-bkit
 * @version 1.0
 * @created 05-Apr-2011 2:35:36 PM
 */
public class Syllable {

    private int IDofSyllable;
    private String sylName;
    private int startIndex;
    private int endIndex;
    private int sylTone;
    /**
     * Length of syllable, the third parameter
     */
    private int sylDuration;
    private String sylTranscript = "";
    /**
     * length of phrase carry the syllable
     */
    private int PhraseLen;
    private int numOfPhone;
    private String carryingFile;
    private String intialPhoneme = "";
    private String middlePhoneme = "";
    private String NucleusPhoneme = "";
    private String finalPhoneme;
    private String initialType = "";
    private String middleType = "";
    private String nucleusType = "";
    private String finalType = "";
    private String leftPhoneType = "";
    private String rightPhoneType = "";
    private float energy;
    private String leftHalfSyl;
    private String rightHalfSyl;
    private String leftSyl;
    private int leftTone;
    private String rightSyl;
    private int rightTone;
    private String leftPhoneme;
    private String rightPhoneme;
    private String leftPhonemeType;
    private String rightPhonemeType;



    public Syllable() {
    }
    

    /**
     * @return the IDofSyllable
     */
    public int getIDofSyllable() {
        return IDofSyllable;
    }

    /**
     * @param IDofSyllable the IDofSyllable to set
     */
    public void setIDofSyllable(int IDofSyllable) {
        this.IDofSyllable = IDofSyllable;
    }

    /**
     * @return the sylName
     */
    public String getSylName() {
        return sylName;
    }

    /**
     * @param sylName the sylName to set
     */
    public void setSylName(String sylName) {
        this.sylName = sylName;
    }

    /**
     * @return the startIndex
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * @param startIndex the startIndex to set
     */
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * @return the endIndex
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * @param endIndex the endIndex to set
     */
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    /**
     * @return the sylTone
     */
    public int getSylTone() {
        return sylTone;
    }

    /**
     * @param sylTone the sylTone to set
     */
    public void setSylTone(int sylTone) {
        this.sylTone = sylTone;
    }

    /**
     * @return the sylDuration
     */
    public int getSylDuration() {
        return sylDuration;
    }

    /**
     * @param sylDuration the sylDuration to set
     */
    public void setSylDuration(int sylDuration) {
        this.sylDuration = sylDuration;
    }

    /**
     * @return the sylTranscript
     */
    public String getSylTranscript() {
        return sylTranscript;
    }

    /**
     * @param sylTranscript the sylTranscript to set
     */
    public void setSylTranscript(String sylTranscript) {
        this.sylTranscript = sylTranscript;
    }

    /**
     * @return the PhraseLen
     */
    public int getPhraseLen() {
        return PhraseLen;
    }

    /**
     * @param PhraseLen the PhraseLen to set
     */
    public void setPhraseLen(int PhraseLen) {
        this.PhraseLen = PhraseLen;
    }

    /**
     * @return the numOfPhone
     */
    public int getNumOfPhone() {
        return numOfPhone;
    }

    /**
     * @param numOfPhone the numOfPhone to set
     */
    public void setNumOfPhone(int numOfPhone) {
        this.numOfPhone = numOfPhone;
    }

    /**
     * @return the intialPhoneme
     */
    public String getInitialPhoneme() {
        return intialPhoneme;
    }

    /**
     * @param intialPhoneme the intialPhoneme to set
     */
    public void setIntialPhoneme(String intialPhoneme) {
        this.intialPhoneme = intialPhoneme;
    }

    /**
     * @return the middlePhoneme
     */
    public String getMiddlePhoneme() {
        return middlePhoneme;
    }

    /**
     * @param middlePhoneme the middlePhoneme to set
     */
    public void setMiddlePhoneme(String middlePhoneme) {
        this.middlePhoneme = middlePhoneme;
    }

    /**
     * @return the NucleusPhoneme
     */
    public String getNucleusPhoneme() {
        return NucleusPhoneme;
    }

    /**
     * @param NucleusPhoneme the NucleusPhoneme to set
     */
    public void setNucleusPhoneme(String NucleusPhoneme) {
        this.NucleusPhoneme = NucleusPhoneme;
    }

    /**
     * @return the finalPhoneme
     */
    public String getFinalPhoneme() {
        return finalPhoneme;
    }

    /**
     * @param finalPhoneme the finalPhoneme to set
     */
    public void setFinalPhoneme(int finalPhoneme) {
        this.setFinalPhoneme(finalPhoneme);
    }

    /**
     * @return the initialType
     */
    public String getInitialType() {
        return initialType;
    }

    /**
     * @param initialType the initialType to set
     */
    public void setInitialType(String initialType) {
        this.initialType = initialType;
    }

    /**
     * @return the middleType
     */
    public String getMiddleType() {
        return middleType;
    }

    /**
     * @param middleType the middleType to set
     */
    public void setMiddleType(String middleType) {
        this.middleType = middleType;
    }

    /**
     * @return the nucleusType
     */
    public String getNucleusType() {
        return nucleusType;
    }

    /**
     * @param nucleusType the nucleusType to set
     */
    public void setNucleusType(String nucleusType) {
        this.nucleusType = nucleusType;
    }

    /**
     * @return the finalType
     */
    public String getFinalType() {
        return finalType;
    }

    /**
     * @param finalType the finalType to set
     */
    public void setFinalType(String finalType) {
        this.finalType = finalType;
    }

    /**
     * @return the leftPhoneType
     */
    public String getLeftPhoneType() {
        return leftPhoneType;
    }

    /**
     * @param leftPhoneType the leftPhoneType to set
     */
    public void setLeftPhoneType(String leftPhoneType) {
        this.leftPhoneType = leftPhoneType;
    }

    /**
     * @return the rightPhoneType
     */
    public String getRightPhoneType() {
        return rightPhoneType;
    }

    /**
     * @param rightPhoneType the rightPhoneType to set
     */
    public void setRightPhoneType(String rightPhoneType) {
        this.rightPhoneType = rightPhoneType;
    }

    /**
     * @return the energy
     */
    public float getEnergy() {
        return energy;
    }

    /**
     * @param energy the energy to set
     */
    public void setEnergy(float energy) {
        this.energy = energy;
    }

    /**
     * @param
     */
    public boolean setSyllable(String name, int tone, int phraseLen, int numOfPhone, String carryingFile,
            String initial, String middle, String nucleus, String finalph,
            String initialT, String middleT, String nucleusT, String finalT, float energy, String leftSyl, String rightSyl,
            int leftTone, int rightTone, String leftPhnm, String rightPhnm, String leftPhnmType, String rightPhnmType) {
        if (name.compareTo(getSylName()) == 0) {
            setSylTone(tone);
            setPhraseLen(phraseLen);
            setNumOfPhone(numOfPhone);
            setCarryingFile(carryingFile);
            setIntialPhoneme(initial);
            setMiddlePhoneme(middle);
            setNucleusPhoneme(nucleus);
            setFinalPhoneme(finalph);
            setInitialType(initialT);
            setMiddleType(middleT);
            setNucleusType(nucleusT);
            setFinalType(finalT);
            setEnergy(energy);
            setLeftSyl(leftSyl);
            setRightSyl(rightSyl);
            setLeftTone(leftTone);
            setRightTone(rightTone);
            setLeftPhoneme(leftPhnm);
            setRightPhoneme(rightPhnm);
            setLeftPhonemeType(leftPhnmType);
            setRightPhonemeType(rightPhnmType);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the carryingFile
     */
    public String getCarryingFile() {
        return carryingFile;
    }

    /**
     * @param carryingFile the carryingFile to set
     */
    public void setCarryingFile(String carryingFile) {
        this.carryingFile = carryingFile;
    }

    /**
     * @param finalPhoneme the finalPhoneme to set
     */
    public void setFinalPhoneme(String finalPhoneme) {
        this.finalPhoneme = finalPhoneme;
    }

    /**
     * @return the leftHalfSyl
     */
    public String getLeftHalfSyl() {
        return leftHalfSyl;
    }

    /**
     * @param leftHalfSyl the leftHalfSyl to set
     */
    public void setLeftHalfSyl(String leftHalfSyl) {
        this.leftHalfSyl = leftHalfSyl;
    }

    /**
     * @return the rightHalfSyl
     */
    public String getRightHalfSyl() {
        return rightHalfSyl;
    }

    /**
     * @param rightHalfSyl the rightHalfSyl to set
     */
    public void setRightHalfSyl(String rightHalfSyl) {
        this.rightHalfSyl = rightHalfSyl;
    }

    /**
     * @return the leftSyl
     */
    public String getLeftSyl() {
        return leftSyl;
    }

    /**
     * @param leftSyl the leftSyl to set
     */
    public void setLeftSyl(String leftSyl) {
        this.leftSyl = leftSyl;
    }

    /**
     * @return the leftTone
     */
    public int getLeftTone() {
        return leftTone;
    }

    /**
     * @param leftTone the leftTone to set
     */
    public void setLeftTone(int leftTone) {
        this.leftTone = leftTone;
    }

    /**
     * @return the rightSyl
     */
    public String getRightSyl() {
        return rightSyl;
    }

    /**
     * @param rightSyl the rightSyl to set
     */
    public void setRightSyl(String rightSyl) {
        this.rightSyl = rightSyl;
    }

    /**
     * @return the rightTone
     */
    public int getRightTone() {
        return rightTone;
    }

    /**
     * @param rightTone the rightTone to set
     */
    public void setRightTone(int rightTone) {
        this.rightTone = rightTone;
    }

    /**
     * @return the leftPhoneme
     */
    public String getLeftPhoneme() {
        return leftPhoneme;
    }

    /**
     * @param leftPhoneme the leftPhoneme to set
     */
    public void setLeftPhoneme(String leftPhoneme) {
        this.leftPhoneme = leftPhoneme;
    }

    /**
     * @return the rightPhoneme
     */
    public String getRightPhoneme() {
        return rightPhoneme;
    }

    /**
     * @param rightPhoneme the rightPhoneme to set
     */
    public void setRightPhoneme(String rightPhoneme) {
        this.rightPhoneme = rightPhoneme;
    }

    /**
     * @return the leftPhonemeType
     */
    public String getLeftPhonemeType() {
        return leftPhonemeType;
    }

    /**
     * @param leftPhonemeType the leftPhonemeType to set
     */
    public void setLeftPhonemeType(String leftPhonemeType) {
        this.leftPhonemeType = leftPhonemeType;
    }

    /**
     * @return the rightPhonemeType
     */
    public String getRightPhonemeType() {
        return rightPhonemeType;
    }

    /**
     * @param rightPhonemeType the rightPhonemeType to set
     */
    public void setRightPhonemeType(String rightPhonemeType) {
        this.rightPhonemeType = rightPhonemeType;
    }
}
