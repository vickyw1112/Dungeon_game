package GameEngine;

import GameEngine.utils.Point;

import java.util.*;

public class RunAwayPathGenerator implements PathGenerator {

    /**
     * runAwayPathGenerator method
     * path generator method when monsters run away from the player due to effects or inherent behaviours of monsters
     * takes in map, monster, player parameters
     * returns a linked list of points
     * @param map
     * @param monster
     * @param player
     * @return
     */
    @Override
    public LinkedList<Point> generatePath(Map map, Monster monster, Player player) {
        List<Point> points = map.getNonBlockAdjacentPoints(monster.location);
        points.add(monster.location);

        Point next = points.stream()
                .max(Comparator.comparingInt(
                        point -> map.getDistance(point, player.location))).get();

        LinkedList<Point> ret = new LinkedList<>();
        if(!next.equals(monster.location))
            ret.add(next);
        return ret;
    }
}
