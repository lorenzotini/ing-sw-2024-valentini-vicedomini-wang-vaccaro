package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

/**
 * The ReconnectedPlayerMessage class represents a message indicating that a player has reconnected to the game.
 * It extends the {@link Message} class and includes a mini model and a string message.
 */
public class ReconnectedPlayerMessage extends Message {

    /**
     * constructor matching super {@link Message}
     *
     * @param miniModel Mini model
     * @param string    string message
     */
    public ReconnectedPlayerMessage(MiniModel miniModel, String string) {
        super(miniModel, string);
    }

    /**
     * Reports the update to the specified VirtualView and View
     * This method copies the mini model to the client's mini model and display the reconnection message
     *
     * @param client The VirtualView to report the update to.
     * @param view   The View to report the update to.
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {

        try {
            client.getMiniModel().copy(this.getMiniModel());
        } catch (RemoteException e) {
            System.out.println("Error while reconnecting player");
        }
        view.showString(string);
    }
}
