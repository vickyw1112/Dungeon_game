package GameEngine.CollisionHandler;

import GameEngine.*;

public class RejectHandler implements CollisionHandler {
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        return new CollisionResult(CollisionResult.REJECT);
    }
}
