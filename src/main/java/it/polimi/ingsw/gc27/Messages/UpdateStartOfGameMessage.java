package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class UpdateStartOfGameMessage extends Message {

    public UpdateStartOfGameMessage(MiniModel miniModel, String string) {
        super(miniModel, string);
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        try{
            client.getMiniModel().copy(this.getMiniModel());
        }catch(RemoteException e){
            e.printStackTrace();
        }

    }
}
