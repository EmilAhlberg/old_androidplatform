package Game;

import android.graphics.Canvas;

import java.util.ArrayList;


/**
 * Created by Emil on 2016-11-22.
 */

public abstract class Mover extends GameObject {
    protected int speed;
    protected double direction;

    public Mover (int x, int y, int speed, int direction) {
        super(x,y);
        this.speed=speed;
        this.direction = direction;
    }

    protected abstract void updatePosition();

    protected abstract boolean edgeCollision();

    protected abstract boolean intersects(ArrayList<GameObject> list);

    protected void getIntersectingObject() {
        ArrayList<GameObject> tempGameObjects = world.createTempGameObjects();
        ArrayList<Mover> tempMovers = world.createTempMovers();
        for (Mover mover : tempMovers) {
            if (mover.intersects(tempGameObjects)) {
                //do something
            }
        }
    }



}
