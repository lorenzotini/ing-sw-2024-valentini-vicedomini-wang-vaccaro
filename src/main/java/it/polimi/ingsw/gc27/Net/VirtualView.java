package it.polimi.ingsw.gc27.Net;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.Commands.Command;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The VirtualView interface represents a client's view in the application.
 * It provides methods for displaying messages, reading input, setting username, running the client,
 * sending commands, getting the MiniModel, updating messages, closing the connection, and pinging the server.
 * This interface extends the Remote and Serializable interfaces to allow its methods to be called from a non-local JVM.
 */
public interface VirtualView extends Remote, Serializable {

    /**
     * Displays a string message to the client.
     *
     * @param s The message to display.
     * @throws IOException If an I/O error occurs.
     */
    void show(String s) throws IOException;

    /**
     * Reads input from the client.
     *
     * @return The input read from the client.
     * @throws IOException If an I/O error occurs.
     * @throws InterruptedException If the operation is interrupted.
     */
    String read() throws IOException, InterruptedException;

    /**
     * Sets the username of the client.
     *
     * @param username The username to set.
     * @throws IOException If an I/O error occurs.
     */
    void setUsername(String username) throws IOException;

    /**
     * Runs the client.
     *
     * @throws IOException If an I/O error occurs.
     * @throws InterruptedException If the operation is interrupted.
     */
    void runClient() throws IOException, InterruptedException;

    /**
     * Gets the username of the client.
     *
     * @return The username of the client.
     * @throws IOException If an I/O error occurs.
     */
    String getUsername() throws IOException;

    /**
     * Sends a command to the server.
     *
     * @param command The command to send.
     * @throws RemoteException If a remote access error occurs.
     */
    void sendCommand(Command command) throws RemoteException;

    /**
     * Gets the MiniModel of the client.
     *
     * @return The MiniModel of the client.
     * @throws RemoteException If a remote access error occurs.
     */
    MiniModel getMiniModel() throws RemoteException;

    /**
     * Updates the messages of the client.
     *
     * @param message The message to update.
     * @throws RemoteException If a remote access error occurs.
     */
    void update(Message message) throws RemoteException;

    /**
     * Closes the connection of the client.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    void close() throws RemoteException;

    /**
     * Pings the server to check if the connection is alive.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    void pingFromServer() throws RemoteException;
}
