package SciApplix;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.*;

/**
 * @author Rakshak.R.Hegde
 */
public class MainMenu extends JFrame {

    int i, j;
    AboutDialog aboutDialog;
    File applixPropsFile;
    DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode("Apps"), childNode;
    TreePath treePath;
    Vector<String> categoryVec = new Vector<>(), classNamesVec = new Vector<>();
    Color whiteColor = new Color(252, 252, 252);
    String category, className, linkStr = "http://mathworld.wolfram.com/Epicycloid.html";
    String UIName = "";
    JLabel linkLabel;
    public static Properties applixProps;
    int x = 100, y = 100;

    public MainMenu() {
        checkFolderExistence();
        setUI();
        java.net.URL imgURL = getClass().getResource("/META-INF/splashscreen.png");
        if (imgURL != null) {
            try {
                setIconImage(ImageIO.read(imgURL));
            } catch (Exception ex) {
	            ex.printStackTrace();
	            System.out.println("Error in MainMenu() while loading icon!");
            }
        }
        treeNodeSetup();
        initComponents();
        setVisible(true);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (UIName != null) {
                    applixProps.setProperty("UI Name", UIName);
                }
                try {
                    Date date = new Date();
                    applixProps.storeToXML(new FileOutputStream(applixPropsFile), "Do not Modify this file, else unexpected behaviour in software will be detected. Delete this file if unexpected behaviour in SciApplix is seen.\n" + EntryClass.APP_NAME + " is created by " + EntryClass.CREATORS + ".\nInformation saved on " + date + ".");
                } catch (Exception ex) {
                    System.out.println("Properties could not be saved to XML: " + ex);
                }
            }
        });
    }

    private void checkFolderExistence() {
        applixProps = new Properties();
        try {
            EntryClass.defaultDIR.mkdir();
            applixPropsFile = new File(EntryClass.defaultDIR, EntryClass.APPLIX_PROPERTIES_FILE);
            applixProps.loadFromXML(new FileInputStream(applixPropsFile));
            UIName = applixProps.getProperty("UI Name");
        } catch (Exception ex) {
            UIName = "Nimbus";
            System.out.println("Error in loading properties from XML: " + ex);
        }
    }

    private void setUI() {
        // Set the Look And Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (UIName.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                }
            }
        } catch (Exception ex) {
        }

        // Set the Web LAF
//        try {
        // Setting up WebLookAndFeel style
