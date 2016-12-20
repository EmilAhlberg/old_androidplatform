package Game;

import android.graphics.drawable.Drawable;

import com.example.emil.app.R;

/**
 * Created by Emil on 2016-12-17.
 */

public class DeathAnimator extends GameObject {
    private Drawable picture;
    private MovementVector mv;

    public DeathAnimator(GameObject g) {
        super(g.getPosition(), g.width, g.height);
        world.addObject(this);
        initializeImage(g);

        mv = new MovementVector();
        mv.applyForce(20, 400);
    }

    private void initializeImage(GameObject g) {
        if (g instanceof Cat) {
            picture = gameActivity.getResources().getDrawable(R.drawable.cat);
        } else if (g instanceof Vetrinarian) {
            picture = gameActivity.getResources().getDrawable(R.drawable.vet); //kan vara andra objekt        }
        }
    }


    @Override
    public void draw() {
        picture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + width, (int) position.getY() + height);
        picture.draw(canvas);
    }

    @Override
    public void update() {
        if (mv.verticalForce > -100) {
            mv.updateSpeed();
            updatePosition();
        } else {
            world.removeObject(this);
        }

    }

    protected void updatePosition() {
        move(position.getX() - mv.horizontalSpeed, position.getY() - mv.verticalSpeed);
    }
}
