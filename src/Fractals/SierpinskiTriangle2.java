package Fractals;

import Artist.Artist;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import static java.lang.Math.*;
import javax.swing.*;

/**
 * @author Rakshak R.Hegde
 */
public class SierpinskiTriangle2 extends Artist {

    BufferedImage img;
    Graphics2D g2d;
    int die, i, index = -1, radius = 10, points = 3, sum;
    int[] xTri = {300, 550, 50}, yTri = {80, 500, 500};
    double x, y;
    Random ran = new Random();
    Rectangle rec;
    boolean render, released = true;

    public SierpinskiTriangle2() {
        rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        img = new BufferedImage(rec.width, rec.height, BufferedImage.TYPE_INT_RGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        antialias(true, g2d);
        setCentroid();

        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.YELLOW);
        controlPanel.setBorder(BorderFactory.createTitledBorder("Control Panel"));
        controlPanel.add(new JLabel("Points"));
        final JSlider pointsSlider = new JSlider(3, 10);
        pointsSlider.setMajorTickSpacing(1);
        pointsSlider.setPaintLabels(true);
        pointsSlider.setValue(3);
        pointsSlider.setOpaque(false);
        pointsSlider.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                released = false;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                points = pointsSlider.getValue();
                xTri = new int[points];
                yTri = new int[points];
                for (i = 0; i < points; i++) {
                    xTri[i] = ran.nextInt(width);
                    yTri[i] = ran.nextInt(height);
                }
                setCentroid();
                released = true;
            }
        });
        pointsSlider.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                points = pointsSlider.getValue();
                xTri = new int[points];
                yTri = new int[points];
                for (i = 0; i < points; i++) {
                    xTri[i] = ran.nextInt(width);
                    yTri[i] = ran.nextInt(height);
                }
            }
        });
        controlPanel.add(pointsSlider);
        add(controlPanel, "North");
        x=300; y=80;

        border(BorderFactory.createTitledBorder("Rendering Panel"));
        setSize(600, 700);
        setTitle("Sierpinski Triangle 2");
        setLocationRelativeTo(null);
    }
    int counter;

    @Override
    public void draw(Graphics2D g) {
        if (released) {
            for (i = 0; i < 20; i++) {
                die = ran.nextInt(points);
                g2d.drawRect((int) round(x = (x + xTri[die]) / 2.0), (int) round(y = (y + yTri[die]) / 2.0), 1, 1);
            }
            g.drawImage(img, 0, 0, null);
        } else {
            g.drawPolygon(xTri, yTri, points);
        }
        for (i = 0; i < points; i++) {
            g.drawOval(xTri[i] - radius, yTri[i] - radius, 2 * radius, 2 * radius);
        }
    }

    @Override
    public void mousePressed() {
        for (i = 0; i < points; i++) {
            if (hypot(xTri[i] - mouseX, yTri[i] - mouseY) <= 2 * radius) {
                index = i;
                break;
            }
        }
        released = index == -1 ? true : false;
    }

    @Override
    public void mouseDragged() {
        super.mouseDragged();
        if (index != -1) {
            xTri[index] = mouseX;
            yTri[index] = mouseY;
        }
    }

    @Override
    public void mouseReleased() {
        super.mouseReleased();
        if (!released) {
            setCentroid();
            index = -1;
            released = true;
        }
    }

    public void setCentroid() {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, rec.width, rec.height);
        g2d.setColor(Color.RED);
        sum = 0;
        for (i = 0; i < points; i++) {
            sum += xTri[i];
        }
        x = (double) sum / points;
        sum = 0;
        for (i = 0; i < points; i++) {
            sum += yTri[i];
        }
        y = (double) sum / points;
    }

    public static void main(String args[]) {
        new SierpinskiTriangle2();
    }
}