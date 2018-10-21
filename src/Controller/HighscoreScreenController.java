package Controller;

import GameEngine.Map;
import GameEngine.ScoreData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import View.Screen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class HighscoreScreenController extends Controller {

	/**
	 * fields values
	 */
	@FXML
	private Button SubmitHighScore;

	@FXML
	private TextField textHighScore;

	private Map map;
	private String mapname;


	/**
	 * Constructor for HighscoreScreenController class
	 *
	 * @param s
	 * @param map
	 * @param mapname
	 */
	public HighscoreScreenController (Stage s, Map map, String mapname){
		super(s);
		this.map = map;
		this.mapname = mapname;
	}

	/**
	 * initialise method
	 */
	@FXML
	public void initialize() {
		// This function is for loading initialisation of your controller.
		// Since this example doesn't need any initialisation, I just leave it empty.
		// I deliberately put this function in every controller in order to let you know
		// that you may(should) need this function in your project.
	}

	/**
	 * handleScoreButton FXML method
	 *
	 * method for when ScoreButton is pressed
	 * Takes in the String input in textbox and serialises it with the map just played
	 */
	@FXML
	public void handleScoreButton(){
		String name = textHighScore.getText();
		ScoreData data = new ScoreData(name, 100);
		List<ScoreData> list = map.getHighScoreList();
		map.addScoreData(data);
		try {
			map.serialize(new FileOutputStream(Config.MAP_BASE_DIR + File.separator + mapname + ".dungeon"));
		} catch (IOException e){
			e.printStackTrace();
		}
		Screen cs = new Screen(this.getStage(), "WinScreen", "View/ScoreBoardScreen.fxml");
		Controller controller = new WinScreenController(this.getStage(), this.map);
		cs.display(controller);
	}

	/**
	 * handleTextScore method
	 */
	@FXML
	public void handleTextScore(){
	}

}
