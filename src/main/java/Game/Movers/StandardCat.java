package Game.Movers;

import com.example.emil.app.R;
import Game.*;
import Game.Framework.World;
import Game.InAnimates.Hazard;
import Game.Util.GameObjectProbe;
import Game.Util.Position;

/**
 * Created by Emil on 2016-12-23.
 */

public class StandardCat extends Cat {

    public StandardCat(Position p, World world) {
        super(p, world);
    }

    @Override
    protected void specificCatCollision(GameObject g,int collisionType) {
        //redundant
    }

    @Override
    protected void catAction() {
        GameObjectProbe probe = probePath();
        if (!(probe.objectAhead()) || probe.getLatestCollider() instanceof Hazard) {
            changeDirection();
        }
    }

    protected GameObjectProbe probePath() {
        int width = rect.width();
        int probeXOffset = direction * -2 + width * direction;
        int probeYOffset = rect.height() + 2;
        if (direction > 0) {
            probeXOffset = probeXOffset - width;
        }
        GameObjectProbe probe = new GameObjectProbe(new Position(rect.left - probeXOffset, rect.top + probeYOffset), 2, 5, world);
        probe.setClearPath(probe.checkCollision(0));
        return probe;
    }
}
