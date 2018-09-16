package GameEngine;

import GameEngine.utils.Point;

public class Treasure extends StandardObject implements Collectable {

    /**
     * Constructor for treasure class
     * Takes in location parameter
     * @param location
     */
    public Treasure(Point location) {
        super(location);
    }

    /**
     * getCollected method for treasure
     * adds the object to player inventory and deletes it from the map
     * @param engine
     * @param playerInventory
     * @return
     *          boolean
     */
    @Override
    public boolean getCollected(GameEngine engine, Inventory playerInventory) {
        playerInventory.addObject(this);
        engine.removeGameObject(this);
        return true;
    }
}
