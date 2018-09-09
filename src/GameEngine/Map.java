package GameEngine;

import java.io.File;
import java.util.List;

public class Map {
    public static final int DUNGEON_SIZE_X = 10;
    public static final int DUNGEON_SIZE_Y = 10;

    public List<GameObject>[][] map;

    /**
     * Empty arg constructor
     */
    public Map(){
        this.map = new List[DUNGEON_SIZE_X][DUNGEON_SIZE_Y];
        // TODO: init all lists
    }

    /**
     * Constructor from a map builder
     *
     * @param mapBuilder
     */
    public Map(MapBuilder mapBuilder){

    }

    /**
     * Construct a Map from a saved file
     */
    public Map(File file){

    }

    /**
     * Save the map object into a file
     * including all objects' location
     */
    public void saveToFile(File file){

    }


}
