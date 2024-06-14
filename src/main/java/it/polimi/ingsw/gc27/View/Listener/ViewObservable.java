package it.polimi.ingsw.gc27.View.Listener;

import it.polimi.ingsw.gc27.Messages.Message;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ViewObservable { //virtual view, scene
    //viene modificata la view, onActionEvent fa la notify all'unico osservatore che è il GigaController
    //gli manda come messaggio il "comando" oppure il dato da inserire
    ArrayList<ViewObserver> observers = new ArrayList<>();
    void addObserver(ViewObserver o);
    void notifyObservers(Message message) throws RemoteException;
}
