package GameEngine;

import GameEngine.utils.Point;

public class Sword extends StandardObject implements Collectable {
    private static final int ATTACKNUM = 5;

    /**
     * Constructor for Sword class
     * takes in location parameter
     * @param location
     */
    public Sword(Point location) {
        super(location);
    }

    /**
     * getCollected method for Sword
     * increases the count of the object in player inventory and deletes it from the map
     * @param engine
     * @param playerInventory
     * @return
     */
    @Override
    public boolean getCollected(GameEngine engine, Inventory playerInventory) {
        playerInventory.setCount(Sword.class, ATTACKNUM);
        engine.removeGameObject(this);
        return true;
    }

}
