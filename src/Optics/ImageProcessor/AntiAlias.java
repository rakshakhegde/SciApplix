package Optics.ImageProcessor;

/**
 * @author Rakshak R.Hegde
 */
public class AntiAlias extends PixelRenderer {

    public int width = 600;
    public int height = 400;
    int[] inPixels, outPixels;
    int x, y;
    int argb1, a1, r1, g1, b1;
    int argb2, a2, r2, g2, b2;
    float mixValue = 0.1F;

    @Override
    public void renderInverse(int[] inPixels, int[] outPixels) {
        this.inPixels = inPixels;
        this.outPixels = outPixels;
        int srcX, srcY;
        for (y = 1; y < dstHeight - 1; y++) {
            for (x = 1; x < dstWidth - 1; x++) {
                srcX = (int) map(x, 0, width, 0, srcWidth);
                srcY = (int) map(y, 0, height, 0, srcHeight);
                if (srcX - 1 > 0 && srcY - 1 > 0 && srcX + 1 < srcWidth - 1 && srcY + 1 < srcHeight - 1) {
                    mixColors(srcX, srcY, srcX - 1, srcY);
                    mixColors(srcX, srcY, srcX + 1, srcY);
                    mixColors(srcX, srcY, srcX, srcY - 1);
                    mixColors(srcX, srcY, srcX, srcY + 1);

                    mixColors(srcX, srcY, srcX + 1, srcY + 1);
                    mixColors(srcX, srcY, srcX + 1, srcY - 1);
                    mixColors(srcX, srcY, srcX - 1, srcY - 1);
                    mixColors(srcX, srcY, srcX - 1, srcY + 1);
                }
            }
        }
    }

    public void mixColors(int x1, int y1, int x2, int y2) {
        argb1 = inPixels[y1 * srcWidth + x1];
        a1 = (argb1) & 0xFF000000;
        r1 = (argb1 >> 16) & 0xFF;
        g1 = (argb1 >> 8) & 0xFF;
        b1 = argb1 & 0xFF;

        argb2 = inPixels[y2 * srcWidth + x2];
        a2 = argb2 & 0xFF000000;
        r2 = (argb2 >> 16) & 0xFF;
        g2 = (argb2 >> 8) & 0xFF;
        b2 = argb2 & 0xFF;

        a1 = (int) ((a1+a2) * mixValue);
        r1 = (int) ((r1+r2) * mixValue);
        g1 = (int) ((g1+g2) * mixValue);
        b1 = (int) ((b1+b2) * mixValue);

        outPixels[y * dstWidth + x] = a1 | r1 << 16 | g1 << 8 | b1;
    }
}
