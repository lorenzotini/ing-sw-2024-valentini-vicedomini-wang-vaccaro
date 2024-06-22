package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.View.Gui.ScenePaths;
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

//fourth scene, sends information about player
public class LoginSceneController extends GenericController {
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
    private String selectedColour;



    public TextArea getGameIDCreated() {
        return gameIDCreated;
    }
    public TextField getErrorUsername() {return errorUsername;}
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
    }
    /**
     * allows to send the message in the chat by clicking the "enter" button on the keyboard
     * @param textField
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

    // does not work at the moment (allows you to click the colour button and send it with "enter" on the keyboard)


    @FXML
    public void sendUsername() {
        Gui.getInstance().stringFromSceneController(usernameInput.getText());
    }

    public void selectColour(MouseEvent event) {
        if (event.getSource().equals(blueButton)) {
            selectedColour="blue";
            blueButton.getStyleClass().add("pressed");
            disableOtherButtons(blueButton);
        } else if (event.getSource().equals(redButton)) {
            selectedColour="red";
            redButton.getStyleClass().add("pressed");
            disableOtherButtons(redButton);
        } else if (event.getSource().equals(yellowButton)) {
            selectedColour="yellow";
            yellowButton.getStyleClass().add("pressed");
            disableOtherButtons(yellowButton);
        } else if(event.getSource().equals(greenButton)){
            selectedColour="green";
            greenButton.getStyleClass().add("pressed");
            disableOtherButtons(greenButton);
        }

    }

    public void sendToLobby() throws IOException {
        if (selectedColour != null) {
            Gui.getInstance().stringFromSceneController(selectedColour);
            Gui.getInstance().switchScene("/fxml/LobbyScene.fxml");
        }

    }

    public void disableOtherButtons(Button button){
        if(!button.equals(redButton)){
            redButton.setDisable(true);
        }
        if(!button.equals(greenButton)){
            greenButton.setDisable(true);
        }
        if(!button.equals(blueButton)){
            blueButton.setDisable(true);
        }
        if(!button.equals(yellowButton)){
            yellowButton.setDisable(true);
        }
    }

    @Override
    public void receiveOk(String ackType) {
        //only shows available pawn colours
        Platform.runLater(() -> {
            if(ackType.contains("Choose your color:")) {
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
            if(ackType.contains("Game created with id")) {//receives game id, sets it into TextArea of LoginScene
                gameIDCreated.setVisible(true);//forse da togliere(?)
                gameIDCreated.setText(ackType);
                //todo: non funziona più dopo a ver messo la resilienza
            }
            if(ackType.contains("playerReconnected")){
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
        });
    }



    @Override
    public void receiveKo(String ackType) {
        Platform.runLater(() -> {
            errorUsername.setText("Username not available");
            errorUsername.setVisible(true);
        });

    }
}
