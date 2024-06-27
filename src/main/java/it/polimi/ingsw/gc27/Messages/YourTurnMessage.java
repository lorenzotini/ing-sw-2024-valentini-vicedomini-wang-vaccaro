package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

/**
 * The YourTurnMessage class represents a message indicating that it is the player's turn to play
 * It extends the {@link Message} class and encapsulates the MiniModel containing the player's state
 * and a string message notifying a player that it is their turn to take action in the game
 */
public class YourTurnMessage extends Message {

    /**
     * constructor matching super {@link Message}
     *
     * @param miniModel mini model
     * @param string    string message
     */
    public YourTurnMessage(MiniModel miniModel, String string) {
        super(miniModel, string);
    }

    /**
     * Reports the update to the specified VirtualView and View
     * This method shows a string message indicating that it is the player's turn to play
     *
     * @param client The VirtualView to report the update to.
     * @param view   The View to report the update to.
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {
        view.showString("It's your turn to play");
    }

}
