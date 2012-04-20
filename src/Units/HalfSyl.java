/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Units;

/**
 *
 * @author thaodv_bkit
 */
public class HalfSyl {
    //UnitName bDeleted wTranPoint dwDipLen UnitType bTone bLeftTone bRightTone dwLowFEnergy dwHighFEnergy cLeftUnitName cRightUnitName
    private int halfSylIndex;
    private String halfSylName;
    private int bDeleted; //don vi am co ton tai trong CSDL hay ko
    private int wTranPoint;//Diem phan biet 2 vung voiced va unvoiced
    private int dwDipLen; //do dai AUnit tinh theo BYTE
    private int unitType; //loai don vi am
    private int tone;
    private int leftTone;
    private int rightTone;
    private int lowEnergy;
    private int highEnergy;
    private String leftUnitName;
    private String rightUnitName;
    private String leftPhnmType;
    private String rightPhnmType;

    public HalfSyl(){

    }
   

    /**
     * @return the halfSylIndex
     */
    public int getHalfSylIndex() {
        return halfSylIndex;
    }

    /**
     * @param halfSylIndex the halfSylIndex to set
     */
    public void setHalfSylIndex(int halfSylIndex) {
        this.halfSylIndex = halfSylIndex;
    }

    /**
     * @return the halfSylName
     */
    public String getHalfSylName() {
        return halfSylName;
    }

    /**
     * @param halfSylName the halfSylName to set
     */
    public void setHalfSylName(String halfSylName) {
        this.halfSylName = halfSylName;
    }

    /**
     * @return the bDeleted
     */
    public int getbDeleted() {
        return bDeleted;
    }

    /**
     * @param bDeleted the bDeleted to set
     */
    public void setbDeleted(int bDeleted) {
        this.bDeleted = bDeleted;
    }

    /**
     * @return the wTranPoint
     */
    public int getwTranPoint() {
        return wTranPoint;
    }

    /**
     * @param wTranPoint the wTranPoint to set
     */
    public void setwTranPoint(int wTranPoint) {
        this.wTranPoint = wTranPoint;
    }

    /**
     * @return the dwDipLen
     */
    public int getDwDipLen() {
        return dwDipLen;
    }

    /**
     * @param dwDipLen the dwDipLen to set
     */
    public void setDwDipLen(int dwDipLen) {
        this.dwDipLen = dwDipLen;
    }

    /**
     * @return the unitType
     */
    public int getUnitType() {
        return unitType;
    }

    /**
     * @param unitType the unitType to set
     */
    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    /**
     * @return the tone
     */
    public int getTone() {
        return tone;
    }

    /**
     * @param tone the tone to set
     */
    public void setTone(int tone) {
        this.tone = tone;
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
     * @return the lowEnergy
     */
    public int getLowEnergy() {
        return lowEnergy;
    }

    /**
     * @param lowEnergy the lowEnergy to set
     */
    public void setLowEnergy(int lowEnergy) {
        this.lowEnergy = lowEnergy;
    }

    /**
     * @return the highEnergy
     */
    public int getHighEnergy() {
        return highEnergy;
    }

    /**
     * @param highEnergy the highEnergy to set
     */
    public void setHighEnergy(int highEnergy) {
        this.highEnergy = highEnergy;
    }

    /**
     * @return the leftUnitName
     */
    public String getLeftUnitName() {
        return leftUnitName;
    }

    /**
     * @param leftUnitName the leftUnitName to set
     */
    public void setLeftUnitName(String leftUnitName) {
        this.leftUnitName = leftUnitName;
    }

    /**
     * @return the rightUnitName
     */
    public String getRightUnitName() {
        return rightUnitName;
    }

    /**
     * @param rightUnitName the rightUnitName to set
     */
    public void setRightUnitName(String rightUnitName) {
        this.rightUnitName = rightUnitName;
    }

    /**
     * @return the leftPhnmType
     */
    public String getLeftPhnmType() {
        return leftPhnmType;
    }

    /**
     * @param leftPhnmType the leftPhnmType to set
     */
    public void setLeftPhnmType(String leftPhnmType) {
        this.leftPhnmType = leftPhnmType;
    }

    /**
     * @return the rightPhnmType
     */
    public String getRightPhnmType() {
        return rightPhnmType;
    }

    /**
     * @param rightPhnmType the rightPhnmType to set
     */
    public void setRightPhnmType(String rightPhnmType) {
        this.rightPhnmType = rightPhnmType;
    }


}
