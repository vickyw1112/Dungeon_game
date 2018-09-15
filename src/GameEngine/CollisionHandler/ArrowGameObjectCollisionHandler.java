package GameEngine.CollisionHandler;

import GameEngine.*;

public class ArrowGameObjectCollisionHandler implements CollisionHandler {
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        Arrow arrow = (Arrow) (obj1 instanceof Arrow ? obj1 : obj2);
        GameObject obj = (obj1 instanceof Arrow ? obj2 : obj1);

        // Check if arrow is in the moving state
        if (arrow.getState() == Arrow.MOVING && obj.isBlocking()) {
            CollisionResult res = new CollisionResult();
            res.addFlag(obj1 instanceof Arrow ? CollisionResult.DELETE_FIRST : CollisionResult.DELETE_SECOND);
            engine.removeGameObject(arrow);
            return res;
        } else {
            return new CollisionResult(CollisionResult.HANDLED);
        }
    }
}
