package Game;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Emil on 2016-12-03.
 */

public class Goal extends GameObject {
    protected static Paint paint = new Paint();

    public Goal(Position position) {
        super(position, 20, 20);
        paint.setColor(Color.MAGENTA);
    }
    @Override
    public void draw() {
        canvas.drawRect((float)position.getX(),(float)position.getY(),(float)(position.getX()+width),(float)(position.getY()+height),paint);
    }

    @Override
    public void update() {

    }
}
