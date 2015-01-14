package Electromagnetism;

import Artist.Artist;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import static java.lang.Math.*;

/**
 * @author Rakshak R.Hegde This program simulates a charged particle in a
 * uniform magnetic field. The direction of the magnetic field is directly out
 * of the screen. F = Q * v * B * sin(phi)
 */
public class Magnetism extends Artist {

    Ellipse2D.Float particle;
    int radius = 10;
    float vx = 100, vy, V = vx;
    float F, phi, acc;
    // Params
    float B = 1.5F, mass = 100, Q = 1;
    BasicStroke thick = new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    BasicStroke thin = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    BufferedImage image;
    Graphics2D g2d;

    @Override
    public void draw(Graphics2D g) {
        phi = (float) atan2(vy, vx);
        F = Q * V * B;
        acc = F / mass;
        vx += acc * sin(phi);
        vy -= acc * cos(phi);
        V = (float) hypot(vy, vx);
        particle.x += vx / frameRate;
        particle.y += vy / frameRate;

        g.translate(width / 2, height / 2);
        g.scale(1, -1);
        // Velocity Vector
        g.setStroke(thick);
        g.setColor(Color.MAGENTA);
        g.rotate(phi, (int) (particle.x + radius), (int) (particle.y + radius));
        g.drawLine((int) (particle.x + radius), (int) (particle.y + radius), (int) (particle.x + radius + V), (int) (particle.y + radius));
        g.rotate(-phi, (int) (particle.x + radius), (int) (particle.y + radius));
        // Force Vector
        g.setColor(Color.CYAN);
        g.rotate(phi - PI / 2, (int) (particle.x + radius), (int) (particle.y + radius));
        g.drawLine((int) (particle.x + radius), (int) (particle.y + radius), (int) (particle.x + radius + F), (int) (particle.y + radius));
        g.rotate(-phi + PI / 2, (int) (particle.x + radius), (int) (particle.y + radius));

        g2d.setColor(Color.PINK);
        g2d.fillRect((int) (particle.x + radius), (int) (particle.y + radius), 2, 2);
        g.setColor(Color.BLUE);
        g.fill(particle);

        g.scale(1, -1);
        g.translate(-width / 2, -height / 2);
        g.setColor(Color.RED);
        g.setStroke(thin);
        g.drawString("Velocity = " + V, 10, 20);
        g.drawString("Force Acting = " + F, 10, 40);
        g.drawImage(image, (width - image.getWidth()) / 2, (height - image.getHeight()) / 2, null);
    }

    public Magnetism() {
        setTitle("Magnetism");
        setSize(1000, 700);
        particle = new Ellipse2D.Float(-radius, height / 4, 2 * radius, 2 * radius);
        image = new BufferedImage(1000, 700, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
        g2d.translate(500, 350);
        g2d.scale(1, -1);
        setLocationRelativeTo(null);
        frameRate(50);
    }

    public static void main(String args[]) {
        new Magnetism();
    }
}
