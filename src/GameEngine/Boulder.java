package GameEngine;

import GameEngine.CollisionHandler.*;
import GameEngine.utils.Direction;
import GameEngine.utils.Point;

public class Boulder extends StandardObject implements Movable, Blockable {
    public static final double SPEED = Player.SPEED / 2;
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
     * 
     * @param facing
     */
    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    /**
     * set speed same as player
     * 
     * @param speed
     */
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    /**
     * set boulder's speed to 0 and s
     */
    @Override
    public boolean setLocation(Point point) {
        this.setSpeed(0.0);
        return super.setLocation(point);
    }

    /**
     * Define collision handler for boulder
     */
    @Override
    public void registerCollisionHandler(GameEngine gameEngine) {
        // boulder and monster
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Monster.class),
                new BoulderMonsterCollisionHandler());

        // handler for boulder with pit
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Pit.class),
                new BoulderPitCollisionHandler());

    }
}
