package GameEngine;

import GameEngine.CollisionHandler.*;
import GameEngine.utils.Direction;
import GameEngine.utils.Point;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Player extends StandardObject implements Movable {
    /**
     * Default movement speed for player
     * Unit is grid per second
     */
    // TODO: set onPushingBoulder to false
    
    public static final double SPEED = 2;

    // player inventory should be newly instantiated when loading map
    // not loading from serialized file
    private transient Inventory inventory;

    private Set<PlayerEffect> effects;
    private Direction facing;
    private boolean onPushingBoulder;

    
    /**
     * Constructor for Player
     *
     * @param location initial location
     */
    public Player(Point location){
        super(location);
        // set default values
        facing = Direction.UP;
        onPushingBoulder = false;
    }

    @Override
    public void initialize(){
        inventory = new Inventory();
        effects = new HashSet<>();
        onPushingBoulder = false;
    }

    /**
     * getter for Inventory
     * @return inventory
     */

    public Inventory getInventory() {
		return inventory;
	}

	/**
     * Get current facing of a movable object
     * @return facing direction
     */
    @Override
    public Direction getFacing(){
        return facing;
    }

    /**
     * Update facing when set location
     *
     * @pre player's location is consistently changed i.e. one grid at a time
     * @param point new location
     * @return whether location changed
     */
    @Override
    public boolean setLocation(Point point) {
        if(point.getX() > this.location.getX())
            facing = Direction.RIGHT;
        else if(point.getX() < this.location.getX())
            facing = Direction.LEFT;
        else if(point.getY() > this.location.getY())
            facing = Direction.DOWN;
        else if(point.getY() < this.location.getY())
            facing = Direction.UP;
        if(this.location == point)
            return false;
        this.location = point;
        return true;
    }

    /**
     * Get current speed of the movable object
     * @return speed
     */
    @Override
    public double getSpeed(){
        return onPushingBoulder ? SPEED / 2 : SPEED;
    }

    /**
     * Actual handler for shoot an arrow
     * Decrement the number of arrow in the inventory
     * returns false if there's no arrow in inventory
     *
     * @see GameEngine#playerShootArrow()
     * @return whether the player has an arrow to shoot
     */
    public Arrow shootArrow(){
        Arrow arrow = (Arrow)inventory.popObject(Arrow.class.getSimpleName());
        if(arrow == null)
            return null;
        // setup the arrow
        arrow.changeState(Arrow.MOVING);
        arrow.setFacing(this.facing);
        arrow.setLocation(getFrontGrid());
        return arrow;
    }

    /**
     * Actual handler for setting bomb
     * Decrement the number of unlit bomb in the inventory
     * Change state of bomb to Lit and return it
     * returns null if there's no unlit bomb in inventory
     * or if the there something else in front of the player
     *
     * @see GameEngine#playerSetBomb()
     * @return the bomb instance or null
     */
    public Bomb setBomb(Map map){
        // if player does not have bomb
        if(inventory.getCount(Bomb.class.getSimpleName()) == 0)
            return null;

        // if there is something else in front of player
        Point setPosition = getFrontGrid();
        if(map.getObjects(setPosition).size() != 0)
            return null;

        // case when bomb is planted
        Bomb bomb = (Bomb)inventory.popObject(Bomb.class.getSimpleName());
        bomb.setLocation(setPosition);   
        bomb.changeState(bomb.ALMOSTLIT);
        return bomb; // the front end will see an almost lit bomb and then use bomb.destroy (front end deals with most of this)
        
    }
 
    /**
     * Get a list of all player effects that the player
     * is carrying currently
     *
     * @return list of player effect
     */
    public List<PlayerEffect> getPlayerEffects(){
        return new LinkedList<>(effects);
    }

    /**
     * Add a effect to the player
     *
     * @param effect the effect to be added
     */
    public void addPlayerEffect(PlayerEffect effect){
        effects.add(effect);
    }

    /**
     * Remove a effect from player
     *
     * @param effect the effect to be removed
     */
    public void removePlyaerEffect(PlayerEffect effect){
        effects.remove(effect);
    }

    /**
     * Get the grid in front of the player
     * TODO: move this to Movable?
     *
     * @see Player#setBomb
     * @see Player#shootArrow
     * TODO: add stategiest path finder here
     * @return the location of that grid
     */
    Point getFrontGrid(){
        Point res = new Point(this.location);
        int dx = 0, dy = 0;
        switch (this.facing){
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
        return res;
    }
    

    /**
     * Define collision handler for player
     */
    @Override
    public void registerCollisionHandler(GameEngine gameEngine){
        // Register handler for Player collide with Pit
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Pit.class),
            (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                // Have to check instance type here
                Player player = (Player)(obj1 instanceof Player ? obj1 : obj2);
                CollisionResult res = new CollisionResult(0);
                if(player.effects.contains(PlayerEffect.HOVER)) {
                    res.addFlag(CollisionResult.HANDLED);
                    return res;
                } else {
                    res.addFlag(CollisionResult.LOSE);
                    return res;
                }
            });
        
        // Register handler for Player collide with moving arrow
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Arrow.class),
            (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                // Have to check instance type here
                Player player = (Player)(obj1 instanceof Player ? obj1 : obj2);
                CollisionResult res = new CollisionResult(0);
                if(player.effects.contains(PlayerEffect.HOVER)) {
                    res.addFlag(CollisionResult.HANDLED);
                    return res;
                } else {
                    res.addFlag(CollisionResult.LOSE);
                    return res;
                }
            });
        
        // Register handler for Player Collide with collectables
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Collectable.class),
                new CollectablesCollisionHandler());
        
        // TODO: collision handlers for player        	
        // Player and Arrow
		
		        		
        // Player and Key
             		
        // Player and Treasure
        
        // Player Boulder
        
        // Player and Monster
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Monster.class),
                new CollisionHandler() {
                    @Override
                    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
                        Player player = (Player) (obj1 instanceof Player?obj1:obj2);
                        CollisionResult res = new CollisionResult(0);
                        if(player.effects.contains(PlayerEffect.INVINCIBLE)) {
                            res.addFlag(CollisionResult.HANDLED);
                            return res;
                        }
                        else if(inventory.getCount(Sword.class.getSimpleName())>0){
                            int n=inventory.getCount(Sword.class.getSimpleName());
                            inventory.setCount(Sword.class.getSimpleName(), n-1);
                            res.addFlag(CollisionResult.HANDLED);
                            return res;
                        }
                        else
                        {
                            res.addFlag(CollisionResult.LOSE);
                            return res;
                        }
                    }
                });

        // Player and Potion
       gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Potion.class), 
               (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                   CollisionResult res = new CollisionResult(0);
                   if(obj1 instanceof Potion)
                       res.addFlag(CollisionResult.DELETE_FIRST);
                   else
                       res.addFlag(CollisionResult.DELETE_SECOND);
                   if(obj1 instanceof HoverPotion || obj2 instanceof HoverPotion)
                       this.effects.add(PlayerEffect.HOVER);
                   else
                       this.effects.add(PlayerEffect.INVINCIBLE);
                   return res;
               });
    }
    
    /**
     * set pushBoulder to true when player intend to push
     */
    public void setPushBoulder(Boolean toogle) {
        this.onPushingBoulder = toogle;
    }
}
