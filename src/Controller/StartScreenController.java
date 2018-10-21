package Controller;

import View.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class StartScreenController extends Controller {

	//startButton is unused.
	@FXML
	private Button StartButton;

	@FXML
	private Button InstructionButton;


	public StartScreenController(Stage s) {
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
	public void handleStartButton() {
		Screen cs = new Screen(this.getStage(), "Select Mode", "View/ModeScreen.fxml");
		Controller controller = new ModeScreenController(this.getStage());
		cs.display(controller);
	}

	@FXML
	public void handleInstructionButton() {
		Screen cs = new Screen(this.getStage(), "Dungeon instruction", "View/InstructionScreen.fxml");
		Controller controller = new InstructionScreenController(this.getStage());
		cs.display(controller);
	}
}

