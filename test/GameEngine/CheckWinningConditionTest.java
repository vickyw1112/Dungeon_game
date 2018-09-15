package GameEngine;

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
    private Monster hound;
    private FloorSwitch floorSwitch1;
    private FloorSwitch floorSwitch2;
    private Exit exit;
    private Monster hunter;
    private MapBuilder mb;

    @Before
    public void setUp(){
        player = new Player(new Point(2, 2));
        player.initialize();
        treasure1 = new Treasure(new Point(3, 4));
        treasure2 = new Treasure(new Point(3, 1));
        boulder1 = new Boulder(new Point(7, 8));
        boulder2 = new Boulder(new Point(7, 1));
        hound = new Hound(new Point(5, 6));
        hunter = new Hunter(new Point(2, 0));
        floorSwitch1 = new FloorSwitch(new Point(8, 1));
        floorSwitch2 = new FloorSwitch(new Point(9, 1));
        exit = new Exit(new Point(1, 2));
        mb = new MapBuilder();
        mb.addObject(player);
        mb.addObject(treasure1);
        mb.addObject(treasure2);
        mb.addObject(boulder1);
        mb.addObject(boulder2);
        mb.addObject(hound);
        mb.addObject(hunter);
        mb.addObject(floorSwitch1);
        mb.addObject(floorSwitch2);
        Map map = new Map(mb);
        ge = new GameEngine(map);
    }

    @Test
    public void treasureWinTest(){
        treasure1.getCollected(ge, player.getInventory());
        assertEquals(ge.checkWiningCondition(), false);
        treasure2.getCollected(ge, player.getInventory());
        assertEquals(ge.checkWiningCondition(), true);
    }

    @Test
    public void monsterWinTest(){
        ge.removeGameObject(hound);
        assertEquals(ge.checkWiningCondition(), false);
        ge.removeGameObject(hunter);
        assertEquals(ge.checkWiningCondition(), true);

    }

    @Test
    public void floorSwitchWinTest(){
        boulder1.setLocation(new Point(8, 1));
        assertEquals(ge.checkWiningCondition(), false);
        boulder2.setLocation(new Point(8, 1));
        assertEquals(ge.checkWiningCondition(), false);
        boulder2.setLocation(new Point(9, 1));
        assertEquals(ge.checkWiningCondition(), true);
    }

    @Test
    public void exitWinTest(){
        mb.addObject(exit);
        ge = new GameEngine(new Map(mb));

        treasure1.getCollected(ge, player.getInventory());
        treasure2.getCollected(ge, player.getInventory());
        assertEquals(ge.checkWiningCondition(), false);

        boulder1.setLocation(new Point(8, 1));
        boulder2.setLocation(new Point(9, 1));
        assertEquals(ge.checkWiningCondition(), false);

        ge.removeGameObject(hound);
        ge.removeGameObject(hunter);
        assertEquals(ge.checkWiningCondition(), false);

        player.setLocation(exit.location);
        assertEquals(ge.checkWiningCondition(), true);
    }
}
