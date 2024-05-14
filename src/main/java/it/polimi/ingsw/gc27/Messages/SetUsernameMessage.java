package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class SetUsernameMessage extends Message{
    public SetUsernameMessage(String username){
        super(username);
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        try {
            client.setUsername(this.string);
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }

}
