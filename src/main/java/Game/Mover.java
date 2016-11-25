package Game;

import android.graphics.Canvas;



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

}
