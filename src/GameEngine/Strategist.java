package GameEngine;

import GameEngine.utils.Point;

public class Strategist extends Monster {

    public Strategist(Point location) {
        super(location);
    }

    @Override
    public void initialize() {
        super.initialize();
        pathGenerator = new StrategistPathGenerator();
    }

}
