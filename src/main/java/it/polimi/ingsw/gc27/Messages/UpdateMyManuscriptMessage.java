package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

/**
 * The UpdateMyManuscriptMessage class represents a message updating the player's manuscript
 * It extends the {@link Message} class and encapsulates the MiniModel containing the updated player's manuscript information,
 * the player whose manuscript is being updated and the string set
 * This message is intended to be received only by the player whose manuscript has been updated
 */
public class UpdateMyManuscriptMessage extends Message {

    /**
     * constructor matching super {@link Message}
     * @param miniModel mini model
     */
    public UpdateMyManuscriptMessage(MiniModel miniModel) {
        super(miniModel, "Something changed in your manuscript!");
    }

    /**
     * Reports the update to the specified VirtualView and View
     * This method updates the client's MiniModel with the player's manuscript information from the MiniModel,
     * then displays the updated manuscript and board on the associated View
     * @param client The VirtualView to report the update to
     * @param view The View to report the update to
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {
        try{
            client.getMiniModel().setPlayer(miniModel.getPlayer());
            client.getMiniModel().setManuscript(miniModel.getPlayer().getManuscript());
            client.getMiniModel().setManuscriptsMap(miniModel.getManuscriptsMap());
            client.getMiniModel().setBoard(miniModel.getBoard());
            view.showString(string);
            view.show(client.getMiniModel().getPlayer().getManuscript());
            view.show(client.getMiniModel().getBoard());
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }

}
