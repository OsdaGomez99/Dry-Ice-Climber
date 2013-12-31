/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dry.ice.climber;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author jlhota16
 */
public class PowerUp extends GameObject {
    
    public static final int FLY = 0;
    
    private int type;
    
    private int clock;
    private static final int fullTime = 30*5; // five seconds (30fps)
    private boolean activated;
    
    public static final int DIMENSION = 20;
    
    public PowerUp(int x, int y, int type) {
        super(x,y);
        this.type = type;
        width = height = DIMENSION;
    }
    
    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.PINK);
        g.fillRect(x, y, width, height);
    }
    
    public void activate() {
        activated = true;
    }
    
    public void updatePhysics() {
        clock++;
        if(clock > fullTime) {
            activated = false;
        }
    }
    
    public boolean isActivated() {
        return activated;
    }
    
    public int getType() {
        return type;
    }
}
