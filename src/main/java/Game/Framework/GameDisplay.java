package Game.Framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.example.emil.Framework.GameActivity;
import com.example.emil.app.R;

import java.util.ArrayList;
import java.util.List;

import Game.GameObject;
import Game.Util.Position;

/**
 * Created by Emil on 2016-12-29.
 */

public class GameDisplay {
    private Bitmap bitmap;
    private Canvas canvas;
    private Drawable backgroundImage;
    private Bitmap background;
    private Bitmap tempMap;
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;

    public GameDisplay(GameActivity gameActivity) {
        bitmap = Bitmap.createBitmap(800, 480, Bitmap.Config.RGB_565);
        background = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
        canvas = new Canvas(bitmap);
        backgroundImage = gameActivity.getResources().getDrawable(R.drawable.background);
        backgroundImage.setBounds(0, 0, 2000, 1000); //(left, top, right, bottom)
        WINDOW_WIDTH = getCanvas().getWidth();
        WINDOW_HEIGHT = getCanvas().getHeight();
    }

    public void beginDraw(double x, double y) {
        Canvas c = new Canvas(background);
        backgroundImage.draw(c);
        centerPlayer(x, y); //player  position
        tempMap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
        canvas.setBitmap(tempMap);
        canvas.drawBitmap(background, null, backgroundImage.getBounds(), null);
    }

    public void endDraw() {
        bitmap = tempMap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    private void centerPlayer(double x, double y) {
        Rect r = canvas.getClipBounds();
        double dx = calculateDx(r, x);
        double dy = calculateDy(r, y);

        //förhindrar 'flimmer' vid stillastående
        if (Math.abs(dx) > 2 || Math.abs(dy) > 2) {
            canvas.translate((float) dx, (float) dy);
        }
    }

    private double calculateDx(Rect r, double x) {
        double dx = 0;
        if (x >= 400 && x <= 1600) {
            dx = r.centerX() - x;
        } else if (x <= 400) {
            dx = r.left;
        } else if (x >= 1600) {
            dx = r.right - 2000;
        }
        return dx;
    }

    private double calculateDy(Rect r, double y) {
        double dy = 0;
        if (y >= 240 && y <= 760) {
            dy = r.centerY() - y;
        } else if (y < 240) {
            dy = r.top;  //icke testad
        } else if (y >= 760) {
            dy = r.bottom - 1000;
        }
        return dy;
    }
}
