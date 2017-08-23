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

    public Position getPosition() {
        return rect.getPosition();
    }

    public int getHeight() {
        return rect.getHeight();
    }

    public int getWidth() {
        return rect.getWidth();
    }


    private void updatePicture() {
        activePicture.setBounds((int) getPosition().getX(), (int) getPosition().getY(), (int) getPosition().getX() + getWidth(), (int) getPosition().getY() + getHeight());
    }

    public Drawable getDrawable() {
        return activePicture;
    }

    public void draw(Canvas canvas) {
        updatePicture();
        activePicture.draw(canvas);
    }

}
