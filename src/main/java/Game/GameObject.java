package Game;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.example.emil.Framework.GameActivity;

/**
 * Created by Emil on 2016-11-20.
 */

public abstract class GameObject {

   /* protected static Canvas canvas;*/
    protected World world;
  /*  protected static GameActivity gameActivity;*/
    protected Position position;
    protected int width;
    protected int height;
    protected Drawable activePicture;

    public GameObject(Position position, int width, int height, World world) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.world = world;
    }
    public Position getPosition() {
        return position;
    }

    public void move(double x, double y) {
        position.setX(x);
        position.setY(y);
    }

    public Drawable getDrawable() {
        return activePicture;
    }

    public abstract void update();

 /*   public static void initialize(World w) {
   *//*     canvas = c;*//*
        world = w;
      *//*  gameActivity = b;*//*
    }*/

}
