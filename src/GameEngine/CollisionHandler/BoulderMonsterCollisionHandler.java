package GameEngine.CollisionHandler;

import GameEngine.*;

public class BoulderMonsterCollisionHandler implements CollisionHandler {
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        CollisionResult res = new CollisionResult(0);
        res.addFlag(CollisionResult.REJECT);
        return res;
    }
}
