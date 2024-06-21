package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Model.Game.ChatMessage;

/**
 * generic interface of all scenes displayed in GUI
 */
public interface GenericController {
    /**
     * invoked by Gui in order to send a string to a generic scene controller,
     * the string is generally a positive feedback
     * @param ackType
     */
    void receiveOk(String ackType);

    /**
     * invoked by Gui when a message is sent by a player in the chat
     * @param chat
     * @param minimodel
     */
    void overwriteChat(ClientChat chat, MiniModel minimodel);

    /**
     * invoked by Gui in order to send a string to a generic scene controller,
     * the string is generally a negative feedback, such as an error
     * @param ackType
     */
    void receiveKo(String ackType);
}
