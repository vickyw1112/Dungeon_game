package GameEngine;

import GameEngine.WinningCondition.WinningCondition;
import GameEngine.utils.Point;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Map implements Serializable {
    public static final int DEFAULT_DUNGEON_SIZE_X = 11;
    public static final int DEFAULT_DUNGEON_SIZE_Y = 11;
    public static final int MAX_HIGHSCORE_SIZE = 10;

    private List<GameObject>[][] map;
    private int sizeX = DEFAULT_DUNGEON_SIZE_X;
    private int sizeY = DEFAULT_DUNGEON_SIZE_Y;
    private String author;
    private List<ScoreData> highScoreList;
    private String mapName;

    /**
     * Not map.getMapName(serialise winning condition code
     * but serialise the class name and load it later
     */
    private transient List<WinningCondition> winningConditions;
    private List<String> winningConditionClasses;

    /**
     * Empty arg constructor for default sized map
     */
    public Map() {
        init();
    }

    /**
     * Constructor from a map builder
     *
     * @param mapBuilder map builder
     */
    public Map(MapBuilder mapBuilder) {
        init();
        build(mapBuilder);
    }

    /**
     * Build given sized map by map builder
     */
    public Map(MapBuilder mapBuilder, int sizeX, int sizeY, String author,
               List<String> winningConditions, String mapName){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        init();
        this.author = author;
        this.highScoreList = new ArrayList<ScoreData>();
        this.winningConditionClasses = winningConditions;
        this.mapName = mapName;
        loadWinningConditions(this);
        build(mapBuilder);
    }

    /**
     * Load all winning conditions
     */
    private static void loadWinningConditions(Map map){
        map.winningConditions = map.winningConditionClasses.stream().map(cls -> {
            try {
                return (WinningCondition) Class.forName(
                        WinningCondition.class.getPackage().getName() + "." + cls
                ).getConstructor().newInstance();
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Initiate the 2D array of list
     */
    private void init(){
        this.winningConditions = new LinkedList<>();
        this.winningConditionClasses = new LinkedList<>();
        this.map = new List[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++)
            for (int j = 0; j < sizeY; j++)
                map[i][j] = new LinkedList<>();
    }

    /**
     * Copy references from map builder to this map
     */
    private void build(MapBuilder mapBuilder){
        GameObject[][] builderMap = mapBuilder.getMap();
        for (int i = 0; i < sizeX; i++)
            for (int j = 0; j < sizeY; j++) {
                if (builderMap[i][j] != null)
                    map[i][j].add(builderMap[i][j]);
                // delete reference to pair if it's not in map anymore
                if (builderMap[i][j] instanceof Pairable){
                    Pairable p = (Pairable) builderMap[i][j];
                    if(!mapBuilder.contains(p.getPair()))
                        p.setPair(null);
                }
            }
    }

    /**
     * Load a map from a saved file
     *
     * @param inputStream map input stream
     */
    public static Map loadFromFile(InputStream inputStream){
        Map map;
        try {
            ObjectInputStream in = new ObjectInputStream(inputStream);
            map = (Map) in.readObject();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        // loop to find max objId
        int maxId = 0;
        for (int i = 0; i < map.sizeX; i++)
            for (int j = 0; j < map.sizeY; j++)
                for(GameObject obj : map.map[i][j])
                    maxId = Math.max(maxId, obj.getObjID());
        StandardObject.setMaxObjId(maxId);
        loadWinningConditions(map);
        return map;
    }

    public String getMapName() {
        return mapName;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public String getAuthor() {
        return author;
    }

    public List<WinningCondition> getWinningConditions() {
        return Collections.unmodifiableList(winningConditions);
    }

    public List<String> getWinningConditionClasses(){
        return Collections.unmodifiableList(winningConditionClasses);
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
        for(int i = 0; i < sizeX; i++)
            for(int j = 0; j < sizeY; j++)
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

    public void serialize() throws IOException {
        FileOutputStream outputStream = new FileOutputStream("map" + File.separator + mapName + ".dungeon");
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(this);
    }

    public void serialize(OutputStream outputStream) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(this);
    }

    boolean isValidPoint(Point p) {
        return p.getX() >= 0 && p.getX() < sizeX && p.getY() >= 0 && p.getY() < sizeY;
    }

    List<GameObject> getAllObjects() {
        List<GameObject> ret = new LinkedList<>();
        for (int i = 0; i < sizeX; i++)
            for (int j = 0; j < sizeY; j++)
                ret.addAll(map[i][j]);
        return ret;
    }

    /**
     * Check the game state in engine is winning
     * @param engine game engine
     */
    public boolean isWinning(GameEngine engine){
        return winningConditions.size() > 0 &&
                winningConditions.stream().allMatch(condition -> condition.check(engine, this));
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

    /**
     * Deep copy of the map
     */
    public Map cloneMap() {
        Map ret = new Map();
        ret.sizeX = this.sizeX;
        ret.sizeY = this.sizeY;
        ret.author = this.author;
        ret.mapName = this.mapName;
        ret.highScoreList = this.highScoreList;
        ret.winningConditionClasses = this.winningConditionClasses;
        // re-init for new size
        ret.init();
        for(int x = 0; x < sizeX; x++){
            for(int y = 0; y < sizeY; y++){
                ret.map[x][y] = new LinkedList<>();
                ret.map[x][y].addAll(
                        map[x][y].stream().map(GameObject::cloneObject)
                                .collect(Collectors.toList()));
            }
        }
        return ret;
    }

	/**
	 * adds ScoreData Data
	 * Keeps the queue size for highscore array at 10
	 *
	 * @param data
	 */
	public void addScoreData(ScoreData data) {
		highScoreList.add(data);
		Collections.sort(highScoreList, new Comparator<ScoreData>() {
			@Override
			public int compare(ScoreData o1, ScoreData o2) {
				return Integer.compare(o1.getHighscore(), o2.getHighscore());
			}
		});
		if (highScoreList.size() > MAX_HIGHSCORE_SIZE)
			highScoreList.remove(MAX_HIGHSCORE_SIZE - 1);
	}


	public List<ScoreData> getHighScoreList() {
		return this.highScoreList;
	}


    /**
     * Return a map builder for this map
     * This allow user to continue to modify the map after
     * saving the map
     */
    public MapBuilder asMapBuilder() {
        MapBuilder builder = new MapBuilder(this.sizeX, this.sizeY);
        builder.setAuthor(author);
        int maxId = 0;
        for(int x = 0; x < sizeX; x++){
            for(int y = 0; y < sizeY; y++){
                for(GameObject obj : map[x][y]){
                    builder.addObject(obj);
                    maxId = Math.max(obj.getObjID(), maxId);
                }
            }
        }
        StandardObject.setMaxObjId(maxId);
        return builder;
    }
}
