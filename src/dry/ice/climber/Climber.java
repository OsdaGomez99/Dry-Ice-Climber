/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dry.ice.climber;

import java.awt.image.BufferedImage;
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
        
        width = 20;
        height = 27;
        
        imageStates = new BufferedImage[5][];
        imageStates[STANDING] = new BufferedImage[24];
        imageStates[RUNNING_RIGHT] = new BufferedImage[24];
        imageStates[RUNNING_LEFT] = new BufferedImage[24];
        imageStates[JUMPING_RIGHT] = new BufferedImage[24];
        imageStates[JUMPING_LEFT] = new BufferedImage[24];
        
        for(int state_id = 0; state_id < imageStates.length; state_id++) {
            BufferedImage[] state = imageStates[state_id];
            String name = "";
            switch(state_id) {
                case STANDING:
                    name = "running";
                    break;
                case RUNNING_RIGHT:
                    name = "standing_right";
                    break;
                case RUNNING_LEFT:
                    name = "standing_left";
                    break;
                case JUMPING_RIGHT:
                    name = "jumping_left";
                    break;
                case JUMPING_LEFT:
                    name = "jumping_left";
                    break;
            }
            for(int frame_id = 0; frame_id < state.length; frame_id++) {
                state[frame_id] = (BufferedImage) new ImageIcon(this.getClass().getResource(name+frame_id+".png")).getImage();
            }
        }
    }
    
}
