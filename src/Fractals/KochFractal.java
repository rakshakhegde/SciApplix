package Fractals;

import Artist.Artist;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import static java.lang.Math.*;

/**
 * @author Rakshak.R.Hegde
 */
public class KochFractal extends Artist {

    /**
     * The maximum level of complexity that this program will dive into.
     */
    int maxLevel;
    /**
     * Holds the current level.
     */
    int level;
    /**
     * 'theta' is equivalent to 60 degrees in radians which is equal to pi/3.
     */
    double theta;
    /**
     * Holds the ratio of the length of the line of the next level to the
     * current level.
     */
    float ratio;
    /**
     * To store an instance of the Graphics2D object.
     */
    private Graphics2D g2d;
    /**
     * A stroke that to draw lines that is 2 pixels thick.
     */
    private BasicStroke thickStroke = new BasicStroke(2F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private LinearGradientPaint gradient;
    /**
     * The colors are in the order VIBGYOR
     */
    private Color colors[] = {new Color(-3014401), new Color(-64982)};
    private float fractions[] = {2 / 7F, 6 / 7F};

    public KochFractal() {
        frameRate(1);
        border(BorderFactory.createTitledBorder("Rendering Panel"));
        maxLevel = 6;
        level = 0;
        theta = PI / 3;
        ratio = 0.4F;

        JPanel controls = new JPanel();
        final JLabel ratioLabel = new JLabel("Ratio (0.4):");
        controls.add(ratioLabel);
        final JSlider slider = new JSlider(1, 9, 4);
        slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                ratio = slider.getValue() / 10F;
                ratioLabel.setText("Ratio (" + ratio + "):");
            }
        });
        controls.setBorder(BorderFactory.createTitledBorder("Control Panel"));
        controls.add(slider);
        controls.setBackground(Color.YELLOW);
        add(controls, BorderLayout.SOUTH);
        setSize(800, 450);
        setLocationRelativeTo(null);
        setTitle("Koch Fractal");
    }

    @Override
    public void draw(Graphics2D g) {
        if (engineCalled) {
            g2d = g;
            g.translate(0, 0.85 * height);
            g.scale(1, -1);
            g.setStroke(thickStroke);
            if (level++ > maxLevel) {
                level = 1;
            }
            render(level, theta, width);
            g.translate(0, 0.85 * height);
            g.scale(1, -1);
        }
    }

    public void render(int level, double theta, float L) {
        if (level-- > 0) {
            gradient = new LinearGradientPaint(0, 0, L, 0, fractions, colors, MultipleGradientPaint.CycleMethod.REFLECT);
            g2d.setPaint(gradient);
            if (level > 0) {
                g2d.drawLine(0, 0, (int) (L * (1 - ratio) / 2), 0);
                g2d.drawLine((int) (L * (1 + ratio) / 2), 0, (int) L, 0);
            } else {
                g2d.drawLine(0, 0, (int) L, 0);
            }
            g2d.translate(L * (1 - ratio) / 2, 0);
            g2d.rotate(theta);
            render(level, theta, L * ratio);
            g2d.rotate(-theta);
            g2d.translate(-L * (1 - ratio) / 2, 0);

            g2d.translate(L * (1 + ratio) / 2, 0);
            g2d.rotate(PI - theta);
            render(level, -theta, L * ratio);
            g2d.rotate(theta - PI);
            g2d.translate(-L * (1 + ratio) / 2, 0);
        }
    }

    public static void main(String args[]) {
        new KochFractal();
    }
}
