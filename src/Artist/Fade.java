package Artist;

import javax.swing.JComponent;

/**
 * @author Rakshak R.Hegde
 */
public class Fade extends Animation {

    JComponent component;

    public Fade(JComponent comp) {
        component = comp;
        component.setOpaque(false);
        
        timer.start();
    }

    @Override
    public void render() {
    }
}
