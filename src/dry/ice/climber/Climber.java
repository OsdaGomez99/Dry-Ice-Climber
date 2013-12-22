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
    
    public Climber(int x, int y) {
        super(x,y);
        state = JUMPING_RIGHT;
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
                    name = "jumping_left";
                    break;
                case JUMPING_LEFT:
                    name = "jumping_left";
                    break;
            }
            for(int frame_id = 0; frame_id < state.length; frame_id++) {
                state[frame_id] = new ImageIcon(this.getClass().getResource(name+frame_id+".png")).getImage();
            }
        }
    }
    
}
