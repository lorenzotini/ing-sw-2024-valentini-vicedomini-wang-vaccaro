package it.polimi.ingsw.gc27.Model.Listener;

import it.polimi.ingsw.gc27.Messages.Message;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Observable {

    ArrayList<Observer> observers = new ArrayList<>();

    void addObserver(Observer o);

    void removeObserver(Observer o);

    void removeObserver(String username);

    void notifyObservers(Message notYourTurnMessage) throws RemoteException;

}
