package View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class InstructionScreenController {

	//startButton is unused.
	@FXML
	private Button backButton;


	private Stage currStage;

	public InstructionScreenController(Stage s) {
		currStage = s;
	}

	@FXML
	public void initialize() {
		// This function is for loading initialisation of your controller.
		// Since this example doesn't need any initialisation, I just leave it empty.
		// I deliberately put this function in every controller in order to let you know
		// that you may(should) need this function in your project.
	}

	@FXML
	public void handleBackButton() {
		StartScreen cs = new StartScreen(currStage);
		cs.start();
	}

}
