package Game;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Emil on 2016-11-27.
 */

public class TouchEventDecoder {

    private Position firstClickPos;
    private Position secondClickPos;
    private int touchAction, firstPointerId, secondPointerId;
    private boolean fingerDown, secondFingerDown;
    private Canvas canvas;


    public TouchEventDecoder (Position firstPos, Position secondPos, Canvas canvas) {
        this.firstClickPos = firstPos;
        this.secondClickPos = secondPos;
        this.canvas = canvas;
        fingerDown = secondFingerDown = false;

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

    public Position getFirstClickPos() {
        return firstClickPos;
    }

    public Position getSecondClickPos() {
        return secondClickPos;
    }
}
