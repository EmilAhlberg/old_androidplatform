package Game;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.emil.app.Board;
import com.example.emil.app.LevelCleared;

/**
 * Created by Emil on 2016-12-03.
 */

public class Goal extends GameObject {
    protected static Paint paint = new Paint();
    protected boolean goalReached = false;

    public Goal(Position position) {
        super(position, 20, 20);
        paint.setColor(Color.MAGENTA);

    }
    @Override
    public void draw() {
        canvas.drawRect((float)position.getX(),(float)position.getY(),(float)(position.getX()+width),(float)(position.getY()+height),paint);
    }
    public void playerReachedGoal() {
        goalReached=true;
    }

    @Override
    public void update() {
        if (goalReached) {
            canvas.drawColor(Color.BLACK);
            world.pauseGame();
            Intent intent = new Intent(board, LevelCleared.class);
            intent.putExtra("Level",world.getLevel());
            board.startActivity(intent);

            //world.startGame();
        }
    }
}
