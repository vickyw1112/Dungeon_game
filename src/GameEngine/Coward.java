package GameEngine;

import GameEngine.utils.Point;

public class Coward extends Monster {
    public Coward(Point location) {
        super(location);
    }

    /**
     * SetLocation method for coward
     * Method returns a point and instantiates the path generation algorithm
     * @param point
     *            new location
     * @return
     */
    @Override
    public boolean setLocation(Point point) {
        boolean ret = super.setLocation(point);
        // if location changed
        if (ret) {
            // TODO: Have another function to calculate distance to a specific point
            // considering obstructive objs
            int distance = 0;
            if (distance < 5)
                this.pathGenerator = new RunAwayPathGenerator();
            else if (distance > 10)
                this.pathGenerator = new ShortestPathGenerator();
        }
        return ret;
    }

    /**
     * Path generator method for Coward
     * @return
     */
    @Override
    public PathGenerator getDefaultPathGenerator() {
        return new ShortestPathGenerator();
    }
}
