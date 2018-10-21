package GameEngine.CollisionHandler;

import GameEngine.*;

import static GameEngine.Arrow.MOVING;

public class ArrowMonsterCollisionHandler implements CollisionHandler {
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        Monster monster = (Monster) (obj1 instanceof Monster ? obj1 : obj2);
        Arrow arrow = (Arrow) (obj1 instanceof Arrow ? obj1 : obj2);

        CollisionResult res = new CollisionResult();

        // Check if arrow is in the moving state
        if (arrow.getState() == MOVING) {
            res.addFlag(CollisionResult.DELETE_FIRST);
            res.addFlag(CollisionResult.DELETE_SECOND);

            engine.removeGameObject(obj1);
            engine.removeGameObject(obj2);

            if(engine.isWinning())
                res.addFlag(CollisionResult.WIN);
        }
        return res;

    }
}
