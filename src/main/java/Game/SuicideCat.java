package Game;

import android.graphics.drawable.Drawable;

import com.example.emil.app.R;

import java.util.Random;

/**
 * Created by Emil on 2016-12-23.
 */

public class SuicideCat extends Cat {
    private Random rdm;
    private int jumpTimer;
    private final int JUMP_TIME = 80;

    public SuicideCat(Position position) {
        super(position, gameActivity.getResources().getDrawable(R.drawable.suicidecat));
        jumpTimer= new Random().nextInt(100);
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
        if (jumpTimer == JUMP_TIME && grounded) {
            jump(400);
            jumpTimer = 0;
        } else {
            jumpTimer++;
        }
        if (mv.verticalSpeed > 150) {
            world.removeObject(this);
        }
    }

}
