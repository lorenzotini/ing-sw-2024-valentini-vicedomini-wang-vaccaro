package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.Game.Chat;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class UpdateChatMessage extends Message{

    public UpdateChatMessage(Chat chat){
        super(new MiniModel());
    }

    @Override
    public void reportUpdate(VirtualView client, View view) throws RemoteException {

    }
}
