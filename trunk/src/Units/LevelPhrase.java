/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Units;

import UnitSelection.Distance;
import UnitSelection.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 *
 * @author thaodv_bkit
 */
public class LevelPhrase extends Phrase {

    private int level;
    private ArrayList<Integer> indexesOfSubLevel = new ArrayList<Integer>();
    private ArrayList<String> syllableIn = new ArrayList<String>();
    private int isFound;// LP co duoc tim thay trong TextDB hay khong
    //isFound = 1; LP duoc tim thay trong TextDB
    //isFound = 2; LP duoc tim thay trong HalfSyl
    //isFound = 0; LP ko duoc tim thay trong HalfSyl
    private ArrayList<Integer> foundSen = new ArrayList<Integer>();//id cua cac cau tim thay LP
    private ArrayList<Integer> foundSylPhrs = new ArrayList<Integer>();// id cua SylPhrase tim thay LP
    private ArrayList<Integer> foundSyllable = new ArrayList<Integer>();// id cua Syllable trong SylPhrase tim thay LP
    private ArrayList<Integer> foundEndSyllable = new ArrayList<Integer>();//vi tri cua syllable cuoi cung trong sylphrase tim thay LP
    private int selectedSen, selectedSylPhrs, selectedSyllable; //id cua cau, sylphrase, syllable duoc chon sau khi co ham chi phi
    private Syllable firstSylInLPhrs, lastSylInLPhrs;// am tiet bat dau va ket thuc cua LevelPhrase. dung de tinh khoang cach ngu canh cho cac LP ben canh
    private int posInSen; // vi tri bat dau cua LP trong Phrase
    private float[][] distanceMatrix;
    //distanceMatrix[i][j] la khoang cach giua CandUnit thu i cua LP hien tai va CandUnit thu j cua RightLP
    private ArrayList<Distance> distanceArray = new ArrayList<Distance>();
    //distanceMatrix[i][j] la khoang cach giua CandUnit thu i cua LP hien tai va CandUnit thu j cua LP tiep theo
    private ArrayList<Integer> indexOfBestNextUnit = new ArrayList<Integer>();
    private ArrayList<Float> minDistanceToNextUnit = new ArrayList<Float>();
    private ArrayList<Integer> bestIndex = new ArrayList<Integer>();
    private ArrayList<Integer> indexesOfPreSelectedUnit = new ArrayList<Integer>();
    private ArrayList<Path> preBestPaths = new ArrayList<Path>(); //cac duong di toi uu phia truoc
    //chi so cua Cand thu i tuong ung voi indexOfBestNextUnit
    //indexOfPreBestUnit.get(i) la chi so cua CandUnit tot nhat cua CandUnit thu i

