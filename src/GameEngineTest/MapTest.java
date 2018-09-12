package GameEngineTest;

import GameEngine.Map;
import GameEngine.MapBuilder;
import GameEngine.Player;
import GameEngine.Point;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MapTest {
    private MapBuilder mapBuilder;
    private Map map;

    private Map generateMap(){

        return new Map();
    }

    @Before
    public void setup(){
        mapBuilder = new MapBuilder();
        map = new Map();
    }

    @Test
    public void MapBuilderTest(){
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        Player player = new Player(p1);
        mapBuilder.addObject(player);
        assertEquals(mapBuilder.getObject(p1), player);

    }
}
