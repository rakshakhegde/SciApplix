package Artist;

/**
 * @author Rakshak R.Hegde
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import static java.lang.Math.*;

/**
 * Artist simplifies creation of animations heavily. You simply have to override
 * a method draw(Graphics2D g) in your favorite class and Artist keeps calling
 * that method regularly with 33 fps as the default frame-rate and then you draw
 * almost anything on to the screen using the Graphics2D object g. Here's a
 * simple sample program where a circle goes in circles and halts for a second
 * every time it completes a rotation.
 */
public abstract class Artist extends JFrame {

    /**
     * Holds whether the key is being pressed or not.
     */
    public boolean keyPressed;
    /**
     * A boolean indicating whether the "engine" called draw() or the system
     * called it.
     */
    public boolean engineCalled;
    /**
     * Holds the character that has been pressed the last time.
     */
    public char keyChar;
    /**
     * Holds the key code that has been pressed the last time.
     */
    public int keyCode;
    /**
     * Holds the previous x-coordinate of the mouse position with respect to the
     * window. For proper results, do not modify this value.
     */
    public int pmouseX;
    /**
     * Holds the previous y-coordinate of the mouse position with respect to the
     * window. For proper results, do not modify this value.
     */
    public int pmouseY;
    /**
     * Holds the x-coordinate of the mouse position with respect to the window.
     * For proper results, do not modify this value.
     */
    public int mouseX;
    /**
     * Holds the y-coordinate of the mouse position with respect to the window.
     * For proper results, do not modify this value.
     */
    public int mouseY;
    /**
     * Width of the rendering panel in pixels.
     */
    public int width;
    /**
     * Height of the rendering panel in pixels.
     */
    public int height;
    /**
     * Framerate
     */
    public double frameRate;
    /**
     * Holds the last key strokes and many other functions.
     */
    public KeyEvent ke;
    /**
     * Holds the current mouse coordinates and many other functions.
     */
    public MouseEvent me;
    /**
     * Holds the information about the mouse wheel motion.
     */
    public MouseWheelEvent mwe;
    /**
     * Holds the number of milliseconds passed from 1 January, 1970 to the start
     * of the application.
     */
    private long beginMillis;
    /**
     * Holds whether the graphics should be anti-aliased or not.
     */
    private boolean antialias = true;
    /**
     * Holds whether the window is in fullscreen mode or not.
     */
    private boolean fullscreen;
    /**
     * Artist reference is used in mouseListener() and mouseMotionListener() to
     * call the class methods.
     */
    private Artist artist = this;
    /**
     * A canvas upon which any shape can be drawn.
     */
    private Canvas canvas;
    /**
     * A Graphics2D object using which any shape can be drawn.
     */
    private Graphics2D g2d;
    /**
     * Used to regularly repaint the canvas.
     */
    private Timer timer;

    /**
     * The method that will be called at regular intervals. By default the delay
     * is 30 milliseconds.
     *
     * @param g Graphics2D object which is used to draw shapes
     */
    abstract public void draw(Graphics2D g);

