package GameEngine.CollisionHandler;

import GameEngine.*;

public class CollectablesCollisionHandler implements CollisionHandler {

    /**
     * Override handle method for all collectables/player interaction
     */
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        // Have to check instance type here
        Player player = (Player) (obj1 instanceof Player ? obj1 : obj2);
        Collectable collectable = (Collectable) (obj1 instanceof Collectable ? obj1 : obj2);
        if(collectable.getState() != Collectable.COLLECTABLESTATE)
            return new CollisionResult(CollisionResult.HANDLED);

        collectable.getCollected(engine, player.getInventory());

        CollisionResult res = new CollisionResult();

        if(engine.checkWiningCondition())
            res.addFlag(CollisionResult.WIN);
        res.addFlag(obj1 instanceof Collectable ? CollisionResult.DELETE_FIRST : CollisionResult.DELETE_SECOND);
        res.addFlag(CollisionResult.REFRESH_INVENTORY);
        return res;
    }
}
