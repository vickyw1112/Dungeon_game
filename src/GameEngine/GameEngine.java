package GameEngine;

import GameEngine.CollisionHandler.*;
import GameEngine.utils.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Game engine which store current state of the whole game
 * Get instantiated when loading a specific map for 1st mode
 */
public class GameEngine {
    private final Map map;
    private final List<Movable> movingObjects;
    private final List<Monster> monsters;
    public static int MONSTERKILLED = 0;
    Player player;


    /**
     * SampleMaps storing all game object and using their ObjId as key
     * @see GameObject#getObjID()
     * @see StandardObject#objId
     */
    private final HashMap<Integer, GameObject> objects;

    /**
     * SampleMaps for finding collision handler for two specific type of object
     * Key is CollisionEntities which contains two classes
     * Value is CollisionHandler
     * Also contains CollisionEntities key which include one or two
     * super type of a specific type for fallback if specific key cannot be found
     * @see GameEngine#getCollisionHandler(CollisionEntities)
     */
    private final HashMap<CollisionEntities, CollisionHandler> collisionHandlerMap;



    /**
     * Constructor for GameEngine Load the map from saving
     * For each unique game object,
     * call it's {@link GameObject#registerCollisionHandler}
     * and {@link GameObject#initialize()} methods
     *
     * @param map map
     * @param observer front end observer for GameObject state change
     */
    public GameEngine(Map map, GameObjectObserver observer){
        this.map = map;
        this.objects = new HashMap<>();
        this.movingObjects = new LinkedList<>();
        this.monsters = new LinkedList<>();
        this.collisionHandlerMap = new HashMap<>();

        for (GameObject obj : map.getAllObjects()) {
            obj.initialize();
            obj.addObserver(observer);
            if (obj instanceof Player)
                this.player = (Player) obj;
            if (obj instanceof Movable)
                this.movingObjects.add((Movable) obj);
            if (obj instanceof Monster)
                this.monsters.add((Monster) obj);

            this.objects.put(obj.getObjID(), obj);
            obj.registerCollisionHandler(this);
        }

        // sort monsters in correct order
        monsters.sort(Monster::compare);


        // register collisionHandler for (GameObject, GameObject) for default handler
        // fall back mechanism see GameEngine#getCollisionHandler
        registerCollisionHandler(new CollisionEntities(GameObject.class, GameObject.class), new DefaultHandler());

        // register collisionHandler for (GameObject, Movable) for GameObjectMovableCollisionHandler
        registerCollisionHandler(new CollisionEntities(GameObject.class, Movable.class),
                new GameObjectMovableCollisionHandler());

        updateMonstersPath();
    }

    /**
     * Wrapper constructor
     *
     * @param mapInput map file input stream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public GameEngine(InputStream mapInput, GameObjectObserver observer) throws IOException, ClassNotFoundException {
        this(Map.loadFromFile(mapInput), observer);
    }

    /**
     * Constructor only takes in map for debugging/testing
     * @param map map
     */
    public GameEngine(Map map){
        this(map, obj -> System.out.println(obj + " changed state to: "+ obj.getState()));
        // to suppress null pointer exception in tests does not involve map
        this.player = player == null ? new Player(new Point(0, 0)) : player;
        // initialize player
        player.initialize();
    }

    /**
     * No arg constructor for debugging/testing
     */
    public GameEngine(){
        this(new Map());
    }

    /**
     * Define the behaviour of backend when the player pressed the key for shooting
     * arrow
     *
     * @return the arrow instance being shot or null if cannot shoot arrow
     */
    public Arrow playerShootArrow() {
        Arrow arrow = player.shootArrow(map);
        if(arrow != null)
            movingObjects.add(arrow);
        return arrow;
    }

    /**
     * Define the behaviour of backend when the player pressed the key for setting
     * bomb
     *
     * @return the bomb instance being set or null if cannot set bomb
     */
    public Bomb playerSetBomb() {
        return player.setBomb(map);
    }

    /**
     * Get the map
     *
     * @return map dungeon layout
     */
    public Map getMap() {
        return this.map;
    }

    /**
     * Get the list of all movable objects except ones with zero speed
     *
     * @return list of all moving objects
     */
    public List<Movable> getMovingObjects() {
        // filter out all obj with speed 0
        return movingObjects.stream().filter(o -> o.getSpeed() > 0)
                .collect(Collectors.toList());
    }

    /**
     * Return a list of all game objects in this game
     *
     * @return list of all game objects
     */
    public List<GameObject> getAllObjects() {
        return new LinkedList<>(objects.values());
    }

    /**
     * Find the correct handler for given two object and call to handle the collision
     *
     * @see CollisionResult
     * @param obj1 first game obj
     * @param obj2 second game obj
     * @return CollisionResult result of collision to be processed by front end
     */
    public CollisionResult handleCollision(GameObject obj1, GameObject obj2) {
        CollisionHandler handler = null;
        try {
            handler = getCollisionHandler(new CollisionEntities(obj1.getClass(), obj2.getClass()));
        } catch (CollisionHandlerNotImplement e) {
            System.err.format("Error when trying to get collision handler for %s and %s\n", obj1, obj2);
            e.printStackTrace();
            System.exit(1);
        }
        CollisionResult result = handler.handle(this, obj1, obj2);
        result.setCollidingObjects(obj1, obj2);

        // TODO DELETE this later
        if(!result.containFlag(CollisionResult.REJECT))
            System.out.format("%s and %s => %s\n", obj1, obj2, result);
        return result;
    }

