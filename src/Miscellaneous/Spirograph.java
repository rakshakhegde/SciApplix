/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Miscellaneous;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Rakshak.R.Hegde
 */
public class Spirograph extends JFrame {

    final boolean HYPO = true, EPI = false;
    boolean frameAlive = true, repaint = true, hypo = HYPO;
    boolean showCircles = true, playing = true;
    int delay = 30;
    double rad, rad1 = Math.toRadians(1);
    double R = 280, r = 70, rr = 70;
    int x, y;
    int width, height;
    Color whiteColor = new Color(252, 252, 252);
    Graphics2D g2d;
    Rectangle rec;
    JFileChooser fileChooser = new JFileChooser();

    /**
     * Creates new form Spirograph
     */
    public Spirograph() {
        rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        initComponents();
        setVisible(true);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                frameAlive = false;
            }
        });
        while (frameAlive) {
            try {
                Thread.sleep(delay);
            } catch (Exception e) {
            }
            if (playing) {
                repaint = true;
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

        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        RTextField = new JTextField();
        jLabel2 = new JLabel();
        rTextField = new JTextField();
        jLabel3 = new JLabel();
        rrTextField = new JTextField();
        showCirclesCheckBox = new JCheckBox();
        drawButton = new JButton();
        comboBox = new JComboBox();
        saveButton = new JButton();
        pauseButton = new JButton();
        painter = new Painter();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Spirograph");

        jPanel1.setBackground(new Color(250, 250, 0));
        jPanel1.setBorder(BorderFactory.createTitledBorder("Input Parameter Panel"));

        jLabel1.setText("Radius of Outer Circle");

        RTextField.setColumns(5);
        RTextField.setText("280");

        jLabel2.setText("Radius of Smaller Circle");

        rTextField.setColumns(5);
        rTextField.setText("70");

        jLabel3.setText("Distance from Smaller Circle");

        rrTextField.setColumns(5);
        rrTextField.setText("70");

        showCirclesCheckBox.setSelected(true);
        showCirclesCheckBox.setText("Show Circles");
        showCirclesCheckBox.setToolTipText("Check/Uncheck to display/not to display the circles");
        showCirclesCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                showCirclesCheckBoxActionPerformed(evt);
            }
        });

        drawButton.setText("Draw");
        drawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                drawButtonActionPerformed(evt);
            }
        });

        comboBox.setModel(new DefaultComboBoxModel(new String[] { "Hypo", "Epi" }));

        saveButton.setText("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        pauseButton.setText("Pause");
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RTextField, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rTextField, GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rrTextField, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(showCirclesCheckBox)
                .addGap(15, 15, 15)
                .addComponent(comboBox, 0, 59, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(pauseButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(drawButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(drawButton, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pauseButton, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(RTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(rTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(rrTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(showCirclesCheckBox)
                                    .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        painter.setBackground(Color.WHITE);

        GroupLayout painterLayout = new GroupLayout(painter);
        painter.setLayout(painterLayout);
        painterLayout.setHorizontalGroup(
            painterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 886, Short.MAX_VALUE)
        );
        painterLayout.setVerticalGroup(
            painterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 589, Short.MAX_VALUE)
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(painter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showCirclesCheckBoxActionPerformed(ActionEvent evt) {//GEN-FIRST:event_showCirclesCheckBoxActionPerformed
        showCircles = showCirclesCheckBox.isSelected();
    }//GEN-LAST:event_showCirclesCheckBoxActionPerformed

    private void drawButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_drawButtonActionPerformed
        R = Double.parseDouble(RTextField.getText());
        r = Double.parseDouble(rTextField.getText());
        rr = Double.parseDouble(rrTextField.getText());
        rad = 0;
        if (comboBox.getSelectedIndex() == 0) {
            hypo = HYPO;
        } else {
            hypo = EPI;
        }
        g2d.setColor(Color.WHITE);
        g2d.fillRect(-rec.width / 2, -rec.height / 2, rec.width, rec.height);
    }//GEN-LAST:event_drawButtonActionPerformed

    private void saveButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        playing = false;
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (file.exists()) {
                int n = JOptionPane.showConfirmDialog(this, file.getName() + " already exists.\nDo you want to replace it?", "Confirm Save As", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    saveImage(file);
                }
            } else {
                saveImage(file);
            }
        }
        playing = true;
        pauseButton.setText("Pause");
    }//GEN-LAST:event_saveButtonActionPerformed

    private void pauseButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
        playing = !playing;
        if (playing) {
            pauseButton.setText("Pause");
        } else {
            pauseButton.setText("Play");
        }

    }//GEN-LAST:event_pauseButtonActionPerformed

    public void saveImage(File file) {
        try {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(stroke);
//            g2d.setColor(Color.WHITE);
//            g2d.fillRect(0, 0, width, height);
            g2d.translate(width / 2, height / 2);
            g2d.scale(1, -1);
            // Rendering
            if (hypo) {
                g2d.setColor(alphaRed);
                for (double t = 0; t <= rad; t += rad1) {
                    g2d.drawLine(xHypo(t - rad1), yHypo(t - rad1), xHypo(t), yHypo(t));
                }
                if (showCircles) {
                    g2d.setColor(alphaBlack);
                    g2d.drawOval(-(int) R, -(int) R, (int) (2.0 * R), (int) (2.0 * R));
                    g2d.setColor(alphaRed);
                    g2d.fillOval(xHypo(rad) - 4, yHypo(rad) - 4, 7, 7);
                    g2d.setColor(alphaBlue);
                    g2d.fillOval((int) ((Math.cos(rad) * (R - r)) - r), (int) ((Math.sin(rad) * (R - r)) - r), (int) (2.0 * r), (int) (2.0 * r));
                    g2d.setColor(alphaBlack);
                    g2d.drawLine(xHypo(rad), yHypo(rad), (int) (Math.cos(rad) * (R - r)), (int) (Math.sin(rad) * (R - r)));
                }
            } else {
                g2d.setColor(alphaRed);
                for (double t = 0; t <= rad; t += rad1) {
                    g2d.drawLine(xEpi(t - rad1), yEpi(t - rad1), xEpi(t), yEpi(t));
                }
                if (showCircles) {
                    g2d.setColor(Color.BLACK);
                    g2d.drawOval((int) -R, (int) -R, (int) (2.0 * R), (int) (2.0 * R));
                    g2d.setColor(alphaRed);
                    g2d.fillOval(xEpi(rad) - 4, yEpi(rad) - 4, 7, 7);
                    g2d.setColor(alphaBlue);
                    g2d.fillOval((int) ((Math.cos(rad) * (R + r)) - r), (int) ((Math.sin(rad) * (R + r)) - r), (int) (2.0 * r), (int) (2.0 * r));
                    g2d.setColor(alphaBlack);
                    g2d.drawLine(xEpi(rad), yEpi(rad), (int) (Math.cos(rad) * (R + r)), (int) (Math.sin(rad) * (R + r)));
                }
            }
            ImageIO.write(image, "png", file);
            g2d.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not save " + file.getName(), "File Not Rendered", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Spirograph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Spirograph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Spirograph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Spirograph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        new Spirograph();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JTextField RTextField;
    private JComboBox comboBox;
    private JButton drawButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPanel jPanel1;
    private JPanel painter;
    private JButton pauseButton;
    private JTextField rTextField;
    private JTextField rrTextField;
    private JButton saveButton;
    private JCheckBox showCirclesCheckBox;
    // End of variables declaration//GEN-END:variables
    BasicStroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    Color alphaBlue = new Color(0, 0, 255, 150), alphaBlack = new Color(0, 0, 0, 200), alphaRed = new Color(255, 0, 0, 200);

    class Painter extends JPanel {

        Graphics2D g2dg;
        BufferedImage image;

        public Painter() {
            setBackground(Color.WHITE);
            image = new BufferedImage(rec.width, rec.height, BufferedImage.TYPE_INT_ARGB);
            g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(stroke);
            g2d.translate(rec.width / 2.0, rec.height / 2.0);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            width = getWidth();
            height = getHeight();
            g2dg = (Graphics2D) g;
            g2dg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2dg.setStroke(stroke);
            g2dg.translate(width / 2.0, height / 2.0);
            g2dg.scale(1, -1);
            g2dg.drawImage(image, -rec.width / 2, -rec.height / 2, null);
            g2dg.setColor(Color.RED);

            g2d.setColor(alphaRed);
            if (hypo) {
                g2d.drawLine(xHypo(rad - rad1), yHypo(rad - rad1), xHypo(rad), yHypo(rad));
                if (showCircles) {
                    g2dg.setColor(alphaBlack);
                    g2dg.drawOval(-(int) R, -(int) R, (int) (2.0 * R), (int) (2.0 * R));
                    g2dg.setColor(alphaRed);
                    g2dg.fillOval(xHypo(rad) - 4, yHypo(rad) - 4, 7, 7);
                    g2dg.setColor(alphaBlue);
                    g2dg.fillOval((int) ((Math.cos(rad) * (R - r)) - r), (int) ((Math.sin(rad) * (R - r)) - r), (int) (2.0 * r), (int) (2.0 * r));
                    g2dg.setColor(alphaBlack);
                    g2dg.drawLine((int) (Math.cos(rad) * (R - r)), (int) (Math.sin(rad) * (R - r)), xHypo(rad), yHypo(rad));
                }
            } else {
                g2d.drawLine(xEpi(rad - rad1), yEpi(rad - rad1), xEpi(rad), yEpi(rad));
                if (showCircles) {
                    g2dg.setColor(Color.BLACK);
                    g2dg.drawOval((int) -R, (int) -R, (int) (2.0 * R), (int) (2.0 * R));
                    g2dg.setColor(alphaRed);
                    g2dg.fillOval(xEpi(rad) - 4, yEpi(rad) - 4, 7, 7);
                    g2dg.setColor(alphaBlue);
                    g2dg.fillOval((int) ((Math.cos(rad) * (R + r)) - r), (int) ((Math.sin(rad) * (R + r)) - r), (int) (2.0 * r), (int) (2.0 * r));
                    g2dg.setColor(alphaBlack);
                    g2dg.drawLine((int) (Math.cos(rad) * (R + r)), (int) (Math.sin(rad) * (R + r)), xEpi(rad), yEpi(rad));
                }
            }

            if (repaint) {
                rad += rad1;
            }
            repaint = false;
        }
    }

    public int xHypo(double rad) {
        return (int) ((R - r) * Math.cos(rad) + rr * Math.cos(rad * (R - r) / r));
    }

    public int yHypo(double rad) {
        return (int) ((R - r) * Math.sin(rad) - rr * Math.sin(rad * (R - r) / r));
    }

    public int xEpi(double rad) {
        return (int) ((R + r) * Math.cos(rad) - rr * Math.cos(rad * (R + r) / r));
    }

    public int yEpi(double rad) {
        return (int) ((R + r) * Math.sin(rad) - rr * Math.sin(rad * (R + r) / r));
    }
}
