package Game.Movers;

import android.graphics.Rect;

import Game.Framework.World;

/**
 * Created by Emil on 25/08/2017.
 */

public abstract class Collidable extends Mover {

    public Collidable(Rect rect, World world) {
        super(rect, world);
    }

    public abstract void handleCollisionWith(Collidable c);

    public boolean collidesWith(Collidable c2) {

        // skulle kunna returnera en int, typ definierade som attribut här, enligt answer härifrån:
        //
        //https://gamedev.stackexchange.com/questions/74387/platformer-collision-detection-order
        //
        //typ::  public static int RightCollision = 1 osv (så om collision från höger -> returnera rightCollision)
        //kan då returnera -1 om ej kollision,
        //då kan handleCollisionWith veta vilken typ av hantering den ska göra, switcha på collisionType eller nåt.



        //OBS oklart om detta är legit, kollade ej på intersect-metoden
        if (this.getRect().intersect(c2.getRect()))
            return true;
        return false;
    }
}
