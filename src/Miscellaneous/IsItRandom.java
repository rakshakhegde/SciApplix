package Miscellaneous;

import Artist.Artist;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author Rakshak.R.Hegde
 */
public class IsItRandom extends Artist {

    int w;
    BufferedImage image;
    Graphics2D g2d;
    Random random = new Random();

    public IsItRandom() {
        frameRate(30);
        setSize(1000, 100);
        setTitle("Is It Random???");
        setResizable(false);
        image = new BufferedImage(1000, 100, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
        antialias(true, g2d);
        g2d.setColor(Color.red);
    }

    public void keyPressed() {
        if (keyChar == ' ') {
            if (isRunning()) {
                noLoop();
            } else {
                loop();
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        w = random.nextInt(width);
        g2d.drawLine(w, 0, w, height);
        g.drawImage(image, 0, 0, null);
    }

    public static void main(String args[]) {
        new IsItRandom();
    }
}