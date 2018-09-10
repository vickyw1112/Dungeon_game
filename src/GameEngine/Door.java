package GameEngine;

public class Door extends GameObject {
	private Key key;
	private int status;
	public static final int OPEN = 1;
	public static final int CLOSED = 0;

	public Door(Point location, Key key) {
		super(location);
		this.status = CLOSED;
		this.key = key;	
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
	 * open door
	 * check whether the key is match to the door or not
	 * if matching set door's status to open
	 * otherwise return false
	 * @param key
	 * @return
	 */
	public boolean openTheDoor(Key key) {
		if(isMatchKey(key)) {
			this.status = OPEN;
			return true;
		}
		return false;
	}
	
	public int getStatus() {
		return this.status;
	}
}
