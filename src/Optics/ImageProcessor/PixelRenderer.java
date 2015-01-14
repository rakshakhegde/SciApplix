package Optics.ImageProcessor;

import java.awt.image.BufferedImage;

/**
 * @author Rakshak R.Hegde
 */
public abstract class PixelRenderer {

    public int srcWidth, srcHeight;
    public int dstWidth, dstHeight;
    public int background = 0;

    public abstract void renderInverse(int[] inPixels, int[] outPixels);

    public void render(BufferedImage src, BufferedImage dst) {
        if (dst == null) {
            throw new IllegalArgumentException("Destination cannot be null.");
        }
        srcWidth = src.getWidth();
        srcHeight = src.getHeight();
        dstWidth = dst.getWidth();
        dstHeight = dst.getHeight();
        int inPixels[] = getRGB(src, 0, 0, srcWidth, srcHeight, null);
        int outPixels[] = new int[dstWidth * dstHeight];
        int out[] = new int[3];
        int x, y;
        /*for (y = 0; y < dstHeight; y++) {
            for (x = 0; x < dstWidth; x++) {
                renderInverse(x, y, out, inPixels);
                if (out[0] > -1 && out[0] < srcWidth && out[1] > -1 && out[1] < srcHeight) {
                    outPixels[x] = out[2];
                } else {
                    outPixels[x] = background;
                }
            }
            setRGB(dst, 0, y, dstWidth, 1, null);
        }*/
        renderInverse(inPixels, outPixels);
        setRGB(dst, 0, 0, dstWidth, dstHeight, outPixels);
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
}
