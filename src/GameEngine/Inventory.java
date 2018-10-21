package GameEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Inventory {
    /**
     * Maintain the count for each kind of game object in the inventory Key is the
     * class name, value is the count for that specific kind of item
     *
     * NOTE: count is not necessary the actual count but a number associated to the
     * object. i.e. Sword's count would mean number of capable hits
     */
    private final HashMap<Class<? extends Collectable>, Integer> countMap;

    /**
     * Maintain the object instances for all collected items
     */
    private final List<Collectable> items;

    /**
     * Constructor for Inventory
     */
    public Inventory() {
        this.countMap = new HashMap<>();
        this.items = new ArrayList<>();
    }

    protected HashMap<Class<? extends Collectable>, Integer> getCountMap() {
        return countMap;
    }

    /**
     * Check if a specific instance of game object is inside the inventory
     *
     * @param obj
     *            the instance being checked
     * @return if the inventory contains that object
     */
    public boolean contains(Collectable obj) {
        return items.contains(obj);
    }

    /**
     * Get count for a type of object in the inventory
     *
     * @param cls
     *            class of the object
     * @return count
     */
    public int getCount(Class<? extends Collectable> cls) {
        if (countMap.get(cls) == null)
            return 0;
        return countMap.get(cls);
    }

    /**
     * Get count by class name
     */
    public int getCount(String classname){
        try {
            Class<?> cls = Class.forName(getClass().getPackage().getName() + "." + classname);
            if(Collectable.class.isAssignableFrom(cls))
                return getCount((Class<? extends Collectable>) cls);
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        return -1; // this line will never be reached
    }

    /**
     * Get all types of objects that are currently in the inventory
     */
    public List<String> getAllClasses(){
        return countMap.keySet().stream().map(Class::getSimpleName).collect(Collectors.toList());
    }

    /**
     * Set the count of a specific type of object to a given number
     *
     * @param cls
     *            class of object
     * @param count
     *            number to set
     */
    public void setCount(Class<? extends Collectable> cls, int count) {
        countMap.put(cls, count);
        if(count == 0)
            countMap.remove(cls);
    }

    /**
     * Add a game object instance to the inventory Also increment the count for the
     * type of object
     *
     * @param obj
     *            the object being added
     */
    public void addObject(Collectable obj) {
        items.add(obj);

        int count;
        if (countMap.get(obj.getClass()) != null) {
            count = countMap.get(obj.getClass());
            countMap.put(obj.getClass(), ++count);
        } else {
            countMap.put(obj.getClass(), 1);
        }
    }

    /**
     * Pop a given type of object from the inventory reduce count of that type by
     * one
     *
     * @param cls
     *            class of object being popped
     * @return popped object, if no such type object return null
     */
    public Collectable popObject(Class<? extends Collectable> cls) {
        int count;
        for (Collectable item : items) {
            if (item.getClass().equals(cls)) {
                items.remove(item);
                count = countMap.get(cls);
                countMap.put(cls, --count);
                return item;
            }
        }
        if(countMap.containsKey(cls) && countMap.get(cls) == 0)
            countMap.remove(cls);

        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Player Inventory:\n");
        for(Entry<Class<? extends Collectable>, Integer> ent : countMap.entrySet()){
            sb.append(String.format("\t%s: %d\n", ent.getKey().getSimpleName(), ent.getValue()));
        }
        return new String(sb);
    }
}
