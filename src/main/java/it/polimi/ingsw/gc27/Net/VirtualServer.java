package it.polimi.ingsw.gc27.Net;

import it.polimi.ingsw.gc27.Net.Commands.Command;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote{

    int DEFAULT_PORT_NUMBER_RMI = 1234;

    int DEFAULT_PORT_NUMBER_SOCKET = 3000;

    void connect(VirtualView client) throws RemoteException;

    void disconnect(VirtualView client) throws RemoteException;

    void welcomePlayer(VirtualView client) throws IOException, InterruptedException;

    void receiveCommand(Command command) throws RemoteException;

    void areClientsAlive() throws RemoteException;

    void receivePing(VirtualView client) throws RemoteException;

}