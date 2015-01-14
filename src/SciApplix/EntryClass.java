package SciApplix;

import java.io.File;
import javax.swing.filechooser.FileSystemView;

/**
 * @author Rakshak R.Hegde
 */
public class EntryClass {

    public static String APP_NAME = "SciApplix";
    public static String APP_NAME_CAPS = "SCIAPPLIX";
    public static String APP_DIR = "SciApplix";
    public static String APPLIX_PROPERTIES_FILE = "Applix Properties.xml";
    public static String CREATORS = "Rakshak R.Hegde";
    public static String VERSION = "Version 2.5.5";
    public static File defaultDIR = new File(FileSystemView.getFileSystemView().getDefaultDirectory(), "SciApplix");

    public static void main(String args[]) {
        // Make this defaultDIR hidden
    }
}
