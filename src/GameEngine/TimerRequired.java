package GameEngine;

/**
 * For GameObject that require front end
 * to have a timer associated with it
 */
public interface TimerRequired extends GameObject {
    public static final int LAST_FOREVER = Integer.MIN_VALUE;

    /**
     * Get duration for the timer
     * @return duration in milliseconds
     */
    public int getDuration();

    /**
     * Behaviour when the timer's remain value
     * is updated
     * @param remain remain time of the timer
     */
    default public void onTimerUpdate(int remain){

    }

    default public TimerType getTimerType(){
        return TimerType.OBJECT_SPECIFIC;
    }

}

