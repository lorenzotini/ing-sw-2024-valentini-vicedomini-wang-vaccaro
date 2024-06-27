package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

/**
 * The SuspendedGameMessage class represents a message indicating that the game has been suspended
 * It extends the {@link Message} class and a string message
 */
public class SuspendedGameMessage extends Message {

    /**
     * constructor matching super {@link Message}
     *
     * @param string string message
     */
    public SuspendedGameMessage(String string) {
        super(string);
    }

    /**
     * Reports the update to the specified VirtualView and View
     * This method triggers the suspension of the game
     *
     * @param client The VirtualView to report the update to.
     * @param view   The View to report the update to.
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {
        view.suspendedGame(this.string);
    }
}
