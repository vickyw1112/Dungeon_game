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
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), GameObject.class), 
                (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                    CollisionResult res = new CollisionResult(0);
                    
                    // handler for door with boulder
                    if(obj1 instanceof Door || obj2 instanceof Door) {
                        Door door = (Door)(obj1 instanceof Door ? obj1 : obj2);
                        if(door.getState() == Door.CLOSED) {
                            res.addFlag(CollisionResult.REJECT);
                        } else if(door.getState() == Door.OPEN)
                            res.addFlag(CollisionResult.HANDLED);
                    }
                    
                    // hndler for boulder with wall or boulder or monster
                    else if(obj1 instanceof Boulder || obj2 instanceof Boulder || obj2 instanceof Monster 
                            || obj1 instanceof Monster)
                        res.addFlag(CollisionResult.REJECT);
                    
                    // handler for boulder with key or potion
                    else if(obj1 instanceof Potion || obj2 instanceof Potion || obj2 instanceof Collectable || obj1 instanceof Collectable)
                        res.addFlag(CollisionResult.HANDLED);
                    
                    // handler for boulder with pit
                    else if(obj1 instanceof Pit || obj2 instanceof Pit) {
                     // check instance type here
                        Boulder boulder = (Boulder)(obj1 instanceof Boulder ? obj1: obj2);
                        gameEngine.removeGameObject(boulder);
                        if(obj1 instanceof Boulder)
                            res.addFlag(CollisionResult.DELETE_FIRST);
                        else
                            res.addFlag(CollisionResult.DELETE_SECOND);
                    }
                    
                    // handler for boulder with player
                    else if(obj1 instanceof Player || obj2 instanceof Player) {
                        Player player = (Player)(obj1 instanceof Player ? obj1 : obj2); 
                        res.addFlag(CollisionResult.REJECT);
                        player.setPushBoulder(true);
                        this.setSpeed(SPEED);
                    }
                    
                    return res;
                });
    }
}
