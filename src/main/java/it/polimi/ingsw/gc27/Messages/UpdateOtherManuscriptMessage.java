package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

/**
 * The UpdateOtherManuscriptMessage class represents a message updating the manuscript of another player
 * It extends the {@link Message} class and encapsulates the MiniModel containing the updated manuscripts mapping and board
 * This message is intended to update the manuscripts of another player and the game board.
 */
public class UpdateOtherManuscriptMessage extends Message {

    /**
     * constructor matching super {@link Message}
     *
     * @param miniModel mini model
     */
    public UpdateOtherManuscriptMessage(MiniModel miniModel) {
        super(miniModel);
    }

    /**
     * Reports the update to the specified VirtualView and View
     * This method updates the client's MiniModel with the manuscripts map and board from the MiniModel,
     * and then updates the manuscript of another player and displays it
     *
     * @param client The VirtualView to report the update to.
     * @param view   The View to report the update to
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {
        try {
            client.getMiniModel().setManuscriptsMap(miniModel.getManuscriptsMap());
            view.updateManuscriptOfOtherPlayer(client.getMiniModel().getPlayer().getManuscript());
            client.getMiniModel().setBoard(miniModel.getBoard());
            view.show(client.getMiniModel().getBoard());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}

