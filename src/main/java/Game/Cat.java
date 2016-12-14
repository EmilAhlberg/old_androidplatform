package Game;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.emil.Framework.GameOver;
import com.example.emil.app.R;

/**
 * Created by Emil on 2016-12-14.
 */

public class Cat extends Mover {
    private Drawable picture;
    private int direction;


    public Cat(Position p) {
        super(p, 20, 20);
        applyForce(35, 0);
        direction = 1;
        picture = board.getResources().getDrawable(R.drawable.cat);
    }


    @Override
    protected void updatePosition() {
        if (!(isBlockAhead())) {
            direction = direction * -1;
        }
        move(position.getX() - mv.horizontalSpeed * direction, position.getY() - mv.verticalSpeed);
    }

    @Override
    protected void specificCollision(GameObject g) {

    }

    @Override
    public void draw() {
        picture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + width, (int) position.getY() + height);
        picture.draw(canvas);

    }

    @Override
    public void update() {
        updateSpeed();
        updatePosition();
        checkCollision(1); //ordning på collisionCheck viktig, annars bugg
        checkCollision(0);
    }

    private boolean isBlockAhead() {
        int probeXOffset =direction*-2 + width*direction;
        int probeYOffset = height +2;
        if (direction >0) {
            probeXOffset=probeXOffset - width;
        }
        GameObjectProbe probe = new GameObjectProbe(new Position(position.getX() - probeXOffset, position.getY() +probeYOffset),2,1);
//        Log.d("CatCollision", "" + "Cat X:" + position.getX() + "  " + "Probe X:" +probe.position.getX()+"\n" +
//                "Cat Y:" + position.getY() + "  " + "Probe Y:" +probe.position.getY()+"\n" + "    \n" + probe.checkCollision(2)+"\n     "+ direction);
        return probe.checkCollision(2);
    }

    public void affectPlayer() {
        canvas.drawColor(Color.BLACK);
        world.pauseGame();
        Intent intent = new Intent(board, GameOver.class);
        intent.putExtra("Level", world.getLevel());
        board.startActivity(intent);
    }
}
