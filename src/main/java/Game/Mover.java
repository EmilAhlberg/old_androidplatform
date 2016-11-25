package Game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Emil on 2016-11-22.
 */

public abstract class Mover extends GameObject {
    protected double direction;
    protected final double GRAVITY = -35;

    protected double verticalForce, horizontalForce, horizontalAcceleration, verticalAcceleration, horizontalSpeed, verticalSpeed, mass;

    public Mover (int x, int y, int direction, int mass) {
        super(x,y);
        this.mass = mass;
        this.direction = direction;
        horizontalForce = horizontalAcceleration = verticalAcceleration = horizontalSpeed = verticalSpeed = 0;
        verticalForce = GRAVITY;
    }

    protected abstract void updateSpeed();

    public abstract void changeVerticalForce(double changeforce);

    public abstract void changeHorizontalForce(double changeforce);

    protected abstract void updatePosition();

    protected abstract boolean edgeCollision();

    protected abstract boolean intersects(GameObject g);

    /**
     * Checks whether or not the Movers intersects with any game object, by calling the mover-objects
     * intersects method.
     */

    protected void getIntersectingObject() {
        ArrayList<GameObject> tempGameObjects = world.createTempGameObjects();
        //ArrayList<Mover> tempMovers = world.createTempMovers();
        for (GameObject g : tempGameObjects) {
            if (intersects(g)) {
                //handleCollision with g
                Log.d("COLLISION", this.getClass() + ", " + g.getClass());
            }
        }
    }


}
