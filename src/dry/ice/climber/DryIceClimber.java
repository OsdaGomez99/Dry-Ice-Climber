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
    
    public static final int SCREEN_WIDTH = 640, SCREEN_HEIGHT = 480,
            GRAV_ACC = 2, SCROLL_VEL = 1, PLAT_HEIGHT_DIST = (int) ((((int)Math.pow(Climber.JUMP_VEL, 2))/(GRAV_ACC*2)) * 1.0f);
    //15 is the jump velocity, 0.75 is a scaling factor to make jumping easier
    
    private JFrame gameFrame;
    private GameSurface surface;
    
    private boolean up, down, left, right, w_key, a_key, s_key, d_key;
    
    private Climber a, b;
    private ArrayList<Platform> platforms, platformsToRemove;
    private PowerUp power;
    
    private Font displayFont;
    
    private int height;
    private boolean instructions, game;
    private boolean firstGame;
    private boolean twoPlayer;
    
    public DryIceClimber() {
        firstGame = true;
        twoPlayer = true;
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
        if(twoPlayer) b = new Climber("P2", 90, 50);
        else b = null;
        platforms = new ArrayList<Platform>();
        platformsToRemove = new ArrayList<Platform>();
        Platform p = new Platform(this, 50,200);
        platforms.add(p);
        
        power = new PowerUp(SCREEN_HEIGHT/2, SCREEN_WIDTH/2, PowerUp.FLY);
        
        int maxJ = SCREEN_WIDTH/Platform.DIMENSION;
        for(int i = 0; i < SCREEN_HEIGHT/PLAT_HEIGHT_DIST; i++) {
            for(int j = 0; j < maxJ; j++) {
                if(i != j) {
                    platforms.add(new Platform(this, j*Platform.DIMENSION, (i)*PLAT_HEIGHT_DIST));
                }
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
            g.drawString("Press 1 for one player, 2 for two.", 20, 3*SCREEN_HEIGHT/4);
            return;
        }
        
        a.paint(g);
        if(twoPlayer) {
            b.paint(g);
        }
        
        synchronized(platforms) {
            for(Platform platform : platforms) {
                platform.paint(g);
            }
            power.paint(g);
        }
        
        if(!game) {
            g.setColor(Color.WHITE);
            g.setFont( displayFont );
            g.drawString("GAME OVER", SCREEN_WIDTH/4, SCREEN_HEIGHT/4);
            
            if(twoPlayer) {
                String winner = a.y < b.y ? "Player One wins." : "Player Two wins.";
                if(a.y == b.y) winner = "Tie.";
                g.drawString(winner, SCREEN_WIDTH/4, SCREEN_HEIGHT/2);
            }
            else {
                g.drawString("Score: " + (height*10), SCREEN_WIDTH/4, SCREEN_HEIGHT/2);
            }
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
            int key = e.getKeyCode();
            
            if(!game && !instructions && key == KeyEvent.VK_R) {
                firstGame = false;
                go();
                return;
            }
            
            if(instructions) {
                if(key == KeyEvent.VK_1) {
                    twoPlayer = false;
                    instructions = false;
                    game = true;
                }
                else if(key == KeyEvent.VK_2) {
                    twoPlayer = true;
                    instructions = false;
                    game = true;
                }
                return;
            }
            
            if(key == KeyEvent.VK_UP) {
                a.setVY(-1);
                up = true;
            }
            else if(key == KeyEvent.VK_DOWN) {
                if(a.hasPower(PowerUp.FLY)) {
                    a.setVY(1);
                    down = true;
                }
            }
            
            if(key == KeyEvent.VK_LEFT) {
                a.setVX(-1);
                left = true;
            }
            else if(key == KeyEvent.VK_RIGHT) {
                a.setVX(1);
                right = true;
            }
            if(twoPlayer) {
                if(key == KeyEvent.VK_W) {
                    b.setVY(-1);
                    w_key = true;
                }
                else if(key == KeyEvent.VK_S) {
                    if(b.hasPower(PowerUp.FLY)) {
                        b.setVY(1);
                        s_key = true;
                    }
                }

                if(key == KeyEvent.VK_A) {
                    b.setVX(-1);
                    a_key = true;
                }
                else if(key == KeyEvent.VK_D) {
                    b.setVX(1);
                    d_key = true;
                }
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
                    a.setVX(1);
                }
            }
            else if(key == KeyEvent.VK_RIGHT) {
                right = false;
                if(!left) {
                    a.setVX(0);
                }
                else {
                    a.setVX(-1);
                }
            }
            
            if(key == KeyEvent.VK_UP) {
                up = false;
                if(!down) {
                    a.setVY(0);
                }
                else {
                    a.setVY(1);
                }
            }
            else if(key == KeyEvent.VK_DOWN) {
                down = false;
                if(!up) {
                    a.setVY(0);
                }
                else {
                    a.setVY(-1);
                }
            }
            
            if(twoPlayer) {
                if(key == KeyEvent.VK_A) {
                    a_key = false;
                    if(!d_key) {
                        b.setVX(0);
                    }
                    else {
                        b.setVX(1);
                    }
                }
                else if(key == KeyEvent.VK_D) {
                    d_key = false;
                    if(!a_key) {
                        b.setVX(0);
                    }
                    else {
                        b.setVX(-1);
                    }
                }
                
                if(key == KeyEvent.VK_W) {
                    w_key = false;
                    if(!s_key) {
                        b.setVY(0);
                    }
                    else {
                        b.setVY(1);
                    }
                }
                else if(key == KeyEvent.VK_S) {
                    s_key = false;
                    if(!w_key) {
                        b.setVY(0);
                    }
                    else {
                        b.setVY(-1);
                    }
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
            surface.repaint();
            if(!game) return;
            
            if(twoPlayer) {
                if(a.y > SCREEN_HEIGHT + 10 || b.y > SCREEN_HEIGHT + 10) {
                    game = false; //Game over
                }
            }
            else if(!twoPlayer && a.y > SCREEN_HEIGHT + 10) {
                game = false; //Game over
            }
            
            a.y += SCROLL_VEL;
            if(twoPlayer) b.y += SCROLL_VEL;
            
            height += SCROLL_VEL;
            
            a.updatePhysics();
            if(twoPlayer) b.updatePhysics();
            
            for(Platform platform : platforms) {
                platform.y += SCROLL_VEL;
                
                a.checkCollisions(platform);
                if(twoPlayer) b.checkCollisions(platform);
            }
            
            for(Platform platform : platforms) {
                if(platform.y > SCREEN_HEIGHT) {
                    platformsToRemove.add(platform);
                }
            }
            
            if(height % PLAT_HEIGHT_DIST == 0) {
                synchronized(platforms) {
                    float prob = (height/PLAT_HEIGHT_DIST)%2==1 ? 0.75f : 0.3f;
                    for(int j = 0; j < SCREEN_WIDTH/Platform.DIMENSION; j++) {
                        if(Math.random()<prob) {
                            platforms.add(new Platform(DryIceClimber.this, j*Platform.DIMENSION, -Platform.DIMENSION));
                        }
                    }
            }
            }
            
            a.checkCollisions(power);
            if(twoPlayer) {
                b.checkCollisions(power);
            }
            
            platforms.removeAll(platformsToRemove);
            platformsToRemove.clear();
        }
    }
}