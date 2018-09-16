package GameEngine;

import GameEngine.utils.Point;

public class Hound extends Monster implements Pairable {
    /**
     * Paired hunter
     */
    private Hunter hunter;

    public Hound(Point location) {
        super(location);
    }

    public Hunter getPair() {
        return hunter;
    }

    @Override
    public void setPair(GameObject pair) {
        hunter = (Hunter) pair;
    }

    @Override
    public PathGenerator getDefaultPathGenerator() {
        return new HoundPathGenerator();
    }
}
