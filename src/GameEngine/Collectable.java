package GameEngine;

public interface Collectable extends GameObject {
	
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
    	playerInventory.addObject(this);
        engine.removeGameObject(this);
        return true;
    }
}
