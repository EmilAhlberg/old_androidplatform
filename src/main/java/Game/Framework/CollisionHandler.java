package Game.Framework;

import java.util.List;

import Game.Movers.Collidable;

/**
 * Created by Emil on 25/08/2017.
 */

public class CollisionHandler {

    /*
    Could send in a list of collidable (movers), and a list of collidable inanimates. Inanimates such as blocks shouldn't need to handle a collision
    from their perspective. Mat för tanken.
     */
    public static void CheckCollisions(List<Collidable> list) {
        for (Collidable c1 : list) {
            for (Collidable c2 : list) {
                if (c1 != c2) {
                    if(c1.collidesWith(c2)) {
                        handleCollision(c1, c2);
                    }
                }
            }
        }
    }

    private static void handleCollision(Collidable c1, Collidable c2) {
        c1.handleCollisionWith(c2);
        c2.handleCollisionWith(c1);
    }
}
