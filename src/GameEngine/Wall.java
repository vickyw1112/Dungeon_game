package GameEngine;

public class Wall extends GameObject  {
	public Wall (Point location) {
		super(location);	
	}
	
	/**
	 * Define collision on walls
	 */

    public void registerCollisionHandler(GameEngine gameEngine){
        // Register handler for wall and arrow
    	// need to see implemntation of movable to know how it works
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Arrow.class),
                (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                    CollisionResult res = new CollisionResult(0);
                    Wall wall = (Wall)(obj1 instanceof Wall ? obj1: obj2);
                    Arrow arrow = (Arrow)(obj1 instanceof Arrow ? obj1 : obj2);
                	// arrow is the only thin;that does soemthign different with a wall. 
                    if (obj2 instanceof Arrow) {
                        
                		res.addFlag(CollisionResult.DELETE_SECOND);
                		return res;
                	} else {
                  		res.addFlag(CollisionResult.DELETE_FIRST);
                		return res;
                	}
                });
        
        // Handler for wall and Player
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Player.class), 
                (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                    CollisionResult res = new CollisionResult(0);
                    res.addFlag(CollisionResult.REJECT);
                    return res;
                });
        
        
        // Handler for wall and Boulder (since this is the same as player i should consider a generic here as well
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Boulder.class),
                (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                    CollisionResult res = new CollisionResult(0);
                    res.addFlag(CollisionResult.REJECT);
                    return res;
                });    
        }
}

