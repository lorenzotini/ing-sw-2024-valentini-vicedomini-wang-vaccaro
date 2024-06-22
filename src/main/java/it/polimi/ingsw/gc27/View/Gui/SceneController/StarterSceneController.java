package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.View.Gui.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

/** first scene of the game as the GUI is launched
 *  displays logo and "Play" button
 */
public class StarterSceneController extends GenericController{
    /** used to start the game */
    @FXML
    public Button startGameButton;

    /**
     * if startGameButton is clicked, the stage switches scene to the ChooseGameScene
     * @param event
     * @throws IOException
     */
    @FXML
    public void switchToChooseGameScene(ActionEvent event) throws IOException {
        Gui.getInstance().switchScene("/fxml/ChooseGameScene.fxml");
    }

}

