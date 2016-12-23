package Game;

import android.graphics.drawable.Drawable;

import com.example.emil.app.R;

/**
 * Created by Emil on 2016-12-14.
 */

public abstract class Cat extends Mover {
    private Drawable picture;
    protected int direction;


    public Cat(Position p, Drawable d) {
        super(p, 20, 20);
        applyForce(35, 0);
        direction = 1;
        picture = d;
    }

    @Override
    protected void updatePosition() {
        for (int i = 1; i >=0 ; i--) {
            if (i == 0) {
                move(position.getX() - mv.horizontalSpeed*direction, position.getY());
            } else if (i == 1) {
                move(position.getX(), position.getY() - mv.verticalSpeed);
            }
            checkCollision(i);
           // move(position.getX() - mv.horizontalSpeed * direction, position.getY() - mv.verticalSpeed);
        }
    }
    protected abstract void specificCatCollision(GameObject g,int collisionType);

    @Override
    protected void specificCollision(GameObject g, int collisionType) {
        if (collisionType == 1 && g instanceof Block) {
            verticalBlockCollision(g);
        } else if ((g instanceof Block || g instanceof Hazard) && collisionType == 0) {
            changeDirection();
        }
        specificCatCollision(g,collisionType);
    }

    @Override
    public void draw() {
        picture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + width, (int) position.getY() + height);
        picture.draw(canvas);
    }

    protected abstract void catAction();

    @Override
    public void update() {
        catAction();
        updateSpeed();
        updatePosition();
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
