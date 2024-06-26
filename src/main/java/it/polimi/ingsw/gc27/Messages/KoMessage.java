package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

/**
 * The KoMessage class represents a negative acknowledgment message in the game
 * It extends the {@link Message} class and implements the reportUpdate method
 */
public class KoMessage extends Message {

    /**
     * constructor matching super {@link Message}
     * @param string string message
     */
    public KoMessage(String string) {
        super(string);
    }

    /**
     * Reports the update to the specified VirtualView and View
     * This method sends a negative acknowledgment
     * @param client The VirtualView to report the update to
     * @param view The View to report the update to
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {
        view.koAck(this.string);
    }

}
