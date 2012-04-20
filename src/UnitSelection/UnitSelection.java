/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UnitSelection;

import Units.LevelPhrase;
import Units.Sentence;
import Units.Syllable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author thaodv_bkit
 */
public class UnitSelection {

    private UnitSearching us;
    private ArrayList<LevelPhrase> foundLPhrs;
    private ArrayList<String> fileNames = new ArrayList<String>();
    private String pathFile;
    ArrayList<Sentence> allSenInTextDB;
    private float minDistance, tmpCost;
    private static final int MAX_CAND_UNIT = 12;
    private StringBuffer inputTextContent;

    public UnitSelection(File inputXMLFile) throws XMLStreamException, FileNotFoundException, UnsupportedEncodingException, IOException {
        us = new UnitSearching(inputXMLFile);
        foundLPhrs = us.getFoundLPhs();
        for (int i = 0; i < foundLPhrs.size(); i++) {
            LevelPhrase levelPhrase = foundLPhrs.get(i);
            //System.out.println("foundLPhrs: "+i+" : "+levelPhrase.getPhraseContent());
        }
        allSenInTextDB = UnitSearching.getAllSenInTextDB();
        setInputTextContent();
        selectLPByCost();
        sortDistanceArray();
        selectFinalCandUnits();
        writeToTextFile();
    }
    /////////////////

    public void selectLP() {
        for (int i = 0; i < getFoundLPhrs().size(); i++) {
            //System.out.println(i + getFoundLPhrs().get(i).getPhraseContent());
            if (getFoundLPhrs().get(i).isFound() == 1) {
                getFoundLPhrs().get(i).setSelectedSen(getFoundLPhrs().get(i).getFoundIndexes1().get(0));
                getFoundLPhrs().get(i).setSelectedSylPhrs(getFoundLPhrs().get(i).getFoundIndexes2().get(0));
                getFoundLPhrs().get(i).setSelectedSyllable(getFoundLPhrs().get(i).getFoundIndexes3().get(0));
            } else {
                //System.out.println("khong thay: " + getFoundLPhrs().get(i).getPhraseContent());
            }
        }
    }

    /**
     * @return the selectedLPhrs
     */
    public ArrayList<LevelPhrase> getFoundLPhrs() {
        return foundLPhrs;
    }

    /**
     * @return the fileNames
     */
    public ArrayList<String> getFileNames() {
        return fileNames;
    }

//    /**
//     * @param fileNames the fileNames to set
//     */
//    public void setFileNames() {
//        fileNames = new ArrayList<String>();
//        for (int i = 0; i < selectedLPhrs.size(); i++) {
//            fileNames.add(UnitSearching.allSenInTextDB.get(selectedLPhrs.get(i).getSelectedSen()).getCarryingFile());
//        }
//    }
    private void setIndex() {
        fileNames = new ArrayList<String>();
        for (int i = 0; i < foundLPhrs.size(); i++) {
            fileNames.add(UnitSearching.getAllSenInTextDB().get(foundLPhrs.get(i).getSelectedSen()).getCarryingFile());
            int selectedSen = foundLPhrs.get(i).getSelectedSen();
            int selectedSylPhrs = foundLPhrs.get(i).getSelectedSylPhrs();
            int selectedSyllable = foundLPhrs.get(i).getSelectedSyllable();
            int startIndex = UnitSearching.getAllSenInTextDB().get(selectedSen).getSylPhrases().get(selectedSylPhrs).getSyllablesInPh().get(selectedSyllable).getStartIndex();
            int endIndex = UnitSearching.getAllSenInTextDB().get(selectedSen).getSylPhrases().get(selectedSylPhrs).getSyllablesInPh().get(selectedSyllable + foundLPhrs.get(i).getPhraseLen() - 1).getEndIndex();
            foundLPhrs.get(i).setStartIndex(startIndex);
            foundLPhrs.get(i).setEndIndex(endIndex);
        }
    }

