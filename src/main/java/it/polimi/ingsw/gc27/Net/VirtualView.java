package it.polimi.ingsw.gc27.Net;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote {

    void showUpdate(String update) throws RemoteException;

    void show(String s) throws RemoteException;

    String read() throws RemoteException, IOException;

    void setUsername(String username)throws RemoteException;
}
