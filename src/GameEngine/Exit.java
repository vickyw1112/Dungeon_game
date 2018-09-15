package GameEngine;

import GameEngine.CollisionHandler.CollisionEntities;
import GameEngine.CollisionHandler.CollisionResult;
import GameEngine.utils.Point;

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
                (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                    CollisionResult res = new CollisionResult(0);
                    // arrow is the only thin;that does something different with a wall.
                    if (obj1 instanceof Exit || obj2 instanceof Exit) {
                        res.addFlag(CollisionResult.WIN);
                        return res;
                    } else {
                        return res;// nothing happens
                    }
                });
    }
}
