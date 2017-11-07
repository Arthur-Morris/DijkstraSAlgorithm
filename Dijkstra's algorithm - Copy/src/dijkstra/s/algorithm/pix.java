/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra.s.algorithm;

import java.util.Comparator;

/**
 *
 * @author arthur
 */
public class pix implements Comparable<pix> {

    public short x, y;
    public int dist;
    public pix last = null;

    public pix(int x) {
        dist = x;
    }

    public pix() {
        dist = -1;
    }

    public int compareTo(pix o) {
        if (this.dist == -1 && o.dist == -1) {
            return 0;
        }
        if (this.dist == -1) {
            return 1;
        }
        if (o.dist == -1) {
            return -1;
        }
        return (this.dist - o.dist);
    }
}
