package GameEngine;

import GameEngine.CollisionHandler.CollisionHandler;
import GameEngine.CollisionHandler.CollisionResult;

public class DefaultHandler implements CollisionHandler {
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        return new CollisionResult(CollisionResult.HANDLED);
    }
}
