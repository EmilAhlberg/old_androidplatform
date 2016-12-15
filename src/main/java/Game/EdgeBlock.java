package Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.example.emil.app.R;

/**
 * Created by Emil on 2016-11-22.
 */

public class EdgeBlock extends Block {
    protected static Paint paint = new Paint();
    private Drawable picture;

    public EdgeBlock(Position position) {
        super(position);
        paint.setColor(Color.BLACK);
        picture = board.getResources().getDrawable(R.drawable.block1);
    }

    @Override
    public void draw() {
        //canvas.drawRect((float)position.getX(),(float)position.getY(),(float)(position.getX()+width),(float)(position.getY()+height),paint);
        picture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() +width, (int) position.getY() + height);
        picture.draw(canvas);
    }

    @Override
    public void update() {

    }
}
