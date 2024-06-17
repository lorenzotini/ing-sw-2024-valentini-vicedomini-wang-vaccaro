package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.View.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

//first scene of the game, displays logo and "Play" button
public class StarterSceneController implements GenericController{

    @FXML
    public Button startGameButton;


    @FXML
    public void switchToChooseGameScene(ActionEvent event) throws IOException {
        Gui.getInstance().switchScene("/fxml/ChooseGameScene.fxml");
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

