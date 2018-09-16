package GameEngine;

import GameEngine.utils.Point;

import java.util.*;

public class RunAwayPathGenerator implements PathGenerator {

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
