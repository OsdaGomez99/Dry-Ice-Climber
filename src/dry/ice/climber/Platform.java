/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dry.ice.climber;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author johnlhota
 */
public class Platform extends GameObject {
    
    public static final int NORMAL = 0, BROKEN = 1;
    
    public Platform(int x, int y) {
        super(x, y);
        
        setState(NORMAL);
        width = 35;
        height = 35;
        
        imageStates = new Image[2][];
        imageStates[NORMAL] = new Image[1];
        imageStates[BROKEN] = new Image[1];
        
        imageStates[NORMAL][0] = new ImageIcon(this.getClass().getResource("platform_normal.png")).getImage();
        imageStates[BROKEN][0] = new ImageIcon(this.getClass().getResource("platform_broken.png")).getImage();
    }
    
}
