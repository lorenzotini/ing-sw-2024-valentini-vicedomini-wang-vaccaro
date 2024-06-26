package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.*;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

/**
 * The UpdateEndGameMessage class represents a message updating the end game state
 * It extends the {@link Message} class and wraps the board in the MiniModel
 */
public class UpdateEndGameMessage extends Message{

    /**
     * constructor matching super {@link Message}
     * @param miniModel mini model
     */
    public UpdateEndGameMessage(MiniModel miniModel) {
        super(miniModel);
    }

    /**
     * Reports the update to the specified VirtualView and View
     * This method updates the client's MiniModel board and displays the winners on the associated View
     * @param client The VirtualView to report the update to.
     * @param view The View to report the update to.
     * @throws RuntimeException If there is a RemoteException or NullPointerException while updating the MiniModel or showing the winners
     */
    @Override
    public void reportUpdate(VirtualView client, View view)  {

        try {
            client.getMiniModel().setBoard(this.miniModel.getBoard());
            view.showWinners();
        }catch(RemoteException | NullPointerException e){
            throw new RuntimeException(e);
        }
    }
}
