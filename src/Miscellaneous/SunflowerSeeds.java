package Miscellaneous;

import Artist.Artist;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;
import static java.lang.Math.*;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;

/**
 * @author Rakshak.R.Hegde
 */
public class SunflowerSeeds extends Artist {

    double angRad = toRadians(135);
    int seedRadius = 5, padding = 2 * seedRadius - 1, seedCount = 0;
    int maxSeeds = 200, maxRadius = 300;
    BufferedImage paintImg;
    Graphics2D g2d;
    double theta = 0, dist, x, y, radius;
    JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
    JButton drawButton, pauseButton, saveButton;

    @Override
    public void draw(Graphics2D g) {
        if (seedCount > maxSeeds) {
            g.drawImage(paintImg, (width - paintImg.getWidth()) / 2, (height - paintImg.getHeight()) / 2, null);
            noLoop();
        } else {
            radius = maxRadius * sqrt(++seedCount / (double) maxSeeds);
            x = cos(theta) * radius;
            y = sin(theta) * radius;
            g2d.drawOval((int) (x - seedRadius), (int) (y - seedRadius), 2 * seedRadius, 2 * seedRadius);
            theta = angRad * seedCount;
            g.drawImage(paintImg, (width - paintImg.getWidth()) / 2, (height - paintImg.getHeight()) / 2, null);
        }
    }

    public SunflowerSeeds() {
        frameRate(50);
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(0xFFFF33));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Control Panel"));
        controlPanel.add(new JLabel("Angle"));
        final JTextField angField = new JTextField((float) toDegrees(angRad) + "", 10);
        angField.setToolTipText("Set the angle in degrees");
        angField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    drawButton.getActionListeners()[0].actionPerformed(null);
                }
            }
        });
        controlPanel.add(angField);
        Dimension buttonSize = new Dimension(80, 35);
        drawButton = new JButton("Draw");
        drawButton.setPreferredSize(buttonSize);
        drawButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                angRad = toRadians(Float.parseFloat(angField.getText()));
                theta = 0;
                seedCount = 0;
                g2d.setColor(Color.WHITE);
                g2d.fillRect(-paintImg.getWidth() / 2, -paintImg.getHeight() / 2, paintImg.getWidth(), paintImg.getHeight());
                g2d.setColor(Color.GREEN);
                pauseButton.setText("Pause");
                loop();
            }
        });
        controlPanel.add(drawButton);
        pauseButton = new JButton("Pause");
        pauseButton.setPreferredSize(buttonSize);
        pauseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (pauseButton.getText().contentEquals("Pause")) {
                    noLoop();
                    pauseButton.setText("Play");
                } else {
                    loop();
                    pauseButton.setText("Pause");
                }
            }
        });
        controlPanel.add(pauseButton);
        controlPanel.add(Box.createHorizontalStrut(20));
        saveButton = new JButton("Save As");
        saveButton.setPreferredSize(buttonSize);
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                noLoop();
                pauseButton.setText("Play");
                if (fileChooser.showSaveDialog(SunflowerSeeds.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        File selecFile = fileChooser.getSelectedFile();
                        if (selecFile.exists()) {
                            if (JOptionPane.showConfirmDialog(SunflowerSeeds.this, selecFile.getName() + " already exists!!!\nDo you want to replace it?", "Confirm Save As", JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
                                ImageIO.write(paintImg, "png", selecFile);
                            }
                        } else {
                            ImageIO.write(paintImg, "png", selecFile);
                        }
                    } catch (Exception exc) {
                        JOptionPane.showMessageDialog(SunflowerSeeds.this, "Error occured while writing to the file!!!", "Error", JFileChooser.ERROR_OPTION);
                    }
                }
            }
        });
        controlPanel.add(saveButton);
        add(controlPanel, BorderLayout.NORTH);
        border(BorderFactory.createTitledBorder("Rendering Panel"));

        Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        paintImg = new BufferedImage(rec.width, rec.height, BufferedImage.TYPE_INT_ARGB);
        g2d = paintImg.createGraphics();
        g2d.translate(rec.width / 2, rec.height / 2);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(1.5F));
        g2d.setColor(Color.WHITE);
        g2d.fillRect(-rec.width / 2, -rec.height / 2, rec.width, rec.height);
        g2d.setColor(Color.GREEN);

        setSize(700, 700);
        setLocationRelativeTo(null);
        setTitle("Sunflower Seeds Test");

        theta = 0;
        seedCount = 0;
    }

    public static void main(String args[]) {
        // Set the Look And Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                }
            }
        } catch (Exception ex) {
        }
        new SunflowerSeeds();
    }
}
