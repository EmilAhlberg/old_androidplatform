package Game.Movers;

import Game.*;
/**
 * Created by Emil on 2016-12-03.
 */

public class MovePhysics {
    protected final double GRAVITY = -35;
    protected double verticalForce, horizontalForce, horizontalAcceleration, verticalAcceleration, horizontalSpeed, verticalSpeed, mass;

    public MovePhysics() {
        horizontalForce = horizontalAcceleration = verticalAcceleration = horizontalSpeed = verticalSpeed = 0;
        verticalForce = GRAVITY;
        mass = 30;
    }

    protected void updateSpeed() {
        updateAcceleration();
        verticalForce = GRAVITY;
        horizontalForce = 0;
        verticalSpeed = verticalSpeed + verticalAcceleration;
        horizontalSpeed = horizontalSpeed + horizontalAcceleration;
    }

    private void updateAcceleration() {
        verticalAcceleration = verticalForce / mass;
        horizontalAcceleration = horizontalForce / mass;
    }

    public void applyForce(double horizontalChange, double verticalChange) {
        verticalForce += verticalChange;
        horizontalForce += horizontalChange;
    }


}
