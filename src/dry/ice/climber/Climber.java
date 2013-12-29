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
public class Climber extends GameObject {
    
    public static final int STANDING = 0, RUNNING_RIGHT = 1,
            RUNNING_LEFT = 2, JUMPING_RIGHT = 3, JUMPING_LEFT = 4;
    
    private boolean onPlatform;
    
    public Climber(int x, int y) {
        super(x,y);
        
        setState(STANDING);
        width = 20;
        height = 27;
        
        imageStates = new Image[5][];
        imageStates[STANDING] = new Image[1];
        imageStates[RUNNING_RIGHT] = new Image[1];
        imageStates[RUNNING_LEFT] = new Image[1];
        imageStates[JUMPING_RIGHT] = new Image[2];
        imageStates[JUMPING_LEFT] = new Image[2];
        
        for(int state_id = 0; state_id < imageStates.length; state_id++) {
            Image[] state = imageStates[state_id];
            String name = "";
            switch(state_id) {
                case STANDING:
                    name = "standing";
                    break;
                case RUNNING_RIGHT:
                    name = "running_right";
                    break;
                case RUNNING_LEFT:
                    name = "running_left";
                    break;
                case JUMPING_RIGHT:
                    name = "jumping_right";
                    break;
                case JUMPING_LEFT:
                    name = "jumping_left";
                    break;
            }
            for(int frame_id = 0; frame_id < state.length; frame_id++) {
                state[frame_id] = new ImageIcon(this.getClass().getResource(name+frame_id+".png")).getImage();
            }
        }
        
        onPlatform = false;
    }
    
    @Override
    public void setVX(int new_vx) {
        super.setVX(new_vx);
        updateStateBasedOnVelocity();
    }
    
    @Override
    public void setVY(int new_vy) {
        super.setVY(new_vy);
        updateStateBasedOnVelocity();
    }
    
    public void updateStateBasedOnVelocity() {
        if(vx >= 0) {
            if(vy < 0) {
                setState(JUMPING_RIGHT);
            }
            else {
                setState(RUNNING_RIGHT);
            }
        }
        else {
            if(vy < 0) {
                setState(JUMPING_LEFT);
            }
            else {
                setState(RUNNING_LEFT);
            }
        }
    }
    
    @Override
    public void updatePhysics() {
        super.updatePhysics();
        
        if(!onPlatform) {
            vy++;
        }
    }
    
    public void checkCollisions(Platform p) {
        if (y+height < p.y) return;
	if (y > p.y+p.height) return;
        
	if (x+3*width/4 < p.x) return;
	if (x > p.x+3*p.width/4) return;
        
        boolean onPlatform = true;
        y = p.y - height - 1;
        if(vy > 0) {
            vy = 0;
        }
        if(vx == 0) {
            setState(STANDING);
        }
    }
}
