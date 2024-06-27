package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.View.Gui.Gui;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * second scene of initialization, player can create a new game or join an existing one
 */
public class ChooseGameSceneController extends GenericController {

    /**
     * used to show that the client didn't reach connection with the server
     */
    @FXML
    private Label serverDownLabel;

    /**
     * used to join and existing game
     */
    @FXML
    public Button joinGameButton;

    /**
     * used to create a new game
     */
    @FXML
    public Button newGameButton;

    /**
     * if newGameButton is clicked, the stage switches scene to the NewGameScene
     *
     * @throws IOException if the fxml file is not found
     */
    public void sendNewGame() throws IOException {
        Gui.getInstance().switchScene("/fxml/NewGameScene.fxml");
    }

    /**
     * if the joinGameButton is clicked, the stage switches scene to JoinGameScene
     *
     * @throws IOException if the fxml file is not found
     */
    public void sendJoinGame() throws IOException {
        Gui.getInstance().switchScene("/fxml/JoinGameScene.fxml");
    }

    /**
     * method implemented from {@link GenericController}, invoked by Gui in order to send a string to a scene controller
     * the string is generally a positive feedback
     *
     * @param ackType the string to be sent
     */
    @Override
    public void receiveOk(String ackType) {
        enableNextScene();
    }

    /**
     * method implemented from {@link GenericController},
     * invoked by Gui when a message is sent by a player in the chat
     *
     * @param chat      the chat to be updated
     * @param minimodel the minimodel to be updated
     */
    @Override
    public void overwriteChat(ClientChat chat, MiniModel minimodel) {

    }

    /**
     * method implemented from {@link GenericController}, invoked by Gui in order to send a string to a generic scene controller,
     * the string is generally a negative feedback, such as an error
     *
     * @param ackType the string to be sent
     */
    @Override
    public void receiveKo(String ackType) {

    }

    /**
     * Enables the interaction with buttons once the server connection is confirmed to be up.
     */
    public void enableNextScene() {
        Platform.runLater(() -> {
            serverDownLabel.setVisible(false);
            newGameButton.setOnMouseClicked(event -> {
                try {
                    sendNewGame();
                } catch (IOException e) {
                    System.out.println("Error while sending new game");
                }
            });
            joinGameButton.setOnMouseClicked(event -> {
                try {
                    sendJoinGame();
                } catch (IOException e) {
                    System.out.println("Error while sending join game");                }
            });
        });
    }

    /**
     * Initializes the controller by enabling the next scene if the server connection is up.
     */
    public void init() {
        if (Gui.getInstance().isServerIsUp()) {
            enableNextScene();
        }
    }

}