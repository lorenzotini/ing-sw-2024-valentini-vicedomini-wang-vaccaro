package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

/**
 * The PlayerJoinedMessage class represents a message indicating that a player has joined the game
 * It extends the {@link Message} class and is intended to be received by all players
 */
public class PlayerJoinedMessage extends Message {

    /**
     * constructor matching super {@link Message}
     *
     * @param username player's username
     */
    public PlayerJoinedMessage(String username) {
        super("\n" + username + " joined the game");
    }

    /**
     * Reports the update to the specified VirtualView and View
     * This method will show the message indicating the player has joined the game
     * Every Client connected must receive this update after a player has joined the game
     *
     * @param client The VirtualView to report the update to
     * @param view   The View to report the update to.
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {
        view.showString(this.string);
    }
}
