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
     * Checks whether or not the Movers intersects with any game object, by calling the mover-object's
     * intersects method.
     */
    protected ArrayList<GameObject> getIntersectingObjects() {
        ArrayList<GameObject> tempGameObjects = world.createTempGameObjects();
        //ArrayList<Mover> tempMovers = world.createTempMovers();
        ArrayList<GameObject> colliders = new ArrayList<GameObject>();
        for (GameObject g : tempGameObjects) {
            if (this != g && intersects(g, 0)) {
                colliders.add(g);
                //Log.d("COLLISION", this.getClass() + ", " + g.getClass());
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
        for (GameObject g : colliders) {
            if (g instanceof Block) {
                move(position.getX(), position.getY() + verticalSpeed);
                verticalAcceleration = 0;
                verticalSpeed = 0;
                grounded = intersects(g,1);                     //      <---- borde bara ske om objekten intersectar enl: "players nedre halva med g övre halva", annars kan man hänga i taket
                //System.out.println(grounded);
            }
        }
    }

    private void handleHorizontalCollision(ArrayList<GameObject> colliders) {

        for (GameObject g : colliders) {
            if (g instanceof Block) {
                move(position.getX() + horizontalSpeed, position.getY());
                horizontalAcceleration = 0;
                horizontalSpeed = 0;
                //grounded = intersects(g,1);                    //     <--- löser infinite wall jump
            }
        }

    }

    public boolean isGrounded() {
        return grounded;
    }

    /*
        Mode:   0 - normal intersection check between two rectangular objects.
                1 - special intersection check between the bottom half rectangle of 'this',
                    and upper half of 'g'
     */
    private boolean intersects(GameObject g, int mode) {
        Position g1UpperLeft = new Position(getPosition().getX(), (getPosition().getY() + mode * width / 2));
        Position g1LowerRight = new Position(getPosition().getX() + width,
                getPosition().getY() + height + mode*g.height/2);                              // mode*g.height/2 eller liknande krävs för intersection i mode = 1,


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