    /**
     * Check if the player is wining the game now
     *
     * @return whether the player has won the game
     */
    public boolean checkWiningCondition() {
        // TODO: refactor this function

        boolean isAllTreasure = false;
        boolean isAllMonster = false;
        boolean isAllSwitch = false;
        Set<Point> boulders = new HashSet<>();
        Set<Point> floorSwitches = new HashSet<>();
        List<Point> exits = new ArrayList<>();
        List<Point> treasure = new ArrayList<>();

        for(GameObject obj: objects.values()){
            if(obj instanceof Boulder)
                boulders.add(obj.getLocation());
            if(obj instanceof FloorSwitch)
                floorSwitches.add(obj.getLocation());
            if(obj instanceof Exit)
                exits.add(obj.getLocation());
            if(obj instanceof Treasure)
                treasure.add(obj.getLocation());
        }

        // exit win is done by WinCollisionHandler
        if(!exits.isEmpty()) {
            return false;
        }
        else {
            // check boulder on switch
            if(!floorSwitches.isEmpty()) {
                if (boulders.containsAll(floorSwitches))
                    isAllSwitch = true;
            }

            // check treasure in player's inventory
            // if there is no treasure in map and player's inventory has treasure player wins
            // otherwise player doesn't win
            if (treasure.isEmpty() && (this.player.getInventory().getCount(Treasure.class) > 0))
                isAllTreasure = true;

            if(MONSTERKILLED > 0 && this.monsters.isEmpty()) {
                isAllMonster = true;
            }
        }
        return (isAllTreasure || isAllMonster || isAllSwitch);
    }

    /**
     * Wrapper of {@link GameObject#setLocation} Updates indexes for this object in
     * SampleMaps Call {@link Monster#updatePath} for each {@link GameEngine#monsters} if
     * player's location changed
     *
     * @param object
     *            game object
     * @param location
     *            new location
     */
    public void changeObjectLocation(GameObject object, Point location) {
        if (object.setLocation(location)) {
            map.updateObjectLocation(object, location);
            object.onUpdatingLocation(this);
            System.out.format("%s updated location\n", object);
        }
    }

    /**
     * Get a game object by it's objID
     *
     * @param objId object ID
     * @return GameObject with given id
     */
    public GameObject getObjectById(int objId) {
        return objects.get(objId);
    }

    /**
     * Remove all reference to a given game object in the backend
     *
     * @see GameEngine#map
     * @see GameEngine#objects
     * @see GameEngine#movingObjects
     * @see GameEngine#monsters
     * @param obj
     *            object to be removed
     */
    public void removeGameObject(GameObject obj) {
        map.removeObject(obj);
        objects.remove(obj.getObjID());
        movingObjects.remove(obj);
        monsters.remove(obj);
    }

    /**
     * Interface to register a collision handler for given entities
     */
    void registerCollisionHandler(CollisionEntities entities, CollisionHandler handler) {
        collisionHandlerMap.put(entities, handler);
    }

    /**
     * Given a entities object, return the corresponding collision handler from the
     * map If entities are not present in the key try to get the entities of parent
     * classes If no handler can be found throw exception
     *
     * @throws CollisionHandlerNotImplement
     * @param entities entities
     * @return collision handler
     */
    public CollisionHandler getCollisionHandler(CollisionEntities entities) throws CollisionHandlerNotImplement {
        // check if there's handler registered matching exactly given entities
        if (collisionHandlerMap.containsKey(entities)) {
            return collisionHandlerMap.get(entities);
        }

        // get entities' super types to fall back
        for (CollisionEntities ent : entities.getAllParentEntities()) {
            if (collisionHandlerMap.containsKey(ent)) {
                return collisionHandlerMap.get(ent);
            }
        }
        throw new CollisionHandlerNotImplement(entities.toString());
    }

    /**
     * Update all monster's path by their path generator
     *
     * @pre all Hunter is before Hound since Hound is dependent on Hunter's path
     */
    public void updateMonstersPath() {
        this.monsters.forEach(monster -> monster.updatePath(map, player));
    }

    /**
     * Set all monsters' pathGenerator to given one
     * if the given one is null, restore all monsters' pathGenerator to default
     * This is called when player get invincible potion
     * and called again by front end with null as arg when invincible effect expires
     *
     * @see PlayerPotionCollisionHandler#handle
     * @see Monster#getDefaultPathGenerator()
     * @param pathGenerator new pathGenerator to set
     */
    public void updateMonstersMovementStrategy(PathGenerator pathGenerator) {
        this.monsters.forEach(monster ->
            monster.pathGenerator = pathGenerator == null ? monster.getDefaultPathGenerator() : pathGenerator
        );
    }

    /**
     * Wrapper for {@link Map#getObjects(Point)}
     */
    public List<GameObject> getObjectsAtLocation(Point p) {
        return map.getObjects(p);
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Delegate method to get the count of given class of object
     * in player's inventory
     *
     * @see Inventory#getCount(String)
     * @return count
     */
    public int getInventoryCounts(String classname){
        return player.getInventoryCount(classname);
    }

    /**
     * @see Inventory#getAllClasses()
     */
    public List<String> getInventoryAllClasses(){
        return player.getInventoryAllClasses();
    }
}
