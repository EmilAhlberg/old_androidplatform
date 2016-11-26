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

    protected void updateSpeed() {
        updateAcceleration();
        verticalForce = GRAVITY;
        horizontalForce = 0;
        //long deltaTime = System.nanoTime() - savedSpeedTime;
        verticalSpeed = verticalSpeed + verticalAcceleration /** deltaTime / 1000000000*/;
        horizontalSpeed = horizontalSpeed + horizontalAcceleration /** deltaTime / 1000000000*/;
        //savedSpeedTime = System.nanoTime();
    }
    private void updateAcceleration () {
        verticalAcceleration = verticalForce / mass;
        horizontalAcceleration = horizontalForce / mass;
    }

    public void applyForce(double horizontalChange, double verticalChange) {
        verticalForce += verticalChange;
        horizontalForce += horizontalChange;
    }

    protected abstract void updatePosition();

    protected abstract boolean edgeCollision();

    /**
     * Check for any collisions between THIS and any other GameObject. If any collision occurs,
     * they are handled one by one, in the order they were registered.
     */
    protected boolean checkCollision() {
        ArrayList <GameObject> collisions = getIntersectingObjects();
        if (collisions != null) {
            handleCollisions(collisions);
            return true;
        }
        return false;
    }

    private void handleCollisions(ArrayList<GameObject> colliders) {
        
    }

    protected abstract boolean intersects(GameObject g);


    /**
     * Checks whether or not the Movers intersects with any game object, by calling the mover-objects
     * intersects method.
     */
    protected ArrayList<GameObject> getIntersectingObjects() {
        ArrayList<GameObject> tempGameObjects = world.createTempGameObjects();
        //ArrayList<Mover> tempMovers = world.createTempMovers();
        ArrayList<GameObject> colliders = new ArrayList<GameObject>();
        for (GameObject g : tempGameObjects) {
            if (intersects(g)) {
                colliders.add(g);
                Log.d("COLLISION", this.getClass() + ", " + g.getClass());
            }
        }
        return colliders;
    }


}
