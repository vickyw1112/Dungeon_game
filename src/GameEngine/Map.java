package GameEngine;

import GameEngine.utils.Point;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Map implements Serializable {
    // TODO: we can probably do multi-sized dungeon by changing these const to variable for extension
    public static final int DUNGEON_SIZE_X = 10;
    public static final int DUNGEON_SIZE_Y = 10;

    private List<GameObject>[][] map;

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
     * @param mapBuilder map builder
     */
    public Map(MapBuilder mapBuilder) {
        this();
        GameObject[][] builderMap = mapBuilder.getMap();
        for (int i = 0; i < DUNGEON_SIZE_X; i++)
            for (int j = 0; j < DUNGEON_SIZE_Y; j++)
                if (builderMap[i][j] != null)
                    map[i][j].add(builderMap[i][j]);
    }

    /**
     * Construct a SampleMaps from a saved file
     *
     * @param inputStream map input stream
     */
    public Map(InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(inputStream);
        this.map = (List<GameObject>[][]) in.readObject();
    }

    /**
     * Get list of object in specific grid
     */
    List<GameObject> getObjects(Point location) {
        return map[location.getX()][location.getY()];
    }

    /**
     * Remove a specific object from the map
     */
    public void removeObject(GameObject obj) {
        if(obj instanceof Monster)
            GameEngine.MONSTERKILLED++; // add static to count monster to be killed
        for(int i = 0; i < DUNGEON_SIZE_X; i++)
            for(int j = 0; j < DUNGEON_SIZE_Y; j++)
                map[i][j].remove(obj);
    }

    /**
     * Update an object's location
     *
     * @param obj
     *            game object
     * @param location
     *            new location
     */
    void updateObjectLocation(GameObject obj, Point location) {
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
    List<Point> getNonBlockAdjacentPoints(Point point) {
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
            if (canMoveThrough(curr))
                ret.add(curr);
        }
        return ret;
    }

    public void serialize(OutputStream outputStream) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(this.map);
    }

    boolean isValidPoint(Point p) {
        return p.getX() >= 0 && p.getX() < DUNGEON_SIZE_X && p.getY() >= 0 && p.getY() < DUNGEON_SIZE_Y;
    }

    List<GameObject> getAllObjects() {
        List<GameObject> ret = new LinkedList<>();
        for (int i = 0; i < DUNGEON_SIZE_X; i++)
            for (int j = 0; j < DUNGEON_SIZE_Y; j++)
                ret.addAll(map[i][j]);
        return ret;
    }


    /**
     * Returns the shortest path between two points without go through points in exception
     * @param from from
     * @param to to
     * @param exception points won't be gone through, if it's null then no exception
     * @return list of points indicating the path, including to but not from
     */
    LinkedList<Point> getShortestPathWithException(Point from, Point to, List<Point> exception) {
        List<Point> visited = new LinkedList<>();
        LinkedList<Point> toBeVisited = new LinkedList<>();
        HashMap<Point, Point> path = new HashMap<>();
        toBeVisited.add(from);
        while (toBeVisited.size() > 0) {
            Point curr = toBeVisited.pop();
            if (visited.contains(curr)) {
                continue;
            }
            if (curr.equals(to)) {
                break;
            }
            visited.add(curr);

            List<Point> nextCandidate = this.getNonBlockAdjacentPoints(curr);
            if(exception != null)
                nextCandidate.removeAll(exception);
            for (Point next : nextCandidate) {
                if (visited.contains(next)) {
                    continue;
                }
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
     * Wrapper for {@link Map#getShortestPathWithException(Point, Point, List)}
     * @param from from
     * @param to to
     * @return list of points indicating the path, including to but not from
     */
    LinkedList<Point> getShortestPath(Point from, Point to) {
        return getShortestPathWithException(from, to, null);
    }

    /**
     * Get shortest distance between two given points
     * considering not passing all blocking objects
     * @param from from point
     * @param to to point
     * @return distance in number of grid
     */
    int getDistance(Point from, Point to) {
        return getShortestPath(from, to).size();
    }

    /**
     * Test if given point is considered a candidate point during
     * path generation
     *
     * @see GameObject#canMoveThrough()
     * @return boolean value
     */
    boolean canMoveThrough(Point point) {
        return getObjects(point).stream().filter(o -> (!o.canMoveThrough()))
                .collect(Collectors.toList()).size() == 0 && isValidPoint(point);
    }
}
