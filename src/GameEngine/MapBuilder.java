package GameEngine;

import GameEngine.utils.Point;
import com.sun.istack.internal.FinalArrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

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
    public void deleteObject(Point location) {
        // simply delete the reference
        map[location.getX()][location.getY()] = null;
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
        ArrayList<Player> player = new ArrayList<>();
        List<Exit> exits = new ArrayList<>();
        List<Monster> monsters = new ArrayList<>();
        List<Treasure> treasures = new ArrayList<>();
        int x = 0;
        for (; x < this.map.length; x++) {
            for (int y = 0; y < this.map[x].length; y++) {
                if (map[x][y] instanceof Player) {
                    player.add((Player) map[x][y]);
                } else if (map[x][y] instanceof Monster) {
                    monsters.add((Monster) map[x][y]);
                } else if (map[x][y] instanceof Treasure) {
                    treasures.add((Treasure) map[x][y]);
                } else if (map[x][y] instanceof Exit) {
                    exits.add((Exit) map[x][y]);
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
}
