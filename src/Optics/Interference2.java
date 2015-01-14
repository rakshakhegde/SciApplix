package Optics;

import Artist.Artist;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import static java.lang.Math.*;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * @author Rakshak R.Hegde
 */
public class Interference2 extends JFrame {

    int width, height, x, y, maxGap = 10, gap;
    int x1, y1, x2, y2;
    double dist1, dist2;
    float waveLen = 5F, waveLenDiff;
    BufferedImage image = null;
    Graphics2D g2d;
    FutureTask<BufferedImage> task;
    boolean createPattern;
    Painter painter;

    public Interference2() {
        painter=new Painter();
        initComponents();
        x1 = 200;
        y1 = 400;
        x2 = 200;
        y2 = 300;
        setTitle("Interference 2");
        setSize(width=800, height=600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        image=new BufferedImage(1, 1, BufferedImage.OPAQUE);
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
        new Interference2();
    }

    class Painter extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            if (resized()) {
                if (task != null) {
                    task.cancel(true);
                }
                gap = maxGap;
                createPattern = true;
            }
            if (createPattern) {
                createInterferencePattern();
                createPattern = false;
                createInterferencePattern();
            }
            if (image != null) {
                g.drawImage(image, 0, 0, null);
            }
            width = painter.getWidth();
            height = painter.getHeight();
        }
    }

    private void createInterferencePattern() {
        if (image == null || resized()) {
            image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            g2d = image.createGraphics();
            gap = maxGap;
        }

        task = new FutureTask<>(new Runnable() {

            @Override
            public void run() {
                for (y = 0; y < image.getHeight(); y += gap) {
                    for (x = 0; x < image.getWidth(); x += gap) {
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
            }
        }, image);
        task.run();

        new Thread() {

            @Override
            public void run() {
                while (!task.isDone()) {
                    try {
                        Thread.sleep(50);
                    } catch (Exception ex) {
                    }
                }
                repaint();
                if (gap > 0) {
                    --gap;
                    createInterferencePattern();
                }
            }
        }.start();
    }

    private boolean resized() {
        return width != image.getWidth() || height != image.getHeight();
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
        enterButton.setPreferredSize(new Dimension(100, 40));
        enterButton.setFont(plainFont);
        enterButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    waveLen = Float.parseFloat(tf.getText());
                    task.cancel(true);
                    createPattern = true;
                    repaint();
                } catch (Exception exc) {
                    Toolkit.getDefaultToolkit().beep();
                    println(exc);
                }
            }
        });
        controlPanel.add(enterButton);
        add(controlPanel, BorderLayout.NORTH);
        add(painter);
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }
}
