package Game;

import android.content.Intent;

import com.example.emil.app.R;

import com.example.emil.Framework.ActivityConstants;
import com.example.emil.Framework.SplashScreen;

/**
 * Created by Emil on 2016-12-03.
 */

public class Goal extends GameObject {
/*    protected static Paint paint = new Paint();*/
    protected boolean goalReached = false;

    public Goal(Position position, World world) {
        super(position, 20, 20, world);
        /*paint.setColor(Color.MAGENTA);*/
        activePicture = world.getGameActivity().getResources().getDrawable(R.drawable.loading);
        activePicture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + width, (int) position.getY() + height);
    }
    /*@Override
    public void draw() {
        canvas.drawRect((float)position.getX(),(float)position.getY(),(float)(position.getX()+width),(float)(position.getY()+height),paint);
    }*/
    public void playerReachedGoal() {
        goalReached=true;
    }

    @Override
    public void update() {
        if (goalReached) {
            world.pauseGame();
            Intent intent = new Intent(world.getGameActivity(), SplashScreen.class);
            intent.putExtra("level",World.Level);
            intent.putExtra("activityID", ActivityConstants.LEVELCLEARED);
            world.getGameActivity().startActivity(intent);
        }
    }
}
