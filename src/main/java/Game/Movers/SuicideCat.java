package Game.Movers;

import com.example.emil.app.R;

import java.util.Random;
import Game.*;
import Game.Framework.World;
import Game.Util.Position;

/**
 * Created by Emil on 2016-12-23.
 */

public class SuicideCat extends Cat {
    private Random rdm;
    private int jumpTimer;
    private final int JUMP_TIME = 80;

    public SuicideCat(Position position, World world) {
        super(position, world.getGameActivity().getResources().getDrawable(R.drawable.suicidecat), world);
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
