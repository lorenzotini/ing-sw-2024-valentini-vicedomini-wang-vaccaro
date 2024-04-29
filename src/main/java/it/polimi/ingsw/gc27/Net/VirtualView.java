package it.polimi.ingsw.gc27.Net;

import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote {

    void showUpdate(String update) throws RemoteException;

    void show(String s) throws RemoteException;
    void showManuscript(Manuscript manuscript) throws RemoteException;

    String read() throws RemoteException, IOException, InterruptedException;

    void setUsername(String username)throws RemoteException;


}
