package Sample;

import GameEngine.*;
import GameEngine.WinningCondition.BouldersOnAllSwitches;
import GameEngine.WinningCondition.EliminateAllMonsters;
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
        mb.addWinningCondition(BouldersOnAllSwitches.class.getSimpleName());
        return mb.build();
    }

    public static Map getInvinciblePotionTestMap(){
        MapBuilder mb = initMap(5, 11);
        for(int x = 1; x <= 3; x++){
            for(int y = 1; y <= 6; y+=2){
                mb.addObject(new Hunter(new Point(x, y)));

            }
        }
        mb.addObject(new InvinciblePotion(new Point( 2, 8)));
        mb.addObject(new Player(new Point(1, 9)));
        mb.addWinningCondition(EliminateAllMonsters.class.getSimpleName());
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

    public static void generateMaps(){
        File dir = new File("map");
        dir.mkdirs();
        try {
            getBoulderTestMap().serialize(new FileOutputStream("map/boulderTest.dungeon"));
            getDoorTestMap().serialize(new FileOutputStream("map/doorTest.dungeon"));
            getInvinciblePotionTestMap().serialize(new FileOutputStream("map/invinciblePotionTest.dungeon"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        generateMaps();
    }
}
