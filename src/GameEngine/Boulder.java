package GameEngine;

public class Boulder extends GameObject implements Movable {
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
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Player.class), 
                (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                    Player player = (Player)(obj1 instanceof Player ? obj1 : obj2);
                    CollisionResult res = new CollisionResult(CollisionResult.REJECT); 
                    player.setPushBoulder(true);
                    this.setSpeed(SPEED);
                    return res;
                });
        
       // boulder with monster or door or potion or collectable gameObj
        this.getHandledRes(gameEngine);
    }
    
    /**
     * helper function for register handler
     * just for boulder
     * @param gameEngine
     */
    private void getHandledRes(GameEngine gameEngine) {
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), GameObject.class), 
                (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                    CollisionResult res = new CollisionResult(CollisionResult.HANDLED);
                    
                    // handler for door with boulder
                    if(obj1 instanceof Door || obj2 instanceof Door) {
                        Door door = (Door)(obj1 instanceof Door ? obj1 : obj2);
                        if(door.getState() == Door.CLOSED) {
                            res = new CollisionResult(CollisionResult.REJECT);
                        } else if(door.getState() == Door.OPEN)
                            res = new CollisionResult(CollisionResult.HANDLED);
                    }
                    
                    // hndler for boulder with wall or boulder
                    else if(obj1 instanceof Boulder || obj2 instanceof Boulder)
                        res = new CollisionResult(CollisionResult.REJECT);
                    
                    // handler for boulder with key or monster or potion
                    else if(obj1 instanceof Potion || obj2 instanceof Potion || obj2 instanceof Monster 
                            || obj1 instanceof Monster || obj2 instanceof Collectable || obj1 instanceof Collectable)
                        res = new CollisionResult(CollisionResult.HANDLED);
                    
                    return res;
                });
    }
}
