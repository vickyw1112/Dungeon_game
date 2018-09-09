package GameEngine;


/**
 * An interface provided to front end
 * to universally register handler for handling collision on the front end
 * and resolve a specific CollisionResult
 *
 * @see CollisionResult
 */
public abstract class GUICollisionHandler {
    private static GUICollisionHandler[] handlers = new GUICollisionHandler[CollisionResult.FLAG_SIZE];


    /**
     * Method to override for front end to provide
     * logic on how to update the front end upon specific flag
     * is set to true
     *
     * @param result collision result
     */
    public abstract void handle(CollisionResult result);


    /**
     * Register a GUI collision handler which will be called
     * if flag bit is set to true
     *
     * @pre flag is one of the static constant bit defined in {@link CollisionResult}
     * @param flag bit that will trigger handler
     * @param handler handler
     */
    public static void registerGUICollisionHandler(int flag, GUICollisionHandler handler){

    }

    /**
     * Interface to resolve all flags in result
     *
     * @throws GUICollisionHandlerNotDefined if a bit in result flags does not have a registered handler
     * @param engine game engine
     * @param result collision result
     */
    public static void handle(GameEngine engine, CollisionResult result){

    }


}
