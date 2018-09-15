package GameEngine;

import GameEngine.utils.Point;

public class Coward extends Monster {
    public Coward(Point location) {
        super(location);
    }

    @Override
    public boolean setLocation(Point point) {
        boolean ret = super.setLocation(point);
        // if location changed
        if (ret) {
            if (pathGenerator instanceof ShortestPathGenerator &&
                    this.pathToDestination.size() < 5)
                // run away
                this.pathGenerator = new RunAwayPathGenerator();
            // if running away and reached the furthest location
            else if (pathGenerator instanceof RunAwayPathGenerator &&
                    this.pathToDestination.size() == 0)
                // chase player again
                this.pathGenerator = new ShortestPathGenerator();
        }
        return ret;
    }

    @Override
    public PathGenerator getDefaultPathGenerator() {
        return new ShortestPathGenerator();
    }
}
