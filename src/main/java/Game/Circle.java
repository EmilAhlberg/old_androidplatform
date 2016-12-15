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
    protected static final int CIRCLE_WIDTH = 40;
    protected static final int CIRCLE_HEIGHT = 40;
    private final static int CIRCLE_SPEED = 5;
    protected static Paint paint = new Paint();
    protected double direction; //bort?


    public Circle(Position position, int radius) {
        super(position, CIRCLE_WIDTH,CIRCLE_HEIGHT);
        this.radius = radius;
        paint.setColor(Color.BLUE);
        direction = 1;
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

    @Override
    protected void specificCollision(GameObject g) {

    }

    @Override
    protected void specificCollisionVertical(GameObject g) {

    }

    @Override
    protected void specificCollisionHorizontal(GameObject g) {

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
