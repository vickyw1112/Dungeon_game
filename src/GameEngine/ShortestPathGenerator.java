package GameEngine;

import GameEngine.utils.Point;
import java.util.LinkedList;

public class ShortestPathGenerator implements PathGenerator {
    /**
     * BFS to find the shortest path from monster to target
     * 
     * @param map
     * @param monster
     * @param target
     * @return list of Point indicating the path
     */
    @Override
    public LinkedList<Point> generatePath(Map map, Monster monster, Point target) {
        return map.getShortestPath(monster.getLocation(), target);
    }

}
