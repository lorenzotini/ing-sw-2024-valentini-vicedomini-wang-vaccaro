package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

/**
 * The StringMessage class represents a generic string message, mainly used for socket communication.
 * It extends the {@link Message} class and encapsulates a string message.
 */
public class StringMessage extends Message{

    /**
     * constructor matching super {@link Message}
     * @param string string message
     */
    public StringMessage(String string) {
        super(string);
    }

    /**
     * Reports the update to the specified VirtualView and View
     * This method displays the generic string message on the associated View
     * @param client The VirtualView to report the update to.
     * @param view The View to report the update to
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {
        view.showString(this.string);
    }

}
