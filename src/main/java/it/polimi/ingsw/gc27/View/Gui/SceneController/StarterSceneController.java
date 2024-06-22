package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.View.Gui.Gui;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;

import java.io.IOException;

/** first scene of the game as the GUI is launched
 *  displays logo and "Play" button
 */
public class StarterSceneController implements GenericController{


    /**
     * method implemented from {@link GenericController},
     * @param chat
     * @param minimodel
     */
    @Override
    public void overwriteChat(ClientChat chat, MiniModel minimodel) {

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
     * method implemented from {@link GenericController}, invoked by Gui in order to send a string to a generic scene controller,
     * the string is generally a negative feedback, such as an error
     * @param ackType
     */
    @Override
    public void receiveKo(String ackType) {

    }

    /**
     * if startGameButton is clicked, the stage switches scene to the ChooseGameScene
     * @param event
     * @throws IOException
     */
    public void switchToChooseGameScene(ActionEvent event) throws IOException {
        Gui.getInstance().switchScene("/fxml/ChooseGameScene.fxml");
    }

}

