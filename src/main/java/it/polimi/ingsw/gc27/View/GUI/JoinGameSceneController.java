package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;



public class JoinGameSceneController implements GenericController{

    public TextField idTextField;
    public Button sendGameIdButton;

    public void sendGameId(ActionEvent event) throws IOException {
        Gui.getInstance().stringFromSceneController(idTextField.getText());
        //prende stringa da mandare
        Gui.getInstance().switchScene("/fxml/LoginScene.fxml" );
    }

}
