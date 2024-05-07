package it.polimi.ingsw.gc27.Model.Listener;

import it.polimi.ingsw.gc27.Messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {

    void update(Message message) throws RemoteException;

    //void update(Observable o);
}
