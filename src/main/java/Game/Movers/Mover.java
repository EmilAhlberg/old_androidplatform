package Game.Movers;

import android.graphics.Rect;
import android.util.Log;

import Game.*;
import Game.Framework.LevelCreator;
import Game.Framework.World;
import Game.InAnimates.Block;
import Game.Util.Position;

import java.util.ArrayList;


/**
 * Created by Emil on 2016-11-22.
 */

public abstract class Mover extends GameObject {

    protected boolean grounded;
    protected MovePhysics mv;
    protected int WJDirection;
    private int WJDirectionHelper;
    protected ArrayList<GameObject> objectsCloseBy;

    public Mover(Rect rect, World world) {
        super(rect, world);
        mv = new MovePhysics();
        grounded = false;
        WJDirection = WJDirectionHelper = 0;
        objectsCloseBy = LevelCreator.GameObjectList;
    }

    protected void updateSpeed() {
        mv.updateSpeed();
    }

    protected void applyForce(double horizontalChange, double verticalChange) {
       mv.applyForce(horizontalChange, verticalChange);
    }

    protected void jump(int force) {
        applyForce(0, force);
        grounded = false;
    }

    protected abstract void updatePosition();

    @Override
    public void updateObject() {
        updateObjectsCloseBy();
        update();
    }

    protected void updateObjectsCloseBy() {
        //long millis = System.currentTimeMillis();
        ArrayList<GameObject> tempGameObjects = world.createTempGameObjects();

        //borde fixa så att bara närliggande objekt ligger i tempGameObjects
        for (int i = 0; i < tempGameObjects.size(); i++) { //for performance
            GameObject g = tempGameObjects.get(i);
            Rect gRect = g.getRect();
            double dist = Math.sqrt(Math.pow(gRect.left - rect.left, 2) + Math.pow(gRect.top - rect.top, 2));
            if (dist > gRect.height()+gRect.width()+rect.height()+rect.width())
                tempGameObjects.remove(i);
        }
        //Log.d("moverUOCB ", "createTempGameObjects: " + (System.currentTimeMillis() - millis));
        objectsCloseBy = tempGameObjects;
    }

    /**
     * Check for any collisions between THIS and any other GameObject. If any collision occurs,
     * they are handled one by one, in the order they were registered.
     * @param vOrH - 0 handles horizontal collisions
     *             - 1 handles vertical collisions
     */
    protected boolean checkCollision(int vOrH) {
        //long millis = System.currentTimeMillis();
        ArrayList<GameObject> collisions = getIntersectingObjects();
        //Log.d("moverCheckCollision ", "getIntersectingObjects: " + (System.currentTimeMillis() - millis));
        //millis = System.currentTimeMillis();
        if (collisions.size() > 0) {
            handleCollisions(collisions, vOrH);
            //Log.d("moverCheckCollision ", "handleCollisions: " + collisions.size() + " : " + (System.currentTimeMillis() - millis));
            return true;
        }
        return false;
    }

    /**
     * Checks whether or not the Movers intersects with any game object, by calling the mover-object's
     * intersects method.
     */
    protected ArrayList<GameObject> getIntersectingObjects() {
        //long millis = System.currentTimeMillis();
        ArrayList<GameObject> colliders = new ArrayList<GameObject>();
        for (GameObject g : objectsCloseBy) {
            if (this != g && intersects(g)) {
                colliders.add(g);
            }
        }
        //Log.d("moverGIO ", "intersects: " + (System.currentTimeMillis() - millis));
        return colliders;
    }

    private void handleCollisions(ArrayList<GameObject> colliders, int collisionType) {
        for (GameObject g : colliders) {
            specificCollision(g, collisionType);
        }
    }

    protected void horizontalBlockCollision(GameObject g) {
        double tempSpeed = mv.horizontalSpeed;
        move(rect.left + tempSpeed, rect.top);
        WJDirection = WJDirectionHelper;
        WJDirectionHelper = (int)(-tempSpeed/Math.abs(tempSpeed));
        mv.horizontalAcceleration = 0;
        mv.horizontalSpeed = 0;
    }

    protected void verticalBlockCollision(GameObject g) {
        grounded = bottomIntersection(g);   //tvek om detta fungerar, även enda stället bottomIntersection anropas från
        mv.verticalAcceleration = 0;
        mv.verticalSpeed = 0;
        if (grounded) {
            move(rect.left, g.getRect().top - rect.height());
        } else {
            move(rect.left, g.getRect().bottom);
        }
    }
    /**
     * Handles collision consequences for specific movers.
     * @param g  GameObject which mover collides with.
     */
    protected abstract void specificCollision(GameObject g, int collisionType);

    public boolean isGrounded() {
        return grounded;
    }

    public int getWJDirection() { return WJDirection; }

    /*
        normal intersection check between two rectangular objects.
     */
    private boolean intersects(GameObject g) {
        return Rect.intersects(rect, g.getRect());
    }
    /*
        special intersection check between the bottom half rectangle of 'this',
                    and upper half of 'g' (collision from "above")
     */
    protected boolean bottomIntersection(GameObject g) {
        Rect gRect = g.getRect();
        Rect temp = new Rect(rect.left, rect.top + rect.height()/2, rect.right, rect.bottom + gRect.height()/2); //En halv extra på rect.bottom eftersom den redan är uppflyttad? Mycket oklart
        return Rect.intersects(temp, gRect);
        //return checkRectIntersection(getRectBounds(g,1));
    }

    /*private Position[] getRectBounds(GameObject g, int collisionType) {
        Rect gRect = g.getRect();
        int gHeight = gRect.height();
        Position[] v = new Position[4];
        v[0] = new Position(rect.left, rect.top+collisionType*rect.height()/2);
        v[1]= new Position(rect.right,
                rect.bottom + collisionType*gHeight/2);  // ::::mode*g.height/2:::: eller liknande krävs för intersection i mode = 1,
        v[2]= new Position(gRect.left, gRect.top);
        v[3]= new Position(gRect.right,
                gRect.bottom-collisionType*gHeight/2);
        return v;
    }

    private boolean checkRectIntersection(Position[] v) {
        if (v[0].getX() > v[3].getX() || v[1].getX() < v[2].getX()
                || v[0].getY() > v[3].getY() || v[1].getY() < v[2].getY()) {
            return false;
        }
        return true;
    }*/
}
