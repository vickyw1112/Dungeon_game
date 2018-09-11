package GameEngine;

public class Door extends GameObject {
	private Key key;
	public static final int OPEN = 1;
	public static final int CLOSED = 0;

	public Door(Point location) {
		super(location);
		this.state = CLOSED;
	}
	
	/**
	 * helper function for open door
	 * @param key
	 * @return
	 */
	private boolean isMatchKey(Key key) {
		if(this.key.equals(key))
			return true;
		return false;
	}
	
	/**
	 * set corresponding key to door
	 * @param key
	 */
	public void setKey(Key key) {
		this.key = key;
	}
	
	/**
	 * open door
	 * check whether the key is match to the door or not
	 * if matching set door's status to open
	 * otherwise return false
	 * @param key
	 * @return
	 */
	public boolean openTheDoor(Key key) {
		if(isMatchKey(key)) {
			this.changeState(OPEN);
			return true;
		}
		return false;
	}
}
