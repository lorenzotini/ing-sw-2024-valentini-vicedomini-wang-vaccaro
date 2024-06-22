package it.polimi.ingsw.gc27.Net;

import it.polimi.ingsw.gc27.Net.Commands.Command;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The VirtualServer interface represents a server in the application.
 * It provides methods for connecting a client, welcoming a player, receiving commands, and pinging.
 * This interface extends the Remote interface to allow its methods to be called from a non-local JVM.
 */
public interface VirtualServer extends Remote {

    /**
     * The default port number for RMI connections.
     */
    int DEFAULT_PORT_NUMBER_RMI = 1099;

    /**
     * The default port number for socket connections.
     */
    int DEFAULT_PORT_NUMBER_SOCKET = 3000;

    /**
     * Connects a client to the server.
     *
     * @param client The client to connect.
     * @throws RemoteException If a remote access error occurs.
     */
    void connect(VirtualView client) throws RemoteException;

    /**
     * Welcomes a player to the server.
     *
     * @param client The client representing the player.
     * @throws IOException If an I/O error occurs.
     */
    void welcomePlayer(VirtualView client) throws IOException ;

    /**
     * Receives a command from a client.
     *
     * @param command The command to receive.
     * @throws RemoteException If a remote access error occurs.
     */
    void receiveCommand(Command command) throws RemoteException;

    /**
     * Receives a ping from a client.
     *
     * @param client The client sending the ping.
     * @throws RemoteException If a remote access error occurs.
     */
    void receivePing(VirtualView client) throws RemoteException;
}