    /*
     *
     */
    private void writeToTextFile() throws UnsupportedEncodingException, FileNotFoundException, IOException {

        File f = new File(System.getProperty("user.dir") + "/mediate files/SelectedLPhrs.txt");
        setPathFile(f.getAbsolutePath());

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "utf-8"));
        for (int i = 0; i < foundLPhrs.size(); i++) {
            if (i < foundLPhrs.size() - 2) {
                if (foundLPhrs.get(i).getPhraseContent().trim().compareTo("SILS") == 0 && foundLPhrs.get(i + 1).getPhraseContent().trim().compareTo("SILS") == 0) {
                    continue;
                }
            }
            bufferedWriter.write((Integer.valueOf(foundLPhrs.get(i).isFound()).toString()) + " ");
            bufferedWriter.write(fileNames.get(i) + " ");
            //bufferedWriter.write((Integer.valueOf(selectedLPhrs.get(i).getSelectedSylPhrs()).toString()) + " ");
            //bufferedWriter.write((Integer.valueOf(selectedLPhrs.get(i).getSelectedSyllable()).toString()) + " ");
            bufferedWriter.write((Integer.valueOf(foundLPhrs.get(i).getStartIndex()).toString()) + " ");
            bufferedWriter.write((Integer.valueOf(foundLPhrs.get(i).getEndIndex()).toString()) + " ");
            bufferedWriter.write(foundLPhrs.get(i).getPhraseContent().trim() + "\n");
        }
        bufferedWriter.close();
    }

    public void printDetails() throws FileNotFoundException {
        for (int i = 0; i < foundLPhrs.size(); i++) {
            System.out.println(foundLPhrs.get(i).getPhraseContent() + "\t" + foundLPhrs.get(i).isFound() + "\t" + fileNames.get(i) + "\t" + foundLPhrs.get(i).getSelectedSylPhrs() + "\t" + foundLPhrs.get(i).getSelectedSyllable() + "\t" + foundLPhrs.get(i).getStartIndex() + " : " + foundLPhrs.get(i).getEndIndex());
        }
    }

    /**
     * @return the pathFile
     */
    public String getPathFile() {
        return pathFile;
    }

    /**
     * @param pathFile the pathFile to set
     */
    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    private void selectLPByCost() {
        calculateCostForCandidateUnits();
    }

    private void calculateCostForCandidateUnits() {
//        int beginSen=0, endSen; // bat dau cau
//        for (int i = 0; i < getFoundLPhrs().size(); i++) {
//            String phraseContent = getFoundLPhrs().get(i).getPhraseContent().trim();
//            if(phraseContent.compareTo("SILS")==0){
//                endSen = i;
//                for (int j = beginSen; j < endSen-1; j++) {
//                     calculateCostFor2LP(getFoundLPhrs().get(j), getFoundLPhrs().get(j + 1));
//                }
//                beginSen = endSen+1;
//            }
//        }
        for (int i = 0; i < getFoundLPhrs().size() - 1; i++) {
            calculateCostFor2LP(getFoundLPhrs().get(i), getFoundLPhrs().get(i + 1));
        }
    }

    private void calculateCostFor2LP(LevelPhrase leftLP, LevelPhrase rightLP) {
        int numberOfCandOfLeftLP = leftLP.getFoundIndexes1().size();
        int numberOfCandOfRightLP = rightLP.getFoundIndexes1().size();
        float[][] distanceMatrixOfLeftLP = new float[numberOfCandOfLeftLP][numberOfCandOfRightLP];
        leftLP.setDistanceMatrix(distanceMatrixOfLeftLP);
        for (int i = 0; i < numberOfCandOfLeftLP; i++) {
//            if (i >= 250) {
//                break;
//            }
            for (int j = 0; j < numberOfCandOfRightLP; j++) {
//                if (j >= 250) {
//                    break;
//                }
                calculateCostFor2CandidateUnits(leftLP, i, rightLP, j);
            }
        }
//        for (int i = 0; i < leftLP.getFoundIndexes1().size(); i++) {
//            if(i>=250 ) break;
//            minDistance = 100;
//            int indexOfBestNextCand = -1;
//            for (int j = 0; j < rightLP.getFoundIndexes1().size(); j++) {
//                if (j >= 250) {
//                    break;
//                } else {
//                    calculateCostFor2CandidateUnits(leftLP, i, rightLP, j);
//                    if (tmpCost == 0.0) {
//                        break;
//                    }
//                    if (minDistance > tmpCost) {
//                        minDistance = tmpCost;
//                        indexOfBestNextCand = j;
//                    }
//                }
//            }
//            leftLP.getIndexOfBestNextUnit().add(indexOfBestNextCand);
//            leftLP.getMinDistanceToNextUnit().add(minDistance);
//            System.out.println("min cost: " + minDistance + " tai vi tri " + indexOfBestNextCand);
//        }
    }

    private void calculateCostFor2CandidateUnits(LevelPhrase leftLP, int i, LevelPhrase rightLP, int j) {
//        if (i >= 250 || j >= 250) {
//            return;
//        }
        //xu ly trong truong hop syllable gom 2 ban am tiet
        if (leftLP.isFound() == 1 & rightLP.isFound() == 1) {
            //xy ly truong hop ca hai deu duoc tim thay trong CSDL
            //xu ly trong truong hop leftLP & rightLP != SILS, SILP
            //System.out.println(i );
            int leftIndex1 = leftLP.getFoundIndexes1().get(i);//chi so cua cau trong CSDL chua leftLP
            int leftIndex2 = leftLP.getFoundIndexes2().get(i);//chi so cua SylPhrase trong CSDL chua leftLP
            int leftIndex3 = leftLP.getFoundIndexes3().get(i);//chi so cua syllable dau tien trong CSDL cua leftLP
            int leftIndex4 = leftLP.getFoundIndexes4().get(i);//chi so cua syllable cuoi cung trong CSDL cua leftLP
            String leftSylName = leftLP.getFirstSylInLPhrs().getSylName();
            String rightSylName = rightLP.getFirstSylInLPhrs().getSylName();
            int rightIndex1 = rightLP.getFoundIndexes1().get(j);//chi so cua cau trong CSDL chua rightLP
            int rightIndex2 = rightLP.getFoundIndexes2().get(j);//chi so cua SylPhrase trong CSDL chua rightLP
            int rightIndex3 = rightLP.getFoundIndexes3().get(j);//chi so cua syllable dau tien trong CSDL cua rightLP
            int rightIndex4 = rightLP.getFoundIndexes4().get(j);//chi so cua syllable cuoi cung trong CSDL cua rightLP
            // Luu y truong hop dau file va cuoi file thi cac am tiet la null
            if (leftSylName.compareTo("SIL") == 0) {
                //am tiet ben trai la SIL
                //return;
            }
            if (rightSylName.compareTo("SIL") == 0) {
                //am tiet ben phai la SIL
                //return;
            }
            Syllable lastSylOfLeftCandUnit = allSenInTextDB.get(leftIndex1).getSylPhrases().get(leftIndex2).getSyllablesInPh().get(leftIndex4);
            Syllable firstSylOfRightCandUnit = allSenInTextDB.get(rightIndex1).getSylPhrases().get(rightIndex2).getSyllablesInPh().get(rightIndex3);

            Syllable rightSylOfLeftCandUnit = new Syllable();
            rightSylOfLeftCandUnit.setSylTone(lastSylOfLeftCandUnit.getRightTone());
            rightSylOfLeftCandUnit.setIntialPhoneme(lastSylOfLeftCandUnit.getRightPhoneme());
            rightSylOfLeftCandUnit.setInitialType(lastSylOfLeftCandUnit.getRightPhonemeType());
            rightSylOfLeftCandUnit.setSylName(lastSylOfLeftCandUnit.getRightSyl());

            Syllable leftSylOfRightCandUnit = new Syllable();
            leftSylOfRightCandUnit.setSylTone(firstSylOfRightCandUnit.getLeftTone());
            leftSylOfRightCandUnit.setFinalPhoneme(firstSylOfRightCandUnit.getLeftPhoneme());
            leftSylOfRightCandUnit.setFinalType(firstSylOfRightCandUnit.getLeftPhonemeType());
            leftSylOfRightCandUnit.setSylName(firstSylOfRightCandUnit.getLeftSyl());

            // Doi voi LeftCandUnit, ta can lay am tiet cuoi cung cua no la LastSylOfLeftCand, va am tiet dau tien ben phai CandUnitLeft la rightSylOfLeftCandUnit
            // Doi voi RightCandUnit, ta can lay am tiet dau tien cua no la FirstSylOfRightCand, va am tiet cuoi cung ben trai CandUnitRight la leftSylOfRightCandUnit
            // Can so sanh LastSylOfLeftCandUnit voi leftSylOfRightCandUnit; so sanh am cuoi va thanh dieu
            // Can so sanh rightSylOfLeftCandUnit voi FirstSylOfRightCand; so sanh am dau va thanh dieu
            //System.out.println(lastSylOfLeftCandUnit.getSylName()+" -- "+firstSylOfRightCandUnit.getSylName());
            if (lastSylOfLeftCandUnit.getSylName().trim().compareTo(leftLP.getLastSylInLPhrs().getSylName().trim()) != 0) {
                //System.out.println("khac tai i = "+i+" : "+j);
                leftLP.getDistanceMatrix()[i][j] = 100;
            } else if (firstSylOfRightCandUnit.getSylName().trim().compareTo(rightLP.getFirstSylInLPhrs().getSylName().trim()) != 0) {
                leftLP.getDistanceMatrix()[i][j] = 100;
                //System.out.println("khac tai j = "+j+" : "+i);
                //System.out.println(allSenInTextDB.get(rightIndex1).getSylPhrases().get(rightIndex2).getPhraseContent());
            } else {
                //System.out.println(lastSylOfLeftCandUnit.getSylName() + " ++ " + leftSylOfRightCandUnit.getSylName());
                float cost1 = costOfTwoSyllable(lastSylOfLeftCandUnit, leftSylOfRightCandUnit, 1);
                //System.out.println("cost: " + costOfTwoSyllable(lastSylOfLeftCandUnit, leftSylOfRightCandUnit, 1));
                //System.out.println(rightSylOfLeftCandUnit.getSylName() + " -- " + firstSylOfRightCandUnit.getSylName());
                float cost2 = costOfTwoSyllable(rightSylOfLeftCandUnit, firstSylOfRightCandUnit, 2);
                //System.out.println("cost: " + costOfTwoSyllable(rightSylOfLeftCandUnit, firstSylOfRightCandUnit, 2));
                float totalcost = cost1 + cost2;
                tmpCost = totalcost;
                if (leftLP.getDistanceMatrix()[i][j] != 0) {
                    System.out.println("co nham lan trong viec tinh toan khoang cach");
                } else if (totalcost == 0.0f) {
                    System.out.println("tai sao cost lai = 0");
                } else {
                    leftLP.getDistanceMatrix()[i][j] = totalcost;
                    leftLP.getDistanceArray().add(new Distance(totalcost, i, j));
                }
                //System.out.println("total cost: " + totalcost);
            }
            //System.out.println(rightSylOfLeftCandUnit.getSylName()+" -- "+leftSylOfRightCandUnit.getSylName());

            //can so sanh LastSylOfLeftLP voi leftSylOfRightLP
            //can so sanh FirstSylOfRightLP voi rightSylOfLeftLP
        } else {
            System.out.println("hien tai chua xu ly buoc nay");
        }
    }

    private float costOfTwoSyllable(Syllable syl1, Syllable syl2, int i) {
        //neu i = 1 thi syl1 la lastSylOfLeftCandUnit, syl2 la leftSylOfRightCandUnit
        //neu i = 2 thi syl1 la rightSylOfLeftCandUnit, syl2 la FirstSylOfRightCand
        float distance1 = 1, distance2 = 1, distance3 = 1; //1 la khoang cach am vi, 2 la khoang cach loai am vi, 3 la khoang cach ve thanh dieu
        float W1 = 0.7f, W2 = 0.5f, W3 = 0.8f;
        float cost = 0.01f;

        if (i == 1) {
            //System.out.println(syl1.getFinalPhoneme()+"::"+syl2.getFinalPhoneme());
            if (syl1.getSylName().compareTo(syl2.getSylName()) == 0) {
                cost = 0.001f;
            } else {
                //so sanh finalPhoneme cua lastSylOfLeftCandUnit voi finalPhoneme cua leftSylOfRightCandUnit
                if (syl1.getFinalPhoneme().compareTo(syl2.getFinalPhoneme()) == 0) {
                    distance1 = 0.1f;
                    //System.out.println("same phoneme 1");
                } else {
                    distance1 = 1;
                }
                if (syl1.getFinalType().compareTo(syl2.getFinalType()) == 0) {
                    distance2 = 0.1f;
                    //System.out.println("same type 1");
                } else {
                    distance2 = 1;
                }
                distance3 = (float) toneDistance(syl1.getSylTone(), syl2.getSylTone());
                cost = W1 * distance1 + W2 * distance2 + W3 * distance3;
            }
        } else if (i == 2) {
            //System.out.println(syl1.getInitialPhoneme()+"<>"+syl2.getInitialPhoneme());
            if (syl1.getSylName().compareTo(syl2.getSylName()) == 0) {
                cost = 0.001f;
            } else {
                //so sanh finalPhoneme cua lastSylOfLeftCandUnit voi finalPhoneme cua leftSylOfRightCandUnit
                if (syl1.getInitialPhoneme().compareTo(syl2.getInitialPhoneme()) == 0) {
                    distance1 = 0.1f;
                    //System.out.println("same phoneme 2");
                } else {
                    distance1 = 1;
                }
                if (syl1.getInitialType().compareTo(syl2.getInitialType()) == 0) {
                    //System.out.println("same type 2");
                    distance2 = 0.1f;
                } else {
                    distance2 = 1;
                }
                distance3 = (float) toneDistance(syl1.getSylTone(), syl2.getSylTone());
                cost = W1 * distance1 + W2 * distance2 + W3 * distance3;
            }
        }
        return cost;
    }

    private float toneDistance(int sylTone1, int sylTone2) {
        float toneDis, Wd = 0.1f, Wc = 0.1f;
        int[] dD = {0, 0, -1, 1, -1, 1, -1, 1, -1};
        int[] dC = {0, 0, 1, 4, 2, 1, 6, 1, 1};
        if ((sylTone1 == sylTone2) || (sylTone1 == 0 && sylTone2 == 1) || (sylTone1 == 1 && sylTone2 == 0)) {
            toneDis = 0.001f;
            //System.out.println("same tone");
        } else {
            toneDis = Wd * Math.abs(dD[sylTone1] - dD[sylTone2]) + Wc * (dC[sylTone1] + dC[sylTone2]);
        }
        return toneDis;
    }

    /*
     *
     */
    private void sortDistanceArray() {
        for (int i = 0; i < getFoundLPhrs().size(); i++) {
            //System.out.println("LP thu: " + i);
            Collections.sort(getFoundLPhrs().get(i).getDistanceArray());
//            for (int j = 0; j < getFoundLPhrs().get(i).getDistanceArray().size(); j++) {
//                System.out.println(getFoundLPhrs().get(i).getDistanceArray().get(j).getDistance() + " : " + getFoundLPhrs().get(i).getDistanceArray().get(j).getIndexOfPreCand() + " : " + getFoundLPhrs().get(i).getDistanceArray().get(j).getIndexOfNextCand());
//            }
        }
    }
    /*
     *
     */

    private void selectBestUnits() {
        int beginSen = 0, endSen; // bat dau cau
        for (int i = 0; i < getFoundLPhrs().size(); i++) {
            String phraseContent = getFoundLPhrs().get(i).getPhraseContent().trim();
            if (phraseContent.compareTo("SILS") == 0 || phraseContent.compareTo("SIL") == 0) {
                endSen = i;
                for (int j = beginSen; j < endSen - 1; j++) {
                    selectBestNextUnitOfaCandUnit(getFoundLPhrs().get(j), getFoundLPhrs().get(j + 1));
                }
                beginSen = endSen + 1;
            }
        }
    }
    /////////////////

    private void selectBestNextUnitOfaCandUnit(LevelPhrase currentUnit, LevelPhrase nextUnit) {
        float minDist = 210;
        int bestNextIndex = 0, bestIndex = 0;
//        for (int i = 0; i < currentUnit.getDistanceMatrix().length; i++) {
//            for (int j = 0; j < currentUnit.getDistanceMatrix()[i].length; j++) {
//                if (currentUnit.getDistanceMatrix()[i][j] == 0.0) {
//                    currentUnit.getDistanceMatrix()[i][j] = 200;
//                }
//            }
//        }
        for (int i = 0; i < currentUnit.getFoundIndexes1().size(); i++) {
            minDist = currentUnit.getDistanceMatrix()[i][0];
            for (int j = 0; j < nextUnit.getFoundIndexes1().size(); j++) {
                if (currentUnit.getDistanceMatrix()[i][j] == 0.0) {
                    //System.out.println("break");
                    //break; //ko co
                } else if (currentUnit.getDistanceMatrix()[i][j] < minDist && currentUnit.getDistanceMatrix()[i][j] > 0) {
                    minDist = currentUnit.getDistanceMatrix()[i][j];
                    bestNextIndex = j;
                    bestIndex = i;
                }
            }
            currentUnit.getIndexOfBestNextUnit().add(bestNextIndex);
            currentUnit.getMinDistanceToNextUnit().add(minDist);
            currentUnit.getBestIndex().add(bestIndex);
            //System.out.println("min distance " + minDistance + " tai " + bestIndex + " : " + bestNextIndex + " : " + currentUnit.getPhraseContent() + " : " + nextUnit.getPhraseContent());
        }
    }

    public void printCostOf2LP(int leftIndex) {
        LevelPhrase leftLP = getFoundLPhrs().get(leftIndex);
        LevelPhrase rightLP = getFoundLPhrs().get(leftIndex + 1);
        System.out.println(leftLP.getPhraseContent() + " : " + rightLP.getPhraseContent());
        for (int i = 0; i < leftLP.getDistanceMatrix().length; i++) {
            for (int j = 0; j < leftLP.getDistanceMatrix()[i].length; j++) {
                System.out.println("cost tai " + i + " : " + j + " : " + leftLP.getDistanceMatrix()[i][j]);
            }
        }
    }

    private boolean bestUnitsSelection() {
        return true;
    }

    private void printBestNextUnit(int index) {
        LevelPhrase lp = getFoundLPhrs().get(index);
        if (lp.getMinDistanceToNextUnit().size() != lp.getIndexOfBestNextUnit().size()) {
            System.out.println("ko biet la sai o cho nao");
        } else {
            for (int i = 0; i < lp.getMinDistanceToNextUnit().size(); i++) {
                System.out.println("min dis: " + lp.getMinDistanceToNextUnit().get(i) + " tai: " + lp.getBestIndex().get(i) + " : " + lp.getIndexOfBestNextUnit().get(i));
            }
        }
    }

    /*
     * giu lai M don vi ung vien tot nhat doi voi moi don vi dich
     */
    private void selectPreFinalCandUnits() {
        for (int i = 0; i < getFoundLPhrs().size() - 1; i++) {
            ArrayList<Distance> distanceArray1 = getFoundLPhrs().get(i).getDistanceArray();
            for (int j = 0; j < distanceArray1.size(); j++) {
                ArrayList<Integer> indexesOfPreSelectedUnit = getFoundLPhrs().get(i).getIndexesOfPreSelectedUnit();
                if (indexesOfPreSelectedUnit.size() < MAX_CAND_UNIT) {
                    //neu chua du so luong CAND_UNIT hoac chua co o trong indexesOfPreSelectedUnit thi add vao indexesOfPreSelectedUnit
                    boolean isExist = false;
                    for (int k = 0; k < indexesOfPreSelectedUnit.size(); k++) {
                        if (indexesOfPreSelectedUnit.get(k) == distanceArray1.get(j).getIndexOfPreCand()) {
                            isExist = true;
                        }
                    }
                    if (!isExist) {
                        indexesOfPreSelectedUnit.add(distanceArray1.get(j).getIndexOfPreCand());
                    }
                }
                ////////////////////
                ArrayList<Integer> indexesOfPreSelectedUnit2 = getFoundLPhrs().get(i + 1).getIndexesOfPreSelectedUnit();
                if (indexesOfPreSelectedUnit2.size() < MAX_CAND_UNIT / 2) {
                    //neu chua du so luong CAND_UNIT hoac chua co o trong indexesOfPreSelectedUnit thi add vao indexesOfPreSelectedUnit
                    boolean isExist = false;
                    for (int k = 0; k < indexesOfPreSelectedUnit2.size(); k++) {
                        if (indexesOfPreSelectedUnit2.get(k) == distanceArray1.get(j).getIndexOfNextCand()) {
                            isExist = true;
                        }
                    }
                    if (!isExist) {
                        indexesOfPreSelectedUnit2.add(distanceArray1.get(j).getIndexOfNextCand());
                    }
                }
            }
        }
    }
    ////////
    /*
     * cai dat thuat toan quy hoach dong. Doi voi moi don vi ung vien, chi giu lai
     * duong di ngan nhat toi no
     */

    private void selectPreFinalCandUnitsByViterbi(int beginIndex, int endIndex) {
        if (getFoundLPhrs().get(beginIndex).getPreBestPaths().isEmpty()) {
            getFoundLPhrs().get(beginIndex).getPreBestPaths().add(new Path(0, 0));
        }
        /////////
        //cai dat thuat toan quy hoach dong - Viterbi
        for (int i = beginIndex; i < endIndex; i++) {
            float[][] distanceMatrix = getFoundLPhrs().get(i).getDistanceMatrix();
            LevelPhrase preLP = getFoundLPhrs().get(i);
            LevelPhrase nextLP = getFoundLPhrs().get(i + 1);
            for (int j = 0; j < nextLP.getFoundIndexes1().size(); j++) {
                int indexOfPreCand = 0;
                float minDisOfCandJofNextLP = distanceMatrix[indexOfPreCand][j] + preLP.getPreBestPaths().get(indexOfPreCand).getTotalDistance();
                for (int k = 0; k < preLP.getFoundIndexes1().size(); k++) {
                    float tmpDis = distanceMatrix[k][j] + preLP.getPreBestPaths().get(k).getTotalDistance();
                    if (minDisOfCandJofNextLP > tmpDis) {
                        minDisOfCandJofNextLP = tmpDis;
                        indexOfPreCand = k;
                    }
                }
                //System.out.println("minDis: " + minDisOfCandJofNextLP);
                nextLP.getPreBestPaths().add(new Path(minDisOfCandJofNextLP, indexOfPreCand, j));
                //nextLP.getPreBestPaths().add(new Path(minDisOfCandJofNextLP, preLP.getPreBestPaths().get(indexOfPreCand), j));
            }
        }
        ///////////////////

    }
    ///////////////////////////

    private void selectFinalCandUnits() {
        int beginSen = 0, endSen;
        boolean isBeginSen = false;
        for (int i = 0; i < getFoundLPhrs().size(); i++) {
            ///////////////////////////////////////////////////////
            String phraseContent = getFoundLPhrs().get(i).getPhraseContent().trim();
            if ((phraseContent.compareTo("SILS") == 0)) {
                //|| phraseContent.compareTo("SIL") == 0
                isBeginSen = !isBeginSen;
                if (!isBeginSen) {
                    //neu ket thuc cau
                    endSen = i;
                    for (int j = beginSen; j < endSen; j++) {
                        //System.out.println("j: " + j);
                        //selectBestNextUnitOfaCandUnit(getFoundLPhrs().get(j), getFoundLPhrs().get(j + 1));
//                        if (j == beginSen) {
//                            getFoundLPhrs().get(j).getPreBestPaths().add(new Path(0, getFoundLPhrs().get(j).getIndexesOfPreSelectedUnit().get(0)));
//                            System.out.println("begin Sen " + beginSen);
//                        }
                        //selectBestPath(j, j + 1);
                        selectPreFinalCandUnitsByViterbi(beginSen, endSen);
                    }
                    Collections.sort(getFoundLPhrs().get(endSen).getPreBestPaths());
                    System.out.println("duong di ngan nhat: " + getFoundLPhrs().get(endSen).getPreBestPaths().size() + " : ");
                    Path bestPath = getFoundLPhrs().get(endSen).getPreBestPaths().get(0);
                    System.out.println("best Path of Sentence start at number: " + beginSen + " distance: " + bestPath.getTotalDistance());

//                    for (int j = 0; j < bestPath.getIndexes().size(); j++) {
//                        int ind = j + beginSen;
//                        System.out.println("LP thu: " + ind + " co bestCand tai vi tri: " + bestPath.getIndexes().get(j));
////                        if (j < bestPath.getIndexes().size()-2) {
////                            costToCheck += getFoundLPhrs().get(ind).getDistanceMatrix()[bestPath.getIndexes().get(j)][bestPath.getIndexes().get(j) + 1];
////                        }
//                    }
//                    if (costToCheck != bestPath.getTotalDistance()) {
//                        System.out.println("min distance ko dung");
//                    }
                    System.out.println("");
                    setIndexOfBestPath(beginSen, endSen, bestPath);
                    //this.setIndexOfBestPath(beginSen, endSen, getFoundLPhrs().get(endSen).getPreBestPaths().get(0).getIndexes());
                    //beginSen = endSen + 1;

                } else {
                    //neu bat dau cau
                    beginSen = i;
                }
            }
            ///////////////////////////////////////////////////////
//            LevelPhrase currentLP = getFoundLPhrs().get(i);
//            LevelPhrase nextLP = getFoundLPhrs().get(i + 1);
//            for (int j = 0; j < currentLP.getIndexesOfPreSelectedUnit().size(); j++) {
//                int indexOfPreSelectedUnit = currentLP.getIndexesOfPreSelectedUnit().get(j);
//                for (int k = 0; k < nextLP.getIndexesOfPreSelectedUnit().get(k); k++) {
//                    float tmpDis = currentLP.getDistanceMatrix()[indexOfPreSelectedUnit][nextLP.getIndexesOfPreSelectedUnit().get(k)];
//                }
//            }
            ///////////////////////////////
        }//end for
    }
    /*
     *
     */

    public void selectBestPath(int indexOfCurrentLP, int indexOfNextLP) {
        LevelPhrase currentLP = getFoundLPhrs().get(indexOfCurrentLP);
        LevelPhrase nextLP = getFoundLPhrs().get(indexOfNextLP);
        for (int j = 0; j < currentLP.getIndexesOfPreSelectedUnit().size(); j++) {
            int indexOfPreSelectedUnitOfCurrentLp = currentLP.getIndexesOfPreSelectedUnit().get(j);
            ArrayList<Path> pathByIndexOfCandUnit = currentLP.getPathByIndexOfCandUnit(indexOfPreSelectedUnitOfCurrentLp);
            for (int k = 0; k < nextLP.getIndexesOfPreSelectedUnit().size(); k++) {
                float tmpDis = currentLP.getDistanceMatrix()[indexOfPreSelectedUnitOfCurrentLp][nextLP.getIndexesOfPreSelectedUnit().get(k)];
                for (int i = 0; i < pathByIndexOfCandUnit.size(); i++) {
                    Path pathByIndexI = pathByIndexOfCandUnit.get(i);
                    //Path p = new Path(pathByIndexOfCandUnit.get(i).getTotalDistance() + tmpDis, pathByIndexOfCandUnit.get(i).getIndexes(), nextLP.getIndexesOfPreSelectedUnit().get(k));
//                    Path p = new Path();
//                    p.setDistance(pathByIndexI.getTotalDistance()+tmpDis);
//                    p.setIndexOfCurrentCandUnitInPath(nextLP.getIndexesOfPreSelectedUnit().get(k));
//                    p.setIndexesOfPreCandUnitsInPath(pathByIndexI.getIndexes());
//                    p.getIndexes().add(p.getIndexOfCurrentCandUnitInPath());
                    Path p = new Path(tmpDis, pathByIndexI, nextLP.getIndexesOfPreSelectedUnit().get(k));
                    nextLP.getPreBestPaths().add(p);
                }
            }
        }
        ////////////////////////////////////
        //phong tranh loi Java heap space
        currentLP.getPreBestPaths().removeAll(currentLP.getPreBestPaths());
    }

    private void setIndexOfBestPath(int beginIndexOfSen, int endIndexOfSen, ArrayList<Integer> indexes) {
        if (indexes.size() - (endIndexOfSen - beginIndexOfSen) != 1) {
            System.out.println("sai roi, hichic");
        } else {
            for (int i = beginIndexOfSen; i <= endIndexOfSen; i++) {
                if (getFoundLPhrs().get(i).isFound() == 1) {
                    Integer index = indexes.get(i - beginIndexOfSen);
                    getFoundLPhrs().get(i).setSelectedSen(getFoundLPhrs().get(i).getFoundIndexes1().get(index));
                    getFoundLPhrs().get(i).setSelectedSylPhrs(getFoundLPhrs().get(i).getFoundIndexes2().get(index));
                    getFoundLPhrs().get(i).setSelectedSyllable(getFoundLPhrs().get(i).getFoundIndexes3().get(index));
                } else {
                    //System.out.println("khong thay: " + getFoundLPhrs().get(i).getPhraseContent());
                }
            }
            /////////////////           
            for (int i = beginIndexOfSen; i <= endIndexOfSen; i++) {
                fileNames.add(UnitSearching.getAllSenInTextDB().get(foundLPhrs.get(i).getSelectedSen()).getCarryingFile());
                int selectedSen = foundLPhrs.get(i).getSelectedSen();
                int selectedSylPhrs = foundLPhrs.get(i).getSelectedSylPhrs();
                int selectedSyllable = foundLPhrs.get(i).getSelectedSyllable();
                int startIndex = UnitSearching.getAllSenInTextDB().get(selectedSen).getSylPhrases().get(selectedSylPhrs).getSyllablesInPh().get(selectedSyllable).getStartIndex();
                int endIndex = UnitSearching.getAllSenInTextDB().get(selectedSen).getSylPhrases().get(selectedSylPhrs).getSyllablesInPh().get(selectedSyllable + foundLPhrs.get(i).getPhraseLen() - 1).getEndIndex();
                foundLPhrs.get(i).setStartIndex(startIndex);
                foundLPhrs.get(i).setEndIndex(endIndex);
            }
        }
    }
    ////////////////////////

    private void setIndexOfBestPath(int beginIndexOfSen, int endIndexOfSen, Path bestPath) {
        int indexOfCurrentCandUnitInPath = getFoundLPhrs().get(endIndexOfSen).getPreBestPaths().get(0).getIndexOfCurrentCandUnitInPath();
        int indexOfPreCandUnitInPath = getFoundLPhrs().get(endIndexOfSen).getPreBestPaths().get(0).getIndexOfPreCandUnitInPath();

        for (int i = endIndexOfSen; i >= beginIndexOfSen; i--) {
            //indexOfCurrentCandUnitInPath = getFoundLPhrs().get(i).getPreBestPaths().get(indexOfCurrentCandUnitInPath).getIndexOfCurrentCandUnitInPath();
            getFoundLPhrs().get(i).setSelectedSen(getFoundLPhrs().get(i).getFoundIndexes1().get(indexOfCurrentCandUnitInPath));
            getFoundLPhrs().get(i).setSelectedSylPhrs(getFoundLPhrs().get(i).getFoundIndexes2().get(indexOfCurrentCandUnitInPath));
            getFoundLPhrs().get(i).setSelectedSyllable(getFoundLPhrs().get(i).getFoundIndexes3().get(indexOfCurrentCandUnitInPath));
            ///////
            indexOfPreCandUnitInPath = getFoundLPhrs().get(i).getPreBestPaths().get(indexOfCurrentCandUnitInPath).getIndexOfPreCandUnitInPath();
            indexOfCurrentCandUnitInPath = indexOfPreCandUnitInPath;
        }
        ////////////////////////
        /////////////////
        for (int i = beginIndexOfSen; i <= endIndexOfSen; i++) {
            fileNames.add(UnitSearching.getAllSenInTextDB().get(foundLPhrs.get(i).getSelectedSen()).getCarryingFile());
            int selectedSen = foundLPhrs.get(i).getSelectedSen();
            int selectedSylPhrs = foundLPhrs.get(i).getSelectedSylPhrs();
            int selectedSyllable = foundLPhrs.get(i).getSelectedSyllable();
            int startIndex = UnitSearching.getAllSenInTextDB().get(selectedSen).getSylPhrases().get(selectedSylPhrs).getSyllablesInPh().get(selectedSyllable).getStartIndex();
            int endIndex = UnitSearching.getAllSenInTextDB().get(selectedSen).getSylPhrases().get(selectedSylPhrs).getSyllablesInPh().get(selectedSyllable + foundLPhrs.get(i).getPhraseLen() - 1).getEndIndex();
            foundLPhrs.get(i).setStartIndex(startIndex);
            foundLPhrs.get(i).setEndIndex(endIndex);
        }
    }

    /**
     * @return the inputTextContent
     */
    public StringBuffer getInputTextContent() {
        return inputTextContent;
    }

    /**
     * @param inputTextContent the inputTextContent to set
     */
    private void setInputTextContent() {
        inputTextContent = new StringBuffer();
        for (int i = 0; i < us.allSenInTextInput.size(); i++) {
            inputTextContent.append(us.allSenInTextInput.get(i).getSenContent()).append(" .\n");
        }
    }
}
