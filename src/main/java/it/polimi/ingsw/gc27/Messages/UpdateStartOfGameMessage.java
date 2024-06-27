package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

/**
 * The UpdateStartOfGameMessage class represents a message indicating the start of the game
 * It extends the {@link Message} class and encapsulates the MiniModel containing the initial state of the game
 * and a string message warning other player's of the start
 * This message is intended to update the client's MiniModel with the initial game state at the start of the game.
 */
public class UpdateStartOfGameMessage extends Message {

    /**
     * constructor matching super {@link Message}
     *
     * @param miniModel mini model
     * @param string    string message
     */
    public UpdateStartOfGameMessage(MiniModel miniModel, String string) {
        super(miniModel, string);
    }

    /**
     * Reports the update to the specified VirtualView and View.
     * This method updates the client's MiniModel with the MiniModel from the message,
     * which contains the initial game state at the start of the game.
     *
     * @param client The VirtualView to report the update to.
     * @param view   The View to report the update to.
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {
        try {
            client.getMiniModel().copy(this.getMiniModel());
        } catch (RemoteException e) {
            System.out.println("Error while reporting the start of the game");
        }
    }
}
