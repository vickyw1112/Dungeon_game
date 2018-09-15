package GameEngine;

import GameEngine.utils.Point;

public class Coward extends Monster {
    public Coward(Point location) {
        super(location);
    }

    @Override
    public void initialize() {
        super.initialize();
        // Coward use ShortestPathGenerator by default
        // but change to RunAwayPathGenerator after getting close to player
        pathGenerator = new ShortestPathGenerator();
    }

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
}
