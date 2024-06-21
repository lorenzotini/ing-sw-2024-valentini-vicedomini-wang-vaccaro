package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.View.Gui.Gui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
//second scene, displays "joinGameButton" and "newGameButton"
public class ChooseGameSceneController implements GenericController{

    @FXML
    public Button joinGameButton;
    @FXML
    public Button newGameButton;


    @FXML
    public void sendNewGame() throws IOException {
        Gui.getInstance().switchScene("/fxml/NewGameScene.fxml");
    }

    public void sendJoinGame() throws IOException {
        Gui.getInstance().switchScene("/fxml/JoinGameScene.fxml");
    }

    @Override
    public void receiveOk(String ackType) {

    }

    @Override
    public void overwriteChat(ClientChat chat, MiniModel minimodel) {

    }

    @Override
    public void receiveKo(String ackType) {

    }
}