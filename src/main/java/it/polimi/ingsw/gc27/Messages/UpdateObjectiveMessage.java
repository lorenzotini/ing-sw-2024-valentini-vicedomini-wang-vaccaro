package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

/**
 * The UpdateObjectiveMessage class represents a message updating the player's secret objective choice.
 * It extends the {@link Message} class and encapsulates the MiniModel containing the updated player's information.
 * This message is intended to inform the player that they have chosen their secret objective.
 */
public class UpdateObjectiveMessage extends Message {

    /**
     * constructor matching super {@link Message}
     *
     * @param miniModel mini model
     */
    public UpdateObjectiveMessage(MiniModel miniModel) {
        super(miniModel, "You chose your secret objective!");
    }

    /**
     * Reports the update to the specified VirtualView and View
     * This method updates the client's MiniModel with the player's information from the MiniModel,
     * and then displays a message indicating that the player has chosen their secret objective
     *
     * @param client The VirtualView to report the update to.
     * @param view   The View to report the update to.
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {

        try {
            client.getMiniModel().setPlayer(this.getMiniModel().getPlayer());
            view.showString(this.string);
        } catch (RemoteException e) {
            System.out.println("Error while updating secret objective");
        }
    }
}
