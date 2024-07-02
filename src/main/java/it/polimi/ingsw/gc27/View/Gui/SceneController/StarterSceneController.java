package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.View.Gui.Gui;
import it.polimi.ingsw.gc27.View.Gui.MainApp;
import it.polimi.ingsw.gc27.View.Gui.ScenePaths;
import javafx.event.ActionEvent;

import java.io.IOException;

/**
 * first scene of the game as the GUI is launched
 * displays logo and "Play" button
 */
public class StarterSceneController extends GenericController {


    /**
     * method implemented from {@link GenericController},
     *
     * @param chat      the chat to be displayed
     * @param minimodel the minimodel to be displayed
     */
    @Override
    public void overwriteChat(ClientChat chat, MiniModel minimodel) {

    }

    /**
     * method implemented from {@link GenericController}, invoked by Gui in order to send a string to a scene controller
     * the string is generally a positive feedback
     *
     * @param ackType the string to be sent
     */
    @Override
    public void receiveOk(String ackType) {
        ChooseGameSceneController controller = (ChooseGameSceneController) Gui.getInstance().getControllerFromName(ScenePaths.CHOOSEGAME.getValue());
        controller.enableNextScene();
    }

    /**
     * method implemented from {@link GenericController}, invoked by Gui in order to send a string to a generic scene controller,
     * the string is generally a negative feedback, such as an error
     *
     * @param ackType the string to be sent
     */
    @Override
    public void receiveKo(String ackType) {

    }

    /**
     * if startGameButton is clicked, the stage switches scene to the ChooseGameScene
     *
     * @param event the event of the button clicked
     * @throws IOException if an I/O error occurs
     */
    public void switchToChooseGameScene(ActionEvent event) throws IOException {
        playClickMusic("/music/click.mp3");
        ChooseGameSceneController chooseGameSceneController = (ChooseGameSceneController) Gui.getInstance().getControllerFromName(ScenePaths.CHOOSEGAME.getValue());
        chooseGameSceneController.init();
        Gui.getInstance().switchScene("/fxml/ChooseGameScene.fxml");
        MainApp.startMusic("/music/CoC_main_theme.mp3", true);
    }

}
