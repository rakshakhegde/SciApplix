package Mechanics;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JPanel;

/**
 * @author Rakshak.R.Hegde
 */
public class GravitationalField extends javax.swing.JFrame {

    boolean frameAlive = true, render = true;
    double delay = 0.015; // Delay in seconds

    /**
     * Creates new form Look_To_Me
     */
    public GravitationalField() {
        initComponents();
        ((Painter) painter).initialize();
        setVisible(true);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                frameAlive = false;
                System.gc();
            }
        });
        requestFocus();
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R') {
                    ((Painter) painter).initialize();
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    render = !render;
                }
            }
        });
        while (frameAlive) {
            try {
                Thread.sleep((int) (delay * 1000F));
            } catch (Exception e) {
            }
            if (render) {
                repaint();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painter = new Painter();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gravitational Field");
        setFocusTraversalPolicyProvider(true);

        javax.swing.GroupLayout painterLayout = new javax.swing.GroupLayout(painter);
        painter.setLayout(painterLayout);
        painterLayout.setHorizontalGroup(
            painterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        painterLayout.setVerticalGroup(
            painterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new GravitationalField();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel painter;
    // End of variables declaration//GEN-END:variables

    class Painter extends JPanel {

        int width, height, objects = 3, mouseX, mouseY, i, j;
        double x[] = new double[objects], y[] = new double[objects], dist;
        double angle, accel, accelX, accelY, G = 10;
        double velX[] = new double[objects], velY[] = new double[objects];
        double[] masses = new double[objects], radii = new double[objects];
        double FPS;
        long begin, end;
        Random random = new Random();
        Graphics2D g2d, imgG2d;
        boolean confirm;
        BufferedImage image;

        public void initialize() {
            setBackground(Color.white);
            width = getWidth();
            height = getHeight();
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            imgG2d = image.createGraphics();
            imgG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            imgG2d.setColor(Color.PINK);
            for (i = 0; i < objects; i++) {
                masses[i] = random.nextDouble() * 1E3 + 10;
                radii[i] = masses[i] / 20;
                velX[i] = 0;
                velY[i] = 0;
                confirm = false;
                while (!confirm) {
                    confirm = true;
                    x[i] = random.nextDouble() * width;
                    y[i] = random.nextDouble() * height;
                    for (j = i - 1; j >= 0; j--) {
                        if (distance(x[i], y[i], x[j], y[j]) < radii[i] + radii[j]) {
                            confirm = false;
                            break;
                        }
                    }
                }
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            begin = System.currentTimeMillis();
            g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(new Color(0, 0, 250, 150));
            g2d.drawString("FPS = " + (long) FPS, 20, 30);
            for (i = 0; i < objects; i++) {
                accelX = 0;
                accelY = 0;
                for (j = 0; j < objects; j++) {
                    if (i != j) {
                        angle = Math.atan((double) (y[j] - y[i]) / (x[j] - x[i]));
                        if (x[j] < x[i]) {
                            angle += Math.PI;
                        }
                        dist = distance(x[i], y[i], x[j], y[j]);
                        if (2 * dist > radii[i] + radii[j]) {
                            accel = G * masses[j] / (dist * dist);
                            accelX += accel * Math.cos(angle);
                            accelY += accel * Math.sin(angle);
                        }
                    }
                }
                velX[i] += accelX;
                velY[i] += accelY;
                x[i] += velX[i] * delay;
                y[i] += velY[i] * delay;
                g2d.fillOval((int) (x[i] - radii[i]), (int) (y[i] - radii[i]), (int) (radii[i] * 2), (int) (radii[i] * 2));
                imgG2d.fillOval((int) x[i], (int) y[i], 2, 2);
            }
            end = System.currentTimeMillis();
            FPS = 1.0 / (delay + (end - begin) / 1000.0);
        }

        double distance(double x1, double y1, double x2, double y2) {
            return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        }
    }
}