package GameEngine;

import GameEngine.CollisionHandler.CollisionEntities;
import GameEngine.CollisionHandler.CollisionResult;
import GameEngine.utils.Direction;
import GameEngine.utils.Point;

public class Arrow extends StandardObject implements Collectable, Movable{
    public static final double SPEED = 4;
    public static final int MOVING = 1;

    private Direction facing;

    public Arrow(Point location){
        super(location);
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    @Override
    public double getSpeed() {
        return this.state == MOVING ? SPEED : 0;
    }

    @Override
    public Direction getFacing() {
        return facing;
    }

    @Override
    public boolean getCollected(GameEngine engine, Inventory playerInventory) {
        playerInventory.addObject(this);
        engine.removeGameObject(this);
        return true;
    }
    
    /**
	 * Boulder && Moving arrow collision handler
	 */
	@Override
    public void registerCollisionHandler(GameEngine gameEngine) {
	    gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Boulder.class),
            (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                Arrow arrow = (Arrow)(obj1 instanceof Arrow ? obj1 : obj2);

                CollisionResult res = new CollisionResult(0);

                //Check if arrow is in the moving state
                if (arrow.getState() == MOVING) {
                	if (obj1 instanceof Arrow) {
                		res.addFlag(CollisionResult.DELETE_FIRST);
                	} else if (obj2 instanceof Arrow) {
                		res.addFlag(CollisionResult.DELETE_SECOND);
                	}
                }
                return res;
            });
	    
	    
	    //Closed door && moving arrow collision handler
	    gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Door.class),
            (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                // Leaving the boulder conditional incase of future change to methodology
                Door door = (Door)(obj1 instanceof Door ? obj1 : obj2);
                Arrow arrow = (Arrow)(obj1 instanceof Arrow ? obj1 : obj2);

                CollisionResult res = new CollisionResult(0);

                //Check if arrow is in the moving state
                if (arrow.getState() == MOVING && door.getState() == 0) {
                	if (obj1 instanceof Arrow) {
                		res.addFlag(CollisionResult.DELETE_FIRST);
                	} else if (obj2 instanceof Arrow) {
                		res.addFlag(CollisionResult.DELETE_SECOND);
                	}
                }
                return res;
            });
	    
	    //Monster && moving arrow collision handler
	    gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Monster.class),
	            (GameEngine engine, GameObject obj1, GameObject obj2) -> {
	                // Leaving the boulder conditional incase of future change to methodology
	                Monster monster = (Monster)(obj1 instanceof Monster ? obj1 : obj2);
	                Arrow arrow = (Arrow)(obj1 instanceof Arrow ? obj1 : obj2);

	                CollisionResult res = new CollisionResult(0);

	                //Check if arrow is in the moving state
	                if (arrow.getState() == MOVING) {
	                	res.addFlag(CollisionResult.DELETE_BOTH);
	                }
	                return res;
	            });
    }
}
