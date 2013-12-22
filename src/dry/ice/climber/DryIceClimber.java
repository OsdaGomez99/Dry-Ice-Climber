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
        gameFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        s = new GameSurface(this);
        gameFrame.add(s);
        gameFrame.addKeyListener(new InputListener());
        
        a = new Climber(30, 50);
        b = new Climber(90, 50);
        
        gameFrame.setVisible(true);
        
        Timer t = new Timer();
        t.scheduleAtFixedRate(new GameUpdater(), 100, 1000/30);
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
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_UP) {
                a.setVY(-5);
            }
            else if(key == KeyEvent.VK_DOWN) {
                a.setVY(5);
            }
            if(key == KeyEvent.VK_LEFT) {
                a.setVX(-5);
            }
            else if(key == KeyEvent.VK_RIGHT) {
                a.setVX(5);
            }
            
            if(key == KeyEvent.VK_W) {
                b.setVY(-5);
            }
            else if(key == KeyEvent.VK_S) {
                b.setVY(5);
            }
            if(key == KeyEvent.VK_A) {
                b.setVX(-5);
            }
            else if(key == KeyEvent.VK_D) {
                b.setVX(5);
            }
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
