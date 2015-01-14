package Artist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.Timer;

/**
 * @author Rakshak R.Hegde
 */
public abstract class Animation {
    protected Timer timer;
    public abstract void render();
    public Animation() {
        ActionListener actionListener=new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                render();
            }
        };
        timer = new Timer(30, actionListener);
    }
}
