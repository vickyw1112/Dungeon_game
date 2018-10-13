package Controller;

import GameEngine.TimerRequired;

import java.util.LinkedList;
import java.util.List;

public class TimerCollection {
    private List<Timer> timers;

    public TimerCollection(){
        timers = new LinkedList<>();
    }

    /**
     * Add or replace timer according to it's type
     * @param timer
     */
    public void add(Timer timer){
        // ignore timer that will never expire
        if(timer.getRemain() == TimerRequired.LAST_FOREVER)
            return;

        switch (timer.getTimerType()){
            case CLASS_SPECIFIC:
                // remove existing timer with same type of associated object
                timers.removeIf(t -> t.equalByType(timer));
                break;
            case OBJECT_SPECIFIC:
                timers.removeIf(t -> t.equalByInstance(timer));
                break;
        }
        timers.add(timer);
    }

    /**
     * Update all timer by tick milliseconds
     * @param tick clock tick in millisecond
     */
    public void updateAll(int tick){
        // remove if timer is expired
        timers.removeIf(timer -> timer.update(tick));
    }
}
