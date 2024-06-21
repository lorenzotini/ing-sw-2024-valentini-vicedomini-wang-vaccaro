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
public class StarterSceneController implements GenericController{
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

    /**
     * method implemented from {@link GenericController}, invoked by Gui in order to send a string to a scene controller
     * the string is generally a positive feedback
     * @param ackType
     */
    @Override
    public void receiveOk(String ackType) {

    }

    /**
     * method implemented from {@link GenericController},
     * @param chat
     * @param minimodel
     */
    @Override
    public void overwriteChat(ClientChat chat, MiniModel minimodel) {

    }

    /**
     * method implemented from {@link GenericController}, invoked by Gui in order to send a string to a generic scene controller,
     * the string is generally a negative feedback, such as an error
     * @param ackType
     */
    @Override
    public void receiveKo(String ackType) {

    }
}

