package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;



public class JoinGameSceneController {


    public TextField idTextField;
    public Button sendGameIdButton;

    public void sendGameId(ActionEvent event) throws IOException {
        //prende stringa da mandare
        Gui.getInstance().switchScene(Gui.getInstance().getStage(),"/fxml/StarterScene.fxml" );
    }
}
