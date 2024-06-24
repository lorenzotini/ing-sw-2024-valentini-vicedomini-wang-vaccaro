package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;

/**
 * An abstract class representing a generic controller.
 * This class provides methods to handle various actions
 * such as receiving acknowledgments, overwriting chat,
 * handling player reconnections, and suspending the game.
 *
 * All methods in this class are empty and should be overridden
 * in subclasses to provide specific functionality.
 */
public abstract class GenericController {
    /**
     * Receives an acknowledgment of a specified type.
     * <p>
     * This method is empty and should be overridden in a subclass.
     * </p>
     *
     * @param ackType the type of acknowledgment received
     */
    public void receiveOk(String ackType) {}

    /**
     * Overwrites the chat with the specified client chat and mini model.
     * <p>
     * This method is empty and should be overridden in a subclass.
     * </p>
     *
     * @param chat the client chat to be overwritten
     * @param minimodel the mini model associated with the chat
     */
    public void overwriteChat(ClientChat chat, MiniModel minimodel) {}

    /**
     * Handles the event when another player reconnects to the game.
     * <p>
     * This method is empty and should be overridden in a subclass.
     * </p>
     */
    public void otherPlayerReconnected() {}

    /**
     * Receives a negative acknowledgment of a specified type.
     * <p>
     * This method is empty and should be overridden in a subclass.
     * </p>
     *
     * @param ackType the type of negative acknowledgment received
     */
    public void receiveKo(String ackType) {}

    /**
     * Suspends the game.
     * <p>
     * This method is empty and should be overridden in a subclass.
     * </p>
     */
    public void suspendeGame() {}
}
