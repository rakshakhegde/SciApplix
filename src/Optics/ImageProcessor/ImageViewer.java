package Optics.ImageProcessor;

import Artist.Artist;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Rakshak.R.Hegde
 */
public class ImageViewer extends Artist {

    BufferedImage src, image;
    WaveTransform filter = new WaveTransform();
    long begin;

    public ImageViewer() {
        frameRate(10);
        setTitle("Image Viewer");
        try {
            File file = new File("C:/Users/Rakshak.R.Hegde/Pictures/Amazing Spiderman/Amazing Spiderman.jpg");
            src = ImageIO.read(file);
            int type = src.getType();
            if (type != BufferedImage.TYPE_INT_ARGB && type != BufferedImage.TYPE_INT_RGB) {
                src = filter.resize(src, src.getWidth(), src.getHeight());
            }
            filter.width = src.getWidth();
            filter.height = src.getHeight();
            setSize(src.getWidth() + 40, src.getHeight() + 50);
            image = new BufferedImage(src.getWidth() + 20, src.getHeight() + 20, BufferedImage.TYPE_INT_ARGB);
        } catch (Exception e) {
            println("Error in image loading: " + e);
        }
        System.out.println("Time taken is " + (millis() - begin) + " millis.");
        setLocationRelativeTo(null);
    }

    @Override
    public void draw(Graphics2D g) {
        begin = millis();
        try {
            filter.t = millis();
            filter.transform(src, image);
        } catch (Exception e) {
            println("Error while drawing: " + e);
        }
        g.drawImage(image, 0, 0, null);
        println("Time taken is " + (millis() - begin) + " millis.");
    }

    public static void main(String[] args) {
        new ImageViewer();
    }
}
