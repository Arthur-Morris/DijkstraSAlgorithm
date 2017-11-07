package dijkstrs2;

public class pix implements Comparable<pix> {

    public short x, y;
    public int dist;
    public pix last = null;

    public pix(int d, int x, int y) {

        this.x = (short) x;
        this.y = (short) y;
        dist = d;
    }

    public pix() {
        dist = -1;
    }

    public pix(pix x) {
        this.x = x.x;
        this.y = x.y;
        dist = x.dist;
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
