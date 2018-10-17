package Controller;

import Controller.ListOfScore;
import java.io.*;

public class ScoreData implements Serializable {

	// Fields
	private String name;
	private int highscore;

	/**
	 * Constructor for ScoreData class
	 * Stores data for highscores
	 * @param name
	 * @param highscore
	 */
	public ScoreData(String name, int highscore){
		this.name = name;
		this.highscore = highscore;
	}

	/**
	 * getter for name
	 * @return
	 */
	public String getName() {
		return name;
	}


	/**
	 * getter for highscore
	 * @return
	 */
	public int getHighscore() {
		return highscore;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.highscore).append("		").append(this.name);
		return sb.toString();
	}
}

