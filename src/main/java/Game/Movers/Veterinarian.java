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
    private Syringe syringe;
    private int reloadTimer;
    private final static int VET_WIDTH = 20;
    private final static int VET_HEIGHT = 40;
    private final int RELOAD_TIME = 75;

    public Veterinarian(Position position, World world) {
        super(new Rectangle(position.getX(), position.getY(), VET_WIDTH, VET_HEIGHT), world);
        reloadTimer = new Random().nextInt(75);
        syringe = new Syringe(new Position(getX() - 20, getY()), world);
        activePicture = world.getGameActivity().getResources().getDrawable(R.drawable.vet);
        activePicture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + getWidth(), (int) position.getY() + getHeight());
    }

    @Override
    public void update() {
//        reloadTimer--;
//        if (reloadTimer == 0) {
//            syringe.activate();
//            reloadTimer = RELOAD_TIME;
//        }

    }

    public void affectPlayer() {
        world.gameOver();
    }

    public Syringe getSyringe() {
        return syringe;
    }

    private class Syringe extends Mover {
        private final static int SYR_WIDTH = 10;
        private final static int SYR_HEIGHT = 20;
        private Position startingPos;
        private boolean isActive;

        public Syringe(Position position, World world) {
            super(new Rectangle(position.getX(), position.getY(), SYR_WIDTH, SYR_HEIGHT), world);
            startingPos = position;
            activePicture = world.getGameActivity().getResources().getDrawable(R.drawable.syringe);
            isActive = false;
        }

        public void activate() {
            applyForce(200, 300);
            isActive = true;
        }

        @Override
        protected void updatePosition() {
            move(getX() - mv.horizontalSpeed, getY() - mv.verticalSpeed);
        }

        @Override
        protected void specificCollision(GameObject g, int collisionType) {
            boolean collision = false;
            if (g instanceof Block) {
                collision = true;
            } else if (g instanceof Player) {
                ((Player) g).sedated();
                collision = true;
            } else if (getY() < -10)
                collision = true;
            else {
                collision = true;
            }
            if (collision) {
                isActive = false;
                move(startingPos.getX(), startingPos.getY());
                mv.horizontalForce=0;
                mv.verticalForce = 0;
            }
        }

        @Override
        public void update() {
            if (isActive) {
                updateSpeed();
                updatePosition();
                checkCollision(0);
                if (mv.verticalSpeed > 150) {
                    world.removeObject(this);
                }
            }
        }
    }
}
