package Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Emil on 2016-11-20.
 */

public class Circle extends Mover {

    private int radius;
    private final static int CIRCLE_SPEED = 5;
    protected static Paint paint = new Paint();


    public Circle(int x, int y, int radius) {
        super(x, y, CIRCLE_SPEED);
        this.radius = radius;
        paint.setColor(Color.BLUE);
    }

    @Override
    public void draw() {
        canvas.drawCircle((int) x, (int) y, radius, paint);
    }

    @Override
    public void update() {
        edgeCollision();
        updatePosition();
    }

    @Override
    protected boolean edgeCollision() {
        //left or right edge collision
        if (x - radius <= 0 || x + radius >= canvas.getWidth()) {
            direction = Math.PI-direction;
            return true;
        } else if(y - radius <= 0 || y + radius >= canvas.getHeight()) {
            direction = Math.PI/2+direction;
        }
        return false;
    }
}
