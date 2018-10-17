package GameEngine;

import GameEngine.utils.Point;
import com.sun.istack.internal.FinalArrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static GameEngine.Map.DEFAULT_DUNGEON_SIZE_X;
import static GameEngine.Map.DEFAULT_DUNGEON_SIZE_Y;

/**
 * Builder for map used in second mode of the game where the player gradually
 * construct a map
 */
public class MapBuilder {
    private int sizeX;
    private int sizeY;
    private final GameObject[][] map;
    private String author = "Unknown Author";

    /**
     * Constructor for MapBuilder
     */
    public MapBuilder() {
        this.sizeX = DEFAULT_DUNGEON_SIZE_X;
        this.sizeY = DEFAULT_DUNGEON_SIZE_Y;
        this.map = new GameObject[DEFAULT_DUNGEON_SIZE_X][DEFAULT_DUNGEON_SIZE_Y];
    }

    public MapBuilder(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.map = new GameObject[sizeX][sizeY];
    }

    public Map build() {
        return new Map(this, sizeX, sizeY, author);
    }

    public void setAuthor(String author){
        this.author = author;
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

    public boolean islegalMap() {
        List<Object> player = new ArrayList<>();
        List<Object> exits = new ArrayList<>();
        List<Object> monsters = new ArrayList<>();
        List<Object> treasures = new ArrayList<>();
        int x = 0;
        for (; x < this.map.length; x++) {
            for (int y = 0; y < this.map[x].length; y++) {
                if (map[x][y] instanceof Player) {
                    player.add(map[x][y]);
                } else if (map[x][y] instanceof Monster) {
                    monsters.add(map[x][y]);
                } else if (map[x][y] instanceof Treasure) {
                    treasures.add(map[x][y]);
                } else if (map[x][y] instanceof Exit) {
                    exits.add(map[x][y]);
                }
            }
        }

        if (player.size() != 1) return false;
        if (!(monsters.size() > 0 || exits.size() > 0 || treasures.size() > 0)) return false;
        return true;
    }

    public GameObject[][] getMap() {
        return map;
    }

    /**
     * Get a point in the map that is still empty
     * @return
     */
    public Point getEmptyPoint(){
        for (int j = 0; j < sizeY; j++)
            for (int i = 0; i < sizeX; i++)
                if(map[i][j] == null)
                    return new Point(i, j);

        return null;
    }

    /**
     * Check if the map contains a given GameObject
     */
    public boolean contains(GameObject obj){
        for (int i = 0; i < sizeX; i++)
            for (int j = 0; j < sizeY; j++)
                if(map[i][j] == obj)
                    return true;
        return false;
    }

}
