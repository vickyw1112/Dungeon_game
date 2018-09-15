package GameEngine;

import GameEngine.utils.Point;

public class Hunter extends Monster {
    public Hunter(Point location) {
        super(location);
    }

    @Override
    public PathGenerator getDefaultPathGenerator() {
       return new ShortestPathGenerator();
    }
}
