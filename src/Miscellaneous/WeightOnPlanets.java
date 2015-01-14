package Miscellaneous;

/**
 * @author Rakshak.R.Hegde
 */
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class WeightOnPlanets extends JFrame {

    String[] names = {"Earth", "Sun", "Moon", "Mercury", "Venus", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto"};
    JTextField weightTF[] = new JTextField[names.length], ageTF[] = new JTextField[names.length];
    JLabel planets[] = new JLabel[names.length];
    String views[] = {"Planet", "Weight", "Age"};
    float weightFac[] = {27.9f, 0.17f, 0.38f, 0.91f, 0.38f, 2.54f, 1.08f, 0.91f, 1.19f, 0.06f};
    float ageFac[] = {0.241f, 0.615f, 1.88f, 11.9f, 29.5f, 84, 164.8f, 248.5f};
    int i;
    float num;
    String str;

    public static void main(String a[]) {
        new WeightOnPlanets();
    }

    public WeightOnPlanets() {
        setTitle("Weight and Age On Planets");
        java.net.URL imgURL = WeightOnPlanets.class.getResource("/planets/0.jpg");
        if (imgURL != null) {
            setIconImage(new ImageIcon(imgURL).getImage());
        }
        JPanel labelPanel = new JPanel(), showPanel = new JPanel();
        JLabel label;
        for (i = 0; i < views.length; i++) {
            label = new JLabel("<html><u><i>" + views[i] + "</i></u></html>");
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
            label.setForeground(Color.red);
            labelPanel.add(label);
        }
        labelPanel.setLayout(new GridLayout(1, 3));
        labelPanel.setBackground(Color.yellow);

        // For the init of inputText and planets
        for (i = 0; i < names.length; i++) {
            planets[i] = new JLabel(names[i], JLabel.CENTER);
            imgURL = WeightOnPlanets.class.getResource("/planets/" + i + ".jpg");
            if (imgURL != null) {
                planets[i].setIcon(new ImageIcon(imgURL));
            }
            planets[i].setForeground(Color.blue);
            showPanel.add(planets[i]);
            weightTF[i] = new JTextField();
            weightTF[i].setHorizontalAlignment(JLabel.CENTER);
            showPanel.add(weightTF[i]);
            ageTF[i] = new JTextField();
            ageTF[i].setHorizontalAlignment(JLabel.CENTER);
            showPanel.add(ageTF[i]);
            if (i > 0) {
                weightTF[i].setEditable(false);
                ageTF[i].setEditable(false);
            }
        }
        weightTF[0].setToolTipText("Enter your weight only in numbers...");
        ageTF[0].setToolTipText("Enter your age only in numbers...");
        showPanel.setLayout(new GridLayout(names.length, 3));
        showPanel.setBackground(Color.white);
        getContentPane().add("North", labelPanel);
        getContentPane().add("Center", showPanel);
        setSize(350, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        weightTF[0].addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent ke) {
                try {
                    str = weightTF[0].getText().replaceAll(",", "");
                    if (!str.isEmpty()) {
                        num = Float.parseFloat(str);
                        if (num < 0) {
                            weightTF[0].setText((num = -num) + "");
                        }
                        for (i = 1; i < weightTF.length; i++) {
                            weightTF[i].setText(num * weightFac[i - 1] + "");
                        }
                    } else {
                        for (i = 1; i < names.length; i++) {
                            weightTF[i].setText("");
                        }
                    }
                } catch (Exception e) {
                    for (i = 1; i < names.length; i++) {
                        weightTF[i].setText("");
                    }
                }
            }
        });
        ageTF[0].addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent ke) {
                try {
                    str = ageTF[0].getText();
                    if (!str.isEmpty()) {
                        num = Float.parseFloat(str);
                        if (num < 0) {
                            ageTF[0].setText((num = -num) + "");
                        }
                        for (i = 1; i < ageTF.length; i++) {
                            if (i > 2) {
                                ageTF[i].setText(num / ageFac[i - 3] + "");
                            } else {
                                ageTF[i].setText("nill");
                            }
                        }
                    } else {
                        for (i = 1; i < names.length; i++) {
                            ageTF[i].setText("");
                        }
                    }
                } catch (Exception e) {
                    for (i = 1; i < names.length; i++) {
                        ageTF[i].setText("");
                    }
                }
            }
        });
    }
}