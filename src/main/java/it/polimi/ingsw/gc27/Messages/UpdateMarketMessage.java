package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class UpdateMarketMessage extends Message {

    //the minimodel of this class will have only market set
    //everyone will receive this
    public UpdateMarketMessage(MiniModel miniModel) {
        super(miniModel, "The Market has been updated!");
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        try {
            client.getMiniModel().setMarket(this.getMiniModel().getMarket());
            view.show(client.getMiniModel().getMarket());

        }catch(RemoteException e){

        }
    }
}
