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
    protected void catAction() {
        GameObjectProbe probe = probePath();
        if (!(probe.objectAhead()) || probe.getLatestCollider() instanceof Hazard) {
            changeDirection();
        }
    }
}
