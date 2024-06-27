package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;
import java.io.Serializable;


/**
 * The abstract class Message serves as the base class for all message types in the game.
 * It implements the Serializable interface for object serialization.
 * Each subclass of Message will have different parameters of the MiniModel set.
 * Specified params set noted in every class
 */
public abstract class Message implements Serializable {

    protected final MiniModel miniModel;
    protected final String string;

    /**
     * Constructs a Message with the specified MiniModel and string {@link MiniModel}
     *
     * @param miniModel The MiniModel associated with the message
     * @param string    The string associated with the message
     */
    protected Message(MiniModel miniModel, String string) {
        this.miniModel = miniModel;
        this.string = string;
    }

    /**
     * Constructs a Message with the specified MiniModel {@link MiniModel}
     *
     * @param miniModel The MiniModel associated with the message
     */
    protected Message(MiniModel miniModel) {
        this.miniModel = miniModel;
        this.string = null;
    }

    /**
     * Constructs a Message with the specified string message
     *
     * @param string The string associated with the message
     */
    protected Message(String string) {
        this.string = string;
        this.miniModel = null;
    }

    /**
     * Gets the MiniModel associated with the message {@link MiniModel}
     *
     * @return The MiniModel associated with the message
     */
    public MiniModel getMiniModel() {
        return miniModel;
    }

    /**
     * Abstract method to report updates to the VirtualView and View
     *
     * @param client The VirtualView to report to
     * @param view   The View to report the update to
     */
    public abstract void reportUpdate(VirtualView client, View view);

    /**
     * Gets the string associated with the message
     *
     * @return The string associated with the message
     */
    public String getString() {
        return this.string;
    }

}
