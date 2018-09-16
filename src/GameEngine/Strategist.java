package GameEngine;

import GameEngine.utils.Point;

public class Strategist extends Monster {

    public Strategist(Point location) {
        super(location);
    }

    @Override
    public PathGenerator getDefaultPathGenerator() {
        return new StrategistPathGenerator();
    }
}
