/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra.s.algorithm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;

import javax.imageio.ImageIO;

public class DijkstraSAlgorithm {

    static BufferedImage bimg;

    public static void main(String[] args) throws IOException {
        String filepath = "test";
        bimg = ImageIO.read(new File(filepath + ".jpg"));
        PriorityQueue<pix> queue = new PriorityQueue<pix>();
        pix[][] stor = new pix[bimg.getHeight()][bimg.getWidth()];
        // System.out.println(bimg.getHeight() + " " + bimg.getWidth());
        for (int x = 0; x < bimg.getHeight(); x++) {
            for (int y = 0; y < bimg.getWidth(); y++) {
                pix t = new pix();
                stor[x][y] = t;
                        //queue.offer(t);
                // System.out.println(y);
                t.x = (short) x;
                t.y = (short) y;
            }
            //System.out.println("loaded line " + x);
        }
        stor[0][0].dist = 0;
        queue.offer(stor[0][0]);
        long time = System.currentTimeMillis();
        int mx = 0;
        // System.out.println("loaded " + queue.size());
        while (queue.size() > 0) {
            if (queue.size() > mx) {
                mx = queue.size();
            }
            pix temp = queue.poll();

            // System.out.println(temp.dist + " " + temp.y);
            if (temp.dist == -1) {
            } else {

                if (temp.x - 1 >= 0) {
                    if (stor[temp.x - 1][temp.y].dist == -1 || stor[temp.x - 1][temp.y].dist > temp.dist + dist(bimg.getRGB(temp.x, temp.y), bimg.getRGB(temp.x - 1, temp.y))) {

                        //System.out.println(temp.dist);
                        stor[temp.x - 1][temp.y].dist = temp.dist + dist(bimg.getRGB(temp.x, temp.y), bimg.getRGB(temp.x - 1, temp.y));
                        stor[temp.x - 1][temp.y].last = temp;
                        if (!queue.contains(stor[temp.x - 1][temp.y])) {
                            queue.add(stor[temp.x - 1][temp.y]);
                        }
                    }
                }
                if (temp.y - 1 >= 0) {
                    if (stor[temp.x][temp.y - 1].dist == -1 || stor[temp.x][temp.y - 1].dist > temp.dist + dist(bimg.getRGB(temp.x, temp.y), bimg.getRGB(temp.x, temp.y - 1))) {
                        stor[temp.x][temp.y - 1].dist = temp.dist + dist(bimg.getRGB(temp.x, temp.y), bimg.getRGB(temp.x, temp.y - 1));
                        stor[temp.x][temp.y - 1].last = temp;
                        if (!queue.contains(stor[temp.x][temp.y - 1])) {
                            queue.add(stor[temp.x][temp.y - 1]);
                        }
                    }

                }
                if (temp.x + 1 < stor.length) {
                    if (stor[temp.x + 1][temp.y].dist == -1 || stor[temp.x + 1][temp.y].dist > temp.dist + dist(bimg.getRGB(temp.x, temp.y), bimg.getRGB(temp.x + 1, temp.y))) {
                        stor[temp.x + 1][temp.y].dist = temp.dist + dist(bimg.getRGB(temp.x, temp.y), bimg.getRGB(temp.x + 1, temp.y));
                        queue.add(stor[temp.x + 1][temp.y]);
                        stor[temp.x + 1][temp.y].last = temp;
                        if (!queue.contains(stor[temp.x + 1][temp.y])) {
                            queue.add(stor[temp.x + 1][temp.y]);
                        }
                    }

                }
                if (temp.y + 1 < stor[0].length) {
                    if (stor[temp.x][temp.y + 1].dist == -1 || stor[temp.x][temp.y + 1].dist > temp.dist + dist(bimg.getRGB(temp.x, temp.y), bimg.getRGB(temp.x, temp.y + 1))) {
                        stor[temp.x][temp.y + 1].dist = temp.dist + dist(bimg.getRGB(temp.x, temp.y), bimg.getRGB(temp.x, temp.y + 1));
                        queue.add(stor[temp.x][temp.y + 1]);
                        stor[temp.x][temp.y + 1].last = temp;
                        if (!queue.contains(stor[temp.x][temp.y + 1])) {
                            queue.add(stor[temp.x][temp.y + 1]);
                        }
                    }

                }
            }
        }
        time = System.currentTimeMillis() - time;
        System.out.println(mx);

        double max = 0;
        for (int x = 0; x < stor.length; x++) {
            for (int y = 0; y < stor[0].length; y++) {
                if (stor[x][y].dist > max) {
                    max = stor[x][y].dist;
                }
                //System.out.print(stor[x][y].dist + " ");
            }
            //System.out.println();
        }
        for (int x = 0; x < stor.length; x++) {
            for (int y = 0; y < stor[0].length; y++) {
                bimg.setRGB(x, y, (int) (stor[x][y].dist / max * 255.0f) + ((int) (stor[x][y].dist / max * 255.0f) << 8) + ((int) (stor[x][y].dist / max * 255.0f) << 16));
            }
        }
        ImageIO.write(bimg, "png", new File(filepath + time + "1.png"));

        bimg = ImageIO.read(new File(filepath + ".jpg"));
        pix t = stor[stor.length - 1][stor[0].length - 1];
        while (t.last != null) {
            bimg.setRGB(t.x, t.y, 0);
            t = t.last;
        }
        ImageIO.write(bimg, "png", new File(filepath + time + "2.png"));
    }

    public static int dist(int A, int B) {
        int rA = (A & 0xff0000) >> 16;
        int rB = (B & 0xff0000) >> 16;

        int gA = (A & 0xff00) >> 8;
        int gB = (B & 0xff00) >> 8;

        int bA = A & 0xff;
        int bB = B & 0xff;

        return 1 + (int) Math.sqrt(((rA - rB) * (rA - rB) + (gA - gB) * (gA - gB) + (bA - bB) * (bA - bB)));
    }
}
