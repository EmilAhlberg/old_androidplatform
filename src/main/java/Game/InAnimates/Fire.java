package Game.InAnimates;

/**
 * Created by Emil on 12/10/2016.
 */

import android.graphics.drawable.Drawable;

import com.example.emil.app.R;

import Game.Framework.World;
import Game.Util.Position;

/**
 * "Supa hot fire"
 */

public class Fire extends Hazard {

    private Drawable picture1, picture2;// drawingPic;
    int animation = 0;

    public Fire(Position position, World world) {
        super(position, Block.BLOCK_WIDTH, Block.BLOCK_HEIGHT, world);
    }

  /*  @Override
    public void draw() {
        drawingPic.draw(canvas);
    }
*/

    @Override
    public void update() {
        //NEEDS FIX, animation in sprite (spritesheet needed)
//        if (animation < 10)
//            activePicture = picture1;
//        else
//            activePicture = picture2;
//        if (animation == 19)
//            animation = 0;
//        animation++;
    }

    @Override
    public void affectPlayer() {
        world.gameOver();
    }
}