    public LevelPhrase() {
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the subLevel
     */
    public ArrayList<Integer> getIndexesOfSubLevel() {
        return indexesOfSubLevel;
    }

    /*
     * kiem tra xem phrase co subLevel hay khong
     */
    public boolean haveSubLevel() {
        if (indexesOfSubLevel.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /*
     *
     */
    public void addIndexOfSubLevel(int indexOfPhrase) {
        indexesOfSubLevel.add(indexOfPhrase);
    }

    /**
     * @return the syllableIn
     */
    public ArrayList<String> getSyllableIn() {
        return syllableIn;
    }

    /**
     * @param syllableIn the syllableIn to set
     */
    public void setSyllableIn() {
        String pc = this.getPhraseContent();
        StringTokenizer stk = new StringTokenizer(pc);
        setPhraseLen(stk.countTokens());

        while (stk.hasMoreTokens()) {
            syllableIn.add(stk.nextToken());
        }
    }

    @Override
    public void setPhraseContent(String phraseContent) {
        super.setPhraseContent(phraseContent.trim());
        //this.setSyllableIn();
    }

    /*
     *
     */
    public void setIsFound(int f) {
        isFound = f;
    }

    public int isFound() {
        return isFound;
    }

    /**
     * @return the foundSen
     */
    public ArrayList<Integer> getFoundIndexes1() {
        return foundSen;
    }

    /**
     * @return the foundSylPhrs
     */
    public ArrayList<Integer> getFoundIndexes2() {
        return foundSylPhrs;
    }

    /**
     * @return the foundSyllable
     */
    public ArrayList<Integer> getFoundIndexes3() {
        return foundSyllable;
    }

    /**
     * @return the selectedSen
     */
    public int getSelectedSen() {
        return selectedSen;
    }

    /**
     * @param selectedSen the selectedSen to set
     */
    public void setSelectedSen(int selectedSen) {
        this.selectedSen = selectedSen;
    }

    /**
     * @return the selectedSylPhrs
     */
    public int getSelectedSylPhrs() {
        return selectedSylPhrs;
    }

    /**
     * @param selectedSylPhrs the selectedSylPhrs to set
     */
    public void setSelectedSylPhrs(int selectedSylPhrs) {
        this.selectedSylPhrs = selectedSylPhrs;
    }

    /**
     * @return the selectedSyllable
     */
    public int getSelectedSyllable() {
        return selectedSyllable;
    }

    /**
     * @param selectedSyllable the selectedSyllable to set
     */
    public void setSelectedSyllable(int selectedSyllable) {
        this.selectedSyllable = selectedSyllable;
    }

    /**
     * @return the firstSylInLPhrs
     */
    public Syllable getFirstSylInLPhrs() {
        return firstSylInLPhrs;
    }

    /**
     * @param firstSylInLPhrs the firstSylInLPhrs to set
     */
    public void setFirstSylInLPhrs(Syllable firstSylInLPhrs) {
        this.firstSylInLPhrs = firstSylInLPhrs;
    }

    /**
     * @return the lastSylInLPhrs
     */
    public Syllable getLastSylInLPhrs() {
        return lastSylInLPhrs;
    }

    /**
     * @param lastSylInLPhrs the lastSylInLPhrs to set
     */
    public void setLastSylInLPhrs(Syllable lastSylInLPhrs) {
        this.lastSylInLPhrs = lastSylInLPhrs;
    }

    /**
     * @return the posInSen
     */
    public int getPosInSen() {
        return posInSen;
    }

    /**
     * @param posInSen the posInSen to set
     */
    public void setPosInSen(int posInSen) {
        this.posInSen = posInSen;
    }

    /**
     * @return the foundEndSyllable
     */
    public ArrayList<Integer> getFoundIndexes4() {
        return foundEndSyllable;
    }

    /**
     * @return the distanceMatrix
     */
    public float[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    /**
     * @param distanceMatrix the distanceMatrix to set
     */
    public void setDistanceMatrix(float[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    /**
     * @return the distanceArray
     */
//    public ArrayList<ArrayList<Float>> getDistanceArray() {
//        return distanceArray;
//    }
//
//    /**
//     * @param distanceArray the distanceArray to set
//     */
//    public void setDistanceArray(ArrayList<ArrayList<Float>> distanceArray) {
//        this.distanceArray = distanceArray;
//    }
    /**
     * @return the indexOfPreBestUnit
     */
    public ArrayList<Integer> getIndexOfBestNextUnit() {
        return indexOfBestNextUnit;
    }

    /**
     * @param indexOfPreBestUnit the indexOfPreBestUnit to set
     */
    public void setIndexOfBestNextUnit(ArrayList<Integer> indexOfBestNextUnit) {
        this.indexOfBestNextUnit = indexOfBestNextUnit;
    }

    /**
     * @return the minDistanceFromPreUnit
     */
    public ArrayList<Float> getMinDistanceToNextUnit() {
        return minDistanceToNextUnit;
    }

    /**
     * @param minDistanceFromPreUnit the minDistanceFromPreUnit to set
     */
    public void setMinDistanceToNextUnit(ArrayList<Float> minDistanceFromPreUnit) {
        this.minDistanceToNextUnit = minDistanceFromPreUnit;
    }

    /**
     * @return the bestIndex
     */
    public ArrayList<Integer> getBestIndex() {
        return bestIndex;
    }

    /**
     * @return the distanceArray
     */
    public ArrayList<Distance> getDistanceArray() {
        return distanceArray;
    }

    /**
     * @param distanceArray the distanceArray to set
     */
    public void setDistanceArray(ArrayList<Distance> distanceArray) {
        this.distanceArray = distanceArray;
    }

    /**
     * @return the indexesOfPreSelectedUnit
     */
    public ArrayList<Integer> getIndexesOfPreSelectedUnit() {
        return indexesOfPreSelectedUnit;
    }

    /**
     * @return the preBestPaths
     */
    public ArrayList<Path> getPreBestPaths() {
        return preBestPaths;
    }

    /**
     * @param preBestPaths the preBestPaths to set
     */
    public void setPreBestPaths(ArrayList<Path> preBestPaths) {
        this.preBestPaths = preBestPaths;
    }

    /**
     * @param indexOfCandUnit chi so cua don vi ung vien muon lay khoang cach
     */
    public ArrayList<Path> getPathByIndexOfCandUnit(int indexOfCandUnit) {
        ArrayList<Path> result = new ArrayList<Path>();
        result.removeAll(result);
        int maxPath = 200;
        for (int i = 0; i < preBestPaths.size(); i++) {
//            ArrayList<Integer> indexesOfPreCandUnitsInPath = preBestPaths.get(i).getIndexes();
//            if (indexesOfPreCandUnitsInPath.get(indexesOfPreCandUnitsInPath.size() - 1) == indexOfCandUnit) {
//                result.add(preBestPaths.get(i));
//            }
            Path pathByIndex = new Path();
            pathByIndex = preBestPaths.get(i);
            if(pathByIndex.getIndexOfCurrentCandUnitInPath()==indexOfCandUnit){
                 result.add(pathByIndex);
            }
        }
        Collections.sort(result);
        if (result.size() < maxPath) {
            maxPath = result.size();
        }
        ArrayList<Path> subList = new ArrayList<Path>();
        for (int i = 0; i < maxPath; i++) {
            subList.add(result.get(i));
        }
        //System.out.println("sublist size: "+subList.size());
        return subList;
    }
}
