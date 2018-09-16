package GameEngine;

import GameEngine.CollisionHandler.CollisionEntities;
import GameEngine.utils.Point;
import GameEngine.CollisionHandler.*;


public class Exit extends StandardObject {
    public Exit(Point location) {
        super(location);
    }

    /**
     * registerCollisionHandler method
     * method to register collisions between players and exit, triggering the winning condition
     * @param gameEngine
     */
    public void registerCollisionHandler(GameEngine gameEngine) {
        // handler for exit and player (not sure if this should be in player?)
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Player.class),
                new WinCollisionHandler());
    }
}