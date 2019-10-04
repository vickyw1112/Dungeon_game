package Controller;

import Controller.Controller;
import GameEngine.utils.Observable;
import View.Screen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import GameEngine.ScoreData;
import GameEngine.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class WinScreenController extends Controller {
	/**
	 * field values
	 */
	@FXML
	private ListView HighScoreBoard;

	@FXML
	private Button BackButton;

	private Map map;

	/**
	 * Constructor WinScreenController
	 * @param s
	 */
	public WinScreenController(Stage s, Map map){
		super(s);
		this.map = map;
	}

	/**
	 * initialize method for javafx
	 */
	@FXML
	public void initialize() {
		ObservableList<ScoreData> list = FXCollections.observableList(map.getHighScoreList());
		HighScoreBoard.setItems(list);
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
