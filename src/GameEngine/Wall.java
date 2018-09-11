
public class Wall extends GameObject  {
	public Wall (Point location) {
		super(location);	
	}
	
	/**
	 * Define collision on walls
	 */
	
    public void registerCollisionHandler(GameEngine gameEngine){
        // Register handler for Player collide with Pit
    	// need to see implemntation of movable to know how it works
        gameEngine.registerCollisionHandler(new CollisionEntities(getClassName(), movable),
                (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                	
                	// arrow is the only thing that does soemthign different with a wall.
                	if (obj2 instanceof Arrow) {
                		res.addFlag(CollisionResult.DELETEFIRST);
                	} else {
                		res.addFlag(CollisionResult.REJECTED);
                		return res;
                	}
                });
    }
	
	
	
	
	
	
	
}
