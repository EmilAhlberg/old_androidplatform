package Game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Emil on 2016-11-22.
 */

public abstract class Mover extends GameObject {

    protected final double GRAVITY = -35;
    protected boolean grounded;

    protected double verticalForce, horizontalForce, horizontalAcceleration, verticalAcceleration, horizontalSpeed, verticalSpeed, mass;

    public Mover(Position position, int mass, int width, int height) {
        super(position, width, height);
        this.mass = mass;

        horizontalForce = horizontalAcceleration = verticalAcceleration = horizontalSpeed = verticalSpeed = 0;
        verticalForce = GRAVITY;
        grounded = false;
    }

    protected void updateSpeed() {
        updateAcceleration();
        verticalForce = GRAVITY;
        horizontalForce = 0;
        verticalSpeed = verticalSpeed + verticalAcceleration;
        horizontalSpeed = horizontalSpeed + horizontalAcceleration;
    }

    //OBS: privat nu,  kanske behöver ändras
    private void updateAcceleration() {
        verticalAcceleration = verticalForce / mass;
        horizontalAcceleration = horizontalForce / mass;
    }

    public void applyForce(double horizontalChange, double verticalChange) {
        verticalForce += verticalChange;
        horizontalForce += horizontalChange;
    }

    protected abstract void updatePosition(int vOrH);


    /**
     * Check for any collisions between THIS and any other GameObject. If any collision occurs,
     * they are handled one by one, in the order they were registered.
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
     * Checks whether or not the Movers intersects with any game object, by calling the mover-objects
     * intersects method.
     */
    protected ArrayList<GameObject> getIntersectingObjects() {
        ArrayList<GameObject> tempGameObjects = world.createTempGameObjects();
        //ArrayList<Mover> tempMovers = world.createTempMovers();
        ArrayList<GameObject> colliders = new ArrayList<GameObject>();
        for (GameObject g : tempGameObjects) {
            if (this != g && intersects(g)) {
                colliders.add(g);
                //Log.d("COLLISION", this.getClass() + ", " + g.getClass());
            }
        }
        return colliders;
    }

//    private void handleCollisions(ArrayList<GameObject> colliders, int vOrH) {
//        for (GameObject g : colliders) {
//            if (g instanceof Block) {
//                if (vOrH == 0) {
//                    move(position.getX() + horizontalSpeed, position.getY());
//                    horizontalAcceleration = 0;
//                    horizontalSpeed = 0;
//                    grounded = true;
//                } else if (vOrH == 1) {
//                    move(position.getX(), position.getY() + verticalSpeed);
//                    verticalAcceleration = 0;
//                    verticalSpeed = 0;
//                    grounded = true;
//                }
//            }
//        }
//    }
private void handleCollisions(ArrayList<GameObject> colliders, int vOrH) {
    for (GameObject g : colliders) {
        if (g instanceof Block) {
            if (vOrH == 0) {
                move(position.getX() + horizontalSpeed, position.getY());
                horizontalAcceleration = 0;
                horizontalSpeed = 0;
                grounded = true;
            } else if (vOrH == 1) {
                move(position.getX(), position.getY() + verticalSpeed);
                verticalAcceleration = 0;
                verticalSpeed = 0;
                grounded = true;
            }
        }
    }
}

    public boolean isGrounded() {
        return grounded;
    }

    private boolean intersects(GameObject g) {
        Position g1UpperLeft = new Position(getPosition().getX(), getPosition().getY());
        Position g1LowerRight = new Position(getPosition().getX() + width,
                getPosition().getY() + height);



        Position g2UpperLeft = g.getPosition();
        Position g2LowerRight = new Position(g.getPosition().getX() + g.width,
                g.getPosition().getY() + g.height);

        if (g1UpperLeft.getX() > g2LowerRight.getX() || g1LowerRight.getX() < g2UpperLeft.getX()
                || g1UpperLeft.getY() > g2LowerRight.getY() || g1LowerRight.getY() < g2UpperLeft.getY()) {
            return false;
        }
        //Log.d("G1", this.getClass()+"left: " + g1UpperLeft.getX() + ". right: " + g1LowerRight.getX());
        //Log.d("G2", g.getClass()+"left: " + g2UpperLeft.getX() + ". right: " + g2LowerRight.getX());

        return true;
    }
}
