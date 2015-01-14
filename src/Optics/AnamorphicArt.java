/*
 * Created on 15 Nov, 2012, 9:13:37 PM
 */
package Optics;

import Optics.ImageProcessor.CircleTransform;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.*;
import static java.lang.Math.*;

/**
 * @author Rakshak R.Hegde
 */
public class AnamorphicArt extends JFrame {

    int DROP_STATE = 5, RENDER_STATE = 6, STATE = DROP_STATE;
    JFileChooser fileChooser = new JFileChooser();
    File imageFile;
    BufferedImage image, morphedImage, smallImage;
    float ratio;
    int srcX, srcY, dest1X, dest1Y, dest2X, dest2Y;
    int radius = 100, height = 300;
    float spreadAngle = 180, startAngle = 0, centerXRatio, centerYRatio;
    CircleTransform circleTrans = new CircleTransform();
    long begin;
    KeyListener keyListener = new KeyAdapter() {

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                repaint();
            }
        }
    };

    public AnamorphicArt() {
        initComponents();
        initialize();
        setVisible(true);
        setLocationRelativeTo(null);
        painter.setTransferHandler(new ImageTransferHandler());
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
        new AnamorphicArt();
    }

    private void initialize() {
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPG & JPEG Images", "jpg", "jpeg", "JPG", "JPEG"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG Images", "png", "PNG"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("BMP & WBMP Images", "bmp", "BMP", "wbmp", "WBMP"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("GIF Images", "gif", "GIF"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("JPG, JPEG, PNG, BMP & GIF Images", "jpg", "jpeg", "png", "bmp", "gif"));
        fileChooser.setPreferredSize(new Dimension(600, 400));
        final FileSystemView systemView = FileSystemView.getFileSystemView();
        fileChooser.setFileView(new FileView() {

            @Override
            public Icon getIcon(File f) {
                return systemView.getSystemIcon(f);
            }
        });

        radiusField.addKeyListener(keyListener);
        heightField.addKeyListener(keyListener);
        spreadAngleField.addKeyListener(keyListener);
        startAngleField.addKeyListener(keyListener);
    }

    public void open() {
        try {
            image = ImageIO.read(imageFile);
            image = circleTrans.manageImage(image);
//            smallImage = circleTrans.resizeFixed(image, 150);
            STATE = RENDER_STATE;
        } catch (Exception e) {
            STATE = DROP_STATE;
            System.out.println("File could not be read: " + e);
            JOptionPane.showMessageDialog(this, "Invalid Input File!!!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        repaint();
        System.gc();
    }

    private void render() {
        try {
            radius = (int) Float.parseFloat(radiusField.getText());
            height = (int) Float.parseFloat(heightField.getText());
            spreadAngle = (float) toRadians(Float.parseFloat(spreadAngleField.getText()));
            startAngle = (float) toRadians(Float.parseFloat(startAngleField.getText()));
            centerXRatio = centerXSlider.getValue() / 20F;
            centerYRatio = centerYSlider.getValue() / 20F;
            if (morphedImage == null || morphedImage.getWidth() != painter.getWidth() || morphedImage.getHeight() != painter.getHeight()) {
                morphedImage = new BufferedImage(painter.getWidth(), painter.getHeight(), BufferedImage.TYPE_INT_ARGB);
            }
            begin = System.currentTimeMillis();
            if (image != null) {
                circleTrans.height = height;
                circleTrans.radius = radius;
                circleTrans.spreadAngle = spreadAngle;
                circleTrans.startAngle = startAngle;
                circleTrans.centerXRatio = centerXRatio;
                circleTrans.centerYRatio = centerYRatio;
                circleTrans.transform(image, morphedImage);
            }
            System.out.println("Anamorphic rendering took " + (System.currentTimeMillis() - begin) + " millis.");
        } catch (NumberFormatException e) {
            System.out.println("Probably exception in reading images!!! = " + e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inputPanel = new JPanel();
        jLabel3 = new JLabel();
        jLabel1 = new JLabel();
        jLabel4 = new JLabel();
        jLabel2 = new JLabel();
        jLabel5 = new JLabel();
        centerXSlider = new JSlider();
        jLabel6 = new JLabel();
        radiusField = new JTextField();
        startAngleField = new JTextField();
        heightField = new JTextField();
        spreadAngleField = new JTextField();
        centerYSlider = new JSlider();
        painter = new Painter();
        jMenuBar1 = new JMenuBar();
        jMenu1 = new JMenu();
        openMenu = new JMenuItem();
        saveMenu = new JMenuItem();
        openImageMenu = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Anamorphic Art");
        setPreferredSize(new Dimension(900, 700));

        inputPanel.setBackground(new Color(255, 255, 51));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Control Panel"));

        jLabel3.setText("Radius");

        jLabel1.setText("Height");

        jLabel4.setText("Spread Angle");

        jLabel2.setText("Start Angle");

        jLabel5.setText("Center X");

        centerXSlider.setMajorTickSpacing(10);
        centerXSlider.setMaximum(20);
        centerXSlider.setMinorTickSpacing(2);
        centerXSlider.setPaintTicks(true);
        centerXSlider.setToolTipText("Ratio of x coordinate of image to the screen width");
        centerXSlider.setValue(10);
        centerXSlider.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                centerXSliderMouseReleased(evt);
            }
        });
        centerXSlider.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                centerXSliderKeyReleased(evt);
            }
        });

        jLabel6.setText("Center Y");

        radiusField.setText(radius+"");
        radiusField.setToolTipText("Radius of the inner circle of the morphed image");

        startAngleField.setText(startAngle+"");
        startAngleField.setToolTipText("Initial angle from where the the morphed image has to start, specified in degrees");

        heightField.setText(height+"");
        heightField.setToolTipText("Distance between the inner and outer circle");

        spreadAngleField.setText(spreadAngle+"");
        spreadAngleField.setToolTipText("Spread angle specified in degrees");

        centerYSlider.setMajorTickSpacing(10);
        centerYSlider.setMaximum(20);
        centerYSlider.setMinorTickSpacing(2);
        centerYSlider.setPaintTicks(true);
        centerYSlider.setToolTipText("Ratio of y coordinate of image to the screen height");
        centerYSlider.setValue(18);
        centerYSlider.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                centerYSliderMouseReleased(evt);
            }
        });
        centerYSlider.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                centerYSliderKeyReleased(evt);
            }
        });

        GroupLayout inputPanelLayout = new GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(
            inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addComponent(radiusField, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                        .addGap(108, 108, 108)
                        .addComponent(jLabel1))
                    .addGroup(GroupLayout.Alignment.TRAILING, inputPanelLayout.createSequentialGroup()
                        .addComponent(startAngleField)
                        .addGap(97, 97, 97)
                        .addComponent(jLabel5)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(heightField, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(centerXSlider, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                .addGap(100, 100, 100)
                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, GroupLayout.Alignment.TRAILING))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(spreadAngleField, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(centerYSlider, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                .addGap(101, 101, 101))
        );
        inputPanelLayout.setVerticalGroup(
            inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(radiusField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(heightField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(spreadAngleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(centerXSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(centerYSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel5)
                        .addComponent(startAngleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painter.setBorder(BorderFactory.createTitledBorder("Morphed Image Panel"));

        GroupLayout painterLayout = new GroupLayout(painter);
        painter.setLayout(painterLayout);
        painterLayout.setHorizontalGroup(
            painterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 948, Short.MAX_VALUE)
        );
        painterLayout.setVerticalGroup(
            painterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 573, Short.MAX_VALUE)
        );

        jMenu1.setText("File");

        openMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        openMenu.setText("Open");
        openMenu.setToolTipText("Open an image");
        openMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                openMenuActionPerformed(evt);
            }
        });
        jMenu1.add(openMenu);

        saveMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        saveMenu.setText("Save As");
        saveMenu.setToolTipText("Save the morphed image in png format");
        saveMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                saveMenuActionPerformed(evt);
            }
        });
        jMenu1.add(saveMenu);

        openImageMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_MASK | InputEvent.CTRL_MASK));
        openImageMenu.setText("Open Image");
        openImageMenu.setToolTipText("Open the image in a native photo viewer");
        openImageMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                openImageMenuActionPerformed(evt);
            }
        });
        jMenu1.add(openImageMenu);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(inputPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(painter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(inputPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void centerXSliderMouseReleased(MouseEvent evt) {//GEN-FIRST:event_centerXSliderMouseReleased
        repaint();
    }//GEN-LAST:event_centerXSliderMouseReleased

    private void centerYSliderMouseReleased(MouseEvent evt) {//GEN-FIRST:event_centerYSliderMouseReleased
        repaint();
    }//GEN-LAST:event_centerYSliderMouseReleased

    private void centerXSliderKeyReleased(KeyEvent evt) {//GEN-FIRST:event_centerXSliderKeyReleased
        repaint();
    }//GEN-LAST:event_centerXSliderKeyReleased

    private void centerYSliderKeyReleased(KeyEvent evt) {//GEN-FIRST:event_centerYSliderKeyReleased
        repaint();
    }//GEN-LAST:event_centerYSliderKeyReleased

    private void openMenuActionPerformed(ActionEvent evt) {//GEN-FIRST:event_openMenuActionPerformed
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            imageFile = fileChooser.getSelectedFile();
            open();
        }
    }//GEN-LAST:event_openMenuActionPerformed

    private void saveMenuActionPerformed(ActionEvent evt) {//GEN-FIRST:event_saveMenuActionPerformed
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            imageFile = fileChooser.getSelectedFile();
            try {
                if (imageFile.exists()) {
                    int result = JOptionPane.showConfirmDialog(this, imageFile.getName() + " already exists.\nDo you want to replace it?", "Confirm Save As", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        ImageIO.write(morphedImage, "png", imageFile);
                    }
                } else {
                    ImageIO.write(morphedImage, "png", imageFile);
                }
                repaint();
            } catch (Exception ex) {
                System.out.println("Image could not be rendered: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Image could not be saved!!!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_saveMenuActionPerformed

    private void openImageMenuActionPerformed(ActionEvent evt) {//GEN-FIRST:event_openImageMenuActionPerformed
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(imageFile);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Could not display the image, probably because no image has been selected!!!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_openImageMenuActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JSlider centerXSlider;
    private JSlider centerYSlider;
    private JTextField heightField;
    private JPanel inputPanel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JMenu jMenu1;
    private JMenuBar jMenuBar1;
    private JMenuItem openImageMenu;
    private JMenuItem openMenu;
    private JPanel painter;
    private JTextField radiusField;
    private JMenuItem saveMenu;
    private JTextField spreadAngleField;
    private JTextField startAngleField;
    // End of variables declaration//GEN-END:variables

    class Painter extends JPanel {

        GradientPaint gradient;
        Graphics2D g2d;

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            gradient = new GradientPaint(0, 0, new Color(-3546625), 0, 0.7F * getHeight(), Color.WHITE, true);
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            if (STATE == DROP_STATE) {
                g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, new float[]{10}, 0));
                g2d.setColor(Color.DARK_GRAY);
                int dropWidth = 200;
                g2d.draw(new RoundRectangle2D.Double((getWidth() - dropWidth) / 2, (getHeight() - dropWidth) / 2, dropWidth, dropWidth, 50, 50));
                g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
                g2d.drawString("Drop Image", getWidth() / 2 - 60, getHeight() / 2);
            } else if (STATE == RENDER_STATE) {
                render();
//                g.setColor(Color.BLACK);
//                g.drawString(imageFile.getName(), 2, smallImage.getHeight() + 15);
//                g.drawImage(smallImage, 0, 0, null);
                g2d.drawImage(morphedImage, 0, 0, null);
            }
        }
    }

    class ImageTransferHandler extends TransferHandler {

        @Override
        public boolean canImport(TransferSupport support) {
            if (!support.isDrop()) {
                return false;
            }
            if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                return false;
            }
            boolean copySupported = (COPY & support.getSourceDropActions()) == COPY;
            if (copySupported) {
                support.setDropAction(COPY);
                return true;
            }
            return false;
        }

        @Override
        public boolean importData(TransferSupport support) {
            if (!support.isDrop()) {
                return false;
            }
            if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                return false;
            }

            Transferable t = support.getTransferable();
            try {
                Object data = t.getTransferData(DataFlavor.javaFileListFlavor);
                imageFile = ((java.util.List<File>) data).get(0);
                fileChooser.setCurrentDirectory(imageFile);
                System.out.println(imageFile);
                open();
            } catch (Exception e) {
                System.out.println("Error while reading Image File: " + e);
            }
            return true;
        }
    }
}