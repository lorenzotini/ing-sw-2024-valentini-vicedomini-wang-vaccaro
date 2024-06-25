package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.*;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class UpdateEndGameMessage extends Message{
    public UpdateEndGameMessage(MiniModel miniModel) {
        super(miniModel);
    }

    @Override
    public void reportUpdate(VirtualView client, View view)  {

        try {
            client.getMiniModel().setBoard(this.miniModel.getBoard());
            view.showWinners();
        }catch(RemoteException | NullPointerException e){
            throw new RuntimeException(e);
        }
    }
}
