package GameEngine;

import GameEngine.utils.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class HoundPathGeneratorTest {
    private GameEngine engine;
    private MapBuilder mb;
    private Map map;
    private Hunter hunter;
    private Hound hound;
    private Player player;

    private boolean isConsecutivePoints (Point p1, Point p2){
        int dx = p1.getX() - p2.getX();
        int dy = p1.getY() - p2.getY();
        return Math.abs(dx + dy) == 1;
    }

    @Before
    public void setup() {
        mb = new MapBuilder();
        for(int x = 2; x <= 6; x++){
            mb.addObject(new Wall(new Point(x, 1)));
            mb.addObject(new Wall(new Point(x, 3)));
        }
        player = new Player(new Point(4, 2));
        mb.addObject(player);
        hunter = new Hunter(new Point(1, 2));
        mb.addObject(hunter);
        hound = new Hound(new Point(1, 3));
        mb.addObject(hound);
        hound.setPair(hunter);
        map = new Map(mb);
        engine = new GameEngine(map);
        engine.updateMonstersPath();
    }

    @Test
    public void testHoundPathGenerator() {
        Point[] path = {new Point(2, 2), new Point(3, 2 ), new Point(4, 2)};
        assertEquals(hunter.getPath(), (Arrays.asList(path)));
        assertEquals(hound.getPath().size(), 12);
        assertEquals(hound.getPath().get(0), new Point(1, 4));
        assertEquals(hound.getPath().get(10), new Point(5, 2));
        assertEquals(hound.getPath().get(11), new Point(4, 2));
        for(int i = 0; i < hound.getPath().size() - 2; i++){
            assertTrue(isConsecutivePoints(hound.getPath().get(i), hound.getPath().get(i+1)));
        }
    }

    @Test
    public void testMonsterUpdatePathOnPlayerMovement() {
        engine.changeObjectLocation(player, new Point(5, 2));
        assertEquals(hunter.getPath().size(), 3);
        assertEquals(hound.getPath().size(), 12);
    }

    @Test
    public void testMonsterUpdateFacingOnMovement() {
        assertEquals(hunter.getFacing(), Direction.RIGHT);
        assertEquals(hound.getFacing(), Direction.DOWN);
        engine.changeObjectLocation(hound, new Point(1, 4));
        assertEquals(hound.getFacing(), Direction.RIGHT);
    }
}
