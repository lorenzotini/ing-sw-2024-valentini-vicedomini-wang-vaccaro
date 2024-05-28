package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.io.IOException;

//this scene will open after button "new game" (in ChooseGameNetScene) clicked,
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

    private String chosenButton;


    @FXML
    public void sendLoginScene() throws IOException {
        //faccio  in modo che è qui che viene mandata l'informazione del numero di players
        Gui.getInstance().stringFromSceneController(chosenButton);
        Gui.getInstance().switchScene( "/fxml/LoginScene.fxml");

    }

        //TODO: fare in modo che i checkbox siano esclusivi (se viene selezionata un'opzione ora posso selezionarne
        //TODO: un'altra ma solo graficamente, il comando selezionato viene mandato correttamente)
    public void chooseNumber(ActionEvent event) {
        if(event.getSource().equals(checkBox2)){
            chosenButton="2";
        }
        else if(event.getSource().equals(checkBox3)){ //sistema in button
            chosenButton="3";
        }
        else{
            chosenButton="4";
        }
    }


}
