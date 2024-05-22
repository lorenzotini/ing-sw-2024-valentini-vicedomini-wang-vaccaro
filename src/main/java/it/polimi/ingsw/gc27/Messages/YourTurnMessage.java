package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class YourTurnMessage extends Message{

    public YourTurnMessage(MiniModel miniModel, String string) {
        super(miniModel, string);
    }


    @Override
    public void reportUpdate(VirtualView client, View view) throws RemoteException {
        view.showString("It's your tourn to play");
    }
}
