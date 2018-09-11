package GameEngine;

public class Boulder extends GameObject implements Movable {
    private Direction facing;
    private double speed;

    public Boulder(Point location) {
        super(location);
    }

    @Override
    public Direction getFacing() {
        return this.facing;
    }
    
    /**
     * set boulder's direction same as player's
     * @param facing
     */
    public void setFacing(Direction facing) {
        this.facing = facing;
    }
    
    /**
     * set speed same as player
     * @param speed
     */
    public void setSpeed(Double speed) {
        this.speed = speed;
    }
    
    @Override
    public double getSpeed() {
        return this.speed;
    }

    /* (non-Javadoc)
     * @see GameEngine.GameObject#registerCollisionHandler(GameEngine.GameEngine)
     */
    @Override
    public void registerCollisionHandler(GameEngine gameEngine) {
        // Register handler for boulder collide with player
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClassName(), Player.class.getSimpleName()), new CollisionHandler() {
            @Override
            public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
                
                return null;
            }
        });
    }
    
    
}