    public Artist() {
        canvas = new Canvas();
        canvas.setBackground(Color.WHITE);
        canvas.setDoubleBuffered(true);
        add(canvas);

        requestFocus();
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(ke);
                ke = e;
                keyChar = e.getKeyChar();
                keyCode = e.getKeyCode();
                keyPressed = true;
                if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    kill();
                } else if (ke.getKeyCode() == KeyEvent.VK_F) {
//                    fullscreen(!fullscreen);
                }
                artist.keyPressed();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                ke = e;
                keyChar = e.getKeyChar();
                keyPressed = false;
                artist.keyReleased();
            }
        });

        canvas.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                me = e;
                artist.mouseClicked();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                me = e;
                artist.mouseEntered();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                me = e;
                artist.mouseExited();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                me = e;
                artist.mousePressed();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                me = e;
                artist.mouseReleased();
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
                mwe = e;
                artist.mouseWheelMoved();
            }
        });

        canvas.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                me = e;
                artist.mouseDragged();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                me = e;
                artist.mouseMoved();
            }
        });

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowStateChanged(WindowEvent e) {
                super.windowStateChanged(e);
            }
            
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Artist.this.windowClosing();
                kill();
            }
        });

        frameRate = 30;
        timer = new Timer((int) (1000 / frameRate), new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                engineCalled = true;
                repaint();
            }
        });
        beginMillis = System.currentTimeMillis();

        setTitle("Artist");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        width = 600;
        height = 600;
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);

        timer.start();
    }

    /**
     * antialias() sets whether the graphics should be anti-aliased or not.
     *
     * @param ON If true anti-alias is activated or else deactivated
     */
    public final void antialias(boolean ON) {
        antialias = ON;
        if (g2d != null) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, ON ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        }
    }

    /**
     * Anti-aliases the Graphics2D g2d object based on the value of ON.
     *
     * @param ON If true anti-alias is activated or else deactivated.
     */
    public final void antialias(boolean ON, Graphics2D g2d) {
        if (g2d != null) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, ON ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        }
    }

    /**
     * Sets the background color.
     *
     * @param color The color of the background
     */
    public final void background(Color color) {
        canvas.setBackground(color);
    }

    /**
     * Sets the background color with gray scale 'grayscale'.
     *
     * @param grayscale Red Value 0 ≤ grayscale ≤ 255
     */
    public final void background(int grayscale) {
        canvas.setBackground(new Color(grayscale, grayscale, grayscale));
    }

    /**
     * Sets the background color with red r, blue b and green g.
     *
     * @param r Red Value 0 ≤ r ≤ 255
     * @param g Green Value 0 ≤ g ≤ 255
     * @param b Blue Value 0 ≤ b ≤ 255
     */
    public final void background(int r, int g, int b) {
        canvas.setBackground(new Color(r, g, b));
    }

    /**
     * Sets the border for the canvas
     *
     * @param border A Border instance that is to be used for the canvas
     */
    public void border(Border border) {
        canvas.setBorder(border);
    }

    /**
     * Delays the call to draw() for the specified millis milliseconds and then
     * continues to call draw() again with the same earlier frame-rate.
     *
     * @param millis Number of milliseconds to pause and then continue
     */
    public final void delay(int millis) {
//        if (timer.isRunning()) {
//            timer.setInitialDelay(millis);
//            timer.restart();
//        }

        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            println(e);
        }
    }

    /**
     * Distance between two points, in two dimensions, is calculated.
     *
     * @param x1 x coordinate of the starting point
     * @param y1 y coordinate of the starting point
     * @param x2 x coordinate of the ending point
     * @param y2 y coordinate of the ending point
     * @return Returns the linear distance between (x1, y1) and (x2, y2)
     */
    public static final double dist(double x1, double y1, double x2, double y2) {
        return hypot(x2 - x1, y2 - y1);
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
    public static final double dist(double x1, double y1, double z1, double x2, double y2, double z2) {
        return sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2) + pow(z2 - z1, 2));
    }

    /**
     * Sets the number of frames displayed per second. Can also be stated as the
     * number of times the method draw() is called per second. Throws an
     * IllegalArgumentException if 'frames' is less than or equal to zero and
     * the frame-rate is unaltered.
     *
     * @param frames The number of frames to be displayed per second
     */
    public final void frameRate(double frames) {
        if (frames <= 0) {
            throw new IllegalArgumentException("Frames per second cannot be less than equal to zero!!!");
        } else {
            frameRate = frames;
            timer.setDelay((int) (1000.0 / frames));
        }
    }

    /**
     * Sets the window to fullscreen.
     *
     * @param ON If true sets the window to fullscreen else makes it a windowed
     * screen
     */
    public final void fullscreen(boolean ON) {
        if (ON) {
//            setVisible(true);
            setUndecorated(true);
            setResizable(false);
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
            invalidate();
        } else {
            setUndecorated(false);
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
        }
        fullscreen = ON;
    }

    /**
     * Tells whether draw() is successively being called or not.
     *
     * @return A boolean which indicates if the "engine" is running or not.
     */
    public boolean isRunning() {
        return timer.isRunning();
    }

    public void keyPressed() {
    }

    public void keyReleased() {
    }

    /**
     * Clears all the resources, closes the window and exits the application
     */
    public final void kill() {
        timer.stop();
        dispose();
        System.gc();
    }

    /**
     * Starts calling draw().
     */
    public final void loop() {
        timer.start();
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
    public double map(double val, double low1, double high1, double low2, double high2) {
        double norm = (val - low1) / (high1 - low1);
        double lerp = norm * (high2 - low2) + low2;
        return lerp;
    }

    /**
     * Calculates the number of milliseconds that has elapsed from the beginning
     * of the application.
     *
     * @return The number of millis elapsed from the beginning of the app.
     */
    public final long millis() {
        return System.currentTimeMillis() - beginMillis;
    }

    public void mouseClicked() {
    }

    public void mouseDragged() {
    }

    public void mouseEntered() {
    }

    public void mouseExited() {
    }

    public void mousePressed() {
    }

    public void mouseMoved() {
    }

    public void mouseReleased() {
    }

    public void mouseWheelMoved() {
    }

    /**
     * Stops calling draw() therefore animation is paused.
     */
    public final void noLoop() {
        timer.stop();
    }

    /**
     * Draws a point at the coordinate (x, y).
     */
    public void point(int x, int y) {
        g2d.drawRect(x, y, 1, 1);
    }

    /**
     * Prints a message to the console (similar to System.out.print()).
     *
     * @param msg The message to be printed to the console
     */
    public final void print(Object msg) {
        System.out.print(msg);
    }

    /**
     * Prints a message to the console (similar to System.out.println()).
     *
     * @param msg The message to be printed to the console
     */
    public final void println(Object msg) {
        System.out.println(msg);
    }

    /**
     * Descriptive information of the class Artist.
     *
     * @return Descriptive information of the class Artist
     */
    @Override
    public String toString() {
        return "Artist: A rapid prototype machine";
    }
    
    public void windowClosing() {}

    class Canvas extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (me != null) {
                pmouseX = mouseX;
                pmouseY = mouseY;
                mouseX = me.getX();
                mouseY = me.getY();
            }
            width = getWidth();
            height = getHeight();
            g2d = (Graphics2D) g;
            antialias(antialias);
            draw(g2d);
            engineCalled = false;
        }
    }
}
