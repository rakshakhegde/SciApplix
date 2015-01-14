package Fractals;

import Artist.Artist;
import java.awt.Color;
import java.awt.Graphics2D;
import static java.lang.Math.*;

/**
 * @author Rakshak.R.Hegde
 */
public class FordsCircles extends Artist {

    int i, j, k, maxOrder = 8, order = 2, terms;
    int[] numer = {0, 1}, tempNumer, denom = {1, 1}, tempDenom;
    double x, y, radius, factor;

    public FordsCircles() {
        frameRate(1);
        setTitle("Ford's Circle");
        setSize(700, 700);
        setLocationRelativeTo(null);
    }

    @Override
    public void draw(Graphics2D g) {
        terms = (int) (pow(2, order - 1) + 1);
        tempNumer = new int[terms];
        tempDenom = new int[terms];
        for (j = 0, k = 0; j < terms; j += 2, k++) {
            tempNumer[j] = numer[k];
            tempDenom[j] = denom[k];
        }
        for (j = 1; j < terms; j += 2) {
            tempNumer[j] = tempNumer[j - 1] + tempNumer[j + 1];
            tempDenom[j] = tempDenom[j - 1] + tempDenom[j + 1];
        }
        numer = tempNumer;
        denom = tempDenom;

        g.translate(0, height);
        g.scale(1, -1);
        factor = min(width, height);
        for (i = 0; i < terms; i++) {
            radius = 1.0 / (2.0 * denom[i] * denom[i]);
            x = (double) numer[i] / denom[i];
            y = radius;
            g.setColor(Color.ORANGE);
            g.fillOval((int) ((x - radius) * factor), (int) ((y - radius) * factor), (int) (2.0 * radius * factor), (int) (2.0 * radius * factor));
            g.setColor(Color.CYAN);
            g.fillOval(0, 0, 1, 1);
        }
        if (++order > maxOrder) {
            order = 2;
            numer = new int[]{0, 1};
            denom = new int[]{1, 1};
        }
    }

    public static void main(String args[]) {
        new FordsCircles();
    }
}
