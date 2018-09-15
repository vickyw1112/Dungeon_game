package GameEngine;

import GameEngine.CollisionHandler.*;
import GameEngine.utils.Direction;
import GameEngine.utils.PlayerEffect;
import GameEngine.utils.Point;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Player extends StandardObject implements Movable {
    /**
     * Default movement speed for player Unit is grid per second
     */
    public static final double SPEED = 2;

    /**
     * transient field so that won't be serialized
     * player inventory should be newly instantiated when loading map
     * not loading from serialized file
     */
    private transient Inventory inventory;

    /**
     * Whether player is pushing boulder
     * This is set to true at the frame when player collide with boulder
     * and set to false after player's location changed
     * @see Player#setLocation
     * @see PlayerBoulderCollisionHandler#handle
     */
    private boolean onPushingBoulder;

    private transient Set<PlayerEffect> effects;
    private Direction facing;



    /**
     * Constructor for Player
     *
     * @param location
     *            initial location
     */
    public Player(Point location) {
        super(location);
        // set default values
        facing = Direction.UP;
        onPushingBoulder = false;
    }


    @Override
    public void initialize() {
        inventory = new Inventory();
        effects = new HashSet<>();
        onPushingBoulder = false;
    }

    /**
     * getter for Inventory
     * 
     * @return inventory
     */

    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Set current facing of player
     *
     * @param facing new facing to set
     */
    public void setFacing(Direction facing){
        this.facing = facing;
    }

    /**
     * Get current facing of player
     * 
     * @return facing direction
     */
    @Override
    public Direction getFacing() {
        return facing;
    }

    /**
     * Set player's onPushingBoulder to false before setting new location
     *
     * @pre player's location is consistently changed i.e. one grid at a time
     * @param point new location
     * @return whether location changed
     */
    @Override
    public boolean setLocation(Point point) {
        if (this.location == point)
            return false;
        this.onPushingBoulder = false;
        this.location = point;
        return true;
    }

    /**
     * Get current speed of the movable object
     * 
     * @return speed
     */
    @Override
    public double getSpeed() {
        return onPushingBoulder ? SPEED / 2 : SPEED;
    }

    /**
     * Actual handler for shoot an arrow Decrement the number of arrow in the
     * inventory returns false if there's no arrow in inventory
     *
     * @see GameEngine#playerShootArrow()
     * @return the arrow instance to shoot,
     *          null if no arrow in inventory or cannot shoot to that direction
     */
    public Arrow shootArrow(Map map) {
        Arrow arrow = (Arrow) inventory.popObject(Arrow.class);
        if (arrow == null || !map.isValidPoint(getFrontGrid(map)))
            return null;
        // setup the arrow
        arrow.changeState(Arrow.MOVING);
        arrow.setFacing(this.facing);
        map.updateObjectLocation(arrow, getFrontGrid(map));
        return arrow;
    }

    /**
     * Actual handler for setting bomb Decrement the number of unlit bomb in the
     * inventory Change state of bomb to Lit and return it returns null if there's
     * no unlit bomb in inventory or if the there something else in front of the
     * player
     *
     * @see GameEngine#playerSetBomb()
     * @return the bomb instance to set,
     *          or null if no bomb in inventory or cannot set to front of player
     */
    public Bomb setBomb(Map map) {
        Bomb bomb = (Bomb) inventory.popObject(Bomb.class);
        Point setPosition = getFrontGrid(map);
        if (bomb == null || !map.getNonBlockAdjacentPoints(location).contains(setPosition))
            return null;

        map.updateObjectLocation(bomb, setPosition);
        bomb.changeState(bomb.ALMOSTLIT);
        return bomb; // the front end will see an almost lit bomb and then use bomb.destroy (front
                     // end deals with most of this)
    }

    /**
     * Get a list of all player effects that the player is carrying currently
     *
     * @return list of player effect
     */
    public List<PlayerEffect> getPlayerEffects() {
        return new LinkedList<>(effects);
    }

    /**
     * Add a effect to the player
     *
     * @param effect
     *            the effect to be added
     */
    public void addPlayerEffect(PlayerEffect effect) {
        effects.add(effect);
    }

    /**
     * Remove a effect from player
     *
     * @param effect
     *            the effect to be removed
     */
    public void removePlyaerEffect(PlayerEffect effect) {
        effects.remove(effect);
    }

    /**
     * Get the grid in front of the player
     *
     * @see Player#setBomb
     * @see Player#shootArrow
     * @see Object TODO: add stategiest path finder here
     * @return the location of that grid or null if that grid is out of bound
     */
    Point getFrontGrid(Map map) {
        Point res = new Point(this.location);
        int dx = 0, dy = 0;
        switch (this.facing) {
        case UP:
            dy--;
            break;
        case DOWN:
            dy++;
            break;
        case LEFT:
            dx--;
            break;
        case RIGHT:
            dx++;
            break;
        }
        res.translate(dx, dy);
        return map.isValidPoint(res) ? res : null;
    }

    /**
     * Define collision handler for player
     */
    @Override
    public void registerCollisionHandler(GameEngine gameEngine) {
        // Register handler for Player collide with Pit
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Pit.class),
                new PlayerPitCollisionHandler());

        // Register handler for Player Collide with collectables
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Collectable.class),
                new CollectablesCollisionHandler());

        // Player and Monster
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Monster.class),
              new PlayerMonsterCollisionHandler());

        // Player and Potion
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Potion.class),
                new PlayerPotionCollisionHandler());

        // Player Boulder
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Boulder.class),
                new PlayerBoulderCollisionHandler());
    }

    /**
     * set pushBoulder to true when player intend to push
     */
    public void setPushBoulder(Boolean val) {
        this.onPushingBoulder = val;
    }
}
