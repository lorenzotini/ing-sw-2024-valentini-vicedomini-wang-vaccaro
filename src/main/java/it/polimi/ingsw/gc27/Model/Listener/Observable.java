package it.polimi.ingsw.gc27.Model.Listener;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Observable {

    ArrayList<Observer> observers = new ArrayList<>();

    void addObserver(Observer o);
    void deleteObserver(Observer o);
    void notifyObservers() throws RemoteException;

}
