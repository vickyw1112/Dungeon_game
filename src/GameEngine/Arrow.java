package GameEngine;

import GameEngine.CollisionHandler.*;
import GameEngine.utils.*;

public class Arrow extends StandardObject implements Collectable, Movable {
    private static final double SPEED = 4;
    public static final int MOVING = 1;

    private Direction facing;

    /**
     * Constructor for arrow
     * @param location
     */
    public Arrow(Point location) {
        super(location);
    }

    /**
     * setter for facing variable, which gives which direction the object is facing
     * @param facing
     */
    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    /**
     * getter for Speed variable, gives the speed of the object
     * @return
     *          speed
     */
    @Override
    public double getSpeed() {
        return this.state == MOVING ? SPEED : 0;
    }

    /**
     * getter for Facing variable
     * @return
     *          facing
     */
    @Override
    public Direction getFacing() {
        return facing;
    }

    /**
     * method getCollected implemented from Collectables interface
     * takes in parameters engine and playerInventory and returns a boolean true if object is collected
     * @param engine
     *          game engine
     * @param playerInventory
     *          player inventory
     * @return
     *          boolean true/false
     */
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
        // Monster and moving arrow collision handler
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Monster.class),
                new ArrowMonsterCollisionHandler());

        // arrow and all other GameObject
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), GameObject.class),
                new ArrowGameObjectCollisionHandler());

    }
}
