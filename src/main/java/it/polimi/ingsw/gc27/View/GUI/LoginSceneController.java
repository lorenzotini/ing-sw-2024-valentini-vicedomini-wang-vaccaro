package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

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
    public void initialize(){
        blueButton.setDisable(true);
        greenButton.setDisable(true);
        yellowButton.setDisable(true);
        redButton.setDisable(true);
        sendColourButton.setDisable(true);
    }

    @FXML
    public void sendUsername() { //does not check if username is valid
        Gui.getInstance().stringFromSceneController(UsernameInput.getText());
//            blueButton.setDisable(false);
//            greenButton.setDisable(false);
//            yellowButton.setDisable(false);
//            redButton.setDisable(false);
//            sendColourButton.setDisable(false);
        System.out.println("\nsent Username: "+UsernameInput.getText());
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

        Platform.runLater(()->{
            blueButton.setDisable(false);
            greenButton.setDisable(false);
            yellowButton.setDisable(false);
            redButton.setDisable(false);
            sendColourButton.setDisable(false);

            System.out.println("\nreceived Ok");
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            });


    }

    @Override
    public void receiveKo(String ackType) {
        Platform.runLater(()->{ //TODO far aprire un popup con un messaggio passato
            try {
                Gui.getInstance().switchScene("/fxml/ErrorScene.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        //sendUsernameButton.setStyle("-fx-background-color: green");

    }
}
