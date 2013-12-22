/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dry.ice.climber;

import java.awt.Graphics2D;
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author jlhota16
 */
public class DryIceClimber {
    
    public static final int SCREEN_WIDTH = 640, SCREEN_HEIGHT = 480;
    
    private JFrame gameFrame;
    private GameSurface s;
    
    private Climber a, b;
    
    public DryIceClimber() {
        
    }
    
    public void go() {
        gameFrame = new JFrame("Dry Ice Climber");
        gameFrame.setVisible(true);
        gameFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        s = new GameSurface(this);
        s.addKeyListener(new InputListener());
        gameFrame.add(s);
        
        a = new Climber(SCREEN_WIDTH/2, SCREEN_HEIGHT-20);
        b = new Climber(SCREEN_WIDTH/2, SCREEN_HEIGHT-20);
        
        Timer t = new Timer();
        t.scheduleAtFixedRate(new GameUpdater(), 1000/30, 10);
    }    
    
    public void paintGameObjects(Graphics2D g) {
        a.paint(g);
        b.paint(g);
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
    
    private class GameUpdater extends TimerTask {
        @Override
        public void run() {
            s.repaint();
        }
    }
}
