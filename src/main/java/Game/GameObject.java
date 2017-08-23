package Game;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import Game.Framework.World;
import Game.Util.Position;
import Game.Util.Rectangle;

/**
 * Created by Emil on 2016-11-20.
 */

public abstract class GameObject {

   /* protected static Canvas canvas;*/
    protected World world;
  /*  protected static GameActivity gameActivity;*/
//    protected Position position;
//    protected int width;
//    protected int height;
    protected Rectangle rect;
    protected Drawable activePicture;

    public GameObject(Rectangle rect, World world) {
        this.rect = rect;
        this.world = world;
    }
    public Position getPosition() {
        return rect.getPosition();
    }

    public void move(double x, double y) {
        rect.getPosition().setX(x);
        rect.getPosition().setY(y);
    }

    public Drawable getDrawable() {
        return activePicture;
    }

    public int getHeight() {
        return rect.getHeight();
    }

    public int getWidth() {
        return rect.getWidth();
    }

    public void updateObject() {
        update();
        updatePicture();
    }
    public abstract void update();

    private void updatePicture() {
        activePicture.setBounds((int) getPosition().getX(), (int) getPosition().getY(), (int) getPosition().getX() + getWidth(), (int) getPosition().getY() + getHeight());
    }

}
