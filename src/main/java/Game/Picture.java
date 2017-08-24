package Game;


import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import Game.Util.Position;
import Game.Util.Rectangle;

/**
 * Created by Emil on 23/08/2017.
 */

public abstract class Picture {

    protected Drawable activePicture;
    protected Rectangle rect;

    public Picture(Rectangle rect) {
        this.rect = rect;
    }

    public double getX() {
        return rect.getX();
    }

    public double getY() {
        return rect.getY();
    }

    public int getHeight() {
        return rect.getHeight();
    }

    public int getWidth() {
        return rect.getWidth();
    }

    public Rectangle getRect() {
        return rect;
    }

    private void updatePicture() {
        activePicture.setBounds((int) getX(), (int) getY(), (int) getX() + getWidth(), (int) getY() + getHeight());
    }

    public Drawable getDrawable() {
        return activePicture;
    }

    public void draw(Canvas canvas) {
        updatePicture();
        activePicture.draw(canvas);
    }

}
