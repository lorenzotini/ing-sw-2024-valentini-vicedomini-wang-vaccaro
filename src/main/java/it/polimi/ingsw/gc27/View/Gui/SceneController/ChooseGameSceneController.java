package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.View.Gui.Gui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import java.io.IOException;

/**
 * second scene of initialization, player can create a new game or join an existing one
 */
public class ChooseGameSceneController implements GenericController{

    /** used to join and existing game */
    @FXML
    public Button joinGameButton;
    /** used to create a new game */
    @FXML
    public Button newGameButton;
    /** used to show that the client didn't reach connection with the server */
    @FXML
    public Pane serverDownPane;

    /**
     * if newGameButton is clicked, the stage switches scene to the NewGameScene
     * @throws IOException
     */
    @FXML
    public void sendNewGame() throws IOException {
        Gui.getInstance().switchScene("/fxml/NewGameScene.fxml");
    }

    /**
     * if the joinGameButton is clicked, the stage switches scene to JoinGameScene
     * @throws IOException
     */
    public void sendJoinGame() throws IOException {
        Gui.getInstance().switchScene("/fxml/JoinGameScene.fxml");
    }

    /**
     * method implemented from {@link GenericController}, invoked by Gui in order to send a string to a scene controller
     * the string is generally a positive feedback
     * @param ackType
     */
    @Override
    public void receiveOk(String ackType) {

    }
    /**
     * method implemented from {@link GenericController},
     * invoked by Gui when a message is sent by a player in the chat
     * @param chat
     * @param minimodel
     */
    @Override
    public void overwriteChat(ClientChat chat, MiniModel minimodel) {

    }

    /**
     * method implemented from {@link GenericController}, invoked by Gui in order to send a string to a generic scene controller,
     * the string is generally a negative feedback, such as an error
     * @param ackType
     */
    @Override
    public void receiveKo(String ackType) {

    }
}