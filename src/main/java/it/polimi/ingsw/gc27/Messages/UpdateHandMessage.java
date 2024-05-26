package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class UpdateHandMessage extends Message{
    //this minimodel's class have player, hand and the string setted,
    //player is the only one that has to receive the message
    public UpdateHandMessage(MiniModel miniModel){
        super(miniModel, "Your hand changed.");
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        try{
            client.getMiniModel().setHand(this.getMiniModel().getHand());
            client.getMiniModel().setPlayer(this.getMiniModel().getPlayer());
            view.showString(this.string);
            view.show(client.getMiniModel().getHand());
        } catch (RemoteException e){
            e.printStackTrace();
        }
    }
}
