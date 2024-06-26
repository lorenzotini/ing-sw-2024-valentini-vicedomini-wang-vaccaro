package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

/**
 * The GenericErrorMessage class represents an error message in the game
 * It extends the {@link Message} class and implements the reportUpdate method
 * In this case the Minimodel's instance only has the currentPlayer and the string set
 * The currentPlayer is the player that has to receive the message
 */
public class GenericErrorMessage extends Message{

    /**
     * constructor matching super {@link Message}
     * @param errorMessage error message
     * @param currentPlayer current player in mini model
     */
    public GenericErrorMessage(String errorMessage, MiniModel currentPlayer) {
        super(currentPlayer, errorMessage);
    }

    /**
     * Reports the update to the specified VirtualView and View
     * This method displays the error message and sends an error acknowledgment
     * @param client The VirtualView to report the update to
     * @param view The View to report the update to
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {

        view.koAck("invalid action: " + this.string);
    }

}
