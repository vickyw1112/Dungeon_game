package GameEngine.CollisionHandler;

import GameEngine.*;

public interface CollisionHandler {
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2);
}
