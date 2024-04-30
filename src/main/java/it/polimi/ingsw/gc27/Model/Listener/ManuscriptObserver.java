package it.polimi.ingsw.gc27.Model.Listener;

import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.rmi.RemoteException;

public class ManuscriptObserver extends Observer{
    private VirtualView client;
    @Override
    public void update(Observable o) throws RemoteException {
        client.showManuscript((Manuscript) o);
    }
}
