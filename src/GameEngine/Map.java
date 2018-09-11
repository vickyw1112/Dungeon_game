package GameEngine;

import javafx.geometry.Pos;

import java.awt.*;
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
     * @param m
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

    /**
     * Get list of object in specific grid
     */
    public List<GameObject> getObjects(Point location){
        return map[location.getX()][location.getY()];
    }

    /**
     * Remove a specific object from the map
     */
    public void removeObject(GameObject obj){
        map[obj.location.getX()][obj.location.getY()].remove(obj);
    }

    /**
     * Update an object's location
     *
     * @param obj game object
     * @param location new location
     */
    public void updateObjectLocation(GameObject obj, Point location){
        removeObject(obj);
        map[location.getX()][location.getY()].add(obj);
        obj.setLocation(location);
    }

}
