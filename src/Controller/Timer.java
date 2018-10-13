package Controller;

import GameEngine.TimerRequired;
import GameEngine.TimerType;

import java.util.function.Consumer;

public class Timer {
    // in ms
    private int remain;
    private TimerRequired obj;
    private Consumer<Integer> onUpdateTimer;
    private Consumer<Object> onTimerExpired;

    public Timer(TimerRequired object, Consumer<Object> onTimerExpired){
        this.obj = object;
        this.remain = object.getDuration();
        this.onTimerExpired = onTimerExpired;
        this.onUpdateTimer = null;
    }

    public int getRemain() {
        return remain;
    }

    public void setOnUpdateTimer(Consumer<Integer> onUpdateTimer){
        this.onUpdateTimer = onUpdateTimer;
    }

    /**
     * Update a timer by a given time
     * If the timer expires, run onTimerExpired
     * @param elapsed elapsed milliseconds
     * @return whether the timer is expired
     */
    public boolean update(int elapsed){
        remain -= elapsed;
        if(remain <= 0){
            onTimerExpired.accept(null);
            return true;
        } else if(onUpdateTimer != null){
            // run backend event
            obj.onTimerUpdate(remain);
            // run front-end registered event
            onUpdateTimer.accept(remain);
        }
        return false;
    }

    public TimerType getTimerType(){
        return obj.getTimerType();
    }

    public boolean equalByType(Timer anotherObject){
        return obj.getClass().equals(anotherObject.obj.getClass());
    }

    public boolean equalByInstance(Timer anotherObject){
        return obj.equals(anotherObject.obj);
    }
}
