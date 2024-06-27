package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

/**
 * The PingMessage class represents a message used for ping operations
 * It extends the {@link Message} class but does not perform any specific action
 * in the {@link #reportUpdate(VirtualView, View)} method
 */
public class PingMessage extends Message {

    /**
     * constructor matching super {@link Message}
     */
    public PingMessage() {
        super("forza napoli");
    }

    /**
     * This method does not perform any actions intentionally, the implementation
     * does not require any update to VirtualView nor View
     *
     * @param client The VirtualView to report the update to.
     * @param view   The View to report the update to.
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {

    }
}
