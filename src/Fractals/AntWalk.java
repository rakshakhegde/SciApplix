package Fractals;

import Artist.Artist;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Ant Rules:
 * Move left if current color is WHITE
 * Move right if current color is BLACK
 */
/**
 * @author Rakshak R.Hegde
 */
public class AntWalk extends Artist {

    BufferedImage img;
    Graphics2D g2d;
    int x, y, d, counter, scale = 3, i;
    int N = 10, S = 12, E = 14, W = 16, dir = N;
    int WHITE = -1, BLACK = -16777216;
    boolean isWhite;

    public AntWalk() {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        x = 200;
        y = 200;
        setTitle("Ant Walk");
    }

    @Override
    public void draw(Graphics2D g) {
        for (i = 0; i < 10; i++) {
            isWhite = img.getRGB(x, y) == WHITE;
            d = isWhite ? -1 : 1;
            img.setRGB(x, y, isWhite ? BLACK : WHITE);
            if (dir == N) {
                x += d;
                dir = isWhite ? W : E;
            } else if (dir == E) {
                y += d;
                dir = isWhite ? N : S;
            } else if (dir == S) {
                x -= d;
                dir = isWhite ? E : W;
            } else if (dir == W) {
                y -= d;
                dir = isWhite ? S : N;
            }
            counter++;
        }
        g.scale(scale, scale);
        g.translate(-150, -150);
        g.drawImage(img, 0, 0, null);
        g.translate(150, 150);
        g.scale(1.0 / scale, 1.0 / scale);
        g.drawString(counter + "", 10, 30);
    }

    public static void main(String args[]) {
        new AntWalk();
    }
}
