package GameEngine;

public class Boulder extends GameObject implements Movable {
    private Direction facing;
    private double speed;

    public Boulder(Point location) {
        super(location);
        this.speed = 0;
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

    /* 
     * Define collision handler for player
     */
    @Override
    public void registerCollisionHandler(GameEngine gameEngine) {
        // Register handler for boulder collide with pit
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Pit.class),
                (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                    // check instance type here
                    Boulder boulder = (Boulder)(obj1 instanceof Boulder ? obj1: obj2);
                    gameEngine.removeGameObject(boulder);
                    CollisionResult res; 
                    if(obj1 instanceof Boulder)
                        res = new CollisionResult(CollisionResult.DELETE_FIRST);
                    else
                        res = new CollisionResult(CollisionResult.DELETE_SECOND);
                    return res;
        });
        
        // Register handler for boulder collide with player
        
        
        
        
        
        
    }
    
    
}
