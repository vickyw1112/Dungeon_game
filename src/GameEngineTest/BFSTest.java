package GameEngineTest;

import static org.junit.Assert.*;

import org.junit.Before;

import GameEngine.*;

import org.junit.Test;

public class BFSTest {
    
    private MapBuilder generateMapBuilder() {
        MapBuilder mapBuilder = new MapBuilder();
        Player player = new Player(start);
        Wall wall0 = new Wall(p0);
        Wall wall1 = new Wall(p1);
        Wall wall2 = new Wall(p2);
        Wall wall3 = new Wall(p3);
        Wall wall4 = new Wall(p4);
//        Monster monster = new Hunter(p6);
//        Treasure treasure = new Treasure(p7);

        mapBuilder.addObject(player);
        mapBuilder.addObject(wall1);
        mapBuilder.addObject(wall2);
        mapBuilder.addObject(wall3);
        mapBuilder.addObject(wall4);
//        mapBuilder.addObject(monster);
//        mapBuilder.addObject(treasure);
    }

    @Before
    public void setup(){
        mapBuilder = new MapBuilder();
        map = new Map();
    }
    
        
    /**
     * Check BFS path generation
     * Will have to generate a map with walls first
     * @throws Exception
     */
    @Test
    public void BFSTest() throws Exception {
        private MapBuilder generateMapBuilder() {
            MapBuilder mapBuilder = new MapBuilder();
            Player player = new Player(start);
            Wall wall0 = new Wall(p0);
            Wall wall1 = new Wall(p1);
            Wall wall2 = new Wall(p2);
            Wall wall3 = new Wall(p3);
            Wall wall4 = new Wall(p4);
    //        Monster monster = new Hunter(p6);
    //        Treasure treasure = new Treasure(p7);
    
            mapBuilder.addObject(player);
            mapBuilder.addObject(wall1);
            mapBuilder.addObject(wall2);
            mapBuilder.addObject(wall3);
            mapBuilder.addObject(wall4);
    //        mapBuilder.addObject(monster);
    //        mapBuilder.addObject(treasure);
        }
        
        MapBuilder mapBuilder = generateMapBuilder();
        Map map = new Map(mapBuilder);
        
    }

}
