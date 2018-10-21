package GameEngine.WinningCondition;

import GameEngine.*;
import GameEngine.utils.Point;

import java.util.Set;
import java.util.stream.Collectors;

public class BouldersOnAllSwitches implements WinningCondition {
    @Override
    public String displayString() {
        return "Put boulder on all switches";
    }

    @Override
    public boolean check(GameEngine engine, Map map) {
        // locations of both boulders and floor switches
        Set<Point> boulders = engine.getObjectsByClass(Boulder.class).stream()
                .map(o -> o.getLocation())
                .collect(Collectors.toSet());
        Set<Point> floorSwitches = engine.getObjectsByClass(FloorSwitch.class).stream()
                .map(o -> o.getLocation())
                .collect(Collectors.toSet());

        return !floorSwitches.isEmpty() && boulders.containsAll(floorSwitches);
    }
}
