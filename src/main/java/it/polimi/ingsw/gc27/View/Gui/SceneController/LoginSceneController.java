package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.View.Gui.ScenePaths;
import it.polimi.ingsw.gc27.View.Gui.Gui;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * Controller for the Login scene where players can enter their username and choose a pawn colour.
 * This scene opens after clicking the "new game" button in ChooseGameScene.
 * It also handles displaying errors related to username availability and pawn color selection.
 */
public class LoginSceneController extends GenericController {

    /**
     * Text field for entering the username.
     */
    @FXML
    public TextField usernameInput;

    /**
     * Button to select the color blue for the player's pawn.
     */
    @FXML
    public Button blueButton;

    /**
     * Button to select the color green for the player's pawn.
     */
    @FXML
    public Button greenButton;

    /**
     * Button to select the color yellow for the player's pawn.
     */
    @FXML
    public Button yellowButton;

    /**
     * Button to select the color red for the player's pawn.
     */
    @FXML
    public Button redButton;

    /**
     * Button to send the entered username.
     */
    @FXML
    public Button sendUsernameButton;

    /**
     * Button to send the selected pawn color.
     */
    @FXML
    public Button sendColourButton;

    /**
     * Label to display errors related to the entered username.
     */
    @FXML
    public Label errorUsername;

    /**
     * Label to display the ID of the game the player has just created.
     */
    @FXML
    public Label gameIDCreated;

    /**
     * Additional custom label 1.
     */
    @FXML
    public Label customlabel1;

    /**
     * Additional custom label 2.
     */
    @FXML
    public Label customlabel2;

    /**
     * Button to go back to the Choose Game scene.
     */
    @FXML
    public Button sendBackButton;

    /**
     * Stores the selected pawn color.
     */
    private String selectedColour;

    /**
     * Flag indicating whether the username availability has been checked.
     */
    private boolean tried = false;

    /**
     * Stores the game ID entered by the player.
     */
    private Integer gameId = -1;

    /**
     * Sets the game ID entered by the player.
     *
     * @param t the game ID entered as a string
     */
    public void setGameId(String t) {
        try {
            this.gameId = Integer.parseInt(t);
        } catch (Exception e) {
            System.out.println("UAU I didn't thought this was possible");
        }
    }

    /**
     * Returns the label displaying the game ID.
     *
     * @return the label displaying the game ID
     */
    public Label getGameIDCreated() {
        return gameIDCreated;
    }

    /**
     * Initializes the controller by disabling color selection buttons and setting up keyboard event handling for the username input.
     */
    @FXML
    public void initialize() {
        blueButton.setDisable(true);
        greenButton.setDisable(true);
        yellowButton.setDisable(true);
        redButton.setDisable(true);
        sendColourButton.setDisable(true);
        sendBackButton.setVisible(false);
        //sendBackButton.setDisable(true);
        handleOnKeyPress(usernameInput);
    }

