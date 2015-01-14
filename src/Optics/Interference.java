package Optics;

import Artist.Artist;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import static java.lang.Math.*;
import javax.swing.*;

/**
 * @author Rakshak R.Hegde
 */
public class Interference extends Artist {

    int w, h, x, y, maxGap = 10, gap;
    int x1, y1, x2, y2;
    double dist1, dist2;
    float waveLen = 5F, waveLenDiff;
    BufferedImage image = null;
    Graphics2D g2d;
    boolean renderPattern = true, renderDraw = true;
    Thread offThreadImaging;

    public Interference() {
        initComponents();
        x1 = 200;
        y1 = 400;
        x2 = 200;
        y2 = 300;
        noLoop();
        setTitle("Interference");
        setSize(w = 800, h = 600);
        setLocationRelativeTo(null);
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
        new Interference();
    }

    @Override
    public void draw(Graphics2D g) {
        if (resized()) {
            renderDraw = true;
            renderPattern = false;
            try {
                if(offThreadImaging!=null)
            offThreadImaging.interrupt();
            }catch(Exception e) {}
        }
        if (renderDraw) {
            renderPattern = true;
            createInterferencePattern();
            g.drawImage(image, 0, 0, null);
            if (gap > 0) {
                --gap;
            } else {
                gap = maxGap;
            }
            renderDraw = false;
        }
        w = width;
        h = height;
    }

    private void createInterferencePattern() {
        if (image == null || resized()) {
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g2d = image.createGraphics();
            gap = maxGap;
        }
        offThreadImaging = new Thread() {

            @Override
            public void run() {
                for (y = 0; y < height && renderPattern; y += gap) {
                    for (x = 0; x < width && renderPattern; x += gap) {
                        dist1 = hypot(x - x1, y - y1);
                        dist2 = hypot(x - x2, y - y2);
                        waveLenDiff = (float) ((abs(dist1 - dist2) / waveLen)) % 1;
                        waveLenDiff *= 2;
                        if (waveLenDiff <= 1) {
                            waveLenDiff = 1 - waveLenDiff;
                        } else {
                            --waveLenDiff;
                        }
                        g2d.setColor(Color.getHSBColor(0, 1, waveLenDiff));
                        g2d.fillRect(x, y, gap, gap);
                    }
                }
                g2d.setColor(Color.BLUE);
                g2d.fillOval(x1 - 5, y1 - 5, 9, 9);
                g2d.fillOval(x2 - 5, y2 - 5, 9, 9);
                renderDraw = true;
                repaint();
            }
        };
        offThreadImaging.start();
    }

    private boolean resized() {
        return w != width || h != height;
    }

    @Override
    public void windowClosing() {
        renderPattern = false;
        renderDraw = false;
    }

    private void initComponents() {
        Font plainFont = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.YELLOW);
        controlPanel.setBorder(BorderFactory.createTitledBorder("Control Panel"));
        JLabel label = new JLabel("Wavelength(λ) = ");
        label.setFont(plainFont);
        controlPanel.add(label);
        final JTextField tf = new JTextField(waveLen + "", 10);
        tf.setToolTipText("Wavelength of the two point sources");
        controlPanel.add(tf);
        controlPanel.add(new Box.Filler(new Dimension(5, 35), new Dimension(10, 35), new Dimension(15, 35)));
        JButton enterButton = new JButton("Enter");
        enterButton.setPreferredSize(new Dimension(80, 35));
        enterButton.setFont(plainFont);
        enterButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    waveLen = Float.parseFloat(tf.getText());
                    renderDraw = true;
                    renderPattern = false;
                    repaint();
                } catch (Exception exc) {
                    Toolkit.getDefaultToolkit().beep();
                    println(exc);
                }
            }
        });
        controlPanel.add(enterButton);
        add(controlPanel, BorderLayout.NORTH);
    }
}
