package Game;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.midi.MidiOutputPort;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Emil on 2016-11-24.
 */

public class Player extends Mover {

    private Paint paint = new Paint();
    private Position firstClickPos, secondClickPos;
    private double clickX = position.getX();
    private double clickY = position.getY();
    private long savedSpeedTime, savedPositionTime;
    private boolean fingerDown, secondFingerDown;
    private int touchAction, firstPointerId, secondPointerId;


    public Player(Position position) {
        super(position, 0, 30);
        paint.setColor(Color.GREEN);
        firstClickPos = new Position(position.getX(), position.getY());
        secondClickPos = new Position(position.getX(), position.getY());
        savedSpeedTime = savedPositionTime = System.nanoTime();
        fingerDown = secondFingerDown = false;
    }


    @Override
    public void draw() {
        canvas.drawCircle((int) position.getX(), (int) position.getY(), 20, paint);

        paint.setColor(Color.BLUE);
        canvas.drawCircle((int) firstClickPos.getX(), (int) firstClickPos.getY(), 50, paint);
        paint.setColor(Color.RED);
        canvas.drawCircle((int) secondClickPos.getX(), (int) secondClickPos.getY(), 50, paint);
    }

    @Override
    public void update() {
        updateSpeed();
        updatePosition();
        checkCollision();
    }

    public void updateClickPosition(double cx, double cy) {

        /*if (touchAction == MotionEvent.ACTION_DOWN) {
            firstClickPos.setX(cx);
            firstClickPos.setY(cy);
        } else if () {

        }*/
        clickX = cx;
        clickY = cy;


        //hoppcheck hamnade här nu
        if (isGrounded()) {
            jump();
        }
        if(clickX < position.getX()) {
            applyForce(30, 0);
        }
        else {
            applyForce(-30,0);
        }
    }

    public void jump() {
        applyForce(0, 650);
        grounded = false;
        //changeVerticalForce(750);
        update();
    }

    public void decodeTouchEvent (MotionEvent event, Point p) {

        int action = event.getAction();
        touchAction = action;

        if (event.getPointerCount() > 1) {
            if (firstPointerId == 0) {
                secondPointerId = 1;
            } else {
                secondPointerId = 0;
            }
            firstClickPos.setX(event.getX(event.getPointerId(firstPointerId)));
            firstClickPos.setY(event.getY(event.getPointerId(firstPointerId)));
            secondClickPos.setX(event.getX(event.getPointerId(secondPointerId)));
            secondClickPos.setY(event.getY(event.getPointerId(secondPointerId)));
            secondFingerDown = true;
            if (action == MotionEvent.ACTION_POINTER_UP) {
                firstPointerId = 1;
            } else if (action == MotionEvent.ACTION_POINTER_2_UP) {
                firstPointerId = 0;
            }
        } else {
            secondFingerDown = false;
            firstClickPos.setX(event.getX());
            firstClickPos.setY(event.getY());
            if (action == MotionEvent.ACTION_DOWN) {
                fingerDown = true;
                firstPointerId = 0;
            } else if (action == MotionEvent.ACTION_UP) {
                fingerDown = false;
            }
        }

        firstClickPos = new Position(firstClickPos.getX() * canvas.getWidth() / p.x, firstClickPos.getY() * canvas.getHeight() / p.y);
        secondClickPos = new Position(secondClickPos.getX() * canvas.getWidth() / p.x, secondClickPos.getY() * canvas.getHeight() / p.y);

        Log.d("MultiTouch", "FirstClickPos = (" + firstClickPos.getX() + ", " + firstClickPos.getY() + ")" + " : SecondClickPos = (" + secondClickPos.getX() + ", " + secondClickPos.getY() + ")");

        Log.d("MultiTouch", "" + firstPointerId + " : " + secondPointerId);
        debugMultiTouch();
    }

    private void debugMultiTouch() {
        if (touchAction == MotionEvent.ACTION_DOWN) {
            Log.d("MultiTouch", "ACTION_DOWN");
        } else if (touchAction == MotionEvent.ACTION_UP) {
            Log.d("MultiTouch", "ACTION_UP");
        } else if (touchAction == MotionEvent.ACTION_POINTER_DOWN) {
            Log.d("MultiTouch", "ACTION_POINTER_DOWN");
        } else if (touchAction == MotionEvent.ACTION_POINTER_UP) {
            Log.d("MultiTouch", "ACTION_POINTER_UP");
        } else if (touchAction == MotionEvent.ACTION_MOVE){
            Log.d("MultiTouch", "ACTION_MOVE");
        } else if (touchAction == MotionEvent.ACTION_POINTER_INDEX_SHIFT){
            Log.d("MultiTouch", "ACTION_POINTER_INDEX_SHIFT");
        } else if (touchAction == MotionEvent.ACTION_CANCEL){
            Log.d("MultiTouch", "ACTION_CANCEL");
        } else if (touchAction == MotionEvent.ACTION_POINTER_INDEX_MASK){
            Log.d("MultiTouch", "ACTION_POINTER_INDEX_MASK");
        } else if (touchAction == MotionEvent.ACTION_BUTTON_PRESS){
            Log.d("MultiTouch", "ACTION_BUTTON_PRESS");
        } else if (touchAction == MotionEvent.ACTION_SCROLL){
            Log.d("MultiTouch", "ACTION_SCROLL");
        } else if (touchAction == MotionEvent.ACTION_HOVER_ENTER){
            Log.d("MultiTouch", "ACTION_HOVER_ENTER");
        } else if (touchAction == MotionEvent.ACTION_MASK){
            Log.d("MultiTouch", "ACTION_MASK");
        } else if (touchAction == MotionEvent.ACTION_OUTSIDE){
            Log.d("MultiTouch", "ACTION_OUTSIDE");
        } else if (touchAction == MotionEvent.ACTION_POINTER_2_UP){
            Log.d("MultiTouch", "ACTION_POINTER_2_UP");
        } else if (touchAction == MotionEvent.ACTION_POINTER_2_DOWN){
            Log.d("MultiTouch", "ACTION_POINTER_2_DOWN");
        } else {
            Log.d("MultiTouch", "UNKNOWN");
        }
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
        horizontalSpeed = horizontalSpeed + horizontalAcceleration */

    /**
     * deltaTime / 1000000000
     *//*;
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

        //Log.d("42", "verticalSpeed= " + verticalSpeed + " : horizontalSpeed= " + horizontalSpeed + " : verticalForce= " + verticalForce + " : verticalAcceleration= " + verticalAcceleration);

        //long deltaTime = System.nanoTime() - savedPositionTime;

        move(position.getX()-horizontalSpeed, position.getY() - verticalSpeed /** deltaTime / 1000000000*/);


//        if (!VerticalCollision() || verticalSpeed > 0) {
//            move(position.getX(), position.getY() - verticalSpeed /** deltaTime / 1000000000*/);
//        } else {
//            verticalSpeed = 0;
//        }
//        if (!HorizontalCollision()) {
//            move(position.getX() + horizontalSpeed /** deltaTime / 1000000000*/, position.getY());
//        } else {
//            horizontalSpeed = 0;
//        }

        //savedPositionTime = System.nanoTime();
    }

//    private boolean HorizontalCollision() {
//        if (position.getX() <= 0 || position.getX() >= canvas.getWidth()) {
//            return true;
//        }
//        return false;
//    }
//
//    private boolean VerticalCollision() {
//        if (position.getY() >= canvas.getHeight()) {
//            position.setY(canvas.getHeight());
//            grounded = true;
//            return true;
//        }
//        grounded = false;
//        return false;
//    }

}

