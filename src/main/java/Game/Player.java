package Game;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import com.example.emil.Framework.GameOverActivity;
import com.example.emil.app.R;

/**
 * Created by Emil on 2016-11-24.
 */

public class Player extends Mover {

    protected static final int PLAYER_WIDTH = 30;
    protected static final int PLAYER_HEIGHT = 30;
    private double clickX = position.getX();
    private double clickY = position.getY();
    private TouchEventDecoder touchEventDecoder;
    private Drawable picture;
    private boolean awake = true;
    private int sleepTime = 0;

    public Player(Position position) {
        super(position, PLAYER_WIDTH, PLAYER_HEIGHT);
        touchEventDecoder = new TouchEventDecoder(new Position(position.getX(), position.getY()), new Position(position.getX(), position.getY()), canvas);
        picture = gameActivity.getResources().getDrawable(R.drawable.test);
    }


    @Override
    public void draw() {
        picture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + PLAYER_WIDTH, (int) position.getY() + PLAYER_HEIGHT);
        picture.draw(canvas);

//        paint.setColor(Color.BLUE);
//        canvas.drawCircle((int) touchEventDecoder.getFirstClickPos().getX(), (int) touchEventDecoder.getFirstClickPos().getY(), 50, paint);
//        paint.setColor(Color.RED);
//        canvas.drawCircle((int) touchEventDecoder.getSecondClickPos().getX(), (int) touchEventDecoder.getSecondClickPos().getY(), 50, paint);
    }

    @Override
    public void update() {
        if (awake) {
            performAction();
        } else {
            sleepTime--;
            if (sleepTime == 0) {
                awake = true;
            }
        }
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
                jump(600);
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

    public void jump(int force) {
        applyForce(0, force);
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

    @Override
    protected void specificCollision(GameObject g, int collisionType) {
        if (collisionType == 0) {
            specificCollisionHorizontal(g);
        } else if (collisionType == 1) {
            specificCollisionVertical(g);
        }
        //kollisioner oberoende av typ
        if (g instanceof Goal) {
            ((Goal) g).playerReachedGoal();
            world.nextLevel();
            move(100, 100);
        } else if (g instanceof Hazard) {
            ((Hazard) g).affectPlayer();
        }

    }

    private void specificCollisionHorizontal(GameObject g) {
        if (g instanceof Block) {
            horizontalBlockCollision(g);
        } else if (g instanceof Cat) {
            ((Cat) g).affectPlayer();
        } else if (g instanceof Vetrinarian) {
            ((Vetrinarian) g).affectPlayer();
        }

    }

    private void specificCollisionVertical(GameObject g) {
        if (g instanceof Block) {
            verticalBlockCollision(g);
        } else if (g instanceof Vetrinarian || g instanceof Cat) {
            kill(g);
        }
    }


    private void kill(GameObject g) {
        world.removeObject(g);
        DeathAnimator d = new DeathAnimator(g);
        mv.verticalSpeed = 0;
        mv.verticalForce = 0;
        jump(150);

    }

    private void applyFriction() {
        if (grounded) {
            mv.horizontalSpeed *= 0.95;
            if (touchEventDecoder.getNbrFingersDown() == 0 && (mv.horizontalSpeed > 0.5 || mv.horizontalSpeed < -0.5)) {
                mv.horizontalSpeed -= mv.horizontalSpeed / Math.abs(mv.horizontalSpeed) * 0.5;
            }
        }
    }


    private void centerPlayer() {
        Rect r = canvas.getClipBounds();
        double dx = calculateDx(r);
        double dy = calculateDy(r);

        //förhindrar 'flimmer' vid stillastående
        if (Math.abs(dx) > 2 || Math.abs(dy) > 2) {
            canvas.translate((float) dx, (float) dy);
        }
    }

    private double calculateDx(Rect r) {
        double dx = 0;
        if (position.getX() >= 400 && position.getX() <= 1600) {
            dx = r.centerX() - position.getX();
        } else if (position.getX() <= 400) {
            dx = r.left;
        } else if (position.getX() >= 1600) {
            dx = r.right - 2000;
        }
        return dx;
    }

    private double calculateDy(Rect r) {
        double dy = 0;
        if (position.getY() >= 240 && position.getY() <= 760) {
            dy = r.centerY() - position.getY();
        } else if (position.getY() < 240) {
            dy = r.top;  //icke testad
        } else if (position.getY() >= 760) {
            dy = r.bottom - 1000;
        }
        return dy;
    }

    private void stillOnScreen() {
        if (position.getY() >= 1000) {
            world.gameOver();
        }
    }

    public void sedated() {
        awake = false;
        sleepTime = 50;
    }
}

