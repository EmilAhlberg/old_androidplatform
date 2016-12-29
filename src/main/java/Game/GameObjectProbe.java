package Game;

//import Mover;

/**
 * 'Mock object', which checks for collisions on assigned locations.
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
    protected void updatePicture() {

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
