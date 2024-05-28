package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

//first scene of the game, displayes logo and "Play", "Exit" buttons
public class StarterSceneController implements GenericController{ //per chiamare start

    @FXML
    public Button startGameButton; //servirà thread che ascolta i bottoni e thread che manda le chiamate al controller

    //creare costruttore che viene chiamato da rmi e server
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;


    //collegato al bottone "start"
    @FXML
    public void switchToChooseGameScene(ActionEvent event) throws IOException {
        Gui.getInstance().switchScene("/fxml/ChooseGameScene.fxml");
    }

}

