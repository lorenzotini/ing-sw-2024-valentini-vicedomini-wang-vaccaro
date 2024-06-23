package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;

/**
 * generic interface of all scenes displayed in GUI
 */
public abstract class GenericController {
    /**
     * invoked by Gui in order to send a string to a generic scene controller,
     * the string is generally a positive feedback
     * @param ackType
     */
    public void receiveOk(String ackType){}

    /**
     * invoked by Gui when a message is sent by a player in the chat
     * @param chat
     * @param minimodel
     */
    public void overwriteChat(ClientChat chat, MiniModel minimodel){}

    public void reconnectPlayer(){};
    /**
     * invoked by Gui in order to send a string to a generic scene controller,
     * the string is generally a negative feedback, such as an error
     * @param ackType
     */
    public void receiveKo(String ackType){}

    public void suspendeGame(){}
}
