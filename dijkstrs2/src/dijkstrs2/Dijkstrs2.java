/*Due date: in class Thursday, Nov 2.

Assignment: Implement two variants of Dijkstra's algorithm and compare their
performance. Write up your findings in 2500-3000 words. Details follow.

Feel free to consult Wikipedia, which has a decent description of Dijkstra's 
algorithm with conventional priority queue.

***

In this mini-project, you will investigate the performance of best-first single
-source shortest path algorithms in the context of geodesic distances. Best-first 
shortest path algorithms are exemplified by Dijkstra's algorithm. Here, geodesic 
distances are distances over the image plane where the distance between adjacent 
pixels is some fixed horizontal distance plus a vertical distance given by the 
color difference between the pixels, combined using Pythagoras.


Best-first shortest path planning algorithms work as follows. You begin with a 
graph in which every distance is unknown except for a set of start nodes, all with
distance zero. The start nodes are put into a priority queue. At every step, the 
top node, say p, is removed from the queue, p's distance is finalized, and then 
its neighbours are examined. For every neighbour n, you compute a hypothetical 
distance: the distance of p plus the distance between p and n. 
If the hypothetical distance for n is less than its previously stored distance -- 
or n's distance was previously unknown -- node n is updated and added to the 
priority queue. Eventually, all nodes have their distances finalized, and the 
algorithm terminates.


We will look at this algorithm for graphs over images, where each pixel is 
connected to its four immediate neighbours, up, down, left, right. In geodesic 
applications, the incremental distance between p and n is sqrt(1+g*colordist(p,n) ) 
where g is a constant and colordist(.) is a function measuring the squared color 
difference between the pixels given as arguments.


Don't be delayed by the the details of the edge weights. You can get pretty far 
in the coding just using constant edge weights of 1, or random edge weights, 
and then come back to the exact function over images at the end.



The algorithm described above is fairly straightforward. The main complexity 
lies in managing the priority queue; feel free to use a library for that. 
You will investigate two variants of the method. First, one where the priority 
queue uses the conventional "decrease-key" operation to update node values. 
Second, a version where nodes are not updated in the queue, but rather in the 
graph, and an updated node is inserted into the queue as a duplicate.

Nodes in the queue can therefore become stale, and you need to check whether a 
node is up to date (does its graph distance agree with the priority queue 
distance?) before processing it. Stale queue entries can safely be discarded.


One you have the implementation of both variants, compare them. Confirm their 
correctness by making sure they give the same values (and maybe check with a friend).

Which is faster? Run them over a few dozen or a few hundred images to collect data. Then, write up your experience with the algorithms in 2500-3000 words (roughly 6 pages). Consider some of the following questions:


1. The theoretical runtime of the algorithm is O(N log N) for N nodes in the graph, i.e., N pixels. Run the method with various image sizes. Can you confirm the upper bound? What is the average-case complexity and what is a typical constant?


2. In the case of a heap-based priority queue, the runtime is actually O(N log K) 
for K nodes in the heap. What is the largest heap size you saw in each variant? 
Here "largest" should be as a fraction of the image pixels -- you can always get a 
bigger heap in absolute terms by running on a bigger image.


3. What do the shortest paths look like? Create a few visualizations of the 
distances on different input images. Two suggestions are (1) show the distance as 
a greyscale value over the image plane; (2) show several shortest paths overlaid 
on the original image.


4. Does your choice of starting node affect the runtime? For example, is there a 
difference between picking the centre, the upper left corner, or the lower right 
corner?






7. The original algorithm was designed for arbitrary graphs, but images have a 
highly regular structure. Can you think of ways to speed up the algorithm by 
exploiting the simple graph structure?


8. You don't need to limit yourself to these questions. If you have additional 
experiments or observations about the algorithm, you can report them.


 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstrs2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;
import javax.imageio.ImageIO;

/**
 *
 * @author arthur
 */
public class Dijkstrs2 {

    static BufferedImage bimg;

