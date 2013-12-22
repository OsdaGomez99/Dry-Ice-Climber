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
    protected static Image[][] imageStates;
    protected int state, frame;
    
    protected int vx, vy;
    
    public GameObject(int x_coord, int y_coord) {
        state = 0;
        frame = 0;
        x = x_coord;
        y = y_coord;
        vx = vy = 0;
    }
    
    public void setState(int s) {
        frame = 0;
        state = s;
    }
    
    public void setVX(int new_vx) {
        vx = new_vx;
    }
    
    public void setVY(int new_vy) {
        vy = new_vy;
    }
    
    public void paint(Graphics2D g) {
        x += vx;
        y += vy;
        
        g.drawImage(imageStates[state][frame], x, y, null);
        frame = (frame+1) % imageStates[state].length;
    }
}
