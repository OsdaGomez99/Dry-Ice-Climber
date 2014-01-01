/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dry.ice.climber;

/**
 *
 * @author jlhota16
 */
public class GamePerson extends GameObject {
    
    protected boolean onPlatform;
    
    private boolean alive;
    
    public GamePerson(DryIceClimber game, int x, int y) {
        super(game,x,y);
        alive = true;
    }
    
    @Override
    public void updatePhysics() {
        super.updatePhysics();
        
        if(!onPlatform) {
            vy += DryIceClimber.GRAV_ACC;
        }
        onPlatform = false;
    }
    
    public void checkCollisions(GameObject p) {
        if(!alive) {
            onPlatform = false;
            return;
        }
        
        if (y+height < p.y) return;
	if (y > p.y+p.height) return;
	if (x+width/2 < p.x) return;
	if (x > p.x+p.width/2) return;
        
        if(p instanceof Platform) {
            if(y < p.y) {
                onPlatform = true;
                y = p.y - height;
                if(vy > 0) {
                    vy = 0;
                }
            }
            else {
                y = p.y + p.height + 1;
           }
        }
    }
    
    public boolean isAlive() {
        return alive;
    }
    
    public void die() {
        alive = false;
    }
}
