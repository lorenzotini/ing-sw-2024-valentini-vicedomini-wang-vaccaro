package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.View.Gui.Gui;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * Controller for the Join Game scene where players can enter a game ID to join an existing game.
 * This scene opens after clicking the "join game" button in ChooseGameNetScene.
 */
public class JoinGameSceneController extends GenericController {
    /**
     * Label to display errors related to the entered game ID.
     */
    @FXML
    public Label errorGameID;

    /**
     * Text field for entering the game ID.
     */
    @FXML
    public TextField idTextField;

    /**
     * Button to send the entered game ID.
     */
    @FXML
    public Button sendGameIdButton;

    /**
     * Button to navigate back to the Choose Game scene.
     */
    @FXML
    public Button backButton;

    private String tempId;

    /**
     * Initializes the scene by setting the visibility of the back button to false and handling the "enter" key press event.
     */
    @FXML
    public void initialize() {
        backButton.setVisible(false);
        handleOnKeyPress(idTextField);
    }

    /**
     * Allows to send the message in the chat by clicking the "enter" button on the keyboard
     *
     * @param textField the text field to which the event is added
     */
    private void handleOnKeyPress(TextField textField) {
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendGameId();
                textField.clear();
                event.consume();
            }
        });
    }

    /**
     * Sends the entered game ID to the server via the Gui instance.
     * Stores the entered game ID in tempId for future reference.
     */
    public void sendGameId() {
        playClickMusic("/music/click.mp3");
        Gui.getInstance().stringFromSceneController(idTextField.getText());
        tempId = idTextField.getText();
    }

    /**
     * Navigates back to the Choose Game scene when the back button is clicked.
     *
     * @param event the mouse event triggered by clicking the back button
     * @throws IOException if the Choose Game scene FXML file is not found
     */
    public void previousScene(MouseEvent event) throws IOException {
        Gui.getInstance().switchScene("/fxml/ChooseGameScene.fxml");
    }

    /**
     * Handles receiving a positive acknowledgment (OK) from the server.
     * Processes the ackType to determine the action, such as switching to the Login Scene or handling a reconnection request.
     *
     * @param ackType the acknowledgment type received from the server
     */
    @Override
    public void receiveOk(String ackType) {

        Platform.runLater(() -> {
            if (ackType.equals("validID")) {
                try {
                    errorGameID.setVisible(false);
                    Gui.getInstance().switchScene("/fxml/LoginScene.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (ackType.equals("disconnectedPlayer")) {
                try {
                    LoginSceneController loginContr = (LoginSceneController) Gui.getInstance().getControllerFromName("/fxml/LoginScene.fxml");
                    loginContr.getGameIDCreated().setText("This game has a disconnected player. Are you him? If so, please enter your username.");
                    loginContr.getGameIDCreated().setVisible(true);
                    loginContr.setGameId(tempId);
                    Gui.getInstance().switchScene("/fxml/LoginScene.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("\nMESSAGE NOT FOUND\n");
            }
        });
    }


    /**
     * Handles receiving a negative acknowledgment (KO) from the server.
     * Processes the ackType to display an error message related to the game ID entered.
     *
     * @param ackType the acknowledgment type received from the server
     */
    @Override
    public void receiveKo(String ackType) {
        Platform.runLater(() -> {
            backButton.setVisible(true);
            if (ackType.equals("invalidID")) {
                errorGameID.setText("THIS GAME ID DOES NOT EXIST! ASK YOUR FRIENDS AGAIN IF YOU WANT TO JOIN THEIR GAME");
                errorGameID.setVisible(true);
            } else if (ackType.equals("gameFull")) {
                errorGameID.setText("THIS GAME IS FULL! ");
                errorGameID.setVisible(true);
            } else if (ackType.equals("invalidFormatID")) {
                errorGameID.setText("INVALID FORMAT: PLEASE INSERT A NUMBER");
                errorGameID.setVisible(true);
            }
        });
    }

}
