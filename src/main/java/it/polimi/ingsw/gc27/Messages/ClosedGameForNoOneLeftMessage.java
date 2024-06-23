package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class ClosedGameForNoOneLeftMessage extends Message{

    public ClosedGameForNoOneLeftMessage(String string){
        super(string);
    }
    @Override
    public void reportUpdate(VirtualView client, View view) {

        view.koAck(this.string);
        view.showString(this.string);
        try {
            client.close();
        }catch (RemoteException e){

        }
    }
}
