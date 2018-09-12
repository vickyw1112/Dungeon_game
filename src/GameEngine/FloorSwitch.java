package GameEngine;

public class FloorSwitch extends GameObject{
    public static final int TRIGGERED = 1;
    public static final int UNTRIGGERED = 0;
    
    public FloorSwitch(Point location) {
        super(location);
        this.state = UNTRIGGERED;
    }

    /* (non-Javadoc)
     * @see GameEngine.GameObject#registerCollisionHandler(GameEngine.GameEngine)
     */
    @Override
    public void registerCollisionHandler(GameEngine gameEngine) {
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Boulder.class), 
                (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                    this.state = TRIGGERED;
                    return new CollisionResult(CollisionResult.HANDLED);
        }); 
    }   
}
