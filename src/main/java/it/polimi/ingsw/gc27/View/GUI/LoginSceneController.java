package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.NumberFormat;

//fourth scene, sends information about player
public class LoginSceneController implements GenericController {
    @FXML
    public TextField UsernameInput;
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
    }

    @FXML
    public void sendUsername() { //does not check if username is valid
        Gui.getInstance().stringFromSceneController(UsernameInput.getText());
    }

    public void selectColour(ActionEvent event) {
        if (event.getSource().equals(blueButton)) {
            Gui.getInstance().stringFromSceneController("blue");
        } else if (event.getSource().equals(redButton)) {
            Gui.getInstance().stringFromSceneController("red");
        } else if (event.getSource().equals(yellowButton)) {
            Gui.getInstance().stringFromSceneController("yellow");
        } else {
            Gui.getInstance().stringFromSceneController("green");
        }
    }

    public void sendToLobby() throws IOException {
        Gui.getInstance().switchScene("/fxml/LobbyScene.fxml");
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
                //if the player chooses the wrong username (already chosen) and then the right one, the message error is set to invisible
                errorUsername.setVisible(false);
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
