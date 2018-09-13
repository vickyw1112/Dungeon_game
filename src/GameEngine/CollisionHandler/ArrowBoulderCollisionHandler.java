package GameEngine.CollisionHandler;

import GameEngine.*;

public class ArrowBoulderCollisionHandler implements CollisionHandler {
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        Arrow arrow = (Arrow) (obj1 instanceof Arrow ? obj1 : obj2);

        CollisionResult res = new CollisionResult(0);

        // Check if arrow is in the moving state
        if (arrow.getState() == Arrow.MOVING) {
            if (obj1 instanceof Arrow) {
                res.addFlag(CollisionResult.DELETE_FIRST);
            } else if (obj2 instanceof Arrow) {
                res.addFlag(CollisionResult.DELETE_SECOND);
            }
        }
        return res;
    }
}
