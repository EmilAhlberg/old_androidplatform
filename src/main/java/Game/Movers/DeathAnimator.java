package Game.Movers;

import com.example.emil.app.R;
import Game.*;
import Game.Framework.World;
import Game.Util.Rectangle;

/**
 * Created by Emil on 2016-12-17.
 */

public class DeathAnimator extends GameObject {
 /*   private Drawable picture;*/
    private MovePhysics mv;

    public DeathAnimator(GameObject g, World world) {
        super(new Rectangle(g.getPosition(), g.getWidth(), g.getHeight()), world);
        world.addObject(this);
        initializeImage(g);

        mv = new MovePhysics();
        mv.applyForce(20, 400);
    }

    private void initializeImage(GameObject g) {
        if (g instanceof StandardCat) {
            activePicture = world.getGameActivity().getResources().getDrawable(R.drawable.cat);
        }
        else if (g instanceof Veterinarian) {
            activePicture = world.getGameActivity().getResources().getDrawable(R.drawable.vet);
        }
    }


   /* @Override
    public void draw() {
        picture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + width, (int) position.getY() + height);
        picture.draw(canvas);
    }
*/
//    private void updatePicture() {
//        activePicture.setBounds((int) getPosition().getX(), (int) getPosition().getY(), (int) getPosition().getX() + getWidth(), (int) getPosition().getY() + getHeight());
//    }
    @Override
    public void update() {
        //updatePicture();
        if (mv.verticalForce > -100) {
            mv.updateSpeed();
            updatePosition();
        } else {
            world.removeObject(this);
        }

    }

    protected void updatePosition() {
        move(getPosition().getX() - mv.horizontalSpeed, getPosition().getY() - mv.verticalSpeed);
    }
}
