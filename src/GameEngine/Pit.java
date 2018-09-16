package GameEngine;

import GameEngine.utils.Point;

public class Pit extends StandardObject {

    /**
     * Pit Constructor
     * @param location
     */
    public Pit(Point location) {
        super(location);
    }

    /**
     * Do not consider a point with Pit on it as a candidate point
     * for path generation
     * @return false
     */
    @Override
    public boolean canMoveThrough() {
        return false;
    }
}
