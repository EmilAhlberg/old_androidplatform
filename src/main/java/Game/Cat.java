package Game;

import android.graphics.drawable.Drawable;

import com.example.emil.app.R;

/**
 * Created by Emil on 2016-12-14.
 */

public abstract class Cat extends Mover {
    private Drawable picture;
    private int direction;


    public Cat(Position p, Drawable d) {
        super(p, 20, 20);
        applyForce(35, 0);
        direction = 1;
        picture = d;
    }

    @Override
    protected void updatePosition() {
        move(position.getX() - mv.horizontalSpeed * direction, position.getY() - mv.verticalSpeed);
    }

    @Override
    protected void specificCollision(GameObject g, int collisionType) {
        if (collisionType == 1 && g instanceof Block) {
            verticalBlockCollision(g);
        } else if ((g instanceof Block || g instanceof Hazard) && collisionType == 0) {
            changeDirection();
        }
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
        checkCollision(1); //ordning på collisionCheck viktig, annars bugg
        checkCollision(0);
    }

    protected GameObjectProbe probePath() {
        int probeXOffset = direction * -2 + width * direction;
        int probeYOffset = height + 2;
        if (direction > 0) {
            probeXOffset = probeXOffset - width;
        }
        GameObjectProbe probe = new GameObjectProbe(new Position(position.getX() - probeXOffset, position.getY() + probeYOffset), 2, 5);
        probe.setClearPath(probe.checkCollision(0));
        return probe;
    }

    public void affectPlayer() {
        world.gameOver();
    }

    protected void changeDirection() {
        direction = direction * -1;
    }
}