    /**
     * allows to send the message in the chat by clicking the "enter" button on the keyboard
     *
     * @param textField the text field to which the event is added
     */
    private void handleOnKeyPress(TextField textField) {
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendUsername();
                textField.clear();
                event.consume();
            }
        });
    }

    /**
     * Sends the entered username to the server via the Gui instance.
     * Checks if the game ID has been entered previously and sends it if so.
     */
    @FXML
    public void sendUsername() {
        if (tried && gameId != -1) {
            Gui.getInstance().stringFromSceneController(gameId.toString());
        }
        Gui.getInstance().stringFromSceneController(usernameInput.getText());
    }

    /**
     * Handles the selection of a pawn color by the player.
     * Disables other color buttons once a color is selected.
     *
     * @param event the mouse event triggered by clicking a color button
     */
    public void selectColour(MouseEvent event) {

        if (event.getSource().equals(blueButton)) {
            selectedColour = "blue";
            blueButton.getStyleClass().add("pressed");
            disableOtherButtons(blueButton);
        } else if (event.getSource().equals(redButton)) {
            selectedColour = "red";
            redButton.getStyleClass().add("pressed");
            disableOtherButtons(redButton);
        } else if (event.getSource().equals(yellowButton)) {
            selectedColour = "yellow";
            yellowButton.getStyleClass().add("pressed");
            disableOtherButtons(yellowButton);
        } else if (event.getSource().equals(greenButton)) {
            selectedColour = "green";
            greenButton.getStyleClass().add("pressed");
            disableOtherButtons(greenButton);
        }

    }

    /**
     * Sends the selected pawn color to the server via the Gui instance.
     */
    public void sendToLobby() {
        if (selectedColour != null) {
            Gui.getInstance().stringFromSceneController(selectedColour);
        }
    }

    /**
     * Disables color buttons other than the pressed button.
     *
     * @param button the button that was selected (and should remain enabled)
     */
    public void disableOtherButtons(Button button) {
        if (!button.equals(redButton)) {
            redButton.setDisable(true);
        }
        if (!button.equals(greenButton)) {
            greenButton.setDisable(true);
        }
        if (!button.equals(blueButton)) {
            blueButton.setDisable(true);
        }
        if (!button.equals(yellowButton)) {
            yellowButton.setDisable(true);
        }
    }

    /**
     * Handles receiving a positive acknowledgment (OK) from the server.
     * Processes the ackType to enable color selection buttons, disable username input, and switch scenes if necessary.
     *
     * @param ackType the acknowledgment type received from the server
     */
    @Override
    public void receiveOk(String ackType) {
        //only shows available pawn colours
        Platform.runLater(() -> {
            if (ackType.contains("Choose your color:")) {
                if (ackType.contains("BLUE"))
                    blueButton.setDisable(false);
                if (ackType.contains("GREEN"))
                    greenButton.setDisable(false);
                if (ackType.contains("YELLOW"))
                    yellowButton.setDisable(false);
                if (ackType.contains("RED"))
                    redButton.setDisable(false);
                sendColourButton.setDisable(false);
                usernameInput.setDisable(true);
                sendUsernameButton.setDisable(true);
                //if the player chooses the wrong username (already chosen) and then the right one, the message error is set to invisible
                errorUsername.setVisible(false);
                customlabel1.setText("Choose the");
                customlabel2.setText("Pawn Colour");
            }
            if (ackType.contains("Game created with id")) {//receives game id, sets it into TextArea of LoginScene
                gameIDCreated.setText(ackType);
                gameIDCreated.setVisible(true);
            }
            if (ackType.contains("playerReconnected")) {
                errorUsername.setText("Welcome back!!!");
                errorUsername.setVisible(true);
                Gui.getInstance().setReconnected(true);
                try {
                    ManuscriptSceneController manuscriptSceneController = (ManuscriptSceneController) Gui.getInstance().getControllerFromName(ScenePaths.MANUSCRIPT.getValue());
                    manuscriptSceneController.init();
                    Gui.getInstance().switchScene("/fxml/ManuscriptScene.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ackType.equals("okColour")) {
                try {
                    Gui.getInstance().switchScene("/fxml/LobbyScene.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Handles receiving a negative acknowledgment (KO) from the server.
     * Processes the ackType to disable color buttons or display an error message related to username availability.
     *
     * @param ackType the acknowledgment type received from the server
     */
    @Override
    public void receiveKo(String ackType) {
        Platform.runLater(() -> {
            if (ackType.equals("koColour")) {
                if (selectedColour.equals("red")) {
                    redButton.setDisable(true);
                }
                if (selectedColour.equals("green")) {
                    greenButton.setDisable(true);
                }
                if (selectedColour.equals("blue")) {
                    blueButton.setDisable(true);
                }
                if (selectedColour.equals("yellow")) {
                    yellowButton.setDisable(true);
                }
            } else {
                tried = true;
                errorUsername.setText("Username not available");
                errorUsername.setVisible(true);
                if (gameId != -1) {
                    sendBackButton.setVisible(true);
                    //sendBackButton.setDisable(false);
                }
            }
        });

    }

    public void goBack(MouseEvent mouseEvent) throws IOException {
        gameId = -1;
        Gui.getInstance().switchScene(ScenePaths.CHOSEGAME.getValue());
    }
}
