package View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class StartScreenController {

	//startButton is unused.
	@FXML
	private Button StartButton;

	@FXML
	private Button InstructionButton;


	private Stage currStage;

	public StartScreenController(Stage s) {
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
	public void handleStartButton() {
		ModeScreen cs = new ModeScreen(currStage);
		cs.start();
	}

	@FXML
	public void handleInstructionButton() {
		InstructionScreen cs = new InstructionScreen(currStage);
		cs.start();
	}
}

