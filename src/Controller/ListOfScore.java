package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;

public class ListOfScore implements Serializable {

	// Fields
	private int MAX_ARRAY_SIZE = 10;
	private List<ScoreData> dataLists;

	/**
	 * Constructor for ListOfScore
	 * A priority queue that keeps tabs of the highscores
	 */
	public ListOfScore() {
		dataLists = new LinkedList<ScoreData>();
	}

	public List<ScoreData> getDataLists() {
		return this.dataLists;
	}

	/**
	 * add method
	 * adds ScoreData Data
	 * Keeps the queue size at 10
	 *
	 * @param data
	 */
	public void add(ScoreData data) {
		dataLists.add(data);
		Collections.sort(dataLists, new Comparator<ScoreData>() {
			@Override
			public int compare(ScoreData o1, ScoreData o2) {
				return Integer.compare(o1.getHighscore(), o2.getHighscore());
			}
		});
		if (dataLists.size() > MAX_ARRAY_SIZE)
			dataLists.remove(MAX_ARRAY_SIZE - 1);
	}

	/**
	 * Serialise method
	 * outputs queue items into a file
	 *
	 * @param list
	 */
	public void serialise(ListOfScore list) {
		if(new File("highscore.ser").isFile()) {
			ObservableList<ScoreData> old = deserialise();
			for (int i = 0; i < old.size(); i++) {
				list.add(old.get(i));
			}
		}

		try {
			FileOutputStream fileOut = new FileOutputStream("highscore.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(list);
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deserialise method
	 * Takes in a file and deserialises it
	 */
	public ObservableList<ScoreData> deserialise() {
		try {
			FileInputStream fileIn = new FileInputStream("highscore.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			ListOfScore list = (ListOfScore) in.readObject();
			in.close();
			fileIn.close();
			return FXCollections.observableList(list.getDataLists());
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			System.out.println("Data class not found");
			c.printStackTrace();
		}
		return FXCollections.emptyObservableList();
	}
}