    public static void main(String[] args) throws IOException {
        for (int e = 1; e <= 50; e++) {
            int xi = (int) Math.sqrt(4096 * 3072 * 3.0 / 4.0 / 50.0 * e);
            int yi = xi * 4 / 3;
            System.out.print("\nsize," + xi + "," + yi + ",");
            for (int n = 0; n < 25; n++) {
                //System.out.println(n);
                //String filepath = "P_20171027_003658_050";
                String filepath = "P_20171103_113445";
                bimg = ImageIO.read(new File(filepath + ".jpg"));
                PriorityQueue<pix> queue = new PriorityQueue<pix>();
                pix[][] stor = new pix[yi][xi];
                int[][] dist = new int[yi][xi];
                pix t = new pix(0, stor.length / 2, stor[0].length / 2);

                queue.offer(new pix(t));
                for (int x = 0; x < dist.length; x++) {
                    for (int y = 0; y < dist[0].length; y++) {
                        dist[x][y] = -1;
                    }
                    //System.out.println("loaded line " + x);
                }
                dist[stor.length / 2][stor[0].length / 2] = 0;
                int mx = 0;
                long time = System.currentTimeMillis();
                while (queue.size() > 0) {
                    if (queue.size() > mx) {
                        mx = queue.size();
                    }
                    pix temp = queue.poll();
                    if (temp.dist == dist[temp.x][temp.y]) {

                        if (temp.x - 1 >= 0) {
                            if (dist[temp.x - 1][temp.y] == -1 || dist[temp.x - 1][temp.y] > temp.dist + distF(bimg.getRGB(temp.x - 1, temp.y), bimg.getRGB(temp.x, temp.y))) {
                                stor[temp.x - 1][temp.y] = temp;
                                dist[temp.x - 1][temp.y] = temp.dist + distF(bimg.getRGB(temp.x - 1, temp.y), bimg.getRGB(temp.x, temp.y));
                                queue.offer(new pix(dist[temp.x - 1][temp.y], temp.x - 1, temp.y));
                            }
                        }
                        if (temp.y - 1 >= 0) {
                            if (dist[temp.x][temp.y - 1] == -1 || dist[temp.x][temp.y - 1] > temp.dist + distF(bimg.getRGB(temp.x, temp.y - 1), bimg.getRGB(temp.x, temp.y))) {
                                stor[temp.x][temp.y - 1] = temp;
                                dist[temp.x][temp.y - 1] = temp.dist + distF(bimg.getRGB(temp.x, temp.y - 1), bimg.getRGB(temp.x, temp.y));
                                queue.offer(new pix(dist[temp.x][temp.y - 1], temp.x, temp.y - 1));
                            }
                        }
                        if (temp.x + 1 < stor.length) {
                            if (dist[temp.x + 1][temp.y] == -1 || dist[temp.x + 1][temp.y] > temp.dist + distF(bimg.getRGB(temp.x + 1, temp.y), bimg.getRGB(temp.x, temp.y))) {
                                stor[temp.x + 1][temp.y] = temp;
                                dist[temp.x + 1][temp.y] = temp.dist + distF(bimg.getRGB(temp.x + 1, temp.y), bimg.getRGB(temp.x, temp.y));
                                queue.offer(new pix(dist[temp.x + 1][temp.y], temp.x + 1, temp.y));
                            }
                        }
                        if (temp.y + 1 < stor[0].length) {
                            if (dist[temp.x][temp.y + 1] == -1 || dist[temp.x][temp.y + 1] > temp.dist + distF(bimg.getRGB(temp.x, temp.y + 1), bimg.getRGB(temp.x, temp.y))) {
                                stor[temp.x][temp.y + 1] = temp;
                                dist[temp.x][temp.y + 1] = temp.dist + distF(bimg.getRGB(temp.x, temp.y + 1), bimg.getRGB(temp.x, temp.y));
                                queue.offer(new pix(dist[temp.x][temp.y + 1], temp.x, temp.y + 1));
                            }
                        }
                    }
                }
                if (n == 0) {
                    System.out.println(mx);
                }
                time = System.currentTimeMillis() - time;
                System.out.print(time + ",");
                //System.out.println(stor[stor.length - 1][stor[0].length - 1].dist);
                double max = 0;/*
                for (int x = 0; x < stor.length; x++) {
                    for (int y = 0; y < stor[0].length; y++) {
                        if (dist[x][y] > max) {
                            max = dist[x][y];
                        }
                        //System.out.print(stor[x][y].dist + " ");
                    }
                    //System.out.println();
                }
                for (int x = 0; x < stor.length; x++) {
                    for (int y = 0; y < stor[0].length; y++) {
                        bimg.setRGB(x, y, (int) (dist[x][y] / max * 255.0f) + ((int) (dist[x][y] / max * 255.0f) << 8) + ((int) (dist[x][y] / max * 255.0f) << 16));
                    }
                }
                ImageIO.write(bimg, "png", new File(filepath + "l" + stor[stor.length - 1][stor[0].length - 1].dist + "1.png"));

                bimg = ImageIO.read(new File(filepath + ".jpg"));

                t = stor[stor.length - 1][stor[0].length - 1];
                while (t != null) {
                    bimg.setRGB(t.x, t.y, 0);
                    t = stor[t.x][t.y];
                }
                ImageIO.write(bimg, "png", new File(filepath + "l" + stor[stor.length - 1][stor[0].length - 1].dist + "2.png"));
            }*/
            }
        }
    }

    public static int distF(int A, int B) {
        int rA = (A & 0xff0000) >> 16;
        int rB = (B & 0xff0000) >> 16;

        int gA = (A & 0xff00) >> 8;
        int gB = (B & 0xff00) >> 8;

        int bA = A & 0xff;
        int bB = B & 0xff;

        double g = 1;

        return (int) Math.sqrt((1 + g * ((rA - rB) * (rA - rB) + (gA - gB) * (gA - gB) + (bA - bB) * (bA - bB))));
    }
}
