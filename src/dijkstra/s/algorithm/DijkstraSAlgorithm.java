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
        String filepath = "21269839_1344140905714877_1838133755_n";
        bimg = ImageIO.read(new File(filepath + ".jpg"));
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

            // System.out.println(temp.dist + " " + temp.y);
            if (temp.dist == -1) {
            } else {

                if (temp.x - 1 >= 0) {
                    if (stor[temp.x - 1][temp.y].dist == -1 || stor[temp.x - 1][temp.y].dist > temp.dist + dist(temp, stor[temp.x - 1][temp.y])) {

                        //System.out.println(temp.dist);
                        stor[temp.x - 1][temp.y].dist = temp.dist + dist(temp, stor[temp.x - 1][temp.y]);
                        queue.add(stor[temp.x - 1][temp.y]);
                        stor[temp.x - 1][temp.y].last = temp;
                    }
                }
                if (temp.y - 1 >= 0) {
                    if (stor[temp.x][temp.y - 1].dist == -1 || stor[temp.x][temp.y - 1].dist > temp.dist + dist(temp, stor[temp.x][temp.y - 1])) {
                        stor[temp.x][temp.y - 1].dist = temp.dist + dist(temp, stor[temp.x][temp.y - 1]);
                        queue.add(stor[temp.x][temp.y - 1]);
                        stor[temp.x][temp.y - 1].last = temp;
                    }

                }
                if (temp.x + 1 < stor.length) {
                    if (stor[temp.x + 1][temp.y].dist == -1 || stor[temp.x + 1][temp.y].dist > temp.dist + dist(temp, stor[temp.x + 1][temp.y])) {
                        stor[temp.x + 1][temp.y].dist = temp.dist + dist(temp, stor[temp.x + 1][temp.y]);
                        queue.add(stor[temp.x + 1][temp.y]);
                        stor[temp.x + 1][temp.y].last = temp;
                    }

                }
                if (temp.y + 1 < stor[0].length) {
                    if (stor[temp.x][temp.y + 1].dist == -1 || stor[temp.x][temp.y + 1].dist > temp.dist + dist(temp, stor[temp.x][temp.y + 1])) {
                        stor[temp.x][temp.y + 1].dist = temp.dist + dist(temp, stor[temp.x][temp.y + 1]);
                        queue.add(stor[temp.x][temp.y + 1]);
                        stor[temp.x][temp.y + 1].last = temp;
                    }

                }
            }
        }
        double max = 0;
        for (int x = 0; x < stor.length; x++) {
            for (int y = 0; y < stor[0].length; y++) {
                if (stor[x][y].dist > max) {
                    max = stor[x][y].dist;
                }
                //System.out.print(stor[x][y].dist + " ");
            }
            System.out.println();
        }
        for (int x = 0; x < stor.length; x++) {
            for (int y = 0; y < stor[0].length; y++) {
                bimg.setRGB(x, y, (int) (stor[x][y].dist / max * 256.0f) + ((int) (stor[x][y].dist / max * 256.0f) << 8) + ((int) (stor[x][y].dist / max * 256.0f) << 16));
            }
        }
        ImageIO.write(bimg, "png", new File(filepath + "1.png"));
    }

    public static int dist(pix A, pix B) {
        return 1 + (int) Math.sqrt(((A.r - B.r) * (A.r - B.r) + (A.g - B.g) * (A.g - B.g) + (A.b - B.b) * (A.b - B.b)));
    }
}
