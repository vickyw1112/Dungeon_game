package GameEngine;

public class Wall extends GameObject  {

	public Wall (Point location) {
		super(location);	
	}
	
	/**
	 * Define collision on walls
	 */
    public void registerCollisionHandler(GameEngine gameEngine){
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Movable.class),
                new DefaultHandler());
    }
	
	
	
	
	
	
	
}
