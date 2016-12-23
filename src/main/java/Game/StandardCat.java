package Game;

import android.graphics.drawable.Drawable;

import com.example.emil.app.R;

/**
 * Created by Emil on 2016-12-23.
 */

public class StandardCat extends Cat {

    public StandardCat(Position p) {
        super(p, gameActivity.getResources().getDrawable(R.drawable.cat));
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
        int probeXOffset = direction * -2 + width * direction;
        int probeYOffset = height + 2;
        if (direction > 0) {
            probeXOffset = probeXOffset - width;
        }
        GameObjectProbe probe = new GameObjectProbe(new Position(position.getX() - probeXOffset, position.getY() + probeYOffset), 2, 5);
        probe.setClearPath(probe.checkCollision(0));
        return probe;
    }
}
