package GameEngine;

public class Wall extends GameObject implements Blockable {

	public Wall (Point location) {
		super(location);	
	}
	
	/**
	 * Define collision on walls
	 */
    public void registerCollisionHandler(GameEngine gameEngine){
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Movable.class),
                // TODO: this should be the reject handler which haven't been implemented yet
                new DefaultHandler());
    }
	
	
	
	
	
	
	
}
