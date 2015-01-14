package Miscellaneous;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Rakshak.R.Hegde
 */
public class Entropy extends JFrame {

    int delay = 50, CPS = (int) (1000F / delay); // Delay in milliseconds
    boolean frameAlive = true, repaint = true;

    public Entropy() {
        initComponents();
        setSize(750, 725);
        setResizable(false);
        setTitle("Entropy");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        while (frameAlive) {
            try {
                Thread.sleep(delay);
            } catch (Exception e) {
            }
            if (repaint) {
                repaint();
            }
        }
    }

    public void initComponents() {
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                frameAlive = false;
            }
        });
        Painter painter = new Painter();

        add(painter);

        final JSlider slider = new JSlider(10, 100, 20);
        final JLabel label = new JLabel(CPS + " CPS");
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                CPS = slider.getValue();
                label.setText(CPS + " CPS");
                delay = (int) (1000F / CPS);
            }
        });
        slider.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent evt) {
                super.keyPressed(evt);
                if (evt.getKeyChar() == 'R' || evt.getKeyChar() == 'r') {
                    state = new boolean[balls][balls];
                    greenCount = 0;
                    repaint = true;
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                } else if (evt.getKeyChar() == ' ') {
                    repaint = !repaint;
                }
            }
        });
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(slider);
        bottomPanel.add(label);
        bottomPanel.setBackground(Color.WHITE);
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Cycles Per Second (CPS)");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        bottomPanel.setBorder(titledBorder);
        add(bottomPanel, BorderLayout.PAGE_END);
    }

    public static void main(String args[]) {
        // Set the Nimbus look and feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
        }
        new Entropy();
    }
    int balls = 10, greenCount;
    boolean state[][] = new boolean[balls][balls];

    class Painter extends JPanel {

        Graphics2D g2d;
        int diameter = 50, gap = 10, i, j, x, y;
        Color color1 = Color.GREEN, color2 = Color.YELLOW;
        int GrBarHeight, barHeight, barWidth = 50;
        float GrBarPerc;
        Random random = new Random();
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 22);

        public Painter() {
            setBackground(Color.WHITE);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            x = gap;
            y = gap;
            for (i = 0; i < balls; i++, y += diameter + gap) {
                x = gap;
                for (j = 0; j < balls; j++, x += diameter + gap) {
                    if (state[i][j]) {
                        g2d.setColor(color1);
                        g2d.fillOval(x, y, diameter, diameter);
                    } else {
                        g2d.setColor(color2);
                        g2d.fillOval(x, y, diameter, diameter);
                    }
                }
            }
            x = (diameter + gap) * balls + 30;
            barHeight = (diameter + gap) * balls;
            g2d.setColor(color2);
            g2d.fillRect(x, gap, barWidth, barHeight);
            GrBarPerc = (float) greenCount / (balls * balls);
            GrBarHeight = (int) (GrBarPerc * (diameter + gap) * balls);
            g2d.setColor(color1);
            g2d.fillRect(x, gap + barHeight - GrBarHeight, barWidth, GrBarHeight);
            g2d.setFont(font);
            g2d.drawString((int) (GrBarPerc * 100) + "%", x + barWidth + 10, gap + barHeight / 2);

            i = random.nextInt(balls);
            j = random.nextInt(balls);
            state[i][j] = !state[i][j];
            if (state[i][j]) {
                greenCount++;
            } else {
                greenCount--;
            }
        }
    }
}