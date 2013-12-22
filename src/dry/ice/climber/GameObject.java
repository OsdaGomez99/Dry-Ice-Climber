/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dry.ice.climber;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author jlhota16
 */
public class GameObject extends Rectangle {
    protected static BufferedImage[][] imageStates;
    private int state, frame;
        
    public GameObject(int x_coord, int y_coord) {
        state = 0;
        frame = 0;
        x = x_coord;
        y = y_coord;
    }
    
    public void setState(int s) {
        state = s;
    }
    
    public void paint(Graphics2D g) {
        g.drawImage(imageStates[state][frame], x, y, null);
    }
}
