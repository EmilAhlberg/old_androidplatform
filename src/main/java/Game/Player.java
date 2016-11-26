package Game;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Emil on 2016-11-24.
 */

public class Player extends Mover {

    private Paint paint = new Paint();
    private double clickX = y;
    private double clickY = x;
    private long savedSpeedTime, savedPositionTime;
    private boolean grounded;


    public Player(int x, int y) {
        super(x, y, 0, 30);
        paint.setColor(Color.GREEN);
        savedSpeedTime = savedPositionTime = System.nanoTime();
        grounded = false;
    }


    @Override
    public void draw() {
        canvas.drawCircle((int) x, (int) y, 20, paint);
    }

    @Override
    public void update() {
        updateSpeed();
        updatePosition();
    }

    public void updateClickPosition(double cx, double cy) {
        clickX = cx;
        clickY = cy;
        //hoppcheck hamnade här nu
        if (isGrounded()) {
            jump();
        }
    }

    public void jump() {
        applyForce(0,750);
        //changeVerticalForce(750);
        update();
    }

    /*public void changeVerticalForce(double verticalChange) {
        verticalForce += verticalChange;
    }
    public void changeHorizontalForce(double horizontalChange) {
        horizontalForce += horizontalChange;
    }

    @Override
    protected void updateSpeed() {
        updateAcceleration();
        verticalForce = GRAVITY;
        horizontalForce = 0;
        //long deltaTime = System.nanoTime() - savedSpeedTime;
        verticalSpeed = verticalSpeed + verticalAcceleration *//** deltaTime / 1000000000*//*;
        horizontalSpeed = horizontalSpeed + horizontalAcceleration *//** deltaTime / 1000000000*//*;
        //savedSpeedTime = System.nanoTime();
    }

    protected void updateAcceleration () {
        verticalAcceleration = verticalForce / mass;
        horizontalAcceleration = horizontalForce / mass;
    }*/

    @Override
    protected void updatePosition() {
        /*
        double dx = clickX - x;
        double dy = clickY - y;
        if (Math.abs(dx) <= speed && (Math.abs(dy) <= speed)) {
        } else {
            double xMov = speed * dx / (Math.abs(dx) + Math.abs(dy));
            double yMov = speed * dy / (Math.abs(dx) + Math.abs(dy));
            //Log.d("WAAAA", "xMov= " + xMov + " : yMov= " + yMov + " : xMov + yMov = " + (Math.abs(xMov) + Math.abs(yMov)));
            move(x + xMov, y + yMov);
        }
        */

        Log.d("42", "verticalSpeed= " + verticalSpeed + " : horizontalSpeed= " + horizontalSpeed + " : verticalForce= " + verticalForce + " : verticalAcceleration= " + verticalAcceleration);

        //long deltaTime = System.nanoTime() - savedPositionTime;
        if (!VerticalCollision() || verticalSpeed > 0) {
            move(x, y - verticalSpeed /** deltaTime / 1000000000*/);
        } else {
            verticalSpeed = 0;
        }
        if (!HorizontalCollision()) {
            move(x + horizontalSpeed /** deltaTime / 1000000000*/, y);
        } else {
            horizontalSpeed = 0;
        }

        //savedPositionTime = System.nanoTime();
    }

    public boolean isGrounded() {
        return grounded;
    }

    private boolean HorizontalCollision () {
        if (x <= 0 || x >= canvas.getWidth()) {
            return true;
        }
        return false;
    }

    private boolean VerticalCollision () {
        if (y >= canvas.getHeight()) {
            y = canvas.getHeight();
            grounded = true;
            return true;
        }
        grounded = false;
        return false;
    }

    @Override
    protected boolean edgeCollision() {
        return false;
    }

    @Override
    protected boolean intersects(GameObject g) {
        //TODO
        return false;
    }
}
