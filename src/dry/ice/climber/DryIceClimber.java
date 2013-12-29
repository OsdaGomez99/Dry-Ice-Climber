/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dry.ice.climber;

import java.awt.Graphics2D;
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author jlhota16
 */
public class DryIceClimber {
    
    public static final int SCREEN_WIDTH = 640, SCREEN_HEIGHT = 480;
    
    private JFrame gameFrame;
    private GameSurface surface;
    
    private boolean up, down, left, right, w_key, a_key, s_key, d_key;
    
    private Climber a, b;
    private ArrayList<Platform> platforms;
    
    private boolean game;
    
    public DryIceClimber() {
        
    }
    
    public void go() {
        up = down = left = right = w_key = a_key = s_key = d_key = false;
        
        gameFrame = new JFrame("Dry Ice Climber");
        gameFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        surface = new GameSurface(this);
        gameFrame.add(surface);
        gameFrame.addKeyListener(new InputListener());
        
        a = new Climber(30, 50);
        b = new Climber(90, 50);
        platforms = new ArrayList<Platform>();
        Platform p = new Platform(50,200);
        platforms.add(p);
        for(int i = 0; i < SCREEN_WIDTH/Platform.DIMENSION; i++) {
            platforms.add(new Platform(i*Platform.DIMENSION, SCREEN_HEIGHT-Platform.DIMENSION));
        }
        
        gameFrame.setVisible(true);
        
        game = true;
        
        Timer t = new Timer();
        t.scheduleAtFixedRate(new GameUpdater(), 100, 1000/30);
    }    
    
    public void paintGameObjects(Graphics2D g) {
        a.paint(g);
        b.paint(g);
        for(Platform platform : platforms) {
            platform.paint(g);
        }
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
                a.setVY(-15);
            }
            else if(key == KeyEvent.VK_DOWN) {
                //a.setVY(5);
            }
            
            if(key == KeyEvent.VK_LEFT) {
                a.setVX(-5);
                left = true;
            }
            else if(key == KeyEvent.VK_RIGHT) {
                a.setVX(5);
                right = true;
            }
            
            if(key == KeyEvent.VK_W) {
                b.setVY(-5);
            }
            else if(key == KeyEvent.VK_S) {
                //b.setVY(5);
            }
            
            if(key == KeyEvent.VK_A) {
                b.setVX(-15);
                a_key = true;
            }
            else if(key == KeyEvent.VK_D) {
                b.setVX(5);
                d_key = true;
            }
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            
            if(key == KeyEvent.VK_LEFT) {
                left = false;
                if(!right) {
                    a.setVX(0);
                }
                else {
                    a.setVX(5);
                }
            }
            else if(key == KeyEvent.VK_RIGHT) {
                right = false;
                if(!left) {
                    a.setVX(0);
                }
                else {
                    a.setVX(-5);
                }
            }
            
            if(key == KeyEvent.VK_A) {
                a_key = false;
                if(!d_key) {
                    b.setVX(0);
                }
                else {
                    b.setVX(5);
                }
            }
            else if(key == KeyEvent.VK_D) {
                d_key = false;
                if(!a_key) {
                    b.setVX(0);
                }
                else {
                    b.setVX(-5);
                }
            }
        }
        
        @Override
        public void keyTyped(KeyEvent e) {
            
        }
    }
    
    private class GameUpdater extends TimerTask {
        @Override
        public void run() {
            if(a.y > SCREEN_HEIGHT + 10) {
                game = false; //Gameover
            }
            
            for(Platform platform : platforms) {
                a.checkCollisions(platform);
                b.checkCollisions(platform);
                
                if(platform.y > SCREEN_HEIGHT) {
                    platforms.remove(platform);
                }
            }
            
            a.updatePhysics();
            b.updatePhysics();
            surface.repaint();            
        }
    }
}
