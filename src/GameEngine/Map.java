package GameEngine;

import GameEngine.utils.Point;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Map implements Serializable {
    // TODO: we can probs do multi-sized dungeon by changing these const to variable
    public static final int DUNGEON_SIZE_X = 10;
    public static final int DUNGEON_SIZE_Y = 10;

    public List<GameObject>[][] map;

    /**
     * Empty arg constructor
     */
    public Map() {
        this.map = new List[DUNGEON_SIZE_X][DUNGEON_SIZE_Y];
        for (int i = 0; i < DUNGEON_SIZE_X; i++)
            for (int j = 0; j < DUNGEON_SIZE_Y; j++)
                map[i][j] = new LinkedList<>();
    }

    /**
     * Constructor from a map builder
     *
     * @param mapBuilder
     */
    // TODO: how are we going to construct a map in second mode?
    // how to enumerate all subclasses of GameObject
    public Map(MapBuilder mapBuilder) {
        this();
        GameObject[][] builderMap = mapBuilder.getMap();
        for (int i = 0; i < DUNGEON_SIZE_X; i++)
            for (int j = 0; j < DUNGEON_SIZE_Y; j++)
                if (builderMap[i][j] != null)
                    map[i][j].add(builderMap[i][j]);
    }

    /**
     * Construct a Map from a saved file
     *
     * @param
     */
    public Map(InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(inputStream);
        this.map = (List<GameObject>[][]) in.readObject();
    }

    /**
     * Get list of object in specific grid
     */
    public List<GameObject> getObjects(Point location) {
        return map[location.getX()][location.getY()];
    }

    /**
     * Remove a specific object from the map
     */
    public void removeObject(GameObject obj) {
        map[obj.getLocation().getX()][obj.getLocation().getY()].remove(obj);
    }

    /**
     * Update an object's location
     *
     * @param obj
     *            game object
     * @param location
     *            new location
     */
    public void updateObjectLocation(GameObject obj, Point location) {
        removeObject(obj);
        map[location.getX()][location.getY()].add(obj);
        obj.setLocation(location);
    }

    /**
     * Interface for monster to find path Return a list of point adjacent to given
     * point that does not contains an object that blocks monsters' movement
     *
     * @param point
     *            current point
     * @return adjacent movable points
     */
    public List<Point> getNonBlockAdjacentPoints(Point point) {
        List<Point> ret = new LinkedList<>();
        // enumerate all adjacent point
        Point[] points = new Point[4];
        points[0] = point.clone().translate(1, 0);
        points[1] = point.clone().translate(-1, 0);
        points[2] = point.clone().translate(0, 1);
        points[3] = point.clone().translate(0, -1);
        for (Point curr : points) {
            if (!isValidPoint(curr))
                continue;

            // check if there's blocking obj in that point
            if (getObjects(curr).stream().filter(o -> (o.isBlocking()))
                    .collect(Collectors.toList()).size() == 0)
                ret.add(curr);
        }
        return ret;
    }

    public void serialize(OutputStream outputStream) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(this.map);
    }

    public static boolean isValidPoint(Point p) {
        return p.getX() >= 0 && p.getX() < DUNGEON_SIZE_X && p.getY() >= 0 && p.getY() < DUNGEON_SIZE_Y;
    }

    public List<GameObject> getAllObjects() {
        List<GameObject> ret = new LinkedList<>();
        for (int i = 0; i < DUNGEON_SIZE_X; i++)
            for (int j = 0; j < DUNGEON_SIZE_Y; j++)
                ret.addAll(map[i][j]);
        return ret;
    }


    /**
     * Returns the shortest path between two points
     */
    public LinkedList<Point> getShortestPath(Point from, Point to) {
        List<Point> visited = new LinkedList<>();
        LinkedList<Point> toBeVisited = new LinkedList<>();
        HashMap<Point, Point> path = new HashMap<>();
        toBeVisited.add(from);
        while (toBeVisited.size() > 0) {
            Point curr = toBeVisited.pop();
            if (visited.contains(curr))
                continue;
            if (curr.equals(to))
                break;
            visited.add(curr);

            for (Point next : this.getNonBlockAdjacentPoints(curr)) {
                if (visited.contains(next))
                    continue;
                path.put(next, curr); // next is from curr
                toBeVisited.add(next);
            }
        }

        LinkedList<Point> ret = new LinkedList<>();

        // if there is a path to target,
        // traverse back and add the points in reverse order
        if (path.containsKey(to)) {
            for (Point curr = to; curr != from; curr = path.get(curr)) {
                ret.add(curr);
            }
        }
        // reverse it to correct order
        Collections.reverse(ret);
        return ret;
    }

    /**
     * a* implementation of fleeing
     * @param from
     * @param to
     * @return
     */
    public LinkedList<Point> getRunAwayPath(Point from, Point to) {

        LinkedList<Point> ret = new LinkedList<>();
        PriorityQueue<Point> open = new PriorityQueue<>();
        LinkedList<Point> closed = new LinkedList<>();
        return null;
    }

}
