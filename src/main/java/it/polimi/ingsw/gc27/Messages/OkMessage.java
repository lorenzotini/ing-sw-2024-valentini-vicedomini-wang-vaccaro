package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

/**
 * The OkMessage class represents a message indicating a successful operation
 * It extends the {@link Message} class and implements the reportUpdate method
 */
public class OkMessage extends Message {

    /**
     * constructor matching super {@link Message}
     *
     * @param string string message
     */
    public OkMessage(String string) {
        super(string);
    }

    /**
     * Reports the update to the specified VirtualView and View
     * This method sends a positive acknowledgment of a successful operation to the view
     *
     * @param client The VirtualView to report the update to
     * @param view   The View to report the update to
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {
        view.okAck(this.string);
    }

}
