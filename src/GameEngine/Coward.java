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
        if (ret)
            updatePathGenerator();
        return ret;
    }

    /**
     * Update coward's path generator based on it's path
     */
    private void updatePathGenerator(){
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

    /**
     * Path generator method for Coward
     * @return
     */
    @Override
    public PathGenerator getDefaultPathGenerator() {
        return new ShortestPathGenerator();
    }

    @Override
    public void updatePath(Map map, Player player) {
        updatePathGenerator();
        super.updatePath(map, player);
    }

    /**
     * Whenever the monster is changing location
     * update path if using run away generator
     * since it only returns one point at a time
     *
     * @see RunAwayPathGenerator#generatePath(Map, Monster, Player)
     * @param engine game engine
     */
    @Override
    public void onUpdatingLocation(GameEngine engine) {
        super.onUpdatingLocation(engine);
        updatePath(engine.getMap(), engine.player);
    }
}
