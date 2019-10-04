package GameEngine.CollisionHandler;

import GameEngine.*;

public class GameObjectMovableCollisionHandler implements CollisionHandler {
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        GameObject obj = (obj1 instanceof Movable ? obj2 : obj1);
        CollisionResult res = new CollisionResult();

        if (obj.isBlocking())
            res.addFlag(CollisionResult.REJECT);
        return res;
    }
}
