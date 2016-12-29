package Game;

/**
 * Created by Emil on 12/10/2016.
 */

import android.graphics.drawable.Drawable;

import com.example.emil.app.R;

/**
 * "Supa hot fire"
 */

public class Fire extends Hazard {

    private Drawable picture1, picture2;// drawingPic;
    int animation = 0;

    public Fire(Position position, World world) {
        super(position, Block.BLOCK_WIDTH, Block.BLOCK_HEIGHT, world);
        picture1 = world.getGameActivity().getResources().getDrawable(R.drawable.hot_fire);
        picture2 = world.getGameActivity().getResources().getDrawable(R.drawable.hot_fire2);
        picture1.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + width, (int) position.getY() + height);
        picture2.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + width, (int) position.getY() + height);
    }

  /*  @Override
    public void draw() {
        drawingPic.draw(canvas);
    }
*/

    @Override
    public void update() {
        if (animation < 10)
            activePicture = picture1;
        else
            activePicture = picture2;
        if (animation == 19)
            animation = 0;
        animation++;
    }

    @Override
    protected void affectPlayer() {
        world.gameOver();
//        canvas.drawColor(Color.BLACK);
//        world.pauseGame();
//        Intent intent = new Intent(gameActivity, GameOverActivity.class);
//        intent.putExtra("Level", world.getLevel());
//        gameActivity.startActivity(intent);
    }
}
