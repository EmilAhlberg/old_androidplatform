package Game.Movers;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import Game.*;
import Game.Framework.GameDisplay;
import Game.Framework.World;
import Game.InAnimates.Block;
import Game.InAnimates.Goal;
import Game.InAnimates.Hazard;
import Game.Util.IDs;
import Game.Util.Position;
import Game.Util.TouchEventDecoder;

import com.example.emil.app.R;

import static Game.Util.IDs.BLOCK;

/**
 * Created by Emil on 2016-11-24.
 */

public class Player extends Mover {

    protected static final int PLAYER_WIDTH = 30;
    protected static final int PLAYER_HEIGHT = 30;
    private double clickX = rect.left;
    private double clickY = rect.top;
    private TouchEventDecoder touchEventDecoder;
    private boolean awake = true;
    private int sleepTime = 0;

    public Player(Position position, World world) {
        super(new Rect((int)position.getX(), (int)position.getY(), (int)position.getX() + PLAYER_WIDTH, (int)position.getY() + PLAYER_HEIGHT), world);
        touchEventDecoder = new TouchEventDecoder(new Position(position.getX(), position.getY()), new Position(position.getX(), position.getY()));

    }


   /* @Override
    public void draw() {
        picture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + PLAYER_WIDTH, (int) position.getY() + PLAYER_HEIGHT);
        picture.draw(canvas);

//        paint.setColor(Color.BLUE);
//        canvas.drawCircle((int) touchEventDecoder.getFirstClickPos().getX(), (int) touchEventDecoder.getFirstClickPos().getY(), 50, paint);
//        paint.setColor(Color.RED);
//        canvas.drawCircle((int) touchEventDecoder.getSecondClickPos().getX(), (int) touchEventDecoder.getSecondClickPos().getY(), 50, paint);
    }*/

    @Override
    public void update() {
        for (GameObject g : objectsCloseBy) {
            if (g instanceof Block)
                Log.d("playerUpdate1 ", "width: "+ g.getRect().width() + "height: " + g.getRect().height());
        }
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
        /*centerPlayer();*/
        for (GameObject g : objectsCloseBy) {
            if (g instanceof Block)
                Log.d("playerUpdate2 ", "width: "+ g.getRect().width() + "height: " + g.getRect().height());
        }
    }

    private void performAction() {
        int nbrFingers = touchEventDecoder.getNbrFingersDown();
        if (nbrFingers == 2) {
            if (WJDirection != 0) {
                mv.verticalSpeed = 0;
                applyForce(WJDirection * 300, 600);
                grounded = false;
            } else if (grounded) {
                int cTemp = GameDisplay.WINDOW_WIDTH / 2;
                double sClickTemp = touchEventDecoder.getSecondClickPos().getX();
                if ((clickX <= cTemp && sClickTemp > cTemp) || (clickX > cTemp && sClickTemp <= cTemp)) {
                    jump(600);
                }
            }
        }
        if (nbrFingers > 0) {
            if (clickX <= GameDisplay.WINDOW_WIDTH / 2) {
                applyForce(30 - mv.horizontalSpeed * 2, 0);
            } else {
                applyForce(-30 - mv.horizontalSpeed * 2, 0);
            }
        }
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
        WJDirection = 0;
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                applyFriction();
                move(rect.left - mv.horizontalSpeed, rect.top);
            } else if (i == 1) {
                move(rect.left, rect.top - mv.verticalSpeed);
            }
            checkCollision(i);
        }
    }

    @Override
    protected void specificCollision(GameObject g, int collisionType) {
        Log.d("playerSC1 ", "width: "+ g.getRect().width() + " height: " + g.getRect().height());
        if (collisionType == 0) {
            specificCollisionHorizontal(g);
        } else if (collisionType == 1) {
            specificCollisionVertical(g);
        }
        //kollisioner oberoende av typ

        if (g instanceof Goal) {
            ((Goal) g).playerReachedGoal();
        } else if (g instanceof Hazard) {
            ((Hazard) g).affectPlayer();
        }
        Log.d("playerSC2 ", "width: "+ g.getRect().width() + " height: " + g.getRect().height());
    }

    private void specificCollisionHorizontal(GameObject g) {
        if (g instanceof Block) {
            horizontalBlockCollision(g);
        } else if (g instanceof Cat) {
            ((Cat) g).affectPlayer();
        } else if (g instanceof Veterinarian) {
            ((Veterinarian) g).affectPlayer();
        }

    }

    private void specificCollisionVertical(GameObject g) {
        if (g instanceof Block) {
            verticalBlockCollision(g);
        } else if (g instanceof Veterinarian || g instanceof Cat) {
            kill(g);
        }
    }

    //should be replaced by new collision methods
    public void kill(GameObject g) {
        world.removeObject(g);
        DeathAnimator d = new DeathAnimator(g, world);
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
        } else {
            mv.horizontalSpeed *= 0.96;
        }
    }

    private void stillOnScreen() {
        if (rect.top >= 1000) {
            world.gameOver();
        }
    }

//    public void sedated() {
//        awake = false;
//        sleepTime = 50;
//    }
}

