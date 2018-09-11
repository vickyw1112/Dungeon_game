package GameEngine;

import java.awt.*;

/**
 * Builder for map used in second mode of the game
 * where the player gradually construct a map
 */
public class MapBuilder {
    private GameObject[][] map;

    /**
     * Constructor for MapBuilder
     */
    public MapBuilder(){
        this.map = new GameObject[Map.DUNGEON_SIZE_X][Map.DUNGEON_SIZE_Y];
    }

    /**
     * Add a new object to the map
     *
     * @param obj
     * @param location
     */
    public void addObject(GameObject obj, Point location){
        obj.location = location;

    }

    /**
     * Delete the object in specific location
     *
     * @param location
     */
    public void deleteObj(Point location){

    }

    /**
     * Get the object in specific location
     *
     * @param location
     * @return the game object
     */
    public GameObject getObj(Point location){

        return null;
    }
}
