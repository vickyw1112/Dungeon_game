package GameEngine;

import GameEngine.CollisionHandler.*;
import GameEngine.utils.Direction;
import GameEngine.utils.Point;

public class Arrow extends StandardObject implements Collectable, Movable {
    public static final double SPEED = 4;
    public static final int MOVING = 1;

    private Direction facing;

    public Arrow(Point location) {
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

        // boulder and arrow
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Boulder.class),
                new ArrowBoulderCollisionHandler());

        // Closed door && moving arrow collision handler
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Door.class),
                 new ArrowClosedDoorCollisionHandler());

        // Monster && moving arrow collision handler
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Monster.class),
                new ArrowMonsterCollisionHandler());
    }
}
