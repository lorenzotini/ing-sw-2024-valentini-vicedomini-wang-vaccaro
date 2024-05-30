package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class OkMessage extends Message{

    public OkMessage(String string) {
        super(string);
    }

    @Override
    public void reportUpdate(VirtualView client, View view) throws RemoteException {
        view.okAck(this.string);
    }
}
