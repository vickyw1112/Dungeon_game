package GameEngine.CollisionHandler;
import GameEngine.*;

public class PlayerExitCollisionHandler implements CollisionHandler {
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        Player player = (Player) (obj1 instanceof Player ? obj1 : obj2);
        Exit exit = (Exit) (obj1 instanceof Exit ? obj1 : obj2);

        CollisionResult res = new CollisionResult(0);
        if (obj1 instanceof Exit || obj2 instanceof Exit) {
            res.addFlag(CollisionResult.WIN);
            return res;
        }
        return res; // placeholder in case the above doesn't work
    }
}
