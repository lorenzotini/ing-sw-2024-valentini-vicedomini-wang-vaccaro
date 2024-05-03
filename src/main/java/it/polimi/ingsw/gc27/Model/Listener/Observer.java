package it.polimi.ingsw.gc27.Model.Listener;

import it.polimi.ingsw.gc27.Model.Game.Player;

import java.rmi.RemoteException;

public interface Observer {


    public abstract void update(Observable o );

}
