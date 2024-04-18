package it.polimi.ingsw.gc27.Net.Socket;

import java.io.IOException;
import java.rmi.RemoteException;

public class ConnectionException extends RemoteException {
    public ConnectionException(String message) {
        super(message);
    }
}
