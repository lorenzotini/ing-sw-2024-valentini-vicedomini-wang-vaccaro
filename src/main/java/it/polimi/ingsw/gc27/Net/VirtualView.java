package it.polimi.ingsw.gc27.Net;

import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Listener.Observer;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote, Observer {

    void showUpdate(String update) throws RemoteException;

    void show(String s) throws RemoteException;

    void showManuscript(Manuscript manuscript) throws RemoteException;

    void showStarter(StarterCard starterCard) throws RemoteException;

    //void showObjectives(ArrayList<ObjectiveCard> secretObjectives) throws RemoteException;

    String read() throws IOException, InterruptedException;

    void setUsername(String username) throws RemoteException;

    String getUsername() throws IOException, InterruptedException;

}
