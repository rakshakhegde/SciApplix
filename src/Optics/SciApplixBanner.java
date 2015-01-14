package Optics;

import Artist.Artist;
import SciApplix.EntryClass;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * @author Rakshak.R.Hegde
 */
public class SciApplixBanner extends Artist {

    BufferedImage image;

    public SciApplixBanner() {
        setSize(300, 100);
        Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 40);
        image = new BufferedImage(300, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setFont(bigFont);
        g2d.setColor(Color.MAGENTA);
        FontMetrics fontMetrics = g2d.getFontMetrics();
        g2d.drawString(EntryClass.APP_NAME_CAPS, 0, 40);
        g2d.drawRect(0, 20, fontMetrics.stringWidth(EntryClass.APP_NAME_CAPS), 30);
        noLoop();
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, 0, 0, null);
    }

    public static void main(String args[]) {
        new SciApplixBanner();
    }
}
