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
    
    public static final int FLY = 0, TALL = 1, NUMBER_OF_POWERUPS = 2;
        
    private int clock;
    public static final int fullTime = 30*10; // ten seconds (30fps)
    private boolean activated;
    private Climber climber;
    
    public static final int DIMENSION = 50;
    
    public PowerUp(DryIceClimber game, int x, int y, int type) {
        super(game, x, y);
        setState(type);
        width = height = DIMENSION;
        imageStates = new Image[NUMBER_OF_POWERUPS][1];
        
        imageStates[FLY][0] = new ImageIcon(this.getClass().getResource("jetpack.png")).getImage();
        imageStates[TALL][0] = new ImageIcon(this.getClass().getResource("mushroom.png")).getImage();
    }
    
    public void activate(Climber climber) {
        activated = true;
        this.climber = climber;
        climber.gotPower(getState());
    }
    
    public int getClock() {
        return clock;
    }
    
    public void updatePhysics() {
        clock++;
        if(clock > fullTime) {
            activated = false;
            climber.lostPower(getState());
        }
    }
    
    public boolean isActivated() {
        return activated;
    }
    
    public int getType() {
        return getState();
    }
}
