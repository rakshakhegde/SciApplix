package Electromagnetism;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.Vector;
import javax.swing.*;
import static java.lang.Math.*;

/**
 * @author Rakshak R.Hegde
 */
public class ElectricField extends JFrame {

    int lenVec = 10, paddingX = 20, paddingY = 10;
    int x, y;
    // Electric Field
    double Ex, Ey;
    double theta, dist2, E;
    Painter painter;
    Timer timer;
    Vector<Particle> particles = new Vector();
    Particle particle;

    public ElectricField() {
        init();

        // Setting window properties
        setTitle("Electric Field");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void init() {
        painter = new Painter();
        painter.setBackground(Color.WHITE);
        add(painter);

        painter.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent me) {
                super.mousePressed(me);
                for (Particle part : particles) {
                    if (part.contains(me.getX(), me.getY())) {
                        particle = part;
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                super.mouseReleased(me);
                particle = null;
            }
        });
        painter.addMouseMotionListener(new MouseMotionAdapter() {

            boolean isInside;

            @Override
            public void mouseMoved(MouseEvent me) {
                super.mouseMoved(me);
                isInside = false;
                for (Particle part : particles) {
                    if (part.contains(me.getX(), me.getY())) {
                        isInside = true;
                        break;
                    }
                }
                if (isInside) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                } else {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
                try {
                    Thread.sleep(30);
                } catch (Exception exc) {
                }
            }

            @Override
            public void mouseDragged(MouseEvent me) {
                super.mouseDragged(me);
                if (particle != null) {
                    particle.setEllipse(me.getX(), me.getY());
                    repaint();
                }
                try {
                    Thread.sleep(30);
                } catch (Exception exc) {
                }
            }
        });

        particles.add(new Particle(40, 40, 10, 1));
    }

    public static void main(String args[]) {
        // Set the Nimbus Look And Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                }
            }
        } catch (Exception ex) {
        }

        new ElectricField();
    }

    class Painter extends JPanel {

        int width, height;
        Graphics2D g2d;
        BasicStroke stroke1=new BasicStroke(2, BasicStroke.JOIN_ROUND, BasicStroke.CAP_ROUND);
        BasicStroke stroke2=new BasicStroke(1.5F, BasicStroke.JOIN_ROUND, BasicStroke.CAP_ROUND);

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            long begin = System.currentTimeMillis();
            width = getWidth();
            height = getHeight();
            g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(stroke2);
            for (y = 0; y <= height; y += paddingY) {
                for (x = 0; x <= width; x += paddingX) {
                    Ex = 0;
                    Ey = 0;
                    E = 0;
                    for (Particle particle : particles) {
                        dist2 = pow(particle.x - x, 2) + pow(particle.y - y, 2);
                        if (dist2 > 0) {
                            theta = atan2(y - particle.y, x - particle.x);
                            E = particle.charge / dist2;
                            Ex += E * cos(theta);
                            Ey += E * sin(theta);
                        }
                    }
                    theta = atan2(Ey, Ex);
                    g2d.rotate(theta, x, y);
                    g2d.drawLine(x, y, x + lenVec, y);
                    g2d.drawLine(x + lenVec - 2, y + 2, x + lenVec, y);
                    g2d.drawLine(x + lenVec - 2, y - 2, x + lenVec, y);
                    g2d.rotate(-theta, x, y);
                }
            }

            for (Particle particle : particles) {
                g2d.setColor(particle.color);
                g2d.fill(particle);
            }
            System.out.println("Time taken to render = " + (System.currentTimeMillis() - begin) + " millis.");
        }
    }

    class Particle extends Ellipse2D.Float {

        int charge;
        float radius;
        /**
         * x coordinate
         */
        float x;
        /**
         * y coordinate
         */
        float y;
        private Color NEGCOL = Color.RED, POSCOL = Color.BLUE, NEUTRAL = Color.LIGHT_GRAY;
        Color color;

        /**
         * Represents a charge.
         *
         * @param x x position of the charge
         * @param y x position of the charge
         * @param charge Charge on the particle
         */
        public Particle(float x, float y, float radius, int charge) {
            super(x - radius, y - radius, 2 * radius, 2 * radius);
            this.x = x;
            this.y = y;
            this.charge = charge;
            this.radius = radius;
            color = charge != 0 ? (charge > 0 ? POSCOL : NEGCOL) : NEUTRAL;
        }

        public void setEllipse(float x, float y) {
            this.x = x;
            this.y = y;
            setFrame(x - radius, y - radius, 2 * radius, 2 * radius);
        }
    }
}