package GameEngine;

import GameEngine.utils.Point;

public class Hound extends Monster implements Pairable {
    /**
     * Paired hunter
     */
    private Hunter hunter = null;

    @Override
    public String getPairingObjectClassName() {
        return Hunter.class.getSimpleName();
    }

    /**
     * Constructor for Hound
     * takes in parameter of location
     * @param location
     */
    public Hound(Point location) {
        super(location);
    }

    @Override
    public GameObject getPair() {
        return hunter;
    }

    /**
     * setter for hunter pair
     * @param pair
     */
    @Override
    public void setPair(GameObject pair) {
        hunter = (Hunter) pair;
    }

    /**
     * getDefaultPathGenerator
     * returns HoundPathGenerator
     * @return
     */
    @Override
    public PathGenerator getDefaultPathGenerator() {
        return hunter == null ? new ShortestPathGenerator() : new HoundPathGenerator();
    }
}
