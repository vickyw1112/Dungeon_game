package GameEngine;

import GameEngine.utils.Point;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
        List<Point> visited = new LinkedList<>();
        LinkedList<Point> toBeVisited = new LinkedList<>();
        HashMap<Point, Point> path = new HashMap<>();
        toBeVisited.add(monster.location);
        while (toBeVisited.size() > 0) {
            Point curr = toBeVisited.pop();
            if (visited.contains(curr))
                continue;
            if (curr.equals(target))
                break;
            visited.add(curr);

            for (Point next : map.getNonBlockAdjacentPoints(curr)) {
                if (visited.contains(next))
                    continue;
                path.put(next, curr); // next is from curr
                toBeVisited.add(next);
            }
        }

        LinkedList<Point> ret = new LinkedList<>();

        // if there is a path to target,
        // traverse back and add the points in reverse order
        if (path.containsKey(target)) {
            for (Point curr = target; curr != monster.location; curr = path.get(curr)) {
                ret.add(curr);
            }
        }
        // reverse it to correct order
        Collections.reverse(ret);
        return ret;
    }
}
