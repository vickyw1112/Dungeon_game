package Controller;

import Controller.Controller;
import View.DungeonPlayScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.stage.Stage;

import java.io.IOException;

public class ModeScreenController extends Controller {

	//startButton is unused.
	@FXML
	private Button playDungeonButton;

	@FXML
	private Button createDungeonButton;


	public ModeScreenController(Stage s) {
		super(s);
	}

	@FXML
	public void initialize() {
		// This function is for loading initialisation of your controller.
		// Since this example doesn't need any initialisation, I just leave it empty.
		// I deliberately put this function in every controller in order to let you know
		// that you may(should) need this function in your project.
	}

	@FXML
	public void handlePlayButton() {
		try {
			new DungeonPlayScreen(this.getStage()).start();
		} catch (IOException e){
			e.printStackTrace();
		}

	}

	@FXML
	public void handleCreateDungeon() {
		// TODO
	}

}
