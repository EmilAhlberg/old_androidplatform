package Game;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Emil on 2016-11-22.
 */

public class EdgeBlock extends Block {
    protected static Paint paint = new Paint();

    public EdgeBlock(Position position) {
        super(position);
        paint.setColor(Color.BLACK);
    }

    @Override
    public void draw() {
        canvas.drawRect((float)position.getX(),(float)position.getY(),(float)(position.getX()+18),(float)(position.getY()+18),paint);

    }

    @Override
    public void update() {

    }
}
