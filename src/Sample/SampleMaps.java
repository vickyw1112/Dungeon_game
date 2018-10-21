package Sample;

import GameEngine.*;
import GameEngine.utils.*;

import java.io.File;
import java.io.FileOutputStream;

public class SampleMaps {
    /**
     * Construct an empty map builder with given size
     * and only a layer of wall
     */
    private static MapBuilder initMap(int sizeX, int sizeY){
        MapBuilder mb = new MapBuilder(sizeX, sizeY);
        mb.setAuthor("Chester");
        for(int x = 0; x < sizeX; x++){
            mb.addObject(new Wall(new Point(x, 0)));
            mb.addObject(new Wall(new Point(x, sizeY - 1)));
        }
        for(int y = 0; y < sizeY; y++){
            mb.addObject(new Wall(new Point(0, y)));
            mb.addObject(new Wall(new Point(sizeX - 1, y)));
        }
        return mb;
    }


    public static Map getBoulderTestMap(){
        MapBuilder mb = initMap(5, 6);
        for(int x = 1; x <= 3; x++){
            mb.addObject(new FloorSwitch(new Point(x, 1)));
            mb.addObject(new Boulder(new Point(x, 3)));
        }
        mb.addObject(new Player(new Point(1, 4)));
        return mb.build();
    }


    public static Map getInvinciblePotionTestMap(){
        MapBuilder mb = initMap(5, 11);
        for(int x = 1; x <= 3; x++){
            for(int y = 1; y <= 6; y++){
                mb.addObject(new Hunter(new Point(x, y)));

            }
        }
        mb.addObject(new InvinciblePotion(new Point( 2, 8)));
        mb.addObject(new Player(new Point(1, 9)));
        return mb.build();
    }

    public static Map getDoorTestMap(){
        MapBuilder mb = initMap(5, 5);
        mb.addObject(new Exit(new Point(3, 1)));
        mb.addObject(new Wall(new Point(1, 2)));
        mb.addObject(new Wall(new Point(2, 2)));
        Door door = new Door(new Point(3, 2));
        mb.addObject(door);
        Key key = new Key(new Point(2, 3));
        door.setPair(key);
        mb.addObject(key);
        mb.addObject(new Player(new Point(1, 3)));
        return mb.build();
    }

    public static Map winningConditionTest(){
        MapBuilder mb = initMap(10, 10);

        // add surounding walls
        for (int i=0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                mb.addObject(new Wall(new Point(i, j)));
            }
        }
        // add player
        mb.addObject(new Player(new Point(1,1)));

        // line of walls
        for (int x = 0; x < 7; x++) {
            mb.addObject(new Wall(new Point(x, 8 )));
        }

        // add door and key
        Key key = new Key(new Point(9, 9));
        Door door = new Door(new Point(7,9));
        door.setKey(key);
        mb.addObject(key);
        mb.addObject(door);


        // cage of monsters
        mb.addObject(new Hunter(new Point(0,9)));
        mb.addObject(new Hound(new Point(1,9)));
        mb.addObject(new Strategist(new Point(2,9)));
        mb.addObject(new Coward(new Point(3,9)));
        mb.addObject(new Hunter(new Point(4,9)));
        mb.addObject(new Hound(new Point(5,9)));
        mb.addObject(new Strategist(new Point(6,9)));

        for (int x = 5; x < 7; x++) {
            mb.addObject(new FloorSwitch(new Point(x, 0 )));
        }
        for (int x = 5; x < 7; x++) {
            mb.addObject(new Boulder(new Point(x, 1 )));
        }
        for (int x = 0; x < 5; x++) {
            mb.addObject(new Boulder(new Point(x, 7 )));
        }
        mb.addObject(new Exit(new Point (0, 4)));
        return mb.build();

    }


    public static Map funMap(){
        MapBuilder mb = initMap(18, 18);

        // add player
        mb.addObject(new Player(new Point(1,1)));

        // add all the walls first
        horizontalWalls(1, 15, 5, mb);
        horizontalWalls(1, 7, 12, mb );
        horizontalWalls(11, 12, 12, mb);
        verticalWalls(2, 4, 7, mb);
        verticalWalls(6, 9, 5, mb);
        verticalWalls(6, 11, 12, mb);
        verticalWalls(13, 15, 12, mb);

        // add all weapons
        mb.addObject(new Arrow(new Point(1, 4)));
        mb.addObject(new Arrow(new Point(2, 4)));
        mb.addObject(new Arrow(new Point(3, 4)));
        mb.addObject(new Bomb(new Point (16,2 )));


        // add all monsters
        mb.addObject(new Strategist(new Point(12, 2)));
        mb.addObject(new Strategist(new Point(12, 4)));
        mb.addObject(new Hunter(new Point(15, 12)));
        mb.addObject(new Hunter(new Point(16, 12)));
        mb.addObject(new Hunter(new Point(15, 14)));
        mb.addObject(new Hunter(new Point(16, 14)));

        mb.addObject(new Coward(new Point(9, 9)));
        Hound hound = new Hound(new Point (5, 15));
        Hunter hunter = new Hunter(new Point (3, 15));
        hound.setPair(hunter);
        mb.addObject(hound);
        mb.addObject(hunter);

        // add potions
        mb.addObject(new HoverPotion(new Point(1,14)));
        mb.addObject(new InvinciblePotion(new Point (16, 9)));

        // add doors and key pairs
        Door door1 = new Door(new Point(7, 1));
        Key key1 = new Key(new Point(6, 4));
        door1.setKey(key1);
        mb.addObject(door1);
        mb.addObject(key1);

        Door door2 = new Door(new Point(16, 5));
        Key key2 = new Key(new Point(16, 1));
        mb.addObject(door2);
        mb.addObject(key2);
        door2.setKey(key2);

        Door door3 = new Door(new Point(12, 16));
        Key key3 = new Key(new Point(16, 16));
        door3.setKey(key3);
        mb.addObject(door3);
        mb.addObject(key3);

        // add pits
        mb.addObject(new Pit(new Point(8,13)));
        mb.addObject(new Pit(new Point(9,13)));
        mb.addObject(new Pit(new Point(10,13)));

        // add boulders
        mb.addObject(new Boulder(new Point(5, 12)));
        mb.addObject(new Boulder(new Point(5, 13)));

        return mb.build();

    }

    // creates a horizontal line of walls
    public static void horizontalWalls(int from, int to, int height,  MapBuilder mb) {
        for (int x = from; x <= to; x++) {
            mb.addObject(new Wall(new Point(x,height)));
        }
    }
    // creates a horizontal line of walls
    public static void verticalWalls(int from, int to, int width,  MapBuilder mb) {
        for (int y = from; y <= to; y++) {
            mb.addObject(new Wall(new Point(width,y)));
        }
    }


    public static void generateMaps(){
        File dir = new File("map");
        dir.mkdirs();
        try {
//            getBoulderTestMap().serialize(new FileOutputStream("map/boulderTest.dungeon"));
//            getDoorTestMap().serialize(new FileOutputStream("map/doorTest.dungeon"));
//            getInvinciblePotionTestMap().serialize(new FileOutputStream("map/invinciblePotionTest.dungeon"));
            funMap().serialize(new FileOutputStream("map/funMap.dungeon"));
            winningConditionTest().serialize(new FileOutputStream("map/winningCondition.dungeon"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        generateMaps();
    }
}
