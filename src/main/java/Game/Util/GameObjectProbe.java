package Game.Util;

//import Mover;

import Game.Framework.World;
import Game.Movers.Mover;
import Game.*;

/**
 * 'Mock object', which checks for collisions on assigned locations.
 * Created by Emil on 2016-12-14.
 */

public class GameObjectProbe extends Mover {

    private GameObject latestCollider = null;
    private boolean clearPath = false;

    public GameObjectProbe(Position p, int width, int height, World world) {
        super(new Rectangle(p.getX(), p.getY(), width, height), world);
    }
    @Override
    protected void updatePosition() {

    }


    @Override
    protected void specificCollision(GameObject g, int collisionType) {
        if (collisionType == 0) {
            latestCollider = g;
        }
    }


  /*  @Override
    public void draw() {

    }*/

    @Override
    public void update() {

    }

    public GameObject getLatestCollider() {
        return latestCollider;
    }
    public boolean objectAhead() {
        return clearPath;
    }
    public void setClearPath(boolean value){
        clearPath=value;
    }
}
