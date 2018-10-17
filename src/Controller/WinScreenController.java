package Controller;

import Controller.Controller;
import View.Screen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class WinScreenController extends Controller {
	// Fields
	@FXML
	private ListView HighScoreBoard;

	@FXML
	private Button BackButton;

	/**
	 * Constructor WinScreenController
	 * @param s
	 */
	public WinScreenController(Stage s){
		super(s);
	}

	/**
	 * initialize method for javafx
	 */
	@FXML
	public void initialize() {

//		ObservableList<String> test = null;
//		test.add("test");
//		test.add("score");
//		test.add("board");
		ListOfScore list = new ListOfScore();
		HighScoreBoard.setItems(list.deserialise());
	}


	/**
	 * handBackButton method for javafx
	 */
	@FXML
	public void handleBackButton(){
		Screen cs = new Screen(this.getStage(), "Dungeon Start", "View/StartScreen.fxml");
		Controller controller = new StartScreenController(this.getStage());
		cs.display(controller);
	}

}
