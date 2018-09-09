/**
 * Arrow collectable
 * @author elliottsun
 *
 */
public class Arrow implements Collectable {
	//Constructor
	public Arrow() {
	}

	/**
	 * Takes in player object
	 * @param player
	 */
	public void getCollected(GameEngine gameEngine, Player player) {
		player.addObject(Arrow);
		Map.removeObject();
	}

}
