package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

//this scene will open after button "new game" (in ChooseGameNetScene) clicked,
//and number of players will be asked
public class NewGameSceneController implements GeneralSceneController {
    private Gui gui;

     @FXML
     private Button go;


    @Override
    public void setGui(Gui gui) {
        this.gui=gui;
    }
    @FXML
    public void sendLoginScene() throws IOException {
        Gui.getInstance().switchScene(Gui.getInstance().getStage(), "/fxml/StarterScene.fxml");

    }

    public void send2Players() {
        Gui.getInstance().stringFromSceneController("2");
    }

    public void send3Players() {
        Gui.getInstance().stringFromSceneController("3");
    }
    public void send4Players() {
        Gui.getInstance().stringFromSceneController("4");
    }


}
