package GameEngine.CollisionHandler;


import GameEngine.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollisionEntities {
    private Class<?> cls1;
    private Class<?> cls2;

    /**
     * Constructor for CollisionEntities Sort two given type so that order does not
     * matter
     *
     * @param cls1
     *            class for first obj
     * @param cls2
     *            class for second obj
     */

    public CollisionEntities(Class<?> cls1, Class<?> cls2) {
        assert (GameObject.class.isAssignableFrom(cls1));
        assert (GameObject.class.isAssignableFrom(cls2));

        String type1 = cls1.getSimpleName();
        String type2 = cls2.getSimpleName();
        if (type1.compareTo(type2) < 0) {
            this.cls1 = cls1;
            this.cls2 = cls2;
        } else {
            this.cls1 = cls2;
            this.cls2 = cls1;
        }
    }

    /**
     * Override equals and hashCode to make sure different instance
     * CollisionEntities can be effective the same key for HashMap
     *
     * @see GameEngine#collisionHandlerMap
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CollisionEntities))
            return false;

        return cls1.equals(((CollisionEntities) obj).cls1) && cls2.equals(((CollisionEntities) obj).cls2);
    }

    @Override
    public int hashCode() {
        return cls1.hashCode() ^ cls2.hashCode();
    }

    @Override
    public String toString() {
        String type1 = cls1.getSimpleName();
        String type2 = cls2.getSimpleName();
        return String.format("<CollisionEnt|(%s, %s)>", type1, type2);
    }

    /**
     * Return a list of entities with two type being super type of this entities
     * This is used when this entities is not found in the key of
     * collisionHandlerMap in GameObject TODO: test this thoroughly
     *
     * @see GameEngine#getCollisionHandler(CollisionEntities)
     * @return list of CollisionEntities in the order that subclasses go first
     */
    public Collection<CollisionEntities> getAllParentEntities() {
        Set<CollisionEntities> ret = new LinkedHashSet<>();

        List<Set<Class<?>>> clsAParents = new ArrayList<>();
        List<Set<Class<?>>> clsBParents = new ArrayList<>();

        clsAParents.add(Collections.singleton(cls1));
        clsBParents.add(Collections.singleton(cls2));

        // init all parent type levels up to GameObject for cls1
        Set<Class<?>> currLvl = clsAParents.get(0);
        do {
            currLvl = currLvl.stream()
                    .flatMap(CollisionEntities::getParentTypes)
                    .collect(Collectors.toSet());
            clsAParents.add(currLvl);
        } while (!currLvl.contains(GameObject.class));

        // init all parent type levels up to GameObject for cls2
        currLvl = clsBParents.get(0);
        do {
            currLvl = currLvl.stream()
                    .flatMap(CollisionEntities::getParentTypes)
                    .collect(Collectors.toSet());
            clsBParents.add(currLvl);
        } while (!currLvl.contains(GameObject.class));

        for(int lvl = 0; lvl < clsAParents.size() || lvl < clsBParents.size(); lvl++){
            for(int base = 0; lvl < clsAParents.size() || lvl < clsBParents.size(); lvl++) {
                // mix base B with top lvl A
                final int ATopLvl = Integer.min(lvl, clsAParents.size() - 1);
                if(base <= ATopLvl){
                    clsBParents.get(base).forEach(clsB ->
                        clsAParents.get(ATopLvl).forEach(clsA ->
                            ret.add(new CollisionEntities(clsA, clsB))
                        )
                    );
                }

                // mix base A with top lvl B
                final int BTopLvl = Integer.min(lvl, clsBParents.size() - 1);
                if(base <= ATopLvl){
                    clsAParents.get(base).forEach(clsA ->
                            clsBParents.get(BTopLvl).forEach(clsB ->
                                    ret.add(new CollisionEntities(clsA, clsB))
                            )
                    );
                }
            }
        }
        ret.add(new CollisionEntities(GameObject.class, GameObject.class)); // Fall back
        return ret;
    }

    /**
     * Get parent types (classes/interfaces) of given class filter out all non
     * GameObject types
     *
     * @return stream of parent types
     */
    private static Stream<Class<?>> getParentTypes(Class<?> cls) {
        List<Class<?>> parents = new ArrayList<>(Arrays.asList(cls.getInterfaces()));
        if (cls.getSuperclass() != null)
            parents.add(cls.getSuperclass());

        return parents.stream().filter(GameObject.class::isAssignableFrom);
    }
}
