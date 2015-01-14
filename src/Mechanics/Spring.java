package Mechanics;

import Artist.Artist;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import static java.lang.Math.*;

/**
 * @author Rakshak R.Hegde
 */
public class Spring extends Artist {

    int radius = 15;
    Ellipse2D.Float bob;
    float M = 10; // Mass
    float K = 0.7F; // Spring constant
    float D = 0.96F; // Damping
    float R; // Rest position
    float ypos; // Position
    float vX, vY; // Velocities in X and Y directions
    float a; // Acceleration
    float F; // Force
    float dx, dy; // Displacements
    boolean released = true;

    public Spring() {
        bob = new Ellipse2D.Float(width / 2 - radius - 100, height / 2 - radius, 2 * radius, 2 * radius);
        setTitle("Spring");
    }

    @Override
    public void draw(Graphics2D g) {
        if (released) {
            dx = width / 2 - bob.x - radius;
            dy = height / 2 - bob.y - radius;
            F = (float) (K * (hypot(dx, dy)));
            a = F / M;

            vX = (float) (D * (vX + cos(atan2(dy, dx)) * a));
            bob.x += vX;

            vY = (float) (D * (vY + sin(atan2(dy, dx)) * a));
            bob.y += vY;
        } else {
            bob.x = mouseX - radius;
            bob.y = mouseY - radius;
        }
        g.drawLine(width / 2, height / 2, (int) (bob.x + radius), (int) (bob.y + radius));
        g.setColor(Color.GRAY);
        g.fillOval(width / 2 - 3, height / 2 - 3, 6, 6);
        g.setColor(Color.GREEN);
        g.fill(bob);
    }

    @Override
    public void mousePressed() {
        super.mousePressed();
        released = false;
    }

    @Override
    public void mouseReleased() {
        super.mouseReleased();
        vX = mouseX - pmouseX;
        vY = mouseY - pmouseY;
        released = true;
    }

    public static void main(String args[]) {
        new Spring();
    }
}