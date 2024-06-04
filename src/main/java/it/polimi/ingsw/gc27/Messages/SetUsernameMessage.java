package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.io.IOException;
import java.rmi.RemoteException;

public class SetUsernameMessage extends Message {
    public SetUsernameMessage(String username) {
        super(username);
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        try {
            client.setUsername(this.string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
