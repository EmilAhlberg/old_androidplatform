package Game.Util;

/**
 * Enumerates object types, first enumerable.ordinal() == 0.
 *
 * HOW TO ADD NEW SPRITES:
 *      1. Add drawable to res/drawable folder.
 *      2. Add object ID to this enum list.
 *      3. Add drawable to IDHandler drawable list, method loadDrawables(), in GameActivity class.
 *      4. Make according additions to class IDHandler's typeToID hashmap.
 *      5. gg
 *
 *
 * Created by Emil on 25/08/2017.
 */

public enum IDs {

    DEFAULT,
    PLAYER,
    BLOCK,
    FIRE,
    GOAL,
    CAT,
    VETERINARIAN,
    SYRINGE,

}


