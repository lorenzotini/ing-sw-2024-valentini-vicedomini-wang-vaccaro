package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ChooseGameSceneController implements GenericController{

    @FXML
    public Button joinGameButton;
    @FXML
    public Button newGameButton;



//TODO: funzione change scene in gui + capire perchè non funziona (gui null), gestire in gui l'inizializzazione delle scene
    @FXML
    public void sendNewGame() throws IOException {
        Gui.getInstance().stringFromSceneController("new");
        Gui.getInstance().switchScene("/fxml/NewGameScene.fxml");
    }

    public void sendJoinGame() throws IOException {
        Gui.getInstance().switchScene("/fxml/JoinGameScene.fxml");
        //changeScene, in quella farà gui.stringFrom..
    }

}