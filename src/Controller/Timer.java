package Controller;

import GameEngine.TimerRequired;
import GameEngine.TimerType;

import java.util.function.Consumer;

public class Timer {

	/**
	 * field values
	 * all values given in ms
	 */
    private int remain;
    private TimerRequired obj;
    private Consumer<Timer> onUpdateTimer;
    private Consumer<Timer> onTimerExpired;

	/**
	 * Constructor for Timer Class
	 *
	 * @param object
	 * @param onTimerExpired
	 */
	public Timer(TimerRequired object, Consumer<Timer> onTimerExpired){
        this.obj = object;
        this.remain = object.getDuration();
        this.onTimerExpired = onTimerExpired;
        this.onUpdateTimer = null;
    }

	/**
	 * getter for remaing time
	 * @return
	 */
    public int getRemain() {
        return remain;
    }

	/**
	 * getter for totalduration
	 * @return
	 */
	public int getTotalDuration() {
    	return obj.getDuration();
	}

	/**
	 * setter for Updating timer
	 * @param onUpdateTimer
	 */
    public void setOnUpdateTimer(Consumer<Timer> onUpdateTimer){
        this.onUpdateTimer = onUpdateTimer;
    }

	/**
	 * getObj method
	 * getter
	 * @return
	 */
	public TimerRequired getObj() {
        return obj;
    }

    /**
     * Update a timer by a given time
     * If the timer expires, run onTimerExpired
     * @param elapsed elapsed milliseconds
     * @return whether the timer is expired
     */
    public boolean update(int elapsed){
        if(remain == TimerRequired.LAST_FOREVER)
            return false;
        remain -= elapsed;
        if(remain <= 0){
            onTimerExpired.accept(this);
            return true;
        } else if(onUpdateTimer != null){
            // run front-end registered event
            onUpdateTimer.accept(this);
        }
        // run backend event
        obj.onTimerUpdate(remain);
        return false;
    }

    public TimerType getTimerType(){
        return obj.getTimerType();
    }

	/**
	 * equals method
	 *
	 * Checking equals by class
	 * @param anotherObject
	 * @return
	 */
	public boolean equalByType(Timer anotherObject){
        return obj.getClass().equals(anotherObject.obj.getClass());
    }

	/**
	 * equals method
	 *
	 * checking equals by instance
	 * @param anotherObject
	 * @return
	 */
	public boolean equalByInstance(Timer anotherObject){
        return obj.equals(anotherObject.obj);
    }

}
