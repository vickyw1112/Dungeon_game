package GameEngine;

import java.awt.*;
import java.util.List;
import java.util.Set;

public class Player extends GameObject implements Movable {
    public static int classID = 0;

    private Inventory inventory;
    private Set<PlayerEffect> states;


    /**
     * Constructor for Player
     *
     * @param location initial location
     */
    public Player(Point location){
        super(location);
    }

    @Override
    public int getClassId(){
        return 0;
    }

    /**
     * Get current facing of a movable object
     * @return facing direction
     */
    @Override
    public Direction getFacing(){
        return Direction.DOWN;
    }

    /**
     * Get current speed of the movable object
     * @return speed
     */
    @Override
    public double getSpeed(){
        return 100;
    }

    /**
     * Actual handler for shoot an arrow
     * Decrement the number of arrow in the inventory
     * returns false if there's no arrow in inventory
     *
     * @see GameEngine#playerShootArrow()
     * @return whether the player has an arrow to shoot
     */
    public boolean shootArrow(){

        return true;
    }

    /**
     * Actual handler for setting bomb
     * Decrement the number of unlit bomb in the inventory
     * returns false if there's no unlit bomb in inventory
     *
     * @see GameEngine#playerSetBomb()
     * @return whether the player has unlit bomb to shoot
     */
    public boolean setBomb(){

        return true;
    }

    /**
     * Get a list of all player effects that the player
     * is carrying currently
     *
     * @return list of player effect
     */
    public List<PlayerEffect> getPlayerEffects(){

        return null;
    }

    /**
     * Add a effect to the player
     *
     * @param effect the effect to be added
     */
    public void addPlayerEffect(PlayerEffect effect){

    }

    /**
     * Remove a effect from player
     *
     * @param effect the effect to be removed
     */
    public void removePlyaerEffect(PlayerEffect effect){

    }
    
    /**
     * Adds inventory to player and amount as well
     * @param game object and the amount to add in
     */
    public void addInventory(String gameObject, int amount) {
    		
    }
    
    

}
