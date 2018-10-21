package GameEngine.WinningCondition;

import GameEngine.*;

public class CollectAllTreasure implements WinningCondition {
    @Override
    public String displayString() {
        return "Collect All Treasures";
    }

    @Override
    public boolean check(GameEngine engine, Map map) {
        // there's treasure in player's inventory but none in the map
        return engine.getInventoryCounts(Treasure.class) > 0 &&
                engine.getObjectsByClass(Treasure.class).size() == 0;
    }
}
