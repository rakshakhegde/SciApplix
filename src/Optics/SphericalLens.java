package Optics;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;

public class SphericalLens extends JFrame implements ActionListener {

    private JTextField textField[] = new JTextField[6];
    private JButton but;
    private JSlider indexSlider1 = new JSlider(10, 25), indexSlider2 = new JSlider(10, 25), sizeSlider = new JSlider(1, 250);
    private JDialog infoRay;
    private String str, filePath = "/Spherical Lens/Data.rak";
    private Toolkit toolkit = getToolkit();
    private CustomGlassPane cgp = new CustomGlassPane();
    private int n, m;
    private boolean frameAlive = true;

    public SphericalLens() {
        super("Spherical Lens");
        for (n = 0; n < textField.length; n++) {
            textField[n] = new JTextField(5);
            textField[n].setHorizontalAlignment(JTextField.CENTER);
        }
        textField[0].setEditable(false);
        textField[1].setEditable(false);
        textField[2].setEditable(false);
        //For the input panel at the top
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setBackground(Color.yellow);
        // For input Refractive index
        inputPanel.add(new JLabel("Refractive Index=µ | "));
        inputPanel.add(new JLabel("µ of Outer Medium:"));
        indexSlider1.setOpaque(false);
        inputPanel.add(indexSlider1);
        inputPanel.add(textField[0]);
        inputPanel.add(new JLabel("µ of Inner Medium:"));
        indexSlider2.setOpaque(false);
        inputPanel.add(indexSlider2);
        inputPanel.add(textField[1]);
        // For input Size of the lens
        inputPanel.add(new JLabel("Size:"));
        sizeSlider.setOpaque(false);
        inputPanel.add(sizeSlider);
        inputPanel.add(textField[2]);
        // For setting the translations to the middle of the screen
        but = new JButton("Reset");
        but.addActionListener(this);
        inputPanel.add(but);
        // For preferences dialog
        but = new JButton("Preferences");
        but.addActionListener(this);
        inputPanel.add(but);
        inputPanel.setSize(inputPanel.getWidth(), 100);
        // For the dialog box
        infoRay = new JDialog(this, true);
        infoRay.setSize(210, 175);
        infoRay.setResizable(false);
        infoRay.setAlwaysOnTop(true);
        infoRay.setLayout(new FlowLayout());
        infoRay.add(new JLabel("Object's Distance"));
        infoRay.add(textField[3]);
        infoRay.add(new JLabel("Object's Height"));
        infoRay.add(textField[4]);
        infoRay.add(new JLabel("Incident Height"));
        infoRay.add(textField[5]);
        but = new JButton("OK");
        but.addActionListener(this);
        infoRay.add(but);
        add(BorderLayout.NORTH, inputPanel);
        //For the lens drawing part
        DrawPanel drawPanel = new DrawPanel(this);
        add(BorderLayout.CENTER, drawPanel);
        //For the glass pane
        setGlassPane(cgp);
        //For setting the size of the frame to the maximum
        Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        setSize(rec.width, rec.height);
        setIconImage(toolkit.getImage("Spherical Lens/Spherical Lens.png"));

        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                save();
                frameAlive = false;
                System.gc();
            }
        });
        // For initialising the lens drawing variables
        drawPanel.init();
        File file = new File(filePath);
        if (file.exists() && file.canRead()) {
            read();
        } else {
            initialize();
        }
        while (frameAlive) {
            try {
                Thread.sleep(33); // Frame Rate=30 fps
            } catch (Exception e) {
            }
            if (!infoRay.isVisible()) {
                repaint();
                cgp.setVisible(false);
            } else {
                cgp.setVisible(true);
            }
        }
    }

    private void save() {
        try {
            DataOutputStream output = new DataOutputStream(new FileOutputStream(filePath));
//            java.net.URL url=Spherical_Lens.class.getResource(filePath);
//            DataOutputStream input = new DataOutputStream();
            for (n = 0; n < o.length; n++) {
                for (m = 0; m < 2; m++) {
                    output.writeInt(o[n][m]);
                }
                output.writeDouble(i[n][1]);
            }
            output.writeInt(rayIndex);
            output.writeInt(indexSlider1.getValue());
            output.writeInt(indexSlider2.getValue());
            output.writeInt(sizeSlider.getValue());
            output.writeFloat(transX);
            output.writeFloat(transY);
            output.close();
            System.out.println("Successfully Saved!!!");
        } catch (Exception e) {
            System.out.println(e + "... Saving failed!!!");
        }
    }

    private void read() {
        try {
            DataInputStream input = new DataInputStream(new FileInputStream(filePath));
//            DataInputStream input = new DataInputStream(Spherical_Lens.class.getResourceAsStream(filePath));
            for (n = 0; n < o.length; n++) {
                for (m = 0; m < 2; m++) {
                    o[n][m] = input.readInt();
                }
                i[n][1] = input.readDouble();
            }
            rayIndex = input.readInt();
            indexSlider1.setValue(input.readInt());
            indexSlider2.setValue(input.readInt());
            sizeSlider.setValue(input.readInt());
            transX = input.readFloat();
            transY = input.readFloat();
            input.close();
            System.out.println("Successfully Read!!!");
        } catch (Exception e) {
            initialize();
            System.out.println(e + "... Reading failed!!!");
        }
    }

    private void initialize() {
        for (n = 0; n < o.length; n++) {
            o[n][0] = -300;
        }
        i[0][1] = o[0][1] = -100;
        i[1][1] = o[1][1] = -50;
        i[2][1] = o[2][1] = 50;
        i[3][1] = o[3][1] = 100;
        indexSlider1.setValue(10);
        indexSlider2.setValue(15);
        transX = width / 2;
        transY = height / 2;
        rayIndex = 0;
    }

    public static void main(String[] args) {
        new SphericalLens();
    }

    public void actionPerformed(ActionEvent ae) {
        str = ae.getActionCommand();
        if (str.equals("Preferences")) {
            dialogSetter();
        } else if (str.equals("Reset")) {
            initialize();
        } else if (str.equals("OK")) {
            n = rayIndex;
            o[n][0] = (int) -Double.parseDouble(textField[3].getText());
            o[n][1] = (int) -Double.parseDouble(textField[4].getText());
            i[n][1] = -Double.parseDouble(textField[5].getText());
            infoRay.setVisible(false);
        }
    }

    private void dialogSetter() {
        n = rayIndex;
        textField[3].setText((float) -o[n][0] + "");
        textField[4].setText((float) -o[n][1] + "");
        textField[5].setText((float) -i[n][1] + "");
        infoRay.setTitle("Ray " + (rayIndex + 1));
        infoRay.setVisible(true);
    }
    //0 = x-Value 1 = y-Value 2 = radius
    int yText, space, num, rayIndex;
    int width, height, mouseX, mouseY, f, source, incidence, xCo, yCo;
    int[] c1 = {100, 0, 200}, c2 = {-100, 0, 200};
    int[][] o = new int[4][2];
    double[][] preO = new double[4][2], i = new double[4][2];
    double[] r = new double[2], e = new double[2];
    double iAng, mI, rAng, r2Ang, mR, eAng, mE, mN, index;
    double deg1, deg2, xExtent, yExtent;
    BufferedImage arrows;
    Graphics2D g2d;
    Arc2D.Double arc;
    Font f1 = new Font(Font.SERIF, Font.BOLD, 16), f2 = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
    GradientPaint gradient;
    float transX, transY, changeXTrans, changeYTrans;
    Polygon poly = new Polygon(new int[]{0, 0, 0}, new int[]{0, 0, 0}, 3);

    private class DrawPanel extends JComponent implements MouseMotionListener, MouseListener, KeyListener {

        static final long serialVersionUID = 1;
        SphericalLens lens;

        public DrawPanel(SphericalLens spl) {
            lens = spl;
            space = getFontMetrics(f2).getHeight();
            addKeyListener(this);
        }

        private void init() {
            width = getWidth();
            height = getHeight();
            addMouseMotionListener(this);
            addMouseListener(this);

            System.out.println("Drawing Width - " + width + " px");
            System.out.println("Drawing Height - " + height + " px");
            gradient = new GradientPaint(0, 0, Color.white, 0, getHeight(), new Color(255, 255, 100));
            requestFocus();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            width = getWidth();
            height = getHeight();
            mouseAction();
            index = (indexSlider2.getValue() / 10.0) / (indexSlider1.getValue() / 10.0);
            textField[0].setText(indexSlider1.getValue() / 10.0 + "");
            textField[1].setText(indexSlider2.getValue() / 10.0 + "");
            g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Clearing the screen
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, width, height);
            g2d.translate(transX, transY);
            g2d.setStroke(new BasicStroke(2));
            // For the Principal Axis
            g2d.setPaint(Color.pink);
            g2d.drawLine((int) -transX, 0, (int) (width - transX), 0);

            c1[0] = sizeSlider.getValue();
            c2[0] = -c1[0];
            c1[2] = (int) map(sizeSlider.getValue(), sizeSlider.getMinimum(), sizeSlider.getMaximum(), 120, 330);
            c2[2] = (int) map(sizeSlider.getValue(), sizeSlider.getMinimum(), sizeSlider.getMaximum(), 120, 330);
            textField[2].setText(sizeSlider.getValue() + "");
            for (n = 0; n < o.length; n++) {
                drawRays();
            }
            // For the lens
            // For the extent of the lens
            g2d.setPaint(new Color(0, 0, 255, 50));
            xExtent = ((c2[2] * c2[2]) - (c1[2] * c1[2]) - (c2[0] * c2[0]) + (c1[0] * c1[0])) / (2.0 * (c1[0] - c2[0]));
            deg1 = Math.toDegrees(Math.acos((xExtent - c1[0]) / c1[2]));
            arc = new Arc2D.Double(c1[0] - c1[2], c1[1] - c1[2], 2 * c1[2], 2 * c1[2], deg1, 2 * (180 - deg1), Arc2D.CHORD);
            g2d.fill(arc);
            deg2 = Math.toDegrees(Math.acos((c2[0] - xExtent) / c2[2]));
            arc = new Arc2D.Double(c2[0] - c2[2], c2[1] - c2[2], 2 * c2[2], 2 * c2[2], deg2 - 180, 2 * (180 - deg2), Arc2D.CHORD);
            g2d.fill(arc);

            g2d.setPaint(Color.blue);
            g2d.fillOval(-3, -3, 6, 6);
            g2d.translate(-transX, -transY);
            try {
                g2d.setPaint(Color.black);
                poly.xpoints[0] = 0;
                poly.ypoints[0] = height / 2;
                poly.xpoints[1] = 40;
                poly.ypoints[1] = height / 2 - 20;
                poly.xpoints[2] = 40;
                poly.ypoints[2] = height / 2 + 20;
                g2d.fillPolygon(poly);

                poly.xpoints[0] = width / 2;
                poly.ypoints[0] = 0;
                poly.xpoints[1] = width / 2 + 20;
                poly.ypoints[1] = 40;
                poly.xpoints[2] = width / 2 - 20;
                poly.ypoints[2] = 40;
                g2d.fillPolygon(poly);

                poly.xpoints[0] = width;
                poly.ypoints[0] = height / 2;
                poly.xpoints[1] = width - 40;
                poly.ypoints[1] = height / 2 + 20;
                poly.xpoints[2] = width - 40;
                poly.ypoints[2] = height / 2 - 20;
                g2d.fillPolygon(poly);

                poly.xpoints[0] = width / 2;
                poly.ypoints[0] = height;
                poly.xpoints[1] = width / 2 - 20;
                poly.ypoints[1] = height - 40;
                poly.xpoints[2] = width / 2 + 20;
                poly.ypoints[2] = height - 40;
                g2d.fillPolygon(poly);
            } catch (Exception e) {
            }
            g2d.dispose();
            g.dispose();
        }

        private void drawRays() {
            corrections();
            g2d.setStroke(new BasicStroke(2));
            if (n == rayIndex) {
                g2d.setStroke(new BasicStroke(3));
            }
            // Incident Ray
            g2d.setPaint(Color.green);
            i[n][0] = c1[0] - Math.sqrt(c1[2] * c1[2] - Math.pow(i[n][1] - c1[1], 2)); // Setting the x-coordinate for point of incidence
            mI = (o[n][1] - i[n][1]) / (o[n][0] - i[n][0]); // Gradient of Incident ray
            g2d.drawLine((int) o[n][0], (int) o[n][1], (int) i[n][0], (int) i[n][1]); // Drawing the incident ray
            // Refracted Ray
            mN = Math.atan((c1[1] - i[n][1]) / (c1[0] - i[n][0])); // Angle of first Normal
            iAng = mN - Math.atan(mI); // Angle of Incidence
            rAng = Math.asin(Math.sin(iAng) / index); // Angle of Refraction 1
            mR = Math.tan(mN - rAng); // Gradient of Refracted ray
            // Calculating the coordinates of r
            i[n][0] -= c2[0];
            //e[0] = (Math.sqrt(-4*(mR*mR+1)*(Math.pow(-mR*r[0]+r[1], 2)+c2[0]*c2[0]-c2[2]*c2[2])+Math.pow(2*mR*(-mR*r[0]+r[1]-2*c2[0]), 2))-2*mR*(-mR*r[0]+r[1])+2*c2[0])/(2*mR*mR+2);
            r[0] = (Math.sqrt(-4 * (mR * mR + 1) * (Math.pow(-mR * i[n][0] + i[n][1], 2) - c2[2] * c2[2]) + Math.pow(2 * mR * (-mR * i[n][0] + i[n][1]), 2)) - 2 * mR * (-mR * i[n][0] + i[n][1])) / (2 * mR * mR + 2);
            r[0] += c2[0];
            i[n][0] += c2[0];
            r[1] = i[n][1] + mR * (r[0] - i[n][0]);
            mN = Math.atan((r[1] - c2[1]) / (r[0] - c2[0])); // Gradient of the second Normal
			/*
             * if(Math.abs(mN) > deg2) { i[n][0] -= c1[0]; r[0] =
             * (Math.sqrt(-4*(mR*mR+1)*(Math.pow(-mR*i[n][0]+i[n][1],
             * 2)-c1[2]*c1[2])+Math.pow(2*mR*(-mR*i[n][0]+i[n][1]),
             * 2))-2*mR*(-mR*i[n][0]+i[n][1]))/(2*mR*mR+2); r[0] += c1[0];
             * i[n][0] += c1[0]; r[1] = i[n][1]+mR*(r[0]-i[n][0]); mN =
             * Math.atan((r[1]-c1[1])/(r[0]-c1[0])); // Gradient of the second
             * Normal }
             */
            g2d.drawLine((int) i[n][0], (int) i[n][1], (int) r[0], (int) r[1]); // Drawing the refracted ray
			/*
             * Drawing the first Normal g2d.setPaint(Color.red); for(f =
             * (int)i[n][0]-150; f <= i[n][0]+200; f+=5) g2d.drawLine(f,
             * (int)(i[n][1]+mN*(f-i[n][0])), f+=5,
             * (int)(i[n][1]+mN*(f-i[n][0])));
             */
            // Emergent Ray
            g2d.setPaint(Color.green);
            r2Ang = mN - Math.atan(mR); // Angle of Refraction 2
            eAng = Math.asin(Math.sin(r2Ang) * index); // Angle of Emergence
            mE = Math.tan(mN - eAng); // Gradient of the Emergent Ray
            e[0] = width - transX;
            e[1] = r[1] + mE * (e[0] - r[0]);
            g2d.drawLine((int) r[0], (int) r[1], (int) e[0], (int) e[1]); // Drawing the emergent ray
			/*
             * Drawing the second Normal g2d.setPaint(Color.red); for(f =
             * (int)r[0]-200; f <= r[0]+150; f+=5) g2d.drawLine(f,
             * (int)(r[1]+mN*(f-r[0])), f+=5, (int)(r[1]+mN*(f-r[0])));
             */
            // For the circle around the source and the point of incidence
            g2d.setStroke(new BasicStroke(1));
            g2d.setPaint(new Color(0, 0, 0, 50));
            if (n == rayIndex) {
                g2d.setPaint(new Color(0, 0, 0, 170));
            }
            g2d.drawOval((int) i[n][0] - 10, (int) i[n][1] - 10, 20, 20);
            g2d.drawOval((int) o[n][0] - 10, (int) o[n][1] - 10, 20, 20);
            // Angle Text to show
            if (n == rayIndex) {
                g2d.translate(-transX, -transY);
                yText = 0;
                g2d.setPaint(Color.red);
                g2d.setFont(f1);
                g2d.drawString("DETAILS OF RAY " + (rayIndex + 1), 5, yText += getFontMetrics(f1).getHeight());
                g2d.setFont(f2);
                g2d.drawString("Distance = " + (-o[n][0]), 5, yText += space);
                g2d.drawString("Height = " + (-o[n][1]), 5, yText += space);
                g2d.drawString("Point Of Incidence (" + (-i[n][0]) + ", " + (-i[n][1]) + ")", 5, yText += space);
                g2d.drawString("Angle of Incidence = " + Math.abs(Math.toDegrees(iAng)) + "°", 5, yText += space);
                g2d.drawString("Angle of Refraction 1 = " + Math.abs(Math.toDegrees(rAng)) + "°", 5, yText += space);
                g2d.drawString("Angle of Refraction 2 = " + Math.abs(Math.toDegrees(r2Ang)) + "°", 5, yText += space);
                g2d.drawString("Angle of Emergence = " + Math.abs(Math.toDegrees(eAng)) + "°", 5, yText += space);
                g2d.drawString("Angle of Incident Ray to the Principal Axis = " + Math.toDegrees(Math.atan(mI)) + "°", 5, yText += space);
                g2d.drawString("Angle of Emergent Ray to the Principal Axis = " + Math.toDegrees(Math.atan(mE)) + "°", 5, yText += space);
                g2d.drawString("Mouse Coordinates (" + (int) (transX - mouseX) + ", " + (int) (transY - mouseY) + ")", 5, yText += space + 5);
                g2d.translate(transX, transY);
            }
        }

        private double map(double value, double low1, double high1, double low2, double high2) {
            double map = (value - low1) / (high1 - low1);
            double lerp = map * (high2 - low2) + low2;
            return lerp;
        }

        private void corrections() {
            if (o[n][0] >= c1[0] - c1[2]) {
                o[n][0] = c1[0] - c1[2] - 1;
//				textField[3].setText((int)-o[n][0]+"");
            }
            yExtent = Math.sqrt(c1[2] * c1[2] - Math.pow(xExtent - c1[0], 2));
            if (i[n][1] >= yExtent) {
                i[n][1] = yExtent - 1E-12;
            } else if (i[n][1] <= -yExtent) {
                i[n][1] = 1E-12 - yExtent;
            }
        }

        private void mouseAction() {
            if (mouseY <= 40 && mouseX >= width / 2 - 20 && mouseX <= width / 2 + 20) // For Up Arrow
            {
                changeYTrans += 0.5;
            } else if (mouseX <= 40 && mouseY >= height / 2 - 20 && mouseY <= height / 2 + 20) // For Left Arrow
            {
                changeXTrans += 0.5;
            } else if (mouseY >= height - 40 && mouseX >= width / 2 - 20 && mouseX <= width / 2 + 20) // For Down Arrow
            {
                changeYTrans -= 0.5;
            } else if (mouseX >= width - 40 && mouseY >= height / 2 - 20 && mouseY <= height / 2 + 20) // For Right Arrow
            {
                changeXTrans -= 0.5;
            } else {
                changeXTrans *= 0.7;
                changeYTrans *= 0.7;
            }
            transX += changeXTrans;
            transY += changeYTrans;
        }

        public void mouseDragged(MouseEvent me) {
            mouseX = (int) (me.getX() - transX);
            mouseY = (int) (me.getY() - transY);
            for (n = 0; n < o.length; n++) {
                if (incidence == n) {
                    i[n][1] = mouseY;
                    rayIndex = n;
                    break;
                } else if (source == n) {
                    o[n][0] = mouseX;
                    o[n][1] = mouseY;
                    rayIndex = n;
                    break;
                }
            }
            mouseX += transX;
            mouseY += transY;
        }
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

        public void mouseMoved(MouseEvent me) {
            mouseX = (int) (me.getX() - transX);
            mouseY = (int) (me.getY() - transY);
            lens.setCursor(Cursor.getDefaultCursor());
            for (n = 0; n < o.length; n++) {
                if (Math.sqrt(Math.pow(o[n][0] - mouseX, 2) + Math.pow(o[n][1] - mouseY, 2)) <= 10) {
                    source = n;
                    lens.setCursor(handCursor);
                    break;
                } else {
                    source = -1;
                }
                if (Math.sqrt(Math.pow(i[n][0] - mouseX, 2) + Math.pow(i[n][1] - mouseY, 2)) <= 10) {
                    incidence = n;
                    lens.setCursor(handCursor);
                    break;
                } else {
                    incidence = -1;
                }
            }
            mouseX += transX;
            mouseY += transY;
        }

        public void mouseClicked(MouseEvent me) {
            changeXTrans = 0;
            changeYTrans = 0;
            if (me.getClickCount() > 1) {
                dialogSetter();
            }
        }

        public void mouseEntered(MouseEvent me) {
            requestFocus();
        }

        public void mouseExited(MouseEvent me) {
            mouseX = -1;
            mouseY = -1;
        }

        public void mousePressed(MouseEvent me) {
            for (n = 0; n < o.length; n++) {
                if (incidence == n) {
                    rayIndex = n;
                    break;
                } else if (source == n) {
                    rayIndex = n;
                    break;
                }
            }
        }

        public void mouseReleased(MouseEvent me) {
        }

        public void keyPressed(KeyEvent ke) {
            int key = ke.getKeyCode();
            if (key == KeyEvent.VK_RIGHT) {
                o[rayIndex][0]++;
            } else if (key == KeyEvent.VK_LEFT) {
                o[rayIndex][0]--;
            } else if (key == KeyEvent.VK_UP) {
                o[rayIndex][1]--;
            } else if (key == KeyEvent.VK_DOWN) {
                o[rayIndex][1]++;
            } else if (key == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            } else if (ke.isShiftDown()) {
                rayIndex++;
                rayIndex = rayIndex > 3 ? 0 : rayIndex;
            }
        }

        public void keyReleased(KeyEvent ke) {
        }

        public void keyTyped(KeyEvent ke) {
        }
    }

    public class CustomGlassPane extends JComponent {

        @Override
        public void paintComponent(Graphics g) {
            g.setColor(new Color(1, 1, 1, 0.65F));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}