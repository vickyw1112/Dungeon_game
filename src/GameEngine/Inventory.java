package GameEngine;

import java.util.HashMap;
import java.util.List;

public class Inventory {
    /**
     * Maintain the count for each kind of game object
     * in the inventory
     * Key is the class name, value is the count for that specific
     * kind of item
     *
     * NOTE: count is not necessary the actual count
     * but a number associated to the object.
     * i.e. Sword's count would mean number of capable hits
     */
    private HashMap<String, Integer> countMap;

    /**
     * Maintain the object instances for all collected items
     */
    private List<GameObject> items;



    /**
     * Check if a specific instance of game object
     * is inside the inventory
     *
     * @param obj the instance being checked
     * @return if the inventory contains that object
     */
    public boolean contains(GameObject obj){
    	return items.contains(obj);
    }
    
    /**
     * Get count for a type of object in the inventory
     *
     * @param classname type of the object
     * @return count
     */
    public int getCount(String classname){
    	if(countMap.get(classname) == null)
    		return 0;
    	return countMap.get(classname);
    }

    /**
     * Set the count of a specific type of object to a
     * given number
     *
     * @param classname type of object
     * @param count number to set
     */
    public void setCount(String classname, int count){
    	countMap.put(classname, count);
    }

    /**
     * Add a game object instance to the inventory
     * Also increment the count for the type of object
     *
     * @param obj the object being added
     */
    public void addObject(GameObject obj){
    	items.add(obj);
    	int count;
    	if(countMap.get(obj.getClassName()) != null) {
    		count = countMap.get(obj.getClassName());
    		countMap.put(obj.getClassName(), ++count);
    	}
    	else {
    		countMap.put(obj.getClassName(), 1);
    	}	
    }

    /**
     * Pop a given type of object from the inventory
     * reduce count of that type by one
     *
     * @param classname type of object being popped
     * @return popped object, if no such type object return null
     */
    public GameObject popObject(String classname){
    	int count;
        for(GameObject item: items) {
        	if(item.getClassName().equals(classname)) {
        		items.remove(item);
        		count = countMap.get(classname);
        		countMap.put(classname, --count);
        		return item;
        	}	
        }
        return null;
    }
}
