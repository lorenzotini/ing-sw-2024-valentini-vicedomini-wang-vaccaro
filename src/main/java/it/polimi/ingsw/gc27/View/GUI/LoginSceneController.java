package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;

import java.io.IOException;



public class LoginSceneController {


    public TextField UsernameInput;
    public Button blueButton;

    public Button greenButton;
    public Button yellowButton;
    public Button redButton;
    public Button sendUsernameButton;
    public Button sendColourButton;

    public void init(){
        blueButton.setDisable(true);
        greenButton.setDisable(true);
        yellowButton.setDisable(true);
        redButton.setDisable(true);
        blueButton.setStyle("-fx-background-color: DeepPink");
        greenButton.setStyle("-fx-background-color: DeepPink");
        yellowButton.setStyle("-fx-background-color: DeepPink");
        redButton.setStyle("-fx-background-color: DeepPink");
    }

    public void sendUsername() { //invia la stringa inserita come username (non fa controlli)

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
        if(event.getSource().equals(blueButton)){
            Gui.getInstance().stringFromSceneController("blue");
        }
        else if(event.getSource().equals(redButton)){ //sistema in button
            Gui.getInstance().stringFromSceneController("red");
        }
        else if(event.getSource().equals(yellowButton)){
            Gui.getInstance().stringFromSceneController("yellow");
        }
        else{
            Gui.getInstance().stringFromSceneController("green");
        }
    }
    public void sendColour() throws IOException {
        Gui.getInstance().switchScene(Gui.getInstance().getStage(),"/fxml/PlaceStarterCardScene.fxml" );
    }



}
