package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.*;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.io.IOException;
import java.rmi.RemoteException;

public class UpdateEndGame extends Message{
    public UpdateEndGame(MiniModel miniModel) {
        super(miniModel);
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        try {
            client.getMiniModel().setBoard(this.getMiniModel().getBoard());
            view.showWinners();
        } catch (RemoteException e){

        }

    }
}
