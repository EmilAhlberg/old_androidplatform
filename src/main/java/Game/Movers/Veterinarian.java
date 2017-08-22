package Game.Movers;

import android.graphics.drawable.Drawable;

import com.example.emil.app.R;

import java.util.Random;
import Game.*;
import Game.Framework.World;
import Game.InAnimates.Block;
import Game.Util.Position;
import Game.Util.Rectangle;

/**
 * Created by Emil on 2016-12-18.
 */

public class Veterinarian extends GameObject {
    private Drawable picture;
    private Drawable syringePic;
    private Syringe syringe;
    private int reloadTimer;
    private final static int VET_WIDTH = 20;
    private final static int VET_HEIGHT = 40;
    private final int RELOAD_TIME = 75;

    public Veterinarian(Position position, World world) {
        super(new Rectangle(position, VET_WIDTH, VET_HEIGHT), world);
        reloadTimer = new Random().nextInt(75);
        syringe = new Syringe(new Position(getPosition().getX() - 20, getPosition().getY()), world);
        activePicture = world.getGameActivity().getResources().getDrawable(R.drawable.vet);
        activePicture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + getWidth(), (int) position.getY() + getHeight());
    }

  /*  @Override
    public void draw() {
        picture.draw(canvas);
    }*/

    @Override
    public void update() {
        reloadTimer--;
        if (reloadTimer == 0) {
            throwSyringe();
            reloadTimer = RELOAD_TIME;
        }
    }

    private void throwSyringe() {
        world.addObject(new Syringe(new Position(getPosition().getX() - 20, getPosition().getY()), world));
    }

    public void affectPlayer() {
        world.gameOver();
    }

    private class Syringe extends Mover {
        private final static int SYR_WIDTH = 10;
        private final static int SYR_HEIGHT = 20;
        private boolean isActive;

        public Syringe(Position position, World world) {
            super(new Rectangle(position, SYR_WIDTH, SYR_HEIGHT), world);
            activePicture = world.getGameActivity().getResources().getDrawable(R.drawable.syringe);
            isActive = false;
            applyForce(200, 300);
        }

        @Override
        protected void updatePosition() {
            move(getPosition().getX() - mv.horizontalSpeed, getPosition().getY() - mv.verticalSpeed);
        }

        @Override
        protected void updatePicture() {
            activePicture.setBounds((int) getPosition().getX(), (int) getPosition().getY(), (int) getPosition().getX() + getWidth(), (int) getPosition().getY() + getHeight());
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

       /* @Override
        public void draw() {
            syringePic.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + width, (int) position.getY() + height);
            syringePic.draw(canvas);

        }*/

        @Override
        public void update() {
            updateSpeed();
            updatePosition();
            checkCollision(0);
            if (mv.verticalSpeed > 150) {
                world.removeObject(this);
            }
            updatePicture();
        }
    }
}
