/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dry.ice.climber;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author jlhota16
 */
public class PowerUp extends GameObject {
    
    public static final int FLY = 0;
    
    private int type;
    
    private int clock;
    public static final int fullTime = 30*5; // five seconds (30fps)
    private boolean activated;
    
    public static final int DIMENSION = 50;
    
    public PowerUp(DryIceClimber game, int x, int y, int type) {
        super(game, x, y);
        this.type = type;
        width = height = DIMENSION;
        imageStates = new Image[1][1];
        imageStates[FLY][0] = new ImageIcon(this.getClass().getResource("jetpack.png")).getImage();
    }
    
    public void activate() {
        activated = true;
    }
    
    public int getClock() {
        return clock;
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
