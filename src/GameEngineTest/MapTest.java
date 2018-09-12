package GameEngineTest;

import GameEngine.*;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.List;

import static org.junit.Assert.*;


public class MapTest {
    private MapBuilder mapBuilder;
    private Map map;
    private Point p1 = new Point(1,1);
    private Point p2 = new Point(2,1);
    private Point p3 = new Point(3,1);
    private Point p4 = new Point(4,1);
    private Point p5 = new Point(5,1);
    private Point p6 = new Point(6,1);
    private Point p7 = new Point(7,1);

    private MapBuilder generateMapBuilder(){
        MapBuilder mapBuilder = new MapBuilder();
        Player player = new Player(p1);
        Wall wall1 = new Wall(p2);
        Wall wall2 = new Wall(p3);
        Wall wall3 = new Wall(p4);
        Wall wall4 = new Wall(p5);
        Monster monster = new Hunter(p6);
        Treasure treasure = new Treasure(p7);

        mapBuilder.addObject(player);
        mapBuilder.addObject(wall1);
        mapBuilder.addObject(wall2);
        mapBuilder.addObject(wall3);
        mapBuilder.addObject(wall4);
        mapBuilder.addObject(monster);
        mapBuilder.addObject(treasure);
        return mapBuilder;
    }

    private Map generateMap(){
        MapBuilder mapBuilder = generateMapBuilder();
        return new Map(mapBuilder);
    }

    @Before
    public void setup(){
        mapBuilder = new MapBuilder();
        map = new Map();
    }

    @Test
    public void mapBuilderTest(){
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        Player player = new Player(p1);
        Wall wall = new Wall(p2);

        // test add & delete
        mapBuilder.addObject(player);
        assertEquals(mapBuilder.getObject(p1), player);
        mapBuilder.deleteObject(p1);
        assertNull(mapBuilder.getObject(p1));

        mapBuilder.addObject(player);
        mapBuilder.addObject(wall);

        map = new Map(mapBuilder);
        assertTrue(map.getObjects(p1).contains(player));
        assertTrue(map.getObjects(p2).contains(wall));
    }

    @Test
    public void mapUpdateLocationTest(){
        map = generateMap();
        // get the treasure
        GameObject treasure = map.getObjects(p7).get(0);
        assertEquals(map.getObjects(p6).size(), 1);
        map.updateObjectLocation(treasure, p6);
        assertEquals(map.getObjects(p6).size(), 2);
        assertEquals(map.getObjects(p7).size(), 0);
        assertTrue(map.getObjects(p6).contains(treasure));
        assertEquals(treasure.getLocation(), p6);
    }

    @Test
    public void mapRemoveTest(){
        map = generateMap();
        GameObject player = map.getObjects(p1).get(0);
        assertEquals(map.getObjects(p1).size(), 1);
        map.removeObject(player);
        assertEquals(map.getObjects(p1).size(), 0);

        GameObject treasure = map.getObjects(p7).get(0);
        map.updateObjectLocation(treasure, p6);
        assertEquals(map.getObjects(p6).size(), 2);
        map.removeObject(treasure);
        assertEquals(map.getObjects(p6).size(), 1);
        assertFalse(map.getObjects(p6).contains(treasure));
    }

    @Test
    public void mapGetAdjacentPointsTest(){
        map = generateMap();
        List<Point> points = map.getNonBlockAdjacentPoints(p1);

        // blocking object check - wall
        assertEquals(points.size(), 3);
        assertFalse(points.contains(p2)); // p2 contains a Wall
        assertTrue(points.contains(new Point(0, 1)));
        assertTrue(points.contains(new Point(1, 0)));
        assertTrue(points.contains(new Point(1, 2)));

        // boundary check
        points = map.getNonBlockAdjacentPoints(new Point(0, 0));
        assertEquals(points.size(), 2);
        assertTrue(points.contains(new Point(0, 1)));
        assertTrue(points.contains(new Point(1, 0)));

        // blocking object check - boulder
        mapBuilder = generateMapBuilder();
        mapBuilder.addObject(new Boulder(new Point(1, 0)));
        map = new Map(mapBuilder);
        points = map.getNonBlockAdjacentPoints(new Point(2, 0));
        assertEquals(points.size(), 1);
        assertTrue(points.contains(new Point(3, 0)));


        // blocking object check - closed door
        Door door = new Door(new Point(3, 0));
        mapBuilder.addObject(door);
        map = new Map(mapBuilder);
        points = map.getNonBlockAdjacentPoints(new Point(2, 0));
        assertEquals(points.size(), 0);

        // TODO: think of better way to handle blockable classes ?
        // after open door
        door.changeState(Door.OPEN);
        assertTrue(points.contains(new Point(3, 0)));
    }

    @Test
    public void mapSerializationTest() throws IOException, ClassNotFoundException {
        mapBuilder = generateMapBuilder();
        Point keyLocation = new Point(2, 0);
        Point doorLocatin = new Point(3, 0);

        // add door - key reference
        Door door = new Door(doorLocatin);
        mapBuilder.addObject(door);
        Key key = new Key(keyLocation);
        mapBuilder.addObject(key);
        door.setPair(key);

        // create map and put serialization result into a pipe
        map = new Map(mapBuilder);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        // create another map from other end of the pipe
        map.serialize(output);
        byte[] buffer = output.toByteArray();

        ByteArrayInputStream input  = new ByteInputStream(buffer, buffer.length);
        map = new Map(input);
        // check new map is the same
        key = (Key) map.getObjects(keyLocation).get(0);
        assertTrue(key instanceof Key);
        door = (Door) map.getObjects(doorLocatin).get(0);
        assertTrue(door instanceof Door);

        assertEquals(door.getKey(), key);
    }


}
