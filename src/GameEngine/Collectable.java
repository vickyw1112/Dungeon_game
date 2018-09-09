package GameEngine;

public interface Collectable {
	/**
	 * getCollected method will add object to inventory class
	 * It will also check if collectable object can be stacked
	 */
    public void getCollected(Player player);
    
    /**
     * removeObjectMap method will remove the object from map
     */
    public void removeObjectMap();

    static int count = 1;

}
