package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

//fourth scene, sends information about player
public class LoginSceneController implements GenericController {


    public TextField UsernameInput;
    public Button blueButton;

    public Button greenButton;
    public Button yellowButton;
    public Button redButton;
    public Button sendUsernameButton;
    public Button sendColourButton;

    public void sendUsername() { //does not check if username is valid

        Gui.getInstance().stringFromSceneController(UsernameInput.getText());
        blueButton.setDisable(false);
        greenButton.setDisable(false);
        yellowButton.setDisable(false);
        redButton.setDisable(false);
        sendColourButton.setDisable(false);
        blueButton.setStyle("-fx-background-color: blue");
        greenButton.setStyle("-fx-background-color: green");
        yellowButton.setStyle("-fx-background-color: yellow");
        redButton.setStyle("-fx-background-color: red");
        sendColourButton.setStyle("-fx-background-color: grey");


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

}
