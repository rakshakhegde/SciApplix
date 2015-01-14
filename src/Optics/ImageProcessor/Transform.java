package Optics.ImageProcessor;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import static java.lang.Math.*;

/**
 * @author Rakshak R.Hegde
 */
public abstract class Transform {

    int srcWidth, srcHeight;
    int dstWidth, dstHeight;
    private int background = 0;

    public abstract void transformInverse(int x, int y, float out[]);

    public void transform(BufferedImage src, BufferedImage dst) {
        srcWidth = src.getWidth();
        srcHeight = src.getHeight();
        dstWidth = dst.getWidth();
        dstHeight = dst.getHeight();
        int inPixels[] = getRGB(src, 0, 0, srcWidth, srcHeight, null);
        int outPixels[] = new int[dstWidth];
        float in[] = new float[2];
        int srcX, srcY, x, y;
        for (y = 0; y < dstHeight; y++) {
            for (x = 0; x < dstWidth; x++) {
                transformInverse(x, y, in);
                srcX = round(in[0]);
                srcY = round(in[1]);
                if (srcX > -1 && srcX < srcWidth && srcY > -1 && srcY < srcHeight) {
                    outPixels[x] = inPixels[srcWidth * srcY + srcX];
                } else {
                    outPixels[x] = background;
                }
            }
            setRGB(dst, 0, y, dstWidth, 1, outPixels);
        }
    }

    /**
     * Distance between two points, in three dimensions, is calculated.
     *
     * @param x1 x coordinate of the starting point
     * @param y1 y coordinate of the starting point
     * @param z1 z coordinate of the starting point
     * @param x2 x coordinate of the ending point
     * @param y2 y coordinate of the ending point
     * @param z2 z coordinate of the ending point
     * @return Returns the linear distance between (x1, y1, z1) and (x2, y2, z2)
     */
    public float dist(float x1, float y1, float x2, float y2) {
        return (float) sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2));
    }

    /**
     * A convenience method for getting ARGB pixels from an image. This tries to
     * avoid the performance penalty of BufferedImage.getRGB unmanaging the
     * image.
     *
     * @param image a BufferedImage object
     * @param x the left edge of the pixel block
     * @param y the right edge of the pixel block
     * @param width the width of the pixel array
     * @param height the height of the pixel array
     * @param pixels the array to hold the returned pixels. May be null.
     * @return the pixels
     * @see #setRGB
     */
    public int[] getRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
        int type = image.getType();
        if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB) {
            return (int[]) image.getRaster().getDataElements(x, y, width, height, pixels);
        }
        return image.getRGB(x, y, width, height, pixels, 0, width);
    }

    public final void setBackground(int color) {
        background = color;
    }

    /**
     * A convenience method for setting ARGB pixels in an image. This tries to
     * avoid the performance penalty of BufferedImage.setRGB unmanaging the
     * image.
     *
     * @param image a BufferedImage object
     * @param x the left edge of the pixel block
     * @param y the right edge of the pixel block
     * @param width the width of the pixel array
     * @param height the height of the pixel array
     * @param pixels the array of pixels to set
     * @see #getRGB
     */
    public void setRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
        int type = image.getType();
        if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB) {
            image.getRaster().setDataElements(x, y, width, height, pixels);
        } else {
            image.setRGB(x, y, width, height, pixels, 0, width);
        }
    }

    /**
     * map() linearly maps a value from a given range to another given range. It
     * takes a source value (val) and then linearly maps that value from a range
     * low1→high1 to low2→high2. Suppose the source value is out of the given
     * source range, the method still linearly gives an output that is out of
     * the destination range.
     *
     * @param val The value to be mapped
     * @param low1 Lower limit of the source range
     * @param high1 Upper limit of the source range
     * @param low2 Lower limit of the destination range
     * @param high2 Upper limit of the destination range
     * @return Returns the mapped value of the val from the source range to
     * destination range.
     */
    public float map(float val, float low1, float high1, float low2, float high2) {
        float norm = (val - low1) / (high1 - low1);
        float lerp = norm * (high2 - low2) + low2;
        return lerp;
    }
    private int n;

    /**
     * Return a mod b. This differs from the % operator with respect to negative
     * numbers.
     *
     * @param a the dividend
     * @param b the divisor
     * @return a mod b
     */
    public float mod(float a, float b) {
        n = (int) (a / b);
        a -= n * b;
        if (a < 0) {
            return a + b;
        }
        return a;
    }

    public BufferedImage manageImage(BufferedImage src) {
        int type = src.getType();
        if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB) {
            return src;
        }
        BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dst.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(src, 0, 0, null);
        g2d.dispose();
        return dst;
    }

    public BufferedImage resizeFixed(BufferedImage srcImg, int width) {
        float ratio = (float) srcImg.getHeight() / srcImg.getWidth();
        int h = (int) (ratio * width);
        return resize(srcImg, width, h);
    }

    public BufferedImage resize(BufferedImage srcImg, int width, int height) {
        BufferedImage destImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = destImg.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(srcImg, 0, 0, width, height, null);
        g2d.dispose();
        return destImg;
    }
}