package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class UpdateObjectiveMessage extends Message {

    public UpdateObjectiveMessage(MiniModel miniModel) {
        super(miniModel, "You chose your secret objective!");
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {

        try {
            client.getMiniModel().setPlayer(this.getMiniModel().getPlayer());

            view.showString(this.string);
            view.show(client.getMiniModel().getPlayer().getSecretObjectives().getFirst());

        }catch(RemoteException e){

        }
    }
}
