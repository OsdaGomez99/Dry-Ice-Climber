/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dry.ice.climber;

import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 *
 * @author jlhota16
 */
public class DryIceClimber {
    
    private JFrame gameFrame;
    private GameSurface s;
    
    public DryIceClimber() {
        gameFrame = new JFrame("Dry Ice Climber");
        gameFrame.setVisible(true);
        gameFrame.setSize(640, 480);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        s = new GameSurface();
        s.addKeyListener(new InputListener());
        gameFrame.add(s);
    }
    
    public void go() {
        
    }    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new DryIceClimber().go();
    }
    
    private class InputListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            
        }
        
        @Override
        public void keyTyped(KeyEvent e) {
            
        }
    }
}
