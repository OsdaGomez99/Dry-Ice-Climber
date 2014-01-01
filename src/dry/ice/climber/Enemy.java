/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dry.ice.climber;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author jlhota16
 */
public class Enemy extends GamePerson {
    
    private int clock;
    
    public static final int LEFT = 0, RIGHT = 1;
        
    public Enemy(DryIceClimber game, int x, int y) {
        super(game,x,y);
        vx = 4;
        if(Math.random() < 0.5) {
            vx = -vx;
        }
        imageStates = new Image[2][1];
        imageStates[LEFT][0] = new ImageIcon(this.getClass().getResource("yeti_left.png")).getImage();
        imageStates[RIGHT][0]= new ImageIcon(this.getClass().getResource("yeti_right.png")).getImage();
        
        updateStateBasedOnVelocity();
        width = 40;
        height = 50;
        
        clock = (int) Math.random()*2*30;
    }
    
    @Override
    public void updatePhysics() {
        super.updatePhysics();
        clock++;
        if(clock%(2*30) == 0) {
            vx = - vx;
            updateStateBasedOnVelocity();
        }
    }
    
    public void updateStateBasedOnVelocity() {
        if(vx > 0) {
            setState(RIGHT);
        }
        else {
            setState(LEFT);
        }
    }
}
