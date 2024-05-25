package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
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
    public void prova() throws IOException {
        Gui.changeScene("/fxml/StarterScene.fxml");

    }

}