//            UIManager.setLookAndFeel ( WebLookAndFeel.class.getCanonicalName () );
//        } catch (Exception e ){}
    }

    private void initComponents() {
        programsScrollPane = new JScrollPane();
        programsTree = new JTree(treeNode);
        descScrollPane = new JScrollPane();
        descTextPane = new JTextPane();
        jMenuBar = new JMenuBar();
        helpMenu = new JMenu();
        aboutMenuItem = new JMenuItem();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(EntryClass.APP_NAME);
        setBackground(whiteColor);

        programsTree.setBackground(whiteColor);
        TitledBorder border = BorderFactory.createTitledBorder(null, "Programs", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font(Font.SANS_SERIF, Font.PLAIN, 13), new Color(100, 100, 100));
        programsTree.setBorder(border);
        programsTree.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        programsTree.setAutoscrolls(true);
        programsTree.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent evt) {
                treePath = programsTree.getPathForLocation(evt.getX(), evt.getY());
                if (treePath != null && treePath.getPathCount() == 3) {
                    Object paths[] = treePath.getPath();
                    category = paths[1] + "";
                    className = paths[2] + "";
                    setDescription();
                    if (evt.getClickCount() > 1) {
                        ClassPlayer.loadClass(category, className);
                    }
                }
            }
        });
        programsTree.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    treePath = programsTree.getSelectionPath();
                    if (treePath != null && treePath.getPathCount() == 3) {
                        Object paths[] = treePath.getPath();
                        ClassPlayer.loadClass(paths[1] + "", paths[2] + "");
                    }
                }
            }
        });
        programsScrollPane.setViewportView(programsTree);

        descTextPane.setEditable(false);
        descTextPane.setEditorKit(new HTMLEditorKit());
        descTextPane.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        descTextPane.setText("Single Click on any app to get a description....<br>"
                + "Double Click or press enter to launch it.<br>"
                + "Feel free to use the Instasearch bar. Type in the name of the program of your choice and single click to view the app description else double click or press enter to launch it.");
        border = BorderFactory.createTitledBorder(null, "Description", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font(Font.SANS_SERIF, Font.PLAIN, 13), new Color(100, 100, 100));
        descTextPane.setBorder(border);
        descScrollPane.setViewportView(descTextPane);

        JPanel searchPanel = createSearchPanel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, searchPanel, programsScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);

        final JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane, descScrollPane);
        splitPane2.setOneTouchExpandable(true);
        splitPane2.setDividerLocation(310);
        JPanel completePanel = new JPanel(new BorderLayout());
        completePanel.add(splitPane2, BorderLayout.CENTER);
        completePanel.setBackground(new Color(240, 240, 240));
        add(completePanel);

        JMenu userInterf = new JMenu("User Interface");
        ActionListener UIActionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UIName = e.getActionCommand();
                JOptionPane.showMessageDialog(MainMenu.this, UIName + " user interface will be used next time you open Science Applix.", "Notice", JOptionPane.PLAIN_MESSAGE);
            }
        };
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                JMenuItem menuItem = new JMenuItem(info.getName());
                menuItem.addActionListener(UIActionListener);
                userInterf.add(menuItem);
            }
        } catch (Exception ex) {
        }
        jMenuBar.add(userInterf);

        helpMenu.setText("Help");

        aboutMenuItem.setText("About SciApplix");
        aboutMenuItem.setToolTipText("Info about the Creators");
        aboutMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                if (aboutDialog == null) {
                    aboutDialog = new AboutDialog();
                }
                aboutDialog.setVisible(true);
            }
        });
        helpMenu.add(aboutMenuItem);

        jMenuBar.add(helpMenu);

        setJMenuBar(jMenuBar);

        setSize(950, 700);
    }

    public static void main(String args[]) {
        new MainMenu();
    }
    // Variables declaration - do not modify
    private JMenuItem aboutMenuItem;
    private JTextPane descTextPane;
    private JMenu helpMenu;
    private JMenuBar jMenuBar;
    private JScrollPane programsScrollPane, descScrollPane;
    private JTree programsTree;
    // End of variables declaration

    private void treeNodeSetup() {
        //Sorting the ClassPlayer's classes
        int topicLength = ClassPlayer.classes.length, demoLength;
        for (int i = 0, j; i < topicLength; i++) {
            childNode = new DefaultMutableTreeNode(ClassPlayer.classes[i][0]);
            demoLength = ClassPlayer.classes[i].length;
            for (j = 1; j < demoLength; j++) {
                childNode.add(new DefaultMutableTreeNode(ClassPlayer.classes[i][j]));
            }
            treeNode.add(childNode);
        }
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(), panel1 = new JPanel();
        searchPanel.setBackground(whiteColor);
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder(null, "Instasearch", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font(Font.SANS_SERIF, Font.PLAIN, 13), new Color(100, 100, 100)));
        panel1.setBackground(whiteColor);
        final JTextField searchField = new JTextField();
        panel1.add(searchField);
        searchPanel.add(searchField, BorderLayout.NORTH);
        searchClasses("");
        final JList list = new JList(classNamesVec);
        list.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        list.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        list.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        searchPanel.add(scrollPane, BorderLayout.CENTER);
        searchField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    list.setSelectedIndex(0);
                    list.requestFocus();
                } else {
                    searchClasses(searchField.getText());
                    list.setListData(classNamesVec);
                }
            }
        });
        list.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (!list.isSelectionEmpty()) {
                    i = list.getSelectedIndex();
                    category = categoryVec.get(i) + "";
                    className = classNamesVec.get(i) + "";
                    setDescription();

                    if (e.getClickCount() > 1) {
                        ClassPlayer.loadClass(category, className);
                    }
                }
            }
        });
        list.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER && !list.isSelectionEmpty()) {
                    i = list.getSelectedIndex();
                    ClassPlayer.loadClass(categoryVec.get(i) + "", classNamesVec.get(i) + "");
                }
            }
        });
        return searchPanel;
    }

    private void searchClasses(String searchElement) {
        categoryVec.clear();
        classNamesVec.clear();
        for (i = 0; i < ClassPlayer.classes.length; i++) {
            for (j = 1; j < ClassPlayer.classes[i].length; j++) {
                if (ClassPlayer.classes[i][j].toLowerCase().contains(searchElement.toLowerCase())) {
                    categoryVec.addElement(ClassPlayer.classes[i][0]);
                    classNamesVec.addElement(ClassPlayer.classes[i][j]);
                }
            }
        }
    }

    public void setDescription() {
        java.net.URL URL = getClass().getResource("/Help Files/" + className + ".html");
        descTextPane.setText(ClassPlayer.loadInfo(URL));
        if (className.contentEquals(ClassPlayer.classes[4][2])) {
            if (linkLabel == null) {
                linkLabel = new JLabel(linkStr);
                linkLabel.setForeground(Color.BLUE);
                linkLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
                linkLabel.addMouseListener(new MouseAdapter() {

                    Desktop desktop;

                    @Override
                    public void mousePressed(MouseEvent me) {
                        if (Desktop.isDesktopSupported()) {
                            if (desktop == null) {
                                desktop = Desktop.getDesktop();
                            }
                            try {
                                desktop.browse(new URI(linkStr));
                            } catch (Exception exc) {
                            }
                        }
                    }
                });
            }
            descTextPane.insertComponent(linkLabel);
        }
    }
}