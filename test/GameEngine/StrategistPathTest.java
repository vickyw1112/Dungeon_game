package GameEngine;

import GameEngine.utils.Direction;
import GameEngine.utils.Point;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class StrategistPathTest {
    private Player player;
    private GameEngine engine;
    private Map map;
    private Monster strategist;
    private Wall wall0;
    private Wall wall2;
    private Wall wall4;
    private Wall wall5;
    private Wall wall6;
    private Wall wall7;
    private MapBuilder mb;
    private PathGenerator pg;

    @Before
    public void setUp() {
        mb = new MapBuilder();
        player = new Player(new Point(0, 0));
        wall0 = new Wall(new Point(0,1));
        wall2 = new Wall(new Point(2,1));
        wall4 = new Wall(new Point(4,1));
        wall5 = new Wall(new Point(5, 1));
        wall6 = new Wall(new Point(6, 1));
        wall7 = new Wall(new Point(7,1 ));
        strategist = new Strategist(new Point(0,2));


        mb.addObject(player);
        mb.addObject(wall0);
        mb.addObject(wall2);
        mb.addObject(wall4);
        mb.addObject(wall5);
        mb.addObject(wall6);
        mb.addObject(wall7);
        mb.addObject(strategist);

        map = new Map(mb);
        engine = new GameEngine(map);
        engine.updateMonstersPath();
    }

    @Test
    public void isRightPathTest1(){
        LinkedList<Point> path = strategist.getPath();
        LinkedList<Point> check = new LinkedList<>();

        check.add(new Point(1,2));
        check.add(new Point(1,1));
        check.add(new Point(1,0));
        check.add(new Point(0,0));
        assertEquals(check, path);
    }

    @Test
    public void isRightPathTest2(){
        Boulder boulder = new Boulder(new Point(1,1 ));
        mb.addObject(boulder);
        map = new Map(mb);
        engine = new GameEngine(map);
        engine.updateMonstersPath();

        player.setFacing(Direction.DOWN);

        LinkedList<Point> path = strategist.getPath();
        LinkedList<Point> check = new LinkedList<>();

        check.add(new Point(1,2));
        check.add(new Point(2,2));
        check.add(new Point(3,2));
        check.add(new Point(3,1));
        check.add(new Point(3,0));
        check.add(new Point(2,0));
        check.add(new Point(1,0));
        check.add(new Point(0,0));
        assertEquals(check, path);
    }

    @Test
    public void isRightPathTest3(){
        Boulder boulder = new Boulder(new Point(1,1 ));
        mb.addObject(boulder);
        map = new Map(mb);
        engine = new GameEngine(map);
        player.setFacing(Direction.RIGHT);
        engine.updateMonstersPath();

        LinkedList<Point> path = strategist.getPath();
        LinkedList<Point> check = new LinkedList<>();

        check.add(new Point(1,2));
        check.add(new Point(2,2));
        check.add(new Point(3,2));
        check.add(new Point(3,1));
        check.add(new Point(3,0));
        check.add(new Point(2,0));
        check.add(new Point(1,0));
        assertEquals(check, path);
    }
}
