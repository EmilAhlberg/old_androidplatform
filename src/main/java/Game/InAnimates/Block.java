package Game.InAnimates;

import android.graphics.Rect;
import android.util.Log;
import Game.*;
import Game.Framework.World;
import Game.Util.Position;

import com.example.emil.app.R;

/**
 * Created by Emil on 2016-11-22.
 */

public class Block extends GameObject {

    protected static final int BLOCK_WIDTH = 18;
    protected static final int BLOCK_HEIGHT = 18;
    //private Drawable picture;

    public Block(Position position, int blockType, World world) {
        //super(new Rect((int)position.getX(), (int)position.getY(), BLOCK_WIDTH, BLOCK_HEIGHT), world);
        super(new Rect((int)position.getX(), (int)position.getY(), (int)position.getX() + BLOCK_WIDTH, (int)position.getY() + BLOCK_HEIGHT), world);
//        initializeImage(blockType);
//        activePicture.setBounds((int) position.getX(), (int) position.getY(), (int) position.getX() + getWidth(), (int) position.getY() + getWidth());
    }


 /*   @Override
    public void draw() {
        //canvas.drawRect((float)position.getX(),(float)position.getY(),(float)(position.getX()+width),(float)(position.getY()+height),paint);
        picture.draw(canvas);
    }*/

    @Override
    public void update() {

    }

    private void initializeImage(int blockType) {
//        if (blockType == 1) {
//            activePicture = world.getGameActivity().getResources().getDrawable(R.drawable.block1);
//        } else if (blockType == 2) {
//            activePicture = world.getGameActivity().getResources().getDrawable(R.drawable.block2);
//        }
    }
}
