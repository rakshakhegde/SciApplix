/*
 * Created on 7 Nov, 2012, 7:56:17 PM
 */
package Fractals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * @author Rakshak.R.Hegde
 */
public class FernFractal extends javax.swing.JFrame {

    boolean colorWhite = true;
    BufferedImage earth;

    public FernFractal() {
        initComponents();
        ((Painter) painter).initialize();
        setVisible(true);
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
        toggleButton = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Fern Fractal");
        setResizable(false);

        toggleButton.setText("Toggle Color");
        toggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painterLayout = new javax.swing.GroupLayout(painter);
        painter.setLayout(painterLayout);
        painterLayout.setHorizontalGroup(
            painterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toggleButton)
                .addContainerGap(411, Short.MAX_VALUE))
        );
        painterLayout.setVerticalGroup(
            painterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(552, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleDescription("Fern fractal");

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void toggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleButtonActionPerformed
    colorWhite = !colorWhite;
    repaint();
}//GEN-LAST:event_toggleButtonActionPerformed

    public static void main(String args[]) {
        new FernFractal();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel painter;
    private javax.swing.JToggleButton toggleButton;
    // End of variables declaration//GEN-END:variables

    class Painter extends JPanel {

        BufferedImage image;
        Graphics2D g2d;
        int width, height;
        float length = 500, distance = 100, angle, mouseX, mouseY;
        float shrinkLength = 0.27f, shrinkDistance = 0.25f;
        GradientPaint gradient1, gradient2;

        public void initialize() {
            width = getWidth();
            height = getHeight();
            gradient1 = new GradientPaint(0, 0, Color.black, 0, height, new Color(100, 100, 100));
            gradient2 = new GradientPaint(0, 0, Color.white, 0, height, Color.LIGHT_GRAY);
            addMouseMotionListener(new MouseMotionAdapter() {

                @Override
                public void mouseMoved(MouseEvent e) {
                    super.mouseMoved(e);
                    mouseX = e.getX();
                    mouseY = e.getY();
                    repaint();
                    try {
                        Thread.sleep(15);
                    } catch (Exception exc) {
                    }
                }
            });

            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        }

        public void render(float length, float distance, double angle, int step) {
            float len = length, dist;
            if (step-- > 0) {
                g2d.rotate(angle);
                g2d.drawLine(0, 0, 0, (int) -length);
                for (int i = 1; (dist = i * distance) <= len; i++) {
                    g2d.translate(0, -dist);
                    length *= 0.97;
                    render(length * shrinkLength, distance * shrinkDistance, -angle, step);
                    render(length * shrinkLength, distance * shrinkDistance, angle, step);
                    g2d.translate(0, dist);
                }
                g2d.rotate(-angle);
            }
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (colorWhite) {
                g2d.setPaint(gradient2);
                g2d.fillRect(0, 0, width, height);
                g2d.setColor(Color.black);
            } else {
                g2d.setPaint(gradient1);
                g2d.fillRect(0, 0, width, height);
                g2d.setColor(Color.white);
            }
            angle = (float) (mouseX / width * Math.PI / 2);
            g2d.translate(width / 2f, height);
            g2d.rotate(-angle);
            render(length, distance, angle, (int) (mouseY / height * 5f + 1));
            g2d.rotate(angle);
            g2d.translate(-image.getWidth() / 2f, -image.getHeight());

            g.drawImage(image, 0, 0, null);
            g.drawImage(earth, 0, 0, null);
        }
    }
}