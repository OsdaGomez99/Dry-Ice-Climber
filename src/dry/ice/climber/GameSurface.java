/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dry.ice.climber;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author jlhota16
 */
public class GameSurface extends JPanel {
    
    private DryIceClimber c;
    
    private Color background;
    
    public GameSurface(DryIceClimber game) {
        c = game;
        
        background = Color.BLACK;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(background);
        g2d.fillRect(0, 0, DryIceClimber.SCREEN_WIDTH, DryIceClimber.SCREEN_HEIGHT);
        c.paintGameObjects(g2d);
    }
    
}
