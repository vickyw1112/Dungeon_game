package GameEngine;

import GameEngine.utils.Point;

public class Strategist extends Monster {

    /**
     * Constructor for Strategist
     * @param location
     */
    public Strategist(Point location) {
        super(location);
    }

    /**
     * useDefaultPathGenerator method
     * sets the default pathGenerator for strategist
     * @return
     */
    @Override
    public PathGenerator getDefaultPathGenerator() {
        return new StrategistPathGenerator();
    }
}
