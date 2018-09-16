package GameEngine;

import GameEngine.utils.Point;

import java.util.LinkedList;
import java.util.List;

public class HoundPathGenerator implements PathGenerator {

    /**
     * Path generation method for hound monster
     * @param map
     * @param monster
     * @param target
     * @return
     */
    @Override
    public LinkedList<Point> generatePath(Map map, Monster monster, Point target) {
        Hound hound = (Hound) monster;
        List<Point> hunterPath = hound.getPair().getPath();
        if(hunterPath.size() != 0){
            // get the path to target without go through
            // the same route (last point before target) as it's paring Hunter
            List<Point> exception = new LinkedList<>();
            exception.add(hunterPath.get(hunterPath.size() - 2));
            LinkedList<Point> path = map.getShortestPathWithException(monster.location, target, hunterPath);
            return path.size() > 0 ? path : map.getShortestPath(monster.location, target);
        } else {
            return new LinkedList<>();
        }
    }
}
