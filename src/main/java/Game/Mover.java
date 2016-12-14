package Game;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.example.emil.Framework.GameOver;

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
     * @param vOrH - 0 handles vertical collisions  (horizontal egentligen?)
     *             - 1 handles horizontal collisions
     */
    protected boolean checkCollision(int vOrH) {
        ArrayList<GameObject> collisions = getIntersectingObjects();
        if (collisions.size() > 0) {
            handleCollisions(collisions, vOrH);
            return true;
        }
        if(this instanceof GameObjectProbe) {
            return false;
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
            if (this != g && intersects(g)) {
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
            case 2: //om ingen handling behövs
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
                grounded = bottomIntersection(g);   //kontinuerlig check krävs
                mv.verticalAcceleration = 0;
                mv.verticalSpeed = 0;
                if (grounded) {
                    move(position.getX(), g.getPosition().getY() - height-1);
                } else {
                    move(position.getX(), g.getPosition().getY() + g.height+1);
                }
            } else {
                specificCollision(g);
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
                specificCollision(g);
            }
        }
    }
    /**
     * Handles collision consequences for specific movers.
     * @param g  GameObject which mover collides with.
     */
    protected abstract void specificCollision(GameObject g);

    public boolean isGrounded() {
        return grounded;
    }

    /*
        normal intersection check between two rectangular objects.
     */
    private boolean intersects(GameObject g) {
       return(checkRectIntersection(getRectBounds(g,0)));
    }
    /*
        special intersection check between the bottom half rectangle of 'this',
                    and upper half of 'g' (collision from "above")
     */
    protected boolean bottomIntersection(GameObject g) {
        return checkRectIntersection(getRectBounds(g,1));
    }

    private Position[] getRectBounds(GameObject g, int collisionType) {
        Position[] v = new Position[4];
        v[0] = new Position(getPosition().getX(), getPosition().getY()+collisionType*height/2);
        v[1]= new Position(getPosition().getX() + width,
                getPosition().getY() + height + collisionType*g.height/2);  // ::::mode*g.height/2:::: eller liknande krävs för intersection i mode = 1,
        v[2]= g.getPosition();
        v[3]= new Position(g.getPosition().getX() + g.width,
                g.getPosition().getY() + g.height-collisionType*g.height/2);
        return v;
    }

    private boolean checkRectIntersection(Position[] v) {
        if (v[0].getX() > v[3].getX() || v[1].getX() < v[2].getX()
                || v[0].getY() > v[3].getY() || v[1].getY() < v[2].getY()) {
            return false;
        }
        return true;
    }
}
