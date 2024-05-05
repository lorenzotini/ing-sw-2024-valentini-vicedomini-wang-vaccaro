package it.polimi.ingsw.gc27.Model.Listener;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {

    void update(String mex) throws RemoteException;

    //void update(Observable o);
}
