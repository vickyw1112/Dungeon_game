package GameEngineTest;

import static org.junit.Assert.*;

import GameEngine.utils.Point;
import org.junit.Before;

import GameEngine.*;

import org.junit.Test;

public class BFSTest {

//    private MapBuilder mapBuilder;
//    private Map map;
//    private Point origin = new Point(0,0);
//    private Point p0 = new Point(0,1);
//    private Point p1 = new Point(1,1);
//    private Point p2 = new Point(2,1);
//    private Point p3 = new Point(3,1);
//    private Point p4 = new Point(4,1);
//    private Point p5 = new Point(5,1);
//    private Point p6 = new Point(6,1);
//    private Point p7 = new Point(7,0);
//
//
//    private MapBuilder generateMapBuilder(){
//        MapBuilder mapBuilder = new MapBuilder();
//        Player player = new Player(origin);
//        Wall wall1 = new Wall(p1);
//        Wall wall2 = new Wall(p2);
//        Wall wall3 = new Wall(p3);
//        Wall wall4 = new Wall(p4);
//        Wall wall5 = new Wall(p5);
//        Wall wall6 = new Wall(p6);
//        Monster monster = new Hunter(p7);
//
//        mapBuilder.addObject(player);
//        mapBuilder.addObject(wall1);
//        mapBuilder.addObject(wall2);
//        mapBuilder.addObject(wall3);
//        mapBuilder.addObject(wall4);
//        mapBuilder.addObject(wall5);
//        mapBuilder.addObject(wall6);
//        mapBuilder.addObject(monster);
//        return mapBuilder;
//    }
//
//    private Map generateMap(){
//        MapBuilder mapBuilder = generateMapBuilder();
//        return new Map(mapBuilder);
//    }
//
//    @Before
//    public void setup(){
//        mapBuilder = new MapBuilder();
//        map = new Map();
//    }
//
//    /**
//     * Generates a Map to check BFS test
//     * @throws Exception
//     */
//    @Test
//    public void BFSTest() throws Exception {
//        map = generateMap();
//        GameEngine engine = new GameEngine(map);
//        Monster monster = new Hunter(p7);
//        Player player = new Player(origin);
//        ShortestPathGenerator path = new ShortestPathGenerator();
//        path = map.getShortestPath(monster.getLocation(), player.getLocation());
//        assertEquals(path, (6,0));
//    }
//


}
