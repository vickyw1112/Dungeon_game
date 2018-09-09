package GameEngine;

public interface Collectable {
    static int count = 1;

    public void getCollected();

    /**
     * Get the count of this object in player's
     * inventory if it is stackable otherwise return 1
     * @param player the player instance
     * @return the count of this object
     */
    public default int getCount(Player player){
        return 1;
    }

}
