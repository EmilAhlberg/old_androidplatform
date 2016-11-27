package Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Emil on 2016-11-22.
 */

public class GameBlock extends Block {
    protected static Paint paint = new Paint();

    public GameBlock(Position position) {
        super(position);
        paint.setColor(Color.RED);
    }



    @Override
    public void draw() {
        canvas.drawRect((float)position.getX(),(float)position.getY(),(float)(position.getX()+width),(float)(position.getY()+height),paint);


    }

    @Override
    public void update() {

    }

}

