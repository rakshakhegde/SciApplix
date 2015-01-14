package Optics.ImageProcessor;

/**
 * @author Rakshak R.Hegde
 */
public class WaveTransform extends Transform {

    public float xAmp = 5;
    public float yAmp = 5;
    public float kX = 0.02F;
    public float kY = 0.02F;
    public float t = 0;
    public float omegaX = 0.01F;
    public float omegaY = 0.01F;
    public float width = 400;
    public float height = 500;

    @Override
    public void transformInverse(int x, int y, float in[]) {
        in[0] = (float) (x + xAmp * Math.sin(omegaX * t - kX * y));
        in[1] = (float) (y + yAmp * Math.sin(omegaY * t - kY * x));
        in[0] = in[0] / width * srcWidth;
        in[1] = in[1] / height * srcHeight;
    }
}
