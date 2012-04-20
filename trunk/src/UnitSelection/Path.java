/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UnitSelection;

import java.util.ArrayList;

/**
 *
 * @author thaodv_bkit
 */
public class Path implements Comparable<Path> {

    private float totalDistance;
    private ArrayList<Integer> indexes = new ArrayList<Integer>();//chi so cua cac don vi ung vien phia truoc LP hien tai
    private int indexOfCurrentCandUnitInPath;//chi so cua don vi ung vien hien tai
    private int indexOfPreCandUnitInPath;
    private int[] indexArray = new int[50];
    private int sizeOfIndexArray=0;
    
    public Path() {
    }

    public Path(float d, int index) {
          totalDistance = d;
          indexOfCurrentCandUnitInPath = index;
          indexes.add(index);
          indexArray[sizeOfIndexArray++] = index;
    }
    public Path(float d,ArrayList<Integer> indexes, int index) {
        totalDistance = d;
        //this.indexesOfPreCandUnitsInPath = new ArrayList<Integer>();
        //indexesOfPreCandUnitsInPath = indexes;
        //this.indexesOfPreCandUnitsInPath = indexes;
//        this.indexes.add(indexOfCurrentCandUnitInPath);
//        indexOfCurrentCandUnitInPath = index;
    }
    public Path(float d, Path p, int index){
        totalDistance = d+p.getTotalDistance();
        for (int i = 0; i < p.getIndexes().size(); i++) {
            Integer get = p.getIndexes().get(i);
            this.indexes.add(get);
        }
        indexes.add(index);
        indexOfCurrentCandUnitInPath = index;
        System.arraycopy(p.getIndexArray(), 0, this.indexArray, 0, p.getSizeOfIndexArray());
        
        sizeOfIndexArray = p.getSizeOfIndexArray();
        indexArray[sizeOfIndexArray++] = index;
    }
    /////
    public Path(float dis, int indexOfPre, int indexOfCurrent){
        totalDistance += dis;
        indexOfPreCandUnitInPath = indexOfPre;
        indexOfCurrentCandUnitInPath = indexOfCurrent;
    }

    public int compareTo(Path o) {
        if (this.totalDistance > o.totalDistance) {
            return 1;
        }else if(this.totalDistance == o.totalDistance){
            return 0;
        }else return -1;
    }

    /**
     * @return the distance
     */
    public float getTotalDistance() {
        return totalDistance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(float distance) {
        this.totalDistance = distance;
    }

    /**
     * @return the indexOfCurrentCandUnitInPath
     */
    public int getIndexOfCurrentCandUnitInPath() {
        return indexOfCurrentCandUnitInPath;
    }

    /**
     * @param indexOfCurrentCandUnitInPath the indexOfCurrentCandUnitInPath to set
     */
    public void setIndexOfCurrentCandUnitInPath(int indexOfCurrentCandUnitInPath) {
        this.indexOfCurrentCandUnitInPath = indexOfCurrentCandUnitInPath;
    }

    /**
     * @return the indexesOfPreCandUnitsInPath
     */
    public ArrayList<Integer> getIndexes() {
        return indexes;
    }

    /**
     * @param indexesOfPreCandUnitsInPath the indexesOfPreCandUnitsInPath to set
     */
    public void setIndexesOfPreCandUnitsInPath(ArrayList<Integer> indexesOfPreCandUnitsInPath) {
        this.indexes = indexesOfPreCandUnitsInPath;
    }

    /**
     * @return the indexArray
     */
    public int[] getIndexArray() {
        return indexArray;
    }

    /**
     * @return the sizeOfIndexArray
     */
    public int getSizeOfIndexArray() {
        return sizeOfIndexArray;
    }

    /**
     * @param sizeOfIndexArray the sizeOfIndexArray to set
     */
    public void setSizeOfIndexArray(int sizeOfIndexArray) {
        this.sizeOfIndexArray = sizeOfIndexArray;
    }

    /**
     * @return the indexOfPreCandUnitInPath
     */
    public int getIndexOfPreCandUnitInPath() {
        return indexOfPreCandUnitInPath;
    }

    /**
     * @param indexOfPreCandUnitInPath the indexOfPreCandUnitInPath to set
     */
    public void setIndexOfPreCandUnitInPath(int indexOfPreCandUnitInPath) {
        this.indexOfPreCandUnitInPath = indexOfPreCandUnitInPath;
    }
}
