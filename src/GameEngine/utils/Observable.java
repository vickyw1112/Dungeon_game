package GameEngine.utils;

/**
 * Observable interface
 */

public interface Observable {
    /**
     * Notify all observer
     */
    public void notifyObservers();

    /**
     * Add a new observer
     * @param observer
     */
    public void addObserver(GameObjectObserver observer);

}
