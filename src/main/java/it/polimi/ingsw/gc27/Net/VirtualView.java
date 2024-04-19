package it.polimi.ingsw.gc27.Net;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote, Runnable {
    void showUpdate(String update) throws RemoteException;

    void show(String s) throws RemoteException;

    String read() throws RemoteException;

}
