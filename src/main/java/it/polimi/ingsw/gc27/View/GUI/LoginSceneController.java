package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.View.Gui;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.IOException;

//fourth scene, sends information about player
public class LoginSceneController implements GenericController {
    @FXML
    public TextField usernameInput;
    @FXML
    public Button blueButton;

    @FXML
    public Button greenButton;
    @FXML
    public Button yellowButton;
    @FXML
    public Button redButton;
    @FXML
    public Button sendUsernameButton;
    @FXML
    public Button sendColourButton;
    @FXML
    public TextField errorUsername;

    @FXML
    public TextArea gameIDCreated;//displays the ID of the game the player has just created
    @FXML
    public Label customlabel1;
    @FXML
    public Label customlabel2;


    public TextArea getGameIDCreated() {
        return gameIDCreated;
    }

    public TextField getErrorUsername() {
        return errorUsername;
    }

    public void setGameIDCreated(TextArea gameIDCreated) {
        this.gameIDCreated = gameIDCreated;
    }


    @FXML
    public void initialize() {
        blueButton.setDisable(true);
        greenButton.setDisable(true);
        yellowButton.setDisable(true);
        redButton.setDisable(true);
        sendColourButton.setDisable(true);
        handleOnKeyPress(usernameInput);
        handleOnKeyPressRed(redButton);
        handleOnKeyPressBlue(blueButton);
        handleOnKeyPressGreeen(greenButton);
        handleOnKeyPressYellow(yellowButton);
    }

    private void handleOnKeyPress(TextField textField) {
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendUsername();
                textField.clear();
                event.consume();
            }
        });
    }

    // does not work at the moment (allows you to click the colour button and send it with "enter" on the keyboard)
    private void handleOnKeyPressRed(Button redButton) {
        redButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    sendToLobby();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                event.consume();
            }
        });
    }

    private void handleOnKeyPressBlue(Button blueButton) {
        blueButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    sendToLobby();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                event.consume();
            }
        });
    }

    private void handleOnKeyPressYellow(Button yellowButton) {
        yellowButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    sendToLobby();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                event.consume();
            }
        });
    }

    private void handleOnKeyPressGreeen(Button greenButton) {
        greenButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    sendToLobby();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                event.consume();
            }
        });
    }

    @FXML
    public void sendUsername() {
        Gui.getInstance().stringFromSceneController(usernameInput.getText());
    }

    public void selectColour(ActionEvent event) {
        if (event.getSource().equals(blueButton)) {
            Gui.getInstance().stringFromSceneController("blue");
            blueButton.getStyleClass().add("pressed");
            disableOtherButtons(blueButton);
        } else if (event.getSource().equals(redButton)) {
            Gui.getInstance().stringFromSceneController("red");
            redButton.getStyleClass().add("pressed");
            disableOtherButtons(redButton);
        } else if (event.getSource().equals(yellowButton)) {
            Gui.getInstance().stringFromSceneController("yellow");
            yellowButton.getStyleClass().add("pressed");
            disableOtherButtons(yellowButton);
        } else {
            Gui.getInstance().stringFromSceneController("green");
            greenButton.getStyleClass().add("pressed");
            disableOtherButtons(greenButton);
        }
    }

    public void sendToLobby() throws IOException {
        Gui.getInstance().switchScene("/fxml/LobbyScene.fxml");
    }

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
                gameIDCreated.setVisible(true);//forse da togliere(?)
                gameIDCreated.setText(ackType);
                //todo: non funziona più dopo a ver messo la resilienza
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
            if (ackType.equals("notReconnected")) {
                try {
                    usernameInput.setText("");
                    Gui.getInstance().switchScene("/fxml/ChooseGameScene.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void overwriteChat(ClientChat chat, MiniModel minimodel) {

    }

    @Override
    public void receiveKo(String ackType) {
        if (ackType.equals("playerReconnected")) {
            Platform.runLater(() -> {
                errorUsername.setText("Username not available");
                errorUsername.setVisible(true);
            });
        } else if (ackType.equals("notReconnected")) {
            Platform.runLater(() -> {
                try {
                    Gui.getInstance().switchScene("/fxml/ChooseGameScene");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}