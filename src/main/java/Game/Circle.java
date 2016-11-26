package Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Emil on 2016-11-20.
 */

public class Circle extends Mover {

    private int radius;
    private final static int CIRCLE_SPEED = 5;
    protected static Paint paint = new Paint();


    public Circle(Position position, int radius) {
        super(position, CIRCLE_SPEED, 1);
        this.radius = radius;
        paint.setColor(Color.BLUE);
    }

    @Override
    public void draw() {
        canvas.drawCircle((int) position.getX(), (int) position.getY(), radius, paint);
        //Log.d("nisse", "X= " + canvas.getWidth() + " : Y= " + canvas.getHeight());
    }

    @Override
    public void update() {
        edgeCollision();
        updatePosition();
    }

    @Override
    public void updateSpeed() {

    }


    @Override
    protected void updatePosition() {
        /*double cos = Math.cos(direction);
        double sin = Math.sin(direction);
        double dx = cos * speed;
        double dy = sin * speed;
        move(x + dx, y + dy);*/
    }

    protected boolean edgeCollision() {
        //left or right edge collision
        if (position.getX() - radius <= 0 || position.getX() + radius >= canvas.getWidth()) {
            direction = Math.PI-direction;
            return true;
        } else if(position.getY() - radius <= 0 || position.getY() + radius >= canvas.getHeight()) {
            direction = Math.PI/2+direction;
        }
        return false;
    }
}
