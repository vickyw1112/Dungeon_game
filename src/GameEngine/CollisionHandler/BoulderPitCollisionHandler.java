package GameEngine.CollisionHandler;

import GameEngine.*;

public class BoulderPitCollisionHandler implements CollisionHandler {
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        CollisionResult res = new CollisionResult(0);
        Boulder boulder = (Boulder) (obj1 instanceof Boulder ? obj1 : obj2);
        engine.removeGameObject(boulder);
        if (obj1 instanceof Boulder)
            res.addFlag(CollisionResult.DELETE_FIRST);
        else
            res.addFlag(CollisionResult.DELETE_SECOND);
        return res;
    }
}
