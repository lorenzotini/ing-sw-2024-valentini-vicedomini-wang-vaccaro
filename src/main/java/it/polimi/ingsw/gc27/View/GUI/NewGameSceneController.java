package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.io.IOException;

//third.1 scene, will open after button "new game" (in ChooseGameNetScene) clicked,
//and number of players will be asked
public class NewGameSceneController implements GenericController{
    @FXML
    public CheckBox checkBox2;
    @FXML
    public CheckBox checkBox4;
    @FXML
    public CheckBox checkBox3;
    @FXML
    public Button go;
    public Button backButton;
    private String chosenButton;


    @FXML
    public void sendLoginScene() throws IOException {
        Gui.getInstance().stringFromSceneController(chosenButton);
        Gui.getInstance().switchScene( "/fxml/LoginScene.fxml");

    }
    //TODO: fare in modo che i checkbox siano esclusivi (se viene selezionata un'opzione ora posso selezionarne
    //TODO: un'altra ma solo graficamente, il comando selezionato viene mandato correttamente)
    public void chooseNumber(ActionEvent event) {
        if(event.getSource().equals(checkBox2)){
            chosenButton="2";
        }
        else if(event.getSource().equals(checkBox3)){
            chosenButton="3";
        }
        else{
            chosenButton="4";
        }
    }

    //players can change their minds and go back to chooseGameScene
    public void previousScene(ActionEvent event) throws IOException {
        Gui.getInstance().switchScene("/fxml/ChooseGameScene.fxml");
    }//TODO: DOES NOT WORK PROPERLY BECAUSE THE "NEW GAME" MESSAGE IS SENT ANYWAY

    public void sendNumberOfPlayers(ActionEvent event) {
    }
    //TODO: POSSIBLE FIX, SEND THE "NEW GAME" OR "JOIN GAME" MESSAGE NOT IMMEDIATELY BUT AFTER INFORMATION ABOUT
    //TODO: NUMBER OF PLAYERS OR GAMEID IS COLLECTED
}
