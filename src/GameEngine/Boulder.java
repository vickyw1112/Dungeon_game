package GameEngine;

public class Boulder extends GameObject implements Movable, Blockable {
    private final double SPEED = Player.SPEED / 2;
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
     * set bpoulder's speed to 0
     * and s
     */
    @Override
    public boolean setLocation(Point point) {
        this.setSpeed(0.0);
        return super.setLocation(point);
    }

    /* 
     * Define collision handler for boulder
     */
    @Override
    public void registerCollisionHandler(GameEngine gameEngine) {
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Movable.class), 
                (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                    CollisionResult res = new CollisionResult(0);                  
                    res.addFlag(CollisionResult.REJECT);                                       
                    return res;
                });
        
        // handler for boulder with player
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Movable.class), 
                (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                    CollisionResult res = new CollisionResult(0);
                    Player player = (Player)(obj1 instanceof Player ? obj1 : obj2); 
                    res.addFlag(CollisionResult.REJECT);
                    player.setPushBoulder(true);
                    this.setSpeed(SPEED);
                    return res;
                });
        
        // handler for boulder with pit
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Movable.class), 
                (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                    CollisionResult res = new CollisionResult(0);
                    Boulder boulder = (Boulder)(obj1 instanceof Boulder ? obj1: obj2);
                    gameEngine.removeGameObject(boulder);
                    if(obj1 instanceof Boulder)
                        res.addFlag(CollisionResult.DELETE_FIRST);
                    else
                        res.addFlag(CollisionResult.DELETE_SECOND);
                    return res;
                });
        
    }
}
