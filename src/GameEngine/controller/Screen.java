package GameEngine.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Screen {
    private Stage stage;
    private String screenTitle;
    private String fxmlFile;
    private FXMLLoader fxmlLoader;

    public Screen(Stage stage, String screenTitle, String file) {
        this.fxmlFile = file;
        this.screenTitle = screenTitle;
        this.stage = stage;
        this.fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlFile));
    }

    public void display(Controller controller) {
        stage.setTitle(screenTitle);
        fxmlLoader.setController(controller);
        try {
            Parent root = fxmlLoader.load();
            Scene sc = new Scene(root);
            stage.setScene(sc);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
