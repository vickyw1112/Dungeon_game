package GameEngine;

import GameEngine.utils.Point;

import java.util.LinkedList;
import java.util.List;

public class HoundPathGenerator implements PathGenerator {

    /**
     * Path generation method for hound monster
     * takes in parameters map, monster, player
     * returns a linked list of points
     * @param map
     * @param monster
     * @param player
     * @return
     */
    @Override
    public LinkedList<Point> generatePath(Map map, Monster monster, Player player) {
        Hound hound = (Hound) monster;
        List<Point> hunterPath = hound.getPair().getPath();
        if(hunterPath.size() != 0){
            // get the path to target without go through
            // the same route (last point before target) as it's paring Hunter
            List<Point> exception = new LinkedList<>();
            exception.add(hunterPath.get(hunterPath.size() - 2));
            LinkedList<Point> path = map.getShortestPathWithException(monster.location, player.location, exception);
            return path.size() > 0 ? path : map.getShortestPath(monster.location, player.location);
        } else {
            return new LinkedList<>();
        }
    }
}
