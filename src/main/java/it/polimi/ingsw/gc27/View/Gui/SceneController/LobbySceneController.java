package it.polimi.ingsw.gc27.View.Gui.SceneController;

//fifth scene
//waiting for other players to join, will be displayed when player is  waiting for other players to join the game
import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;

/** fifth scene of initialization,
 * will be displayed when player is  waiting for other players to join the game
 * */
public class LobbySceneController implements GenericController{
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
