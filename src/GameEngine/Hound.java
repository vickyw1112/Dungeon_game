package GameEngine;

import GameEngine.utils.Point;

public class Hound extends Monster implements Pairable {
    /**
     * Paired hunter
     */
    private Hunter hunter;

    /**
     * Constructor for Hound
     * takes in parameter of location
     * @param location
     */
    public Hound(Point location) {
        super(location);
    }

    /**
     * Method to obtain the paired hunter
     * @return
     *          Hunter
     */
    public Hunter getPair() {
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
        return new HoundPathGenerator();
    }
}
