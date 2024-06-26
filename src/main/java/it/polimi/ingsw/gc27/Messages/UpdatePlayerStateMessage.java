package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

/**
 * The UpdatePlayerStateMessage class represents a message updating the state of a player
 * It extends the {@link Message} class and encapsulates the MiniModel containing the updated player state.
 * This message is intended to update the state of a player and acknowledge the update.
 */
public class UpdatePlayerStateMessage extends Message {

    /**
     * constructor matching super {@link Message}
     * @param miniModel mini model
     */
    public UpdatePlayerStateMessage(MiniModel miniModel) {
        super(miniModel, miniModel.getPlayer().getPlayerState().toString());
    }

    /**
     * Reports the update to the specified VirtualView and View.
     * This method updates the client's MiniModel with the player from the MiniModel,
     * and then acknowledges the update with an OK acknowledgment message on the associated View.
     * @param client The VirtualView to report the update to.
     * @param view The View to report the update to.
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {
        try{
            client.getMiniModel().setPlayer(this.getMiniModel().getPlayer());
            view.okAck(string);
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }
}
