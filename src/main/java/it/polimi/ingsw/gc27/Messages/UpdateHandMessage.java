package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

/**
 * The UpdateHandMessage class represents a message updating the hand of a specific player
 * It extends the {@link Message} class and encapsulates the MiniModel containing the player's hand information
 */
public class UpdateHandMessage extends Message {

    /**
     * constructor matching super {@link Message}
     *
     * @param miniModel mini model
     */
    public UpdateHandMessage(MiniModel miniModel) {
        super(miniModel, "Your hand changed.");
    }

    /**
     * Reports the update to the specified VirtualView and View.
     * This method updates the client's MiniModel with the hand and player information from the MiniModel,
     * and then displays the string message and the updated hand on the associated View.
     *
     * @param client The VirtualView to report the update to.
     * @param view   The View to report the update to
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {
        try {
            client.getMiniModel().setHand(this.getMiniModel().getHand());
            client.getMiniModel().setPlayer(this.getMiniModel().getPlayer());
            view.showString(this.string);
            view.show(client.getMiniModel().getHand());
        } catch (RemoteException e) {
            System.out.println("Error while updating player's hand: " + e);
        }
    }
}
