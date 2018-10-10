package Controller;

import Controller.Controller;
import View.Screen;
import View.StartScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.stage.Stage;

public class InstructionScreenController extends Controller {

	//startButton is unused.
	@FXML
	private Button backButton;


	public InstructionScreenController(Stage s) {
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
	public void handleBackButton() {
		Screen cs = new StartScreen(this.getStage(), "Dungeon Start", "View/StartScreen.fxml");
		Controller controller = new StartScreenController(this.getStage());
		cs.display(controller);
	}

}
