package it.polimi.ingsw.gc27.View.Listener;

import it.polimi.ingsw.gc27.Messages.Message;

import java.rmi.Remote;

public interface ViewObserver extends Remote {
    void update(Message message);
}
