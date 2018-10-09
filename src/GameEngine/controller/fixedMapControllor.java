package GameEngine.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class fixedMapControllor extends Controller{
    private Stage s;
    public fixedMapControllor(Stage s){
        super(s);

    }

    @FXML
    private Label wall2;

    @FXML
    private Label wall3;

    @FXML
    private Label hunter;

    @FXML
    private Label wall1;

    @FXML
    private Label player;

    @FXML
    public void initialize(){
        Image image = new Image(getClass().getResourceAsStream("Player.png"));
        player.setGraphic(new ImageView(image));
        image = new Image(getClass().getResourceAsStream("Wall.png"));
        wall1.setGraphic(new ImageView(image));
        wall2.setGraphic(new ImageView(image));
        wall3.setGraphic(new ImageView(image));
        image = new Image(getClass().getResourceAsStream("Hunter.png"));
        hunter.setGraphic(new ImageView(image));



    }

    @FXML
    void wallCollisionHandler(ActionEvent event) {

    }

    @FXML
    void hunterCollisionHandler(ActionEvent event) {

    }

}
