package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class UpdatePlayerStateMessage extends Message {

    public UpdatePlayerStateMessage(MiniModel miniModel) {
        super(miniModel, "You are now playing!");
    }

    public UpdatePlayerStateMessage(MiniModel miniModel, String currentState) {
        super(miniModel, currentState);
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        try{
            client.getMiniModel().setPlayer(this.getMiniModel().getPlayer());
            view.showString(this.string);
            view.okAck(string);
        }catch(RemoteException e){

        }

    }
}
