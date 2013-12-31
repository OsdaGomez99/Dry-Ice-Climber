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
    
    public GameObject(int x_coord, int y_coord) {
        state = 0;
        frame = 0;
        x = x_coord;
        y = y_coord;
        vx = vy = 0;
        frame = 0;
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
        x = (x + vx) % DryIceClimber.SCREEN_WIDTH;
        if(x < 0) x = DryIceClimber.SCREEN_WIDTH + x;
        y += vy;
    }
    
    public void paint(Graphics2D g) {
        g.drawImage(imageStates[state][frame], x, y, width, height, null);
        frame = (frame+1) % imageStates[state].length;
    }
}
