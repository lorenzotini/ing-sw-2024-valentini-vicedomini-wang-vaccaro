package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class UpdatePlayerStateMessage extends Message {

    public UpdatePlayerStateMessage(MiniModel miniModel) {
        super(miniModel, miniModel.getPlayer().getPlayerState().toString());
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        try{
            client.getMiniModel().setPlayer(this.getMiniModel().getPlayer());
            client.getMiniModel().setBoard(this.getMiniModel().getBoard());
            view.okAck(string);
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }
}
