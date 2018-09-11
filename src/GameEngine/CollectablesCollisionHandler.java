package GameEngine;

public class CollectablesCollisionHandler implements CollisionHandler {

	@Override
	public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
		// Have to check instance type here
        Player player = (Player)(obj1 instanceof Player ? obj1 : obj2);
        CollisionResult res = new CollisionResult(0);
        if(obj2.getState() == Collectable.COLLECTABLESTATE) {
            res.addFlag(obj1 instanceof Collectable ? CollisionResult.DELETE_FIRST : CollisionResult.DELETE_SECOND);
            res.addFlag(CollisionResult.REFRESH_INVENTORY);
            return res;
        } else {
        	res.addFlag(CollisionResult.HANDLED);
        	return res;
        }
	}
}
