package it.polimi.ingsw.gc27.Model.Listener;

import it.polimi.ingsw.gc27.Net.VirtualView;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Observer {
    protected Observable obj;

    public abstract void update(Observable o ) throws RemoteException;

}
