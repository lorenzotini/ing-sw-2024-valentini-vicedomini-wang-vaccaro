package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class UpdateManuscriptMessage extends Message {
    //this minimodel's class have player, manuscript and the string set,
    //player is the only one that has to receive the message

    public UpdateManuscriptMessage(MiniModel miniModel) {
        super(miniModel, "Something changed in your manuscript!");
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        try{
            client.getMiniModel().setPlayer(this.getMiniModel().getPlayer());
            client.getMiniModel().setManuscript(this.getMiniModel().getPlayer().getManuscript());
            view.showString(this.string);
            view.show(client.getMiniModel().getPlayer().getManuscript());
        }catch(RemoteException e){

        }

    }
}
