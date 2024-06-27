package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

/**
 * The UpdateMarketMessage class represents a message updating the market for all players
 * It extends the {@link Message} class and wraps the updated market information in the Minimodel
 */
public class UpdateMarketMessage extends Message {

    /**
     * constructor matching super {@link Message}
     *
     * @param miniModel mini model
     */
    public UpdateMarketMessage(MiniModel miniModel) {
        super(miniModel, "The Market has been updated!");
    }

    /**
     * Reports the update to the specified VirtualView and View
     * This method updates the client's MiniModel with the market information from the message's MiniModel
     * and then displays the updated market on the View
     *
     * @param client The VirtualView to report the update to.
     * @param view   The View to report the update to.
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {
        try {
            client.getMiniModel().setMarket(this.getMiniModel().getMarket());
            view.show(client.getMiniModel().getMarket());

        } catch (RemoteException e) {
            System.out.println("Error while updating market: " + e);
        }
    }
}
