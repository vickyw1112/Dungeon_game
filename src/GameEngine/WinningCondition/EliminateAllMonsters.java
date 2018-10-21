package GameEngine.WinningCondition;

import GameEngine.*;

public class EliminateAllMonsters implements WinningCondition {
    @Override
    public String displayString() {
        return "Eliminate all monsters";
    }

    @Override
    public boolean check(GameEngine engine, Map map) {
        return engine.isMapHasMonster() && !engine.hasMonster();
    }
}
