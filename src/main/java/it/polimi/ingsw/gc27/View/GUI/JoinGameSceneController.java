package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

//third.2 scene, will open after button "join game" (in ChooseGameNetScene) clicked,
//and gameID will be asked

public class JoinGameSceneController implements GenericController{

    public TextField idTextField;
    public Button sendGameIdButton;
    public Button backButton;

    public void sendGameId(ActionEvent event) throws IOException {
        Gui.getInstance().stringFromSceneController(idTextField.getText());
        Gui.getInstance().switchScene("/fxml/LoginScene.fxml" );
    }

    //players can change their minds and go back to chooseGameScene
    public void previousScene(ActionEvent event) throws IOException {
        Gui.getInstance().switchScene("/fxml/ChooseGameScene.fxml");
    }//TODO: DOES NOT WORK PROPERLY, LOOK AT NOTE IN newGameSceneController TO KNOW WHY
}
