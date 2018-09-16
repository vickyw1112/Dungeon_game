package GameEngine.CollisionHandler;
import GameEngine.*;


public class WinCollisionHandler implements CollisionHandler {
        @Override
        public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
            return new CollisionResult(CollisionResult.WIN);
        }
}
