package Optics.ImageProcessor;

import java.awt.image.BufferedImage;
import static java.lang.Math.*;

/**
 * @author Rakshak R.Hegde
 */
public class CircleTransform extends Transform {

    public float startAngle = 0;
    public float spreadAngle = (float) PI;
    public float centerXRatio = 0.5f;
    public float centerYRatio = 0.5f;
    public float centerX;
    public float centerY;
    public float radius = 50;
    public float height = 200;
    private float dist;
    private float angle;

    @Override
    public void transform(BufferedImage src, BufferedImage dst) {
        centerX = dst.getWidth() * centerXRatio;
        centerY = dst.getHeight() * centerYRatio;
        super.transform(src, dst);
    }

    @Override
    public void transformInverse(int x, int y, float[] in) {
        dist = dist(centerX, centerY, x, y);
        if (dist >= radius && dist <= radius + height) {
            angle = (float) atan2(centerY - y, centerX - x) - startAngle;
            angle = mod(angle, (float) (2 * PI));
            in[0] = angle / spreadAngle * srcWidth;
            in[1] = map(dist, radius, radius + height, srcHeight, 0);
        } else {
            in[0] = -1;
            in[1] = -1;
        }
    }
}
