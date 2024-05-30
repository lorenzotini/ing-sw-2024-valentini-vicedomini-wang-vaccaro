package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class KoMessage extends Message{
    public KoMessage(String string) {
        super(string);
    }

    @Override
    public void reportUpdate(VirtualView client, View view) throws RemoteException {
        view.koAck();
    }
}
