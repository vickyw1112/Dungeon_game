package GameEngine;

/**
 * Result of a collision
 * An instance of this class is returned back to front end
 * upon a finished backend handling of collision
 * which contains information on how the front end should
 * update the View
 */
public class CollisionResult {
    /**
     * Bit flags
     */
    public static final int DELETE_FIRST = 0x01;
    public static final int DELETE_SECOND = 0x02;
    public static final int DELETE_BOTH = 0x03;
    public static final int REJECT = 0x04;
    public static final int HANDLED = 0x08; // do we need this?
    public static final int REFRESH_INVENTORY = 0x10;
    public static final int REFRESH_EFFECT_TIMER = 0x20;
    public static final int SHOOT_ARROW = 0x40;
    public static final int SET_BOMB = 0x80;
    public static final int WIN = 0x100;
    public static final int LOSE = 0x200;

    public static final int FLAG_SIZE = 32;

    /**
     * 32 bits string
     */
    private int flags;

    /**
     * Array of length 2
     * Two objects being collided
     */
    public GameObject[] collisionObjects;


    /**
     * Constructor of CollisionResult
     *
     * @param flags initial flags
     */
    public CollisionResult(int flags){

    }


    /**
     * Set a specific bit of flags to true
     * @param flag
     */
    public void addFlag(int flag) {

    }

    /**
     * Get flags
     * @return flags
     */
    public int getFlags(){
        return flags;
    }
}