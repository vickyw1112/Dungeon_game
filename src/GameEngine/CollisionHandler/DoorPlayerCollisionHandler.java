package GameEngine.CollisionHandler;

import GameEngine.*;

public class DoorPlayerCollisionHandler implements CollisionHandler {
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        // Have to check instance type here
        Player player = (Player) (obj1 instanceof Player ? obj1 : obj2);
        Door door = (Door) (obj1 instanceof Door ? obj1 : obj2);

        CollisionResult res = new CollisionResult(0);

        // Check if obj1 is the door && Door is closed
        if (door.getState() == Door.CLOSED) {
            // Collision result regardless if player has key is REJECT
            res.addFlag(CollisionResult.REJECT);
            Key key = door.getKey();
            Inventory inventory = player.getInventory();
            if (inventory.contains(key)) {
                door.openTheDoor(key);
            }
        }
        return res;
    }
}
