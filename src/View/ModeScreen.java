package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ModeScreen extends Screen{

	private Stage s;
	private String title;
	private FXMLLoader fxmlLoader;

	public ModeScreen(Stage stage, String title, String file) {
		super(stage, title, file);
	}
}