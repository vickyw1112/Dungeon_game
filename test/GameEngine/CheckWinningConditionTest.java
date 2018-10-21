package GameEngine;

import GameEngine.WinningCondition.BouldersOnAllSwitches;
import GameEngine.WinningCondition.CollectAllTreasure;
import GameEngine.WinningCondition.EliminateAllMonsters;
import GameEngine.utils.Point;
import static org.junit.Assert.*;
import org.junit.*;

public class CheckWinningConditionTest {
    private Player player;
    private GameEngine ge;
    private Treasure treasure1;
    private Treasure treasure2;
    private Boulder boulder1;
    private Boulder boulder2;
    private Monster hunter1;
    private FloorSwitch floorSwitch1;
    private FloorSwitch floorSwitch2;
    private Exit exit;
    private Monster hunter2;
    private MapBuilder mb;

    @Before
    public void setUp(){
        player = new Player(new Point(2, 2));
        player.initialize();
        treasure1 = new Treasure(new Point(3, 4));
        treasure2 = new Treasure(new Point(3, 1));
        boulder1 = new Boulder(new Point(7, 8));
        boulder2 = new Boulder(new Point(7, 1));
        hunter1 = new Hunter(new Point(5, 6));
        hunter2 = new Hunter(new Point(2, 0));
        floorSwitch1 = new FloorSwitch(new Point(8, 1));
        floorSwitch2 = new FloorSwitch(new Point(9, 1));
        exit = new Exit(new Point(1, 2));
        mb = new MapBuilder();
        mb.addObject(player);
        mb.addObject(treasure1);
        mb.addObject(treasure2);
        mb.addObject(boulder1);
        mb.addObject(boulder2);
        mb.addObject(hunter1);
        mb.addObject(hunter2);
        mb.addObject(floorSwitch1);
        mb.addObject(floorSwitch2);
    }

    @Test
    public void treasureWinTest(){
        mb.addWinningCondition(CollectAllTreasure.class.getSimpleName());
        Map map = mb.build();
        ge = new GameEngine(map);
        assertFalse(ge.isWinning());
        treasure1.getCollected(ge, player.getInventory());
        assertFalse(ge.isWinning());
        treasure2.getCollected(ge, player.getInventory());
        assertTrue(ge.isWinning());
    }

    @Test
    public void monsterWinTest(){
        mb.addWinningCondition(EliminateAllMonsters.class.getSimpleName());
        Map map = mb.build();
        ge = new GameEngine(map);
        ge.removeGameObject(hunter1);
        assertFalse(ge.isWinning());
        ge.removeGameObject(hunter2);
        assertTrue(ge.isWinning());
    }

    @Test
    public void floorSwitchWinTest(){
        mb.addWinningCondition(BouldersOnAllSwitches.class.getSimpleName());
        Map map = mb.build();
        ge = new GameEngine(map);
        boulder1.setLocation(new Point(8, 1));
        assertFalse(ge.isWinning());
        boulder2.setLocation(new Point(8, 1));
        assertFalse(ge.isWinning());
        boulder2.setLocation(new Point(9, 1));
        assertTrue(ge.isWinning());
    }
}
