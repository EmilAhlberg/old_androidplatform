package Game;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.emil.app.R;

/**
 * Created by Emil on 2016-12-17.
 */

public class DeathAnimator extends GameObject {
    private Drawable picture;
    private MovementVector mv;

    public DeathAnimator(Mover g) {
        super(g.getPosition(),g.width,g.height);
        addToUpdateList(this);
        picture = board.getResources().getDrawable(R.drawable.cat);
        mv = new MovementVector();
        mv.applyForce(20,400);
    }

    private void addToUpdateList(GameObject g) {
        world.addObject(g);
    }

    @Override
    public void draw() {
        picture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + width, (int) position.getY() + height);
        picture.draw(canvas);
    }

    @Override
    public void update() {
        if(mv.verticalForce > -100) {
            mv.updateSpeed();
            updatePosition();
        } else {
            world.removeObject(this);
        }

    }

    protected void updatePosition() {
        move(position.getX() - mv.horizontalSpeed, position.getY()-mv.verticalSpeed);
       // move(position.getX(), position.getY() - mv.verticalSpeed);
    }
}
