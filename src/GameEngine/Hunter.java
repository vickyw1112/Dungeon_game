package GameEngine;

import GameEngine.utils.Point;

public class Hunter extends Monster {
    public Hunter(Point location) {
        super(location);
    }

    /**
     * Path generation for hunter monster
     * returns a linked list of points
     * @return
     */
    @Override
    public PathGenerator getDefaultPathGenerator() {
       return new ShortestPathGenerator();
    }
}
