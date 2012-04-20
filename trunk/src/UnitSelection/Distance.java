/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UnitSelection;

/**
 *
 * @author thaodv-bkit
 */
public class Distance implements Comparable<Distance> {

    private float distance;
    private int indexOfPreCand;
    private int indexOfNextCand;

    public Distance(float dis, int index1, int index2) {
        this.distance = dis;
        this.indexOfPreCand = index1;
        this.indexOfNextCand = index2;
    }

    /**
     * @return the distance
     */
    public float getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(float distance) {
        this.distance = distance;
    }

    /**
     * @return the indexOfPreCand
     */
    public int getIndexOfPreCand() {
        return indexOfPreCand;
    }

    /**
     * @param indexOfPreCand the indexOfPreCand to set
     */
    public void setIndexOfPreCand(int indexOfPreCand) {
        this.indexOfPreCand = indexOfPreCand;
    }

    /**
     * @return the indexOfNextCand
     */
    public int getIndexOfNextCand() {
        return indexOfNextCand;
    }

    /**
     * @param indexOfNextCand the indexOfNextCand to set
     */
    public void setIndexOfNextCand(int indexOfNextCand) {
        this.indexOfNextCand = indexOfNextCand;
    }

    public int compareTo(Distance o) {
        if (this.distance > o.distance) {
            return 1;
        } else if (this.distance == o.distance) {
            return 0;
        } else {
            return -1;
        }
    }
}
