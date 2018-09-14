package GameEngine.CollisionHandler;

import GameEngine.*;

public class GameObjectMovableCollisionHandler implements CollisionHandler {
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        GameObject obj = (GameObject) (obj1 instanceof GameObject ? obj1 : obj2);
        CollisionResult res = new CollisionResult();

        if (obj.isBlocking())
            res.addFlag(CollisionResult.REJECT);
        return res;

    }
}
