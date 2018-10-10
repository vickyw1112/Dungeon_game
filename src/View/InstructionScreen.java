package View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InstructionScreen {

	private Stage s;
	private String title;
	private FXMLLoader fxmlLoader;

	public InstructionScreen(Stage s) {
		this.s = s;
		this.title = "Instruction Screen";
		this.fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("view/InstructionScreen.fxml"));
	}

	public void start()  {
		s.setTitle(title);
		// set controller for start.fxml
		fxmlLoader.setController(new InstructionScreenController(s));
		try {
			// load into a Parent node called root
			Parent root = fxmlLoader.load();
			Scene sc = new Scene(root);
			s.setScene(sc);
			s.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}