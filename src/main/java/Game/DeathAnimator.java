package Game;

import com.example.emil.app.R;

/**
 * Created by Emil on 2016-12-17.
 */

public class DeathAnimator extends GameObject {
 /*   private Drawable picture;*/
    private MovementVector mv;

    public DeathAnimator(GameObject g, World world) {
        super(g.getPosition(), g.width, g.height, world);
        world.addObject(this);
        initializeImage(g);

        mv = new MovementVector();
        mv.applyForce(20, 400);
    }

    private void initializeImage(GameObject g) {
        if (g instanceof StandardCat) {
            activePicture = world.getGameActivity().getResources().getDrawable(R.drawable.cat);
        } else if (g instanceof SuicideCat) {
            activePicture = world.getGameActivity().getResources().getDrawable(R.drawable.suicidecat);
        } else if (g instanceof Veterinarian) {
            activePicture = world.getGameActivity().getResources().getDrawable(R.drawable.vet);
        }
    }


   /* @Override
    public void draw() {
        picture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + width, (int) position.getY() + height);
        picture.draw(canvas);
    }
*/
    private void updatePicture() {
        activePicture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + width, (int) position.getY() + height);
    }
    @Override
    public void update() {
        updatePicture();
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
