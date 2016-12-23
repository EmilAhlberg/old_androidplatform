package Game;

import android.graphics.drawable.Drawable;

import com.example.emil.app.R;

import java.util.Random;

/**
 * Created by Emil on 2016-12-23.
 */

public class SuicideCat extends Cat {
    Random rdm;

    public SuicideCat(Position position) {
        super(position, gameActivity.getResources().getDrawable(R.drawable.suicidecat));
        rdm = new Random();
        //changeDirection();
    }

    @Override
    protected void specificCatCollision(GameObject g, int collisionType) {
        if (collisionType == 1 && g instanceof Player && mv.verticalSpeed>0) {
            ((Player)g).kill(this);
        }
    }

    @Override
    protected void catAction() {
        if (rdm.nextInt(10) > 8 && grounded) {
            jump(400);
        }
        if (mv.verticalSpeed > 150) {
            world.removeObject(this);
        }
    }

}
