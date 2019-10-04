package Controller;

import GameEngine.TimerRequired;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TimerCollection {
	/**
	 * field values
	 */
    private ObservableList<Node> mountPoint;
    private List<Timer> timers;
    private HashMap<Timer, ImageView> timerImageViewMap;
    private HashMap<Timer, ProgressBar> timerProgressBarHashMap;
    private ResourceManager resources;

	/**
	 * Constructor for TimerCollection
	 *
	 * @param timerMountPoint
	 * @param resourceManager
	 */
	public TimerCollection(ObservableList<Node> timerMountPoint, ResourceManager resourceManager){
        timers = new LinkedList<>();
        mountPoint = timerMountPoint;
        timerImageViewMap = new HashMap<>();
        timerProgressBarHashMap = new HashMap<>();
        resources = resourceManager;
    }

	/**
	 * removeTimer method
	 *
	 * removes the timer
	 * @param t
	 */
	private void removeTimer(Timer t){
        timers.remove(t);
        mountPoint.remove(timerImageViewMap.remove(t));
        mountPoint.remove(timerProgressBarHashMap.remove(t));
    }

    /**
     * Add or replace timer according to it's type
     * @param timer
     */
    public void add(Timer timer){
        switch (timer.getTimerType()){
            case CLASS_SPECIFIC:
                // remove existing timer with same type of associated object
                for(Timer t: new LinkedList<>(timers)){
                    if(t.equalByType(timer))
                        removeTimer(t);
                }
                break;
            case OBJECT_SPECIFIC:
                for(Timer t: new LinkedList<>(timers)){
                    if(t.equalByInstance(timer))
                        removeTimer(t);
                }
                break;
        }

        ImageView imageView = resources.createImageViewByClassName(timer.getObj().getClassName());
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(30);
        timerImageViewMap.put(timer, imageView);
        mountPoint.add(imageView);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setMaxWidth(80);
        progressBar.setMinWidth(80);
        timerProgressBarHashMap.put(timer, progressBar);
        mountPoint.add(progressBar);

        timers.add(timer);
    }

	/**
	 * updateTimer method
	 *
	 * method to update the timer
	 * @param timer
	 * @param tick
	 * @return
	 */
    private boolean updateTimer(Timer timer, int tick){
        boolean ret = timer.update(tick);
        if(timer.getRemain() != TimerRequired.LAST_FOREVER)
            timerProgressBarHashMap.get(timer)
                    .setProgress(timer.getRemain() * 1.0 / timer.getTotalDuration());
        return ret;
    }

    /**
     * Update all timer by tick milliseconds
     * @param tick clock tick in millisecond
     */
    public void updateAll(int tick){
        // remove if timer is expired
        for(Timer t: new LinkedList<>(timers)){
            if(updateTimer(t, tick))
                removeTimer(t);
        }
    }
}
