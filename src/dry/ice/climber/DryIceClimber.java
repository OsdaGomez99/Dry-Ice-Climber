/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dry.ice.climber;

import java.awt.Color;
import java.awt.Font;
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
    
    public static final int SCREEN_WIDTH = 640, SCREEN_HEIGHT = 480, GRAV_ACC = 2, SCROLL_VEL = 1;
    
    private JFrame gameFrame;
    private GameSurface surface;
    
    private boolean up, down, left, right, w_key, a_key, s_key, d_key;
    
    private Climber a, b;
    private ArrayList<Platform> platforms, platformsToRemove;
    
    private Font displayFont;
    
    private int height;
    private boolean instructions, game;
    private boolean firstGame;
    
    public DryIceClimber() {
        firstGame = true;
    }
    
    public void go() {
        up = down = left = right = w_key = a_key = s_key = d_key = false;
        
        if(firstGame) {
            gameFrame = new JFrame("Dry Ice Climber");
            gameFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setResizable(false);

            surface = new GameSurface(this);
            gameFrame.add(surface);
            gameFrame.addKeyListener(new InputListener());
            displayFont = new Font("Comic Sans MS", Font.BOLD, 36);
        }
        
        a = new Climber("P1", 30, 50);
        b = new Climber("P2", 90, 50);
        platforms = new ArrayList<Platform>();
        platformsToRemove = new ArrayList<Platform>();
        Platform p = new Platform(this, 50,200);
        platforms.add(p);
        for(int i = 0; i < SCREEN_WIDTH/Platform.DIMENSION; i++) {
            if(Math.random()<0.5) {
                platforms.add(new Platform(this, i*Platform.DIMENSION, SCREEN_HEIGHT-Platform.DIMENSION));
            }
        }
        
        gameFrame.setVisible(true);
        height = 0;
        instructions = true;
        game = false;
        
        if(firstGame) {
            Timer t = new Timer();
            t.scheduleAtFixedRate(new GameUpdater(), 100, 1000/30);
        }
    }    
    
    public void paintGameObjects(Graphics2D g) {
        if(instructions) {
            g.setColor(Color.WHITE);
            g.setFont( displayFont );
            g.drawString("Player One Uses Arrow Keys.", 20, SCREEN_HEIGHT/4);
            g.drawString("Player Two Uses WASD.", 20, SCREEN_HEIGHT/2);
            g.drawString("Press any key to begin.", 20, 3*SCREEN_HEIGHT/4);
            return;
        }
        
        a.paint(g);
        b.paint(g);
        for(Platform platform : platforms) {
            platform.paint(g);
        }
        
        if(!game) {
            g.setColor(Color.WHITE);
            g.setFont( displayFont );
            g.drawString("GAME OVER", SCREEN_WIDTH/4, SCREEN_HEIGHT/4);
            String winner = a.y < b.y ? "Player One wins." : "Player Two wins.";
            if(a.y == b.y) winner = "Tie.";
            g.drawString(winner, SCREEN_WIDTH/4, SCREEN_HEIGHT/2);
            g.drawString("Press r to restart.", SCREEN_WIDTH/4, 3*SCREEN_HEIGHT/4);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new DryIceClimber().go();
    }
    
    public void removePlatform(Platform platform) {
        platformsToRemove.add(platform);
    }
    
    private class InputListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            if(instructions) {
                instructions = false;
                game = true;
                return;
            }
            
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
                b.setVY(-15);
            }
            else if(key == KeyEvent.VK_S) {
                //b.setVY(5);
            }
            
            if(key == KeyEvent.VK_A) {
                b.setVX(-5);
                a_key = true;
            }
            else if(key == KeyEvent.VK_D) {
                b.setVX(5);
                d_key = true;
            }
            
            if(!game && !instructions && key == KeyEvent.VK_R) {
                firstGame = false;
                go();
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
            if(!game) return;
            
            if(a.y > SCREEN_HEIGHT + 10 || b.y > SCREEN_HEIGHT + 10) {
                game = false; //Game over
            }
            
            a.y += SCROLL_VEL;
            b.y += SCROLL_VEL;
            
            for(Platform platform : platforms) {
                platform.y += SCROLL_VEL;
                
                a.checkCollisions(platform);
                b.checkCollisions(platform);
            }
            
            a.updatePhysics();
            b.updatePhysics();
            surface.repaint();
            
            for(Platform platform : platforms) {
                if(platform.y > SCREEN_HEIGHT) {
                    platformsToRemove.add(platform);
                }
            }
            
            platforms.removeAll(platformsToRemove);
            platformsToRemove.clear();
        }
    }
}