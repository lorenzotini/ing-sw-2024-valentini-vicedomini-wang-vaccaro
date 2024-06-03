package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class PingMessage extends Message{

    public PingMessage(String string) {
        super("forza napoli");
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        try{
            client.pingFromServer();
        }catch(RemoteException e){

        }
    }
}
