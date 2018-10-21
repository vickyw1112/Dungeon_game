package Controller;

import View.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.stage.Stage;

import javax.swing.text.html.ImageView;

public class InstructionScreenController extends Controller {

	/**
	 * field values
	 */
	@FXML
	private Button backButton;

	/**
	 * InstructionScreenController method
	 * @param s
	 */
	public InstructionScreenController(Stage s) {
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
	 * handleBackButton FXML method
	 *
	 * on BackButton being pressed, brings the player back to the start screen
	 */
	@FXML
	public void handleBackButton() {
		Screen cs = new Screen(this.getStage(), "Dungeon Start", "View/StartScreen.fxml");
		Controller controller = new StartScreenController(this.getStage());
		cs.display(controller);
	}

}
