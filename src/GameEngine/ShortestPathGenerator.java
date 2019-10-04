package GameEngine;

import GameEngine.utils.Point;
import java.util.LinkedList;

public class ShortestPathGenerator implements PathGenerator {
    /**
     * call SampleMaps's BFS to find the shortest path from monster to target
     * 
     * @param map map
     * @param monster monster
     * @param player target point
     * @return list of Point indicating the path
     */
    @Override
    public LinkedList<Point> generatePath(Map map, Monster monster, Player player) {
        return map.getShortestPath(monster.getLocation(), player.location);
    }

}
