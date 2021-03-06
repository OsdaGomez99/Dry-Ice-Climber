/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dry.ice.climber;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 *
 * @author jlhota16
 */
public class Climber extends GamePerson {
    
    public static final int STANDING = 0, RUNNING_RIGHT = 1,
            RUNNING_LEFT = 2, JUMPING_RIGHT = 3, JUMPING_LEFT = 4;
    
    public static final int JUMP_VEL = 20, RUN_VEL = 10;
    
    private HashMap<Integer, PowerUp> powers;
    //private boolean onPlatform;
    private String title;
    
    private Font font;
    private Color color;
    
    public static final int DEFAULT_WIDTH = 40, DEFAULT_HEIGHT = 54;
        
    public Climber(DryIceClimber game, String t, int x, int y) {
        super(game, x,y);
        
        powers = new HashMap<Integer, PowerUp>();
        
        setState(STANDING);
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        
        imageStates = new Image[5][];
        imageStates[STANDING] = new Image[1];
        imageStates[RUNNING_RIGHT] = new Image[1];
        imageStates[RUNNING_LEFT] = new Image[1];
        imageStates[JUMPING_RIGHT] = new Image[1];
        imageStates[JUMPING_LEFT] = new Image[1];
        
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
        
        font = new Font("Arial", Font.BOLD, 14);
        color = new Color(153, 255, 255);
        title = t;
        onPlatform = false;

    }
    
    @Override
    public void paint(Graphics2D g) {
        super.paint(g);
        g.setColor(color);
        g.setFont(font);
        g.drawString(title, x + width/3, y);
        
        //TODO draw some clock thing for the powerups
    }
    
    @Override
    public void setVX(int new_vx) {
        super.setVX(new_vx * RUN_VEL);
        updateStateBasedOnVelocity();
    }
    
    @Override
    public void setVY(int new_vy) {
        if(onPlatform || hasPower(PowerUp.FLY)) {
            super.setVY(new_vy * JUMP_VEL);
        }//You can't jump if you're not on a platform.
        updateStateBasedOnVelocity();
    }
    
    public void updateStateBasedOnVelocity() {
        if(vx > 0) {
            if(vy < 0) {
                setState(JUMPING_RIGHT);
            }
            else {
                setState(RUNNING_RIGHT);
            }
        }
        else if(vx < 0) {
            if(vy < 0) {
                setState(JUMPING_LEFT);
            }
            else {
                setState(RUNNING_LEFT);
            }
        }
        else {
            setState(STANDING);
        }
    }
    
    @Override
    public void updatePhysics() {
        super.updatePhysics();
        
        if(hasPower(PowerUp.FLY)) {
            vy -= DryIceClimber.GRAV_ACC;
        }
        for(PowerUp power : powers.values()) {
            power.updatePhysics();
        }
    }
    
    @Override
    public void checkCollisions(GameObject p) {        
        if (y+height < p.y) return;
	if (y > p.y+p.height) return;
	if (x+width/2 < p.x) return;
	if (x > p.x+p.width/2) return;
        
        if(p instanceof Platform) {
            if(hasPower(PowerUp.FLY)) {
                ((Platform)p).remove();
            }
            else if(hasPower(PowerUp.TALL)) {
                if(y+height<=p.y+p.height/2) {
                    onPlatform = true;
                    y = p.y - height;
                    if(vy > 0) {
                        vy = 0;
                    }
                    if(vx == 0) {
                        setState(STANDING);
                    }
                }
                else {
                    ((Platform)p).damage();
                }
            }
            else {
                if(y < p.y) {
                    onPlatform = true;
                    y = p.y - height;
                    if(vy > 0) {
                        vy = 0;
                    }
                    if(vx == 0) {
                        setState(STANDING);
                    }
                }
                else {
                    y = p.y + p.height + 1;
                    if(vy < -15) {
                        ((Platform)p).damage();
                        vy = -vy/2;
                    }
                }
            }
        }
        else if(p instanceof PowerUp) {
            PowerUp pow = (PowerUp) p;
            powers.put(pow.getType(), pow);
            pow.activate(this);
            pow.remove();
        }
        else if(p instanceof Enemy) {
            if(((Enemy)p).isAlive()) {
                if(y < p.y && vy > 0) {
                    ((Enemy)p).die();
                    vy = -vy;
                }
            }
        }
    }
    
    public boolean hasPower(Integer powerUp) {
        if(powers.get(powerUp) != null) {
            return powers.get(powerUp).isActivated();
        }
        else return false;
    }
    
    public void gotPower(int type) {
        switch(type) {
            case PowerUp.FLY:
                break;
            case PowerUp.TALL:
                width = 2 * DEFAULT_WIDTH;
                height = 2 * DEFAULT_HEIGHT;
                break;
        }
    }
    
    public void lostPower(int type) {
        switch(type) {
            case PowerUp.FLY:
                break;
            case PowerUp.TALL:
                width = DEFAULT_WIDTH;
                height = DEFAULT_HEIGHT;
                break;
        }
    }
}
