package Illusions;

import Artist.Artist;
import java.awt.*;
import java.awt.geom.Arc2D;
import javax.swing.*;
import javax.swing.event.*;

/**
 * @author Rakshak.R.Hegde
 */
public class Moving_Square extends Artist {

    int radius = 60, padding = 150, i, y1 = 100, y2 = 250, rotate = 3;
    Arc2D.Float arcs[] = new Arc2D.Float[8];
    
    public Moving_Square() {
        background(250);
        for (i = 0; i < 4; i++) {
            arcs[i] = new Arc2D.Float((1 + i) * padding - radius, y1 - radius, radius * 2, radius * 2, 0, 270, Arc2D.PIE);
        }
        for (i = 4; i < 8; i++) {
            arcs[i] = new Arc2D.Float((i - 3) * padding - radius, y2 - radius, radius * 2, radius * 2, 0, 270, Arc2D.PIE);
        }
        arcs[1].start = arcs[6].start = -90;
        arcs[2].start = arcs[5].start = 180;
        arcs[3].start = arcs[4].start = 90;

        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(245, 245, 0)); // Yellow Tint
        controlPanel.add(new JLabel("Speed"));
        final JSlider speedSlider = new JSlider(-10, 10);
        speedSlider.setToolTipText("Set the rotatory speed");
        speedSlider.setValue(rotate);
        speedSlider.setMajorTickSpacing(5);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                rotate = speedSlider.getValue();
            }
        });
        controlPanel.add(speedSlider);
        controlPanel.setBorder(BorderFactory.createTitledBorder("Control Panel"));
        add(controlPanel, BorderLayout.SOUTH);

        setTitle("Moving Square");
        setResizable(false);
        setSize(750, 450);
        setLocationRelativeTo(null);
        border(BorderFactory.createTitledBorder("Rot-A-tor"));
    }

    public static void main(String args[]) {
        new Moving_Square();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        for (i = 0; i < 4; i++) {
            arcs[i].start += rotate;
            g.fill(arcs[i]);
        }
        for (i = 4; i < 8; i++) {
            arcs[i].start -= rotate;
            g.fill(arcs[i]);
        }
    }
}
