package GameEngine;

public class Wall extends GameObject implements Blockable {

	public Wall (Point location) {
		super(location);	
	}

    /*
     * collision handler for wall
     */
    @Override
    public void registerCollisionHandler(GameEngine gameEngine) {
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), GameObject.class), 
                (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                    return new CollisionResult(CollisionResult.HANDLED);
                });
    }
}
