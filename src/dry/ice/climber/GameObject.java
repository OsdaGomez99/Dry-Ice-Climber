/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dry.ice.climber;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author jlhota16
 */
public class GameObject extends Rectangle {
    protected Image[][] imageStates;
    private int state, frame;
    
    protected int vx, vy;
    
    private DryIceClimber diGame;
    
    public GameObject(DryIceClimber game, int x_coord, int y_coord) {
        state = 0;
        frame = 0;
        x = x_coord;
        y = y_coord;
        vx = vy = 0;
        frame = 0;
        
        diGame = game;
    }
    
    public synchronized void setState(int s) {
        frame = 0;
        state = s;
    }
    
    public int getState() {
        return state;
    }
    
    public void setVX(int new_vx) {
        vx = new_vx;
    }
    
    public void setVY(int new_vy) {
        vy = new_vy;
    }
    
    public void updatePhysics() {
        if(x+vx >=0 && x+width+vx <= DryIceClimber.SCREEN_WIDTH) {
            x += vx;
        }
        if(y+vy >= 0) {
            y += vy;
        }
    }
    
    public void paint(Graphics2D g) {
        g.drawImage(imageStates[state][frame], x, y, width, height, null);
        frame = (frame+1) % imageStates[state].length;
    }
    
    public void remove() {
        diGame.removeObject(this);
    }
}
