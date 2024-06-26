package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

/**
 * The LastRoundMessage class represents a message indicating the last round of the game
 * It extends the {@link Message} class and implements the reportUpdate method
 */
public class LastRoundMessage extends Message{

    /**
     * constructor matching super {@link Message}
     * @param miniModel the moni model
     * @param string string message
     */
    public LastRoundMessage(MiniModel miniModel, String string) {
        super(miniModel, string);
    }

    /**
     * Reports the update to the specified VirtualView and View
     * This method sends a negative acknowledgment and displays the message
     * the last turn is displayed as a warning therefore the usage of a negative acknowledgment
     * @param client The VirtualView to report the update to
     * @param view The View to report the update to
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {
        view.koAck(string);
        view.showString(string);
    }
}
