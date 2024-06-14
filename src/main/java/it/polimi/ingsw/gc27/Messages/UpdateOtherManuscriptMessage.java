package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class UpdateOtherManuscriptMessage extends Message {

    public UpdateOtherManuscriptMessage(MiniModel miniModel) {
        super(miniModel);
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        try{
            //client.getMiniModel().setPlayer(miniModel.getPlayer());
            //client.getMiniModel().setManuscript(miniModel.getPlayer().getManuscript());
            client.getMiniModel().setManuscriptsMap(miniModel.getManuscriptsMap());
            //view.showString(string);
            view.show(client.getMiniModel().getPlayer().getManuscript());
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }

}

