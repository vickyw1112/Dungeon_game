package GameEngine.WinningCondition;

import GameEngine.*;

public interface WinningCondition {
    /**
     * Check if the game is winning
     */
    public boolean check(GameEngine engine, Map map);

    /**
     * Explaining string associated with the winning condition
     */
    public String displayString();
}
