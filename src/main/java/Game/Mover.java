package Game;

import android.graphics.Canvas;



/**
 * Created by Emil on 2016-11-22.
 */

public abstract class Mover extends GameObject {
    protected int speed;
    protected double direction;

    public Mover (int x, int y, int speed) {
        super(x,y);
        this.speed=speed;
        direction = 1;
    }

    protected void updatePosition() {
        double cos = Math.cos(direction);
        double sin = Math.sin(direction);
        double dx = cos * speed;
        double dy = sin * speed;
        move(x + dx, y + dy);
    }

    protected abstract boolean edgeCollision();

}
