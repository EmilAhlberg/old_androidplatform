package Game.Movers;

import android.graphics.Rect;

import com.example.emil.app.R;
import Game.*;
import Game.Framework.World;

/**
 * Created by Emil on 2016-12-17.
 */

public class DeathAnimator extends GameObject {
 /*   private Drawable picture;*/
    private MovePhysics mv;

    public DeathAnimator(GameObject g, World world) {
        super(new Rect(g.getRect().left, g.getRect().top, g.getRect().right, g.getRect().bottom), world);
        world.addObject(this);
        initializeImage(g);

        mv = new MovePhysics();
        mv.applyForce(20, 400);
    }

    //TODO
    private void initializeImage(GameObject g) {
//        if (g instanceof StandardCat) {
//            activePicture = world.getGameActivity().getResources().getDrawable(R.drawable.cat);
//        }
//        else if (g instanceof Veterinarian) {
//            activePicture = world.getGameActivity().getResources().getDrawable(R.drawable.vet);
//        }
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
        move(rect.left - mv.horizontalSpeed, rect.top - mv.verticalSpeed);
    }
}
