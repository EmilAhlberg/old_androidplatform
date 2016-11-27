package Game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.media.midi.MidiOutputPort;

import android.graphics.Picture;
import android.text.method.Touch;
import android.util.Log;
import android.view.MotionEvent;

import com.example.emil.app.R;

import java.util.ArrayList;

import static com.example.emil.app.R.drawable.test;

/**
 * Created by Emil on 2016-11-24.
 */

public class Player extends Mover {

    protected static final int PLAYER_WIDTH = 30;
    protected static final int PLAYER_HEIGHT = 30;
    private Paint paint = new Paint();
    private double clickX = position.getX();
    private double clickY = position.getY();
    private TouchEventDecoder touchEventDecoder;
    private Drawable picture;



    public Player(Position position) {
        super(position, 30, PLAYER_WIDTH, PLAYER_HEIGHT);
        paint.setColor(Color.GREEN);
        touchEventDecoder = new TouchEventDecoder(new Position(position.getX(), position.getY()),new Position(position.getX(), position.getY()), canvas);
        picture = board.getResources().getDrawable(R.drawable.test);
    }


    @Override
    public void draw() {
        picture.setBounds((int)position.getX(), (int)position.getY(), (int)position.getX()+PLAYER_WIDTH, (int)position.getY()+PLAYER_HEIGHT);
        picture.draw(canvas);
       // paint.setColor(Color.BLUE);
      //  canvas.drawCircle((int) touchEventDecoder.getFirstClickPos().getX(), (int) touchEventDecoder.getFirstClickPos().getY(), 50, paint);
       // paint.setColor(Color.RED);
       // canvas.drawCircle((int) touchEventDecoder.getSecondClickPos().getX(), (int) touchEventDecoder.getSecondClickPos().getY(), 50, paint);
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
    }

    public void decodeTouchEvent (MotionEvent event, Point p) {
        touchEventDecoder.decodeTouchEvent(event, p);
    }

    @Override
    protected void updatePosition() {
        move(position.getX()-horizontalSpeed, position.getY() - verticalSpeed);
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

