package Game;

import android.graphics.drawable.Drawable;

import com.example.emil.app.R;

import java.util.Random;

/**
 * Created by Emil on 2016-12-18.
 */

public class Vetrinarian extends GameObject {
    private Drawable picture;
    private Drawable syringePic;
    private int reloadTimer;
    private final int RELOAD_TIME = 75;

    public Vetrinarian(Position position) {
        super(position, 20, 40);
        picture = gameActivity.getResources().getDrawable(R.drawable.vet);
        syringePic = gameActivity.getResources().getDrawable(R.drawable.syringe);
        reloadTimer = new Random().nextInt(75);
        picture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + width, (int) position.getY() + height);
    }

    @Override
    public void draw() {
        picture.draw(canvas);
    }

    @Override
    public void update() {
        reloadTimer--;
        if (reloadTimer == 0) {
            throwSyringe();
            reloadTimer = RELOAD_TIME;
        }
    }

    private void throwSyringe() {
        world.addObject(new Syringe(new Position(position.getX() - 20, position.getY())));
    }

    public void affectPlayer() {
        world.gameOver();
    }

    private class Syringe extends Mover {


        public Syringe(Position position) {
            super(position, 10, 20);
            applyForce(200, 300);
        }

        @Override
        protected void updatePosition() {
            move(position.getX() - mv.horizontalSpeed, position.getY() - mv.verticalSpeed);
        }

        @Override
        protected void specificCollision(GameObject g, int collisionType) {
            if (g instanceof Block) {
                world.removeObject(this);
            } else if (g instanceof Player) {
                ((Player) g).sedated();
                world.removeObject(this);
            } else {
                world.removeObject(this);
            }
        }

        @Override
        public void draw() {
            syringePic.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + width, (int) position.getY() + height);
            syringePic.draw(canvas);

        }

        @Override
        public void update() {
            updateSpeed();
            updatePosition();
            checkCollision(0);
            if (mv.verticalSpeed > 150) {
                world.removeObject(this);
            }
        }
    }
}
