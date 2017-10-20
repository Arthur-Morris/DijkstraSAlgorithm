/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra.s.algorithm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.PriorityQueue;

import javax.imageio.ImageIO;

public class DijkstraSAlgorithm {

    static BufferedImage bimg;

    public static void main(String[] args) throws IOException {
        String filepath = "test.jpg";
        bimg = ImageIO.read(new File(filepath));
        PriorityQueue<pix> queue = new PriorityQueue<pix>();

        pix[][] stor = new pix[bimg.getWidth()][bimg.getHeight()];

        System.out.println(bimg.getHeight() + " " + bimg.getWidth());
        for (int x = 0; x < bimg.getWidth(); x++) {
            for (int y = 0; y < bimg.getHeight(); y++) {
                pix t = new pix();
                stor[x][y] = t;
                queue.offer(t);
                // System.out.println(y);
                int color = bimg.getRGB(x, y);
                t.x = x;
                t.y = y;
                t.b = color & 0xff;
                t.g = (color & 0xff00) >> 8;
                t.r = (color & 0xff0000) >> 16;
            }
            System.out.println("loaded line " + x);
        }
        stor[0][0].dist = 0;
        //System.out.println(queue.remove().dist + " " + queue.size());
        while (queue.size() > 0) {
            pix temp = queue.poll();
            if (temp.x - 1 >= 0) {
                if (stor[temp.x - 1][temp.y].dist == -1 || stor[temp.x - 1][temp.y].dist > temp.dist + dist(temp, stor[temp.x - 1][temp.y])) {
                    stor[temp.x - 1][temp.y].dist = temp.dist + dist(temp, stor[temp.x - 1][temp.y]);
                }
            }
            if (temp.y - 1 >= 0) {
                if (stor[temp.x][temp.y - 1].dist == -1 || stor[temp.x][temp.y - 1].dist > temp.dist + dist(temp, stor[temp.x][temp.y - 1])) {
                    stor[temp.x][temp.y - 1].dist = temp.dist + dist(temp, stor[temp.x][temp.y - 1]);
                }

            }
            if (temp.x + 1 < stor.length) {
                if (stor[temp.x + 1][temp.y].dist == -1 || stor[temp.x + 1][temp.y].dist > temp.dist + dist(temp, stor[temp.x + 1][temp.y])) {
                    stor[temp.x + 1][temp.y].dist = temp.dist + dist(temp, stor[temp.x + 1][temp.y]);
                }

            }
            if (temp.y + 1 < stor[0].length) {
                if (stor[temp.x][temp.y + 1].dist == -1 || stor[temp.x][temp.y + 1].dist > temp.dist + dist(temp, stor[temp.x][temp.y + 1])) {
                    stor[temp.x][temp.y + 1].dist = temp.dist + dist(temp, stor[temp.x][temp.y + 1]);
                }

            }
            System.out.println(temp.dist+" ");
            System.out.println(stor[0][0].dist+" ");
            System.out.println();
        }
        for (int x = 0; x < stor.length; x++) {
            for (int y = 0; y < stor[0].length; y++) {
            System.out.print(stor[x][y].dist+" ");
            }
            System.out.println();
        }
            System.out.print(stor[0][0].dist+" ");
    }
    

    

    public static int dist(pix A, pix B) {
        return (int) Math.sqrt(1 + ((A.r - B.r) * (A.r - B.r) + (A.g - B.g) * (A.g - B.g) + (A.b - B.b) * (A.b - B.b)));
    }
}
