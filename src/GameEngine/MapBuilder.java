package GameEngine;

import GameEngine.utils.Point;

import static GameEngine.Map.DUNGEON_SIZE_X;
import static GameEngine.Map.DUNGEON_SIZE_Y;

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
        this.map = new GameObject[DUNGEON_SIZE_X][DUNGEON_SIZE_Y];
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
        Point prevLocation = obj.getLocation();
        if(prevLocation != null && map[prevLocation.getX()][prevLocation.getY()] == obj)
            map[prevLocation.getX()][prevLocation.getY()] = null;

        map[newLocation.getX()][newLocation.getY()] = obj;
        obj.setLocation(newLocation);
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

    /**
     * Get a point in the map that is still empty
     * @return
     */
    public Point getEmptyPoint(){
        for (int i = 0; i < DUNGEON_SIZE_X; i++)
            for (int j = 0; j < DUNGEON_SIZE_Y; j++)
                if(map[i][j] == null)
                    return new Point(i, j);

        return null;
    }
}
