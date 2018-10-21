package Controller;

import View.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.stage.Stage;

public class ModeScreenController extends Controller {

	/**
	 * field value
	 */
	@FXML
	private Button playDungeonButton;

	@FXML
	private Button createDungeonButton;

	/**
	 * Constructor for ModeScreenController
	 *
	 * @param s
	 */
	public ModeScreenController(Stage s) {
		super(s);
	}

	/**
	 * initalise FXML method
	 */
	@FXML
	public void initialize() {
		// This function is for loading initialisation of your controller.
		// Since this example doesn't need any initialisation, I just leave it empty.
		// I deliberately put this function in every controller in order to let you know
		// that you may(should) need this function in your project.
	}

	/**
	 * handlePlayButton FXML method
	 *
	 * Brings player to MapSelection screen on pressing Select Map button
	 */
	@FXML
	public void handlePlayButton() {
        Screen cs = new Screen(this.getStage(), "Select Map", "View/MapSelectScreen.fxml");
        Controller controller = new MapSelectScreenController(this.getStage());
        cs.display(controller);
	}

	/**
	 * handleCreateDungeon FXML method
	 *
	 * brings player to Dungeon Creation screen on pressing button
	 */
	@FXML
	public void handleCreateDungeon() {
		Screen cs = new Screen(this.getStage(), "Dungeon design", "View/DesignScreen.fxml");
		Controller controller = new DesignScreenController(this.getStage());
		cs.display(controller);
	}

}
