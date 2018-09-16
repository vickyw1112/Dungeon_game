package GameEngine.CollisionHandler;

import GameEngine.*;

/**
 * Result of a collision An instance of this class is returned back to front end
 * upon a finished backend handling of collision which contains information on
 * how the front end should update the View
 */
public class CollisionResult {
    /**
     * Bit flags Handled means equivalent to collision has not happened
     */
    public static final int HANDLED = 0x00;
    public static final int DELETE_FIRST = 0x01;
    public static final int DELETE_SECOND = 0x02;
    public static final int DELETE_BOTH = DELETE_FIRST | DELETE_SECOND;
    public static final int REJECT = 0x04;
    public static final int REFRESH_INVENTORY = 0x08;
    public static final int REFRESH_EFFECT_TIMER = 0x10;
    public static final int WIN = 0x20;
    public static final int LOSE = 0x40;

    public static final int FLAG_SIZE = 32; // int size

    /**
     * 32 bits string
     */
    private int flags;

    /**
     * Constructor of CollisionResult
     *
     * @param flags
     *            initial flags
     */
    public CollisionResult(int flags) {
        this.flags = flags;
    }

    /**
     * Empty constructor
     */
    public CollisionResult() {
        this.flags = 0;
    }

    /**
     * Set a specific bit of flags to true BITWISE OR
     * 
     * @param flag flag to set
     */
    public void addFlag(int flag) {
        this.flags |= flag;
    }

    /**
     * Get flags
     * 
     * @return flags
     */
    public int getFlags() {
        return flags;
    }

    /**
     * for testing flag
     * @return whether the result contains the flag
     */
    public boolean containFlag(int flag){
        return (this.flags & flag) > 0;
    }
}
