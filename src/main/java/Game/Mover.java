package Game;

import android.graphics.Canvas;



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

}
