
public class Bomb implements Collectable {
	
	public void getCollected(Player p) {
		p.addInventory("Bomb", 1);
	}
}
