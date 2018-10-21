package View;

import Controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Screen {
    private Stage stage;
    private String title;
    private FXMLLoader fxmlLoader;

    public Screen(Stage stage, String title, String fxmlFile){
        this.stage = stage;
        this.title = title;
        this.fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlFile));
    }

    public Stage getStage() {
        return stage;
    }

    public void display(Controller controller){
        stage.setTitle(title);
        fxmlLoader.setController(controller);
        try{
            Parent root = fxmlLoader.load();
            Scene sc = new Scene(root);
            stage.setScene(sc);
            controller.afterInitialize();
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
