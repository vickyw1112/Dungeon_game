package GameEngine.utils;

/**
 * Observable interface
 */

public interface Observable {
    /**
     * Notify all observer
     */
    void notifyObservers();

    /**
     * Add a new observer
     * @param observer new observer
     */
    void addObserver(GameObjectObserver observer);

}
