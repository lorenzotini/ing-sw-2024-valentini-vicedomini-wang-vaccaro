package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.View.Gui.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;

import java.io.IOException;

/**
 * Controller for the New Game scene where players can choose the number of players for the new game.
 * This scene opens after clicking the "new game" button in ChooseGameNetScene.
 */
public class NewGameSceneController extends GenericController {
    @FXML
    public CheckBox checkBox2;
    @FXML
    public CheckBox checkBox4;
    @FXML
    public CheckBox checkBox3;
    @FXML
    public Button go;
    @FXML
    public Button backButton;
    @FXML
    private String chosenButton;

    /**
     * Sends the chosen number of players to the server and switches to the Login Scene.
     *
     * @throws IOException if an I/O error occurs
     */
    @FXML
    public void sendNumberOfPlayers() throws IOException {
        Gui.getInstance().stringFromSceneController("new");
        Gui.getInstance().stringFromSceneController(chosenButton);
        Gui.getInstance().switchScene("/fxml/LoginScene.fxml");

    }

    /**
     * sends the chosen number of players handling the "enter" key press event.
     *
     * @param checkBox2 the checkbox for 2 players
     */
    private void handleOnKeyPress2(CheckBox checkBox2) {
        checkBox2.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    sendNumberOfPlayers();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                event.consume();
            }
        });
    }

    /**
     * sends the chosen number of players handling the "enter" key press event.
     *
     * @param checkBox3 the checkbox for 3 players
     */
    private void handleOnKeyPress3(CheckBox checkBox3) {
        checkBox3.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    sendNumberOfPlayers();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                event.consume();
            }
        });
    }

    /**
     * sends the chosen number of players handling the "enter" key press event.
     *
     * @param checkBox4 the checkbox for 4 players
     */
    private void handleOnKeyPress4(CheckBox checkBox4) {
        checkBox4.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    sendNumberOfPlayers();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                event.consume();
            }
        });
    }

    /**
     * Handles the event when a player chooses the number of players.
     *
     * @param event the action event triggered by the player
     */
    public void chooseNumber(ActionEvent event) {
        if (event.getSource().equals(checkBox2)) {
            handleOnKeyPress2(checkBox2);
            chosenButton = "2";
            deselectOtherBoxes(checkBox2);
        } else if (event.getSource().equals(checkBox3)) {
            handleOnKeyPress3(checkBox3);
            chosenButton = "3";
            deselectOtherBoxes(checkBox3);
        } else {
            deselectOtherBoxes(checkBox4);
            handleOnKeyPress4(checkBox4);
            chosenButton = "4";
        }
    }

    /**
     * Deselects the checkboxes that are not selected.
     * Does not allow more than one checkbox to be selected at a time.
     *
     * @param checkBox the checkbox that is selected
     */
    public void deselectOtherBoxes(CheckBox checkBox) {
        if (!checkBox.equals(checkBox2)) {
            checkBox2.setSelected(false);
        }
        if (!checkBox.equals(checkBox3)) {
            checkBox3.setSelected(false);
        }
        if (!checkBox.equals(checkBox4)) {
            checkBox4.setSelected(false);
        }
    }

    /**
     * Navigates back to the Choose Game scene when the back button is clicked.
     *
     * @param event the action event triggered by clicking the back button
     * @throws IOException if the Choose Game scene FXML file is not found
     */
    public void previousScene(ActionEvent event) throws IOException {
        Gui.getInstance().switchScene("/fxml/ChooseGameScene.fxml");
    }

    /**
     * Handles receiving a positive acknowledgment (OK) from the server.
     * Processes the ackType to determine the action, such as switching to the Login Scene or handling a reconnection request.
     *
     * @param ackType the acknowledgment type received from the server
     */
    @Override
    public void receiveOk(String ackType) { //shows the game id in the login scene
        LoginSceneController log = (LoginSceneController) Gui.getInstance().getControllerFromName("/fxml/LoginScene.fxml");
        log.getGameIDCreated().setVisible(true);
        log.getGameIDCreated().setText(ackType);
    }


}
