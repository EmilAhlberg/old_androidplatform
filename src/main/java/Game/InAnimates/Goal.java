package Game.InAnimates;

import android.app.Activity;
import android.content.Intent;

import com.example.emil.app.R;
import Game.*;
import Game.Framework.World;
import Game.Util.Position;
import Game.Util.Rectangle;

import com.example.emil.Framework.ActivityConstants;
import com.example.emil.Framework.ActivityHandler;

/**
 * Created by Emil on 2016-12-03.
 */

public class Goal extends GameObject {
/*    protected static Paint paint = new Paint();*/
    protected boolean goalReached = false;
    private final static int GOAL_SIZE = 20;

    public Goal(Position position, World world) {
        super(new Rectangle(position.getX(), position.getY(), GOAL_SIZE, GOAL_SIZE), world);
        /*paint.setColor(Color.MAGENTA);*/
        activePicture = world.getGameActivity().getResources().getDrawable(R.drawable.loading);
        activePicture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + getWidth(), (int) position.getY() + getHeight());
    }

    public void playerReachedGoal() {
        goalReached=true;
    }

    @Override
    public void update() {
        if (goalReached) {
            world.pauseGame();
            Intent intent = new Intent(world.getGameActivity().getApplicationContext(), ActivityHandler.class);
            intent.putExtra("level",World.Level);
            intent.putExtra("ActivityConstant", ActivityConstants.LEVELCLEARED);
            world.getGameActivity().startActivity(intent);
            world.getGameActivity().finish();
        }
    }
}
