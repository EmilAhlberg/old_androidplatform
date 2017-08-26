package Game.Movers;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.util.Log;

import Game.*;
import Game.Framework.World;
import Game.InAnimates.Block;
import Game.InAnimates.Hazard;
import Game.Util.Position;

/**
 * Created by Emil on 2016-12-14.
 */

public abstract class Cat extends Mover {
    private Drawable picture;
    protected int direction;
    private final static int CAT_SIZE = 20;


    public Cat(Position p, World world) {
        super(new Rect((int)p.getX(), (int)p.getY(), (int)p.getX() +CAT_SIZE, (int) p.getY() + CAT_SIZE), world);
        applyForce(35, 0);
        direction = 1;
        //activePicture = d;
    }

    @Override
    protected void updatePosition() {
        for (int i = 1; i >=0 ; i--) {
            //long millis = System.currentTimeMillis();
            if (i == 0) {
                move(rect.left - mv.horizontalSpeed*direction, rect.top);
            } else if (i == 1) {
                move(rect.left, rect.top - mv.verticalSpeed);
            }
            //Log.d("catUpdatePosition: ", "move: " + i + " : " + (System.currentTimeMillis() - millis));
            //millis = System.currentTimeMillis();
            checkCollision(i);
            //Log.d("catUpdatePosition: ", "collision: " + i + " : " + (System.currentTimeMillis() - millis));
           // move(position.getX() - mv.horizontalSpeed * direction, position.getY() - mv.verticalSpeed);
        }
    }
    protected abstract void specificCatCollision(GameObject g, int collisionType);

    @Override
    protected void specificCollision(GameObject g, int collisionType) {
        if (collisionType == 1 && g instanceof Block) {
            verticalBlockCollision(g);
        } else if ((g instanceof Block || g instanceof Hazard) && collisionType == 0) {
            changeDirection();
        }
        specificCatCollision(g,collisionType);
    }

  /*  @Override
    public void draw() {
        picture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + width, (int) position.getY() + height);
        picture.draw(canvas);
    }*/

    protected abstract void catAction();

    @Override
    public void update() {
        //long millis = System.currentTimeMillis();
        catAction();
        //Log.d("catUpdate ", "catAction: " + (System.currentTimeMillis() - millis));
        //millis = System.currentTimeMillis();
        updateSpeed();
        //Log.d("catUpdate ", "updateSpeed: " + (System.currentTimeMillis() - millis));
        //millis = System.currentTimeMillis();
        updatePosition();
        //Log.d("catUpdate ", "updatePosition: " + (System.currentTimeMillis() - millis));
    /*    checkCollision(1); //ordning på collisionCheck viktig, annars bugg
        checkCollision(0);*/
    }


    public void affectPlayer() {
        world.gameOver();
    }

    protected void changeDirection() {
        direction = direction * -1;
    }
}
