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
        //canvas.drawCircle((int)position.getX(), (int)position.getY(), 30, paint);

        paint.setColor(Color.BLUE);
        canvas.drawCircle((int) touchEventDecoder.getFirstClickPos().getX(), (int) touchEventDecoder.getFirstClickPos().getY(), 50, paint);
        paint.setColor(Color.RED);
        canvas.drawCircle((int) touchEventDecoder.getSecondClickPos().getX(), (int) touchEventDecoder.getSecondClickPos().getY(), 50, paint);
    }

    @Override
    public void update() {
        performAction();
        updateSpeed();
        //horizontal
        updatePosition(0);
        checkCollision(0);
        //vertical
        updatePosition(1);
        checkCollision(1);
    }

    private void performAction () {
        int nbrFingers = touchEventDecoder.getNbrFingersDown();
        if (isGrounded() && nbrFingers == 2) {
            int cTemp = canvas.getWidth() / 2;
            double sClickTemp = touchEventDecoder.getSecondClickPos().getX();
            if ((clickX <= cTemp && sClickTemp > cTemp) || (clickX > cTemp && sClickTemp <= cTemp)) {
                jump();
            }
        }
        if (nbrFingers > 0) {
            if (clickX <= canvas.getWidth() / 2) {
                applyForce(30 - horizontalSpeed * 2, 0);
            } else {
                applyForce(-30 - horizontalSpeed * 2, 0);
            }
        }
    }

    public void jump() {
        applyForce(0, 650);
        grounded = false;
    }

    public void decodeTouchEvent (MotionEvent event, Point p) {
        touchEventDecoder.decodeTouchEvent(event, p);
        clickX = touchEventDecoder.getFirstClickPos().getX();
        clickY = touchEventDecoder.getFirstClickPos().getY();
    }

    @Override
    protected void updatePosition(int vOrH) {
        if (vOrH == 0) {
            //Friktion typ
            ////////////////////////////////////////
            if (grounded) {
                horizontalSpeed *= 0.95;
                if (touchEventDecoder.getNbrFingersDown() == 0 && (horizontalSpeed > 0.5 || horizontalSpeed < -0.5)) {
                    horizontalSpeed -= horizontalSpeed / Math.abs(horizontalSpeed) * 0.5;
                }
            }
            ////////////////////////////////////////
            move(position.getX() - horizontalSpeed, position.getY());
        } else if (vOrH == 1) {
            move(position.getX(), position.getY() - verticalSpeed);
        }
    }
}

