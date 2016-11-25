package Game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Emil on 2016-11-22.
 */

public abstract class Mover extends GameObject {
    protected int speed;
    protected double direction;

    public Mover(int x, int y, int speed, int direction) {
        super(x, y);
        this.speed = speed;
        this.direction = direction;
    }

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
