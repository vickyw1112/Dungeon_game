package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import View.Screen;

public class HighscoreScreenController extends Controller {
	@FXML
	private Button SubmitHighScore;

	@FXML
	private TextField textHighScore;



	public HighscoreScreenController (Stage s){
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
	public void handleScoreButton(){
		String name = textHighScore.getText();
		ScoreData data = new ScoreData(name, 100);
		ListOfScore list = new ListOfScore();
		list.add(data);
		list.serialise(list);
		Screen cs = new Screen(this.getStage(), "WinScreen", "View/ScoreBoardScreen.fxml");
		Controller controller = new WinScreenController(this.getStage());
		cs.display(controller);
	}

	@FXML
	public void handleTextScore(){
	}

}
