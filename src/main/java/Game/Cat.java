package Game;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.emil.app.R;

/**
 * Created by Emil on 2016-12-14.
 */

public class Cat extends Mover {
    private Drawable picture;


    public Cat(Position p) {
        super(p, 20, 20);
        applyForce(15,0);
        picture = board.getResources().getDrawable(R.drawable.cat);
    }


    @Override
    protected void updatePosition() {
        move(position.getX() - mv.horizontalSpeed, position.getY()-mv.verticalSpeed);
    }

    @Override
    protected void specificCollision(int collisionType, GameObject g) {

    }

    @Override
    public void draw() {
        picture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + width, (int) position.getY() + height);
        picture.draw(canvas);

    }

    @Override
    public void update() {
        updateSpeed();
        updatePosition();
        //checkCollision(0); //bugg
        checkCollision(1);
    }
}
