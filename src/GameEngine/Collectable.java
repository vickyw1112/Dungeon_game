package GameEngine;

public interface Collectable {
	
	/**
	 * Constant
	 * Allow us to check Collectable state of collectable objects.
	 */
	public static final int COLLECTABLESTATE = 0;
	/**
	 * getCollected method will add object to inventory class
	 * It will also check if collectable object can be stacked
	 */
    public default boolean getCollected(GameEngine engine, Inventory playerInventory) {
    	playerInventory.addObject((GameObject) this);
        engine.removeGameObject((GameObject) this);
        return true;
    }
}
