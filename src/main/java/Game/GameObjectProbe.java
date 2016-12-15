package Game;

//import Mover;

/**
 * Class used to probe for GameObjects at future positions. Only uses Mover methods.
 * Created by Emil on 2016-12-14.
 */

public class GameObjectProbe extends Mover {

    private GameObject latestCollider = null;
    private boolean clearPath = false;

    public GameObjectProbe(Position p, int width, int height) {
        super(p, width, height);
    }
    @Override
    protected void updatePosition() {

    }

    @Override
    protected void specificCollision(GameObject g) {

    }

    @Override
    protected void specificCollisionVertical(GameObject g) {

    }

    @Override
    protected void specificCollisionHorizontal(GameObject g) {
        latestCollider = g;
    }

    @Override
    public void draw() {

    }

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
