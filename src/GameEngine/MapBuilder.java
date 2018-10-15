package GameEngine;

import GameEngine.utils.Point;

/**
 * Builder for map used in second mode of the game where the player gradually
 * construct a map
 */
public class MapBuilder {
    private final GameObject[][] map;

    /**
     * Constructor for MapBuilder
     */
    public MapBuilder() {
        this.map = new GameObject[Map.DUNGEON_SIZE_X][Map.DUNGEON_SIZE_Y];
    }

    /**
     * Add a new object to the map
     *
     * @param obj object to be added
     */
    public void addObject(GameObject obj) {
        map[obj.getLocation().getX()][obj.getLocation().getY()] = obj;
    }

    /**
     * Delete the object in specific location
     *
     * @param location location
     */
    public GameObject deleteObject(Point location) {
        GameObject ret = map[location.getX()][location.getY()];
        map[location.getX()][location.getY()] = null;
        return ret;
    }

    /**
     * Update a existing GameObject's location
     */
    public void updateObjectLocation(GameObject obj, Point newLocation){
        map[obj.getLocation().getX()][obj.getLocation().getY()] = null;
        map[newLocation.getX()][newLocation.getY()] = obj;
    }


    /**
     * Get the object in specific location
     *
     * @param location location
     * @return the game object
     */
    public GameObject getObject(Point location) {
        return map[location.getX()][location.getY()];
    }

    public GameObject[][] getMap() {
        return map;
    }
}
