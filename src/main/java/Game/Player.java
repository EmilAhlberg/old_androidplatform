package Game;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import com.example.emil.app.GameOver;
import com.example.emil.app.LevelCleared;
import com.example.emil.app.R;

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
        super(position, PLAYER_WIDTH, PLAYER_HEIGHT);
        paint.setColor(Color.GREEN);
        touchEventDecoder = new TouchEventDecoder(new Position(position.getX(), position.getY()), new Position(position.getX(), position.getY()), canvas);
        picture = board.getResources().getDrawable(R.drawable.test);

    }


    @Override
    public void draw() {
        picture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + PLAYER_WIDTH, (int) position.getY() + PLAYER_HEIGHT);
        picture.draw(canvas);

        paint.setColor(Color.BLUE);
        canvas.drawCircle((int) touchEventDecoder.getFirstClickPos().getX(), (int) touchEventDecoder.getFirstClickPos().getY(), 50, paint);
        paint.setColor(Color.RED);
        canvas.drawCircle((int) touchEventDecoder.getSecondClickPos().getX(), (int) touchEventDecoder.getSecondClickPos().getY(), 50, paint);
    }

    @Override
    public void update() {
        performAction();
        updateSpeed();
        updatePosition();
        stillOnScreen();
        centerPlayer();
    }

    private void performAction() {
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
                applyForce(30 - mv.horizontalSpeed * 2, 0);
            } else {
                applyForce(-30 - mv.horizontalSpeed * 2, 0);
            }
        }
    }

    public void jump() {
        applyForce(0, 650);
        grounded = false;
    }

    public void decodeTouchEvent(MotionEvent event, Point p) {
        touchEventDecoder.decodeTouchEvent(event, p);
        clickX = touchEventDecoder.getFirstClickPos().getX();
        clickY = touchEventDecoder.getFirstClickPos().getY();
    }

    /*
        i = 0: vertical movement and collisionHandling
        i = 1: horizontal movement and collisionHandling
     */
    @Override
    protected void updatePosition() {
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                applyFriction();
                move(position.getX() - mv.horizontalSpeed, position.getY());
            } else if (i == 1) {
                move(position.getX(), position.getY() - mv.verticalSpeed);
            }
            checkCollision(i);
        }
    }

    private void applyFriction() {
        if (grounded) {
            mv.horizontalSpeed *= 0.95;
            if (touchEventDecoder.getNbrFingersDown() == 0 && (mv.horizontalSpeed > 0.5 || mv.horizontalSpeed < -0.5)) {
                mv.horizontalSpeed -= mv.horizontalSpeed / Math.abs(mv.horizontalSpeed) * 0.5;
            }
        }
    }

    @Override
    protected void specificCollision(int collisionType, GameObject g) {
        if (g instanceof Goal) {
            ((Goal) g).playerReachedGoal();
            world.nextLevel();
            move(100, 100);
        }
    }

    private void centerPlayer() {
        if (position.getX()>=canvas.getWidth()-350) {
            canvas.translate((float)mv.horizontalSpeed,0);
        } else if (position.getX()<=canvas.getWidth()-650) {
            canvas.translate((float)mv.horizontalSpeed,0);
        }
        if (position.getY()<=canvas.getHeight()-250) {
            canvas.translate(0,(float)mv.verticalSpeed);
        }else if (position.getY()>canvas.getHeight()-150) {
            canvas.translate(0,(float)mv.verticalSpeed);
        }
    }

    private void stillOnScreen() {
        if (position.getY()>=canvas.getHeight()) {
            canvas.drawColor(Color.BLACK);
            world.pauseGame();
            Intent intent = new Intent(board, GameOver.class);
            intent.putExtra("Level",world.getLevel());
            board.startActivity(intent);
        }

    }
}

