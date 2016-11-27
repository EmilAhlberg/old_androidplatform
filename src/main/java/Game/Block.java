package Game;

import android.graphics.Canvas;

/**
 * Created by Emil on 2016-11-22.
 */

public abstract class Block extends GameObject{

    protected static final int BLOCK_WIDTH = 18;
    protected static final int BLOCK_HEIGHT = 18;

    public Block(Position position) {
        super(position, BLOCK_WIDTH, BLOCK_HEIGHT);
    }


}
