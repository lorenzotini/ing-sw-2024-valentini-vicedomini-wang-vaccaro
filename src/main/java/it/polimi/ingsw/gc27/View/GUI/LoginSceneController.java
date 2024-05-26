package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class LoginSceneController {


    public TextField UsernameInput;
    public Button blueButton;
    public Button greenButton;
    public Button yellowButton;
    public Button redColour;
    public Button sendUsernameButton;
    public Button sendColourButton;

    public void sendUsername() {
        Gui.getInstance().stringFromSceneController(UsernameInput.getText());
    }

    public void sendColour() throws IOException {
        Gui.getInstance().switchScene(Gui.getInstance().getStage(),"/fxml/PlaceStarterCardScene.fxml" );
    }


    public void selectColour(ActionEvent event) {
        if(event.getSource().equals(blueButton)){
            Gui.getInstance().stringFromSceneController("blue");
        }
        else if(event.getSource().equals(redColour)){ //sistema in button
            Gui.getInstance().stringFromSceneController("red");
        }
        else if(event.getSource().equals(yellowButton)){
            Gui.getInstance().stringFromSceneController("yellow");
        }
        else{
            Gui.getInstance().stringFromSceneController("green");
        }
    }
}
