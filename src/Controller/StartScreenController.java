package Controller;

import View.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class StartScreenController extends Controller {

	/**
	 * field values
	 */
	@FXML
	private Button StartButton;

	@FXML
	private Button InstructionButton;

	/**
	 * Constructor for StartScreenController class
	 * @param s
	 */
	public StartScreenController(Stage s) {
		super(s);
	}

	/**
	 * initialise FXML method
	 */
	@FXML
	public void initialize() {
		// This function is for loading initialisation of your controller.
		// Since this example doesn't need any initialisation, I just leave it empty.
		// I deliberately put this function in every controller in order to let you know
		// that you may(should) need this function in your project.
	}

	/**
	 * handleStartButton FXML method
	 *
	 * brings player to ModeSelection screen upon pressing button
	 */
	@FXML
	public void handleStartButton() {
		Screen cs = new Screen(this.getStage(), "Select Mode", "View/ModeScreen.fxml");
		Controller controller = new ModeScreenController(this.getStage());
		cs.display(controller);
	}

	/**
	 * handleInstructionButton FXML method
	 *
	 * brings player to instruction screen upon pressing instruction button
	 */
	@FXML
	public void handleInstructionButton() {
		Screen cs = new Screen(this.getStage(), "Dungeon instruction", "View/InstructionScreen.fxml");
		Controller controller = new InstructionScreenController(this.getStage());
		cs.display(controller);
	}
}

