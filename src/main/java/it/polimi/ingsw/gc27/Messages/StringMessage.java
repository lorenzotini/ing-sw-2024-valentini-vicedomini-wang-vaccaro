package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class StringMessage extends Message{
    //used for socket communication
    public StringMessage(String string) {
        super(string);
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        view.showString(this.string);
    }

}
