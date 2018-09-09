package GameEngine;

import java.awt.*;

public interface Movable {

    /**
     * Get current facing of a movable object
     * @return facing direction
     */
    public Direction getFacing();

    /**
     * Get current speed of the movable object
     * @return speed in grid per second
     */
    public double getSpeed();
}
