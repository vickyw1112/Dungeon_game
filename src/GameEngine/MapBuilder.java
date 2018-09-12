package GameEngine;

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
    public void addObject(GameObject obj){
        map[obj.getLocation().getX()][obj.getLocation().getY()] = obj;
    }

    /**
     * Delete the object in specific location
     *
     * @param location
     */
    public void deleteObject(Point location){
        // simply delete the reference
        map[location.getX()][location.getY()] = null;
    }

    /**
     * Get the object in specific location
     *
     * @param location
     * @return the game object
     */
    public GameObject getObject(Point location){
        return map[location.getX()][location.getY()];
    }

    public GameObject[][] getMap(){
        return map;
    }
}
