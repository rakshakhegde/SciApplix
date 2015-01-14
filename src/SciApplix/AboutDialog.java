package SciApplix;

import java.awt.*;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.*;
import javax.swing.*;

/**
 * @author Rakshak.R.Hegde
 */
public class AboutDialog extends JDialog {

    int width = 500, height = 300;

    public AboutDialog() {
        Point point = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        point.translate(-width / 2, -height / 2);
        setLocation(point);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setResizable(false);
        setSize(width, height);
        setShape(new RoundRectangle2D.Float(0, 0, width, height, 40, 40));
        setOpacity(0.95F);
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    setVisible(false);
                }
            }
        });
        addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                setVisible(false);
            }
        });
        add(new Painter());
    }

    class Painter extends JPanel {

        Graphics2D g2d;
        BufferedImage image;
        RadialGradientPaint radialPaint = new RadialGradientPaint(width, 50, height, new float[]{0, 0.2F, 0.5F}, new Color[]{new Color(135, 205, 247), new Color(79, 184, 248), new Color(135, 205, 247)}, CycleMethod.REPEAT);

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setPaint(radialPaint);
            g2d.fillRect(0, 0, width, height);

            /*
             * image = getGaussianBlurFilter(5, true).filter(image, null); image
             * = getGaussianBlurFilter(5, false).filter(image, null);
             */

            g2d = image.createGraphics();
            g2d.translate(10, height - 55);
            g2d.setPaint(Color.DARK_GRAY);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            g2d.drawString("Created by Rakshak R.Hegde", 0, 0);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            g2d.drawString("© 2013 Science Applications. All Rights NOT Reserved", 0, 18);
            g2d.drawString(EntryClass.VERSION, 0, 34);
            g.drawImage(image, 0, 0, null);
            g2d.dispose();
            g.dispose();
        }

        public ConvolveOp getBlurFilter(int radius) {
            if (radius < 1) {
                throw new IllegalArgumentException("Radius must be >= 1");
            }
            int size = radius * 2 + 1;
            float weight = 1.0f / (size * size);
            float[] data = new float[size * size];
            for (int i = 0; i < data.length; i++) {
                data[i] = weight;
            }
            return new ConvolveOp(new Kernel(size, size, data));
        }

        public ConvolveOp getGaussianBlurFilter(int radius, boolean horizontal) {
            if (radius < 1) {
                throw new IllegalArgumentException("Radius must be >= 1");
            }
            int size = radius * 2 + 1;
            float[] data = new float[size];
            float sigma = radius / 3.0f;
            float twoSigmaSquare = 2.0f * sigma * sigma;
            float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
            float total = 0.0f;
            for (int i = -radius; i <= radius; i++) {
                float distance = i * i;
                int index = i + radius;
                data[index] = (float) Math.exp(-distance / twoSigmaSquare)
                        / sigmaRoot;
                total += data[index];
            }
            for (int i = 0; i < data.length; i++) {
                data[i] /= total;
            }
            Kernel kernel = null;
            if (horizontal) {
                kernel = new Kernel(size, 1, data);
            } else {
                kernel = new Kernel(1, size, data);
            }
            return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        }
    }
}