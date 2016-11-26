package Game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Emil on 2016-11-22.
 */

public abstract class Mover extends GameObject {
    protected double direction;
    protected final double GRAVITY = -35;
    protected boolean grounded;

    protected double verticalForce, horizontalForce, horizontalAcceleration, verticalAcceleration, horizontalSpeed, verticalSpeed, mass;

    public Mover(Position position, int direction, int mass) {
        super(position);
        this.mass = mass;
        this.direction = direction;
        horizontalForce = horizontalAcceleration = verticalAcceleration = horizontalSpeed = verticalSpeed = 0;
        verticalForce = GRAVITY;
        grounded = false;
    }

    protected void updateSpeed() {
        updateAcceleration();
        verticalForce = GRAVITY;
        horizontalForce = 0;
        //long deltaTime = System.nanoTime() - savedSpeedTime;
        verticalSpeed = verticalSpeed + verticalAcceleration /** deltaTime / 1000000000*/;
        horizontalSpeed = horizontalSpeed + horizontalAcceleration /** deltaTime / 1000000000*/;
        //savedSpeedTime = System.nanoTime();
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

    protected abstract void updatePosition();


    /**
     * Check for any collisions between THIS and any other GameObject. If any collision occurs,
     * they are handled one by one, in the order they were registered.
     */
    protected boolean checkCollision() {
        ArrayList<GameObject> collisions = getIntersectingObjects();
        if (collisions != null) {
            handleCollisions(collisions);
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
                Log.d("COLLISION", this.getClass() + ", " + g.getClass());
            }
        }
        return colliders;
    }

    private void handleCollisions(ArrayList<GameObject> colliders) {
        for (GameObject g : colliders) {
            if (g instanceof Block) {
                move(position.getX()+horizontalSpeed, position.getY() + verticalSpeed /** deltaTime / 1000000000*/);
                verticalAcceleration = 0;
                horizontalSpeed = 0;
                verticalSpeed=0;
                grounded = true;
            }
        }
    }

    public boolean isGrounded() {
        return grounded;
    }

    //lär behöva förbättras
    private boolean intersects(GameObject g) {
        Position g1UpperLeft = new Position(getPosition().getX()-15, getPosition().getY()-15);                                   //:::: HÅRDKODAD OBJEKTSTORLEK :::::
        Position g1LowerRight = new Position(getPosition().getX() + 15,         // getWidth()
                getPosition().getY() + 15);                                     //getHeight

        Position g2UpperLeft = g.getPosition();
        Position g2LowerRight = new Position(g.getPosition().getX() + 18,       //getWidth()
                g.getPosition().getY() + 18);                                   //getHeight

        if (g1UpperLeft.getX() > g2LowerRight.getX() || g1LowerRight.getX() < g2UpperLeft.getX()
                || g1UpperLeft.getY() > g2LowerRight.getY() || g1LowerRight.getY() < g2UpperLeft.getY()) {
            return false;
        }
        return true;
    }





}
