package GameEngine;

import GameEngine.utils.Point;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class RunAwayPathGeneratorTest {
    private Player player;
    private GameEngine engine;
    private Map map;
    private Monster monster;
    private Wall wall0;
    private Wall wall1;
    private Wall wall2;
    private Wall wall3;
    private Wall wall4;
    private Wall wall5;
    private Wall wall6;
    private Wall wall7;
    private MapBuilder mb;
    private InvinciblePotion invincibility;

    @Before
    public void setUp() {
        mb = new MapBuilder();
        player = new Player(new Point(0, 0));
        wall0 = new Wall(new Point(0,1));
        wall1 = new Wall(new Point(1,1));
        wall2 = new Wall(new Point(2,1));
        wall3 = new Wall(new Point(3,1));
        wall4 = new Wall(new Point(4,1));
        wall5 = new Wall(new Point(5,1));
        wall6 = new Wall(new Point(6,1));
        wall7 = new Wall(new Point(7,1));
        monster = new Hunter(new Point(2,0));
        invincibility = new InvinciblePotion(new Point(0,3));


        mb.addObject(player);
        mb.addObject(wall0);
        mb.addObject(wall1);
        mb.addObject(wall2);
        mb.addObject(wall3);
        mb.addObject(wall4);
        mb.addObject(wall5);
        mb.addObject(wall6);
        mb.addObject(wall7);

        map = new Map(mb);
        engine = new GameEngine(map);
        player.initialize();
    }

    /**
     *
     */
    @Test
    public void runAwayPathTest() {
        player.applyPotionEffect(engine, invincibility);
        long start = System.nanoTime();
        engine.updateMonstersPath();
        LinkedList<Point> path = monster.getPath();
        long time = System.nanoTime() - start;
        System.out.println(time);

        Point check = new Point(4,0);
        assertEquals(check, path.pop());

        check.move(5,0);
        assertEquals(check, path.pop());

        check.move(6,0);
        assertEquals(check, path.pop());

        check.move(7,0);
        assertEquals(check, path.pop());
    }

    /**
     * runAwayPathBoulderTest method
     * checks runaway path generator if there is an obstacle boulder
     */
    @Test
    public void runAwayPathBoulderTest() {
        Boulder boulder = new Boulder(new Point(8, 1));
        Wall walltest = new Wall(new Point(9,0));
        mb.addObject(boulder);
        mb.addObject(walltest);
        map = new Map(mb);

        player.applyPotionEffect(engine, invincibility);
        long start = System.nanoTime();
        engine.updateMonstersPath();
        LinkedList<Point> path = monster.getPath();
        long time = System.nanoTime() - start;
        System.out.println(time);

        Point check = new Point(4,0);
        assertEquals(check, path.pop());

        check.move(5,0);
        assertEquals(check, path.pop());

        check.move(6,0);
        assertEquals(check, path.pop());

        check.move(7,0);
        assertEquals(check, path.pop());

        check.move(8, 0);
        assertEquals(check, path.pop());

        check.move(8,1);
        assertEquals(check, path.pop());

        check.move(8,2);
        assertEquals(check, path.pop());
    }

    /**
     * runAwayPitTest method
     * Checks RunAway path generator when there is obstacle pit blocking
     */
    @Test
    public void runAwayPitTest() {
        Pit pit = new Pit(new Point(8, 1));
        Wall walltest = new Wall(new Point(9,0));
        mb.addObject(pit);
        mb.addObject(walltest);
        map = new Map(mb);

        player.applyPotionEffect(engine, invincibility);
        long start = System.nanoTime();
        engine.updateMonstersPath();
        LinkedList<Point> path = monster.getPath();
        long time = System.nanoTime() - start;
        System.out.println(time);

        Point check = new Point(4,0);
        assertEquals(check, path.pop());

        check.move(5,0);
        assertEquals(check, path.pop());

        check.move(6,0);
        assertEquals(check, path.pop());

        check.move(7,0);
        assertEquals(check, path.pop());

        check.move(8, 0);
        assertEquals(check, path.pop());

        check.move(8,1);
        assertEquals(check, path.pop());

        check.move(8,2);
        assertEquals(check, path.pop());
    }

    /**
     * runAwayDoorTest method
     * checks the runAway path generator when there is obstacle closed door
     */
    @Test
    public void runAwayDoorTest() {
        Door door = new Door(new Point(8, 1));
        Wall walltest = new Wall(new Point(9,0));
        mb.addObject(door);
        mb.addObject(walltest);
        map = new Map(mb);

        player.applyPotionEffect(engine, invincibility);
        long start = System.nanoTime();
        engine.updateMonstersPath();
        LinkedList<Point> path = monster.getPath();
        long time = System.nanoTime() - start;
        System.out.println(time);

        Point check = new Point(4,0);
        assertEquals(check, path.pop());

        check.move(5,0);
        assertEquals(check, path.pop());

        check.move(6,0);
        assertEquals(check, path.pop());

        check.move(7,0);
        assertEquals(check, path.pop());

        check.move(8, 0);
        assertEquals(check, path.pop());

        check.move(8,1);
        assertEquals(check, path.pop());

        check.move(8,2);
        assertEquals(check, path.pop());
    }

}