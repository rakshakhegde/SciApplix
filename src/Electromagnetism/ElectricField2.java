package Electromagnetism;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.*;
import static java.lang.Math.*;

/**
 * @author Rakshak R.Hegde
 */
public class ElectricField2 extends JFrame {

    int lenVec = 15;
    int width, height;
    int mouseX, mouseY, pmouseX, pmouseY;
    boolean isDragged;
    float Ex, Ey;
    double E, theta, dist2, K = 5E4;
    Painter painter;
    ArrayList<Particle> particles = new ArrayList();
    Particle particle;
    BufferedImage image;
    Graphics2D imgG2d;
    BasicStroke stroke = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    Dimension dim;
    JPopupMenu popupMenu = new JPopupMenu("Menu");
    JLabel strengthLabel = new JLabel("Electric Field");
    JLabel xCoordLabel = new JLabel("X Coordinate");
    JLabel yCoordLabel = new JLabel("Y Coordinate");
    JLabel stateLabel = new JLabel();
    PropsDialog propsDialog;
    Random ran = new Random();

    public ElectricField2() {
        initComponents();
        initState();

        // Setting window properties
        setTitle("Electric Field 2");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initComponents() {
        painter = new Painter();
        painter.setBackground(Color.WHITE);
        painter.setBorder(BorderFactory.createTitledBorder("Rendering Panel"));
        add(painter);

        JPanel panel = new JPanel();
        panel.setBackground(Color.YELLOW);
        panel.setBorder(BorderFactory.createTitledBorder("Control Panel"));
        try {
            JButton posBut = new JButton("Positive");
            posBut.setToolTipText("Add a positive charge");
//            , new ImageIcon(getClass().getResource("/images/blue-sphere - Copy.png"))
            posBut.setPreferredSize(new Dimension(80, 35));
            posBut.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    particles.add(new Particle(width / 2, height / 2, 10, 1));
                    stateLabelInit();
                    repaint();
                }
            });
            JButton negBut = new JButton("Negative");
            negBut.setToolTipText("Add a negative charge");
            negBut.setPreferredSize(new Dimension(80, 35));
            negBut.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    particles.add(new Particle(width / 2, height / 2, 10, -1));
                    stateLabelInit();
                    repaint();
                }
            });
            JButton clearBut = new JButton("Clear Screen");
            clearBut.setToolTipText("Clear the drawings in the rendering panel");
            clearBut.setPreferredSize(new Dimension(100, 35));
            clearBut.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    imgG2d.setColor(Color.WHITE);
                    imgG2d.fillRect(0, 0, dim.width, dim.height);
                    imgG2d.setColor(Color.MAGENTA);
                    repaint();
                }
            });
            final JLabel permLabel = new JLabel("K = " + K);
            final JTextField permField = new JTextField(K + "", 7);
            permField.setToolTipText("Specify the electrostatic constant (=1/(4*PI*Permittivity))");
            permField.addKeyListener(new KeyAdapter() {

                @Override
                public void keyReleased(KeyEvent ke) {
                    super.keyReleased(ke);
                    try {
                        K = Double.parseDouble(permField.getText());
                    } catch (Exception exc) {
                        K = 5E4;
                    }
                    permLabel.setText("K = " + K);
                }
            });
            panel.add(posBut);
            panel.add(new Box.Filler(new Dimension(5, 35), new Dimension(10, 35), new Dimension(15, 35)));
            panel.add(negBut);
            panel.add(new Box.Filler(new Dimension(5, 35), new Dimension(10, 35), new Dimension(15, 35)));
            panel.add(clearBut);
            panel.add(new Box.Filler(new Dimension(10, 35), new Dimension(20, 35), new Dimension(20, 35)));
            panel.add(permLabel);
            panel.add(permField);
        } catch (Exception exc) {
        }
        add(panel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createTitledBorder("Information Panel"));
        infoPanel.setBackground(Color.YELLOW);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        infoPanel.add(strengthLabel);
        infoPanel.add(xCoordLabel);
        infoPanel.add(yCoordLabel);
        stateLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        infoPanel.add(stateLabel);
        add(infoPanel, BorderLayout.SOUTH);

        propsDialog = new PropsDialog(this);
        propsDialog.setBackground(Color.WHITE);
    }

    public void initState() {
        painter.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent me) {
                super.mousePressed(me);
                pmouseX = mouseX;
                pmouseY = mouseY;
                mouseX = me.getX();
                mouseY = me.getY();
                painter.requestFocus();
                particle = null;
                for (Particle part : particles) {
                    if (part.contains(me.getX(), me.getY())) {
                        particle = part;
                        break;
                    }
                }
                if (me.getButton() == MouseEvent.BUTTON3 && particle != null) {
                    popupMenu.setLocation(me.getXOnScreen(), me.getYOnScreen());
                    popupMenu.setVisible(true);
                }
                if (me.getClickCount() > 1 && particle != null) {
                    prepDialog();
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                super.mouseReleased(me);
                isDragged = false;
            }
        });
        painter.addMouseMotionListener(new MouseMotionAdapter() {

            boolean isInside;

            @Override
            public void mouseMoved(MouseEvent me) {
                super.mouseMoved(me);
                pmouseX = mouseX;
                pmouseY = mouseY;
                mouseX = me.getX();
                mouseY = me.getY();
                isInside = false;
                for (Particle part : particles) {
                    if (part.contains(mouseX, mouseY)) {
                        isInside = true;
                        break;
                    }
                }
                if (isInside) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                } else {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
                repaint();
                try {
                    Thread.sleep(20);
                } catch (Exception exc) {
                }
            }

            @Override
            public void mouseDragged(MouseEvent me) {
                super.mouseDragged(me);
                pmouseX = mouseX;
                pmouseY = mouseY;
                mouseX = me.getX();
                mouseY = me.getY();
                isDragged = true;
                if (particle != null) {
                    particle.setParticle(mouseX, mouseY);
                }
                repaint();
                try {
                    Thread.sleep(20);
                } catch (Exception exc) {
                }
            }
        });

        ActionListener popupMenuListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                popupMenu.setVisible(false);
                if (e.getActionCommand().contentEquals("Properties")) {
                    prepDialog();
                } else if (e.getActionCommand().contentEquals("Remove")) {
                    if (particle != null) {
                        particles.remove(particle);
                        stateLabelInit();
                        repaint();
                    }
                }
            }
        };
        popupMenu.setInvoker(painter);
        popupMenu.add("Properties").addActionListener(popupMenuListener);
        popupMenu.add("Remove").addActionListener(popupMenuListener);

        particles.add(new Particle(350, 300, 10, 1));

        dim = Toolkit.getDefaultToolkit().getScreenSize();
        image = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB);
        imgG2d = (Graphics2D) image.createGraphics();
        imgG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        imgG2d.setColor(Color.MAGENTA);
        imgG2d.setStroke(stroke);
    }

    private void stateLabelInit() {
        String state = "";
        if (particles.size() == 2) {
            if (particles.get(0).charge * particles.get(1).charge < 0) {
                state = "Hey that's a dipole you made there!!!";
            }
        } else if (particles.size() > 12 - ran.nextInt(5)) {
            state = "Phew... that's just too many particles!!!";
        }
        stateLabel.setText(state);
    }

    public void prepDialog() {
        propsDialog.setTitle("Properties Dialog " + (particles.indexOf(particle) + 1));
        propsDialog.xField.setText(particle.x + "");
        propsDialog.yField.setText(particle.y + "");
        propsDialog.radiusField.setText(particle.radius + "");
        propsDialog.chargeField.setText(particle.charge + "");
        propsDialog.setVisible(true);
    }

    public static void main(String args[]) {
        // Set the Nimbus Look And Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                }
            }
        } catch (Exception ex) {
        }

        new ElectricField2();
    }

    class Painter extends JPanel {

        Graphics2D g2d;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            width = getWidth();
            height = getHeight();
            g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(Color.BLACK);
            Ex = 0;
            Ey = 0;
            E = 0;
            for (Particle particle : particles) {
                dist2 = pow(particle.x - mouseX, 2) + pow(particle.y - mouseY, 2);
                if (dist2 > 0) {
                    theta = atan2(mouseY - particle.y, mouseX - particle.x);
                    E = particle.charge / dist2;
                    Ex += E * cos(theta);
                    Ey += E * sin(theta);
                }
            }
            E = hypot(Ex, Ey) * K;
            theta = atan2(Ey, Ex);
            g2d.setStroke(stroke);
            g2d.rotate(theta, mouseX, mouseY);
            g2d.drawLine(mouseX, mouseY, (int) (mouseX + (lenVec * E)), mouseY);
            g2d.drawLine((int) (mouseX + (lenVec * E) - 5), mouseY + 5, (int) (mouseX + (lenVec * E)), mouseY);
            g2d.drawLine((int) (mouseX + (lenVec * E) - 5), mouseY - 5, (int) (mouseX + (lenVec * E)), mouseY);
            g2d.rotate(-theta, mouseX, mouseY);

            if (particle == null && isDragged) {
                imgG2d.drawLine(pmouseX, pmouseY, mouseX, mouseY);
            }
            for (Particle particle : particles) {
                g2d.setColor(particle.color);
                g2d.fill(particle);
            }

            // Setting the info labels
            strengthLabel.setText("Electric Field = " + E);
            xCoordLabel.setText("X Coordinate = " + mouseX);
            yCoordLabel.setText("Y Coordinate = " + mouseY);
        }
    }

    class Particle extends Ellipse2D.Float {

        float charge;
        /**
         * Radius of the circle
         */
        float radius;
        /**
         * x coordinate
         */
        float x;
        /**
         * y coordinate
         */
        float y;
        private Color NEGCOL = Color.RED, POSCOL = Color.BLUE, NEUTRAL = Color.LIGHT_GRAY;
        Color color;

        /**
         * Represents a charge.
         *
         * @param x x position of the charge
         * @param y x position of the charge
         * @param charge Charge on the particle
         */
        public Particle(float x, float y, float radius, float charge) {
            super(x - radius, y - radius, 2 * radius, 2 * radius);
            this.x = x;
            this.y = y;
            this.charge = charge;
            this.radius = radius;
            color = charge != 0 ? (charge > 0 ? POSCOL : NEGCOL) : NEUTRAL;
        }

        public void setParticle(float x, float y) {
            this.x = x;
            this.y = y;
            setFrame(x - radius, y - radius, 2 * radius, 2 * radius);
        }

        public void setParticle(float x, float y, float r, float c) {
            this.x = x;
            this.y = y;
            radius = r;
            charge = c;
            color = charge != 0 ? (charge > 0 ? POSCOL : NEGCOL) : NEUTRAL;
            setFrame(x - r, y - r, 2 * r, 2 * r);
        }
    }

    class PropsDialog extends JDialog {

        JTextField xField = new JTextField(10);
        JTextField yField = new JTextField(10);
        JTextField radiusField = new JTextField(10);
        JTextField chargeField = new JTextField(10);

        public PropsDialog(JFrame frame) {
            super(frame, "Properties Dialog");

            JPanel p = new JPanel();
            GridLayout grid = new GridLayout(5, 2);
            p.setLayout(grid);
            p.setBackground(new Color(250, 250, 250));
            p.setBorder(BorderFactory.createTitledBorder("Properties Panel"));
            // Setting the dialog features
            p.add(new JLabel("X Position"));
            p.add(xField);
            xField.setToolTipText("Specify the X coordinate");
            p.add(new JLabel("Y Position"));
            p.add(yField);
            yField.setToolTipText("Specify the Y coordinate (positive downwards)");
            p.add(new JLabel("Radius"));
            p.add(radiusField);
            radiusField.setToolTipText("Specify the radius");
            p.add(new JLabel("Charge"));
            chargeField.setToolTipText("Specify the charge");
            p.add(chargeField);
            JButton okBut = new JButton("OK");
            okBut.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    setProps();
                }
            });
            p.add(okBut);
            JButton cancelBut = new JButton("Cancel");
            cancelBut.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                }
            });
            p.add(cancelBut);

            // Key Listener for the text fields
            KeyAdapter keyAdapter = new KeyAdapter() {

                @Override
                public void keyPressed(KeyEvent e) {
                    super.keyPressed(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        setProps();
                    }
                }
            };
            xField.addKeyListener(keyAdapter);
            yField.addKeyListener(keyAdapter);
            radiusField.addKeyListener(keyAdapter);
            chargeField.addKeyListener(keyAdapter);

            add(p);

            // Setting window properties
//            setModal(true);
            setSize(240, 230);
            setLocationRelativeTo(frame);
        }

        public void setProps() {
            try {
                particle.setParticle(Float.parseFloat(xField.getText()),
                        Float.parseFloat(yField.getText()),
                        Float.parseFloat(radiusField.getText()),
                        Float.parseFloat(chargeField.getText()));
                setVisible(false);
                ElectricField2.this.repaint();
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(PropsDialog.this, "Invalid Input!!! Please enter valid numbers again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
