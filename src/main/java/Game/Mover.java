package Game;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.example.emil.app.GameOver;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by Emil on 2016-11-22.
 */

public abstract class Mover extends GameObject {

    protected boolean grounded;
    protected MovementVector mv;

    public Mover(Position position, int width, int height) {
        super(position, width, height);

        mv = new MovementVector();
        grounded = false;
    }

    protected void updateSpeed() {
        mv.updateSpeed();
    }

    protected void applyForce(double horizontalChange, double verticalChange) {
       mv.applyForce(horizontalChange, verticalChange);
    }

    protected abstract void updatePosition();

    /**
     * Check for any collisions between THIS and any other GameObject. If any collision occurs,
     * they are handled one by one, in the order they were registered.
     * @param vOrH - 0 handles vertical collisions
     *             - 1 handles horizontal collisions
     */
    protected boolean checkCollision(int vOrH) {
        ArrayList<GameObject> collisions = getIntersectingObjects();
        if (collisions != null) {
            handleCollisions(collisions, vOrH);
            return true;
        }
        return false;
    }

    /**
     * Checks whether or not the Movers intersects with any game object, by calling the mover-object's
     * intersects method.
     */
    protected ArrayList<GameObject> getIntersectingObjects() {
        ArrayList<GameObject> tempGameObjects = world.createTempGameObjects();
        ArrayList<GameObject> colliders = new ArrayList<GameObject>();
        for (GameObject g : tempGameObjects) {
            if (this != g && intersects(g, 0)) {
                colliders.add(g);
            }
        }
        return colliders;
    }

    private void handleCollisions(ArrayList<GameObject> colliders, int vOrH) {
        switch (vOrH) {
            case 0:
                handleHorizontalCollision(colliders);
                break;
            case 1:
                handleVerticalCollision(colliders);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }


    private void handleVerticalCollision(ArrayList<GameObject> colliders) {
        Iterator<GameObject> itr = colliders.iterator();
        while (itr.hasNext()) {
            GameObject g = itr.next();
            if (g instanceof Block) {
                /////////NYTT!!!
                if (!grounded) {
                /////////
                    grounded = intersects(g, 1);
                }
                mv.verticalAcceleration = 0;
                mv.verticalSpeed = 0;
                /////////////////////////////////NYTT!!!
                if (grounded) {
                    move(position.getX(), g.getPosition().getY() - height-1);
                } else {
                    move(position.getX(), g.getPosition().getY() + g.height+1);
                }
                /////////////////////////////////
                // move(position.getX(), position.getY() + mv.verticalSpeed); GAMMALT!!!

            } else if (g instanceof Hazard) {
                canvas.drawColor(Color.BLACK);
                world.pauseGame();
                Intent intent = new Intent(board, GameOver.class);
                intent.putExtra("Level",world.getLevel());
                board.startActivity(intent);
            } else {
                specificCollision(0,g );
            }
        }
    }

    private void handleHorizontalCollision(ArrayList<GameObject> colliders) {
        for (GameObject g : colliders) {
            if (g instanceof Block) {
                move(position.getX() + mv.horizontalSpeed, position.getY());
                mv.horizontalAcceleration = 0;
                mv.horizontalSpeed = 0;
            }
            else {
                specificCollision(1, g);
            }
        }
    }

    /**
     * Handles collision consequences for specific movers.
     * @param collisionType Int, which gives type of collision, 0 for vertical, 1 for horizontal.
     * @param g  GameObject which mover collides with.
     */
    protected abstract void specificCollision(int collisionType, GameObject g);

    public boolean isGrounded() {
        return grounded;
    }

    /*
        Mode:   0 - normal intersection check between two rectangular objects.
                1 - special intersection check between the bottom half rectangle of 'this',
                    and upper half of 'g' (collision from "above")
     */
    private boolean intersects(GameObject g, int mode) {
        Position g1UpperLeft = new Position(getPosition().getX(), (getPosition().getY() + mode * width / 2));
        Position g1LowerRight = new Position(getPosition().getX() + width,
                getPosition().getY() + height + mode*g.height/2);                              // ::::mode*g.height/2:::: eller liknande krävs för intersection i mode = 1,
        Position g2UpperLeft = g.getPosition();
        Position g2LowerRight = new Position(g.getPosition().getX() + g.width,
                g.getPosition().getY() + g.height - (g.height / 2) * mode);

        if (g1UpperLeft.getX() > g2LowerRight.getX() || g1LowerRight.getX() < g2UpperLeft.getX()
                || g1UpperLeft.getY() > g2LowerRight.getY() || g1LowerRight.getY() < g2UpperLeft.getY()) {
            return false;
        }
        return true;
    }
}
