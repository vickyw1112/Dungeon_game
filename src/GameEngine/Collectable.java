package GameEngine;

public interface Collectable {
	static final int CollectableState = 0;
	/**
	 * getCollected method will add object to inventory class
	 * It will also check if collectable object can be stacked
	 */
    public default boolean getCollected(GameEngine engine, Inventory playerInventory) {
    	playerInventory.addObject((GameObject) this);
        engine.getMap().removeObject((GameObject) this);
        return true;
    }
}
