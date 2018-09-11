package GameEngine;

import java.awt.*;

public class Arrow extends GameObject implements Collectable, Movable{
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
        engine.getMap().removeObject(this);
        return true;
    }
}
