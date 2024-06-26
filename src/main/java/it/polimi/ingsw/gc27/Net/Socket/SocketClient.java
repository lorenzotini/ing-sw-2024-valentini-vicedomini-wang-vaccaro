package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Commands.Command;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * The RmiClient class implements the VirtualView interface and represents the client in the RMI network.
 * It talks with the Proxy server and contains a View instance for handling client view and a MiniModel
 * instance for storing the game state. Whenever here there is an interaction with the server it's a Proxy.
 */
public class SocketClient implements VirtualView {

    private final SocketServerProxy server;
    private String username;
    private final View view;
    private final MiniModel miniModel;


    public SocketClient(String ipAddress, int port, View view) {
        this.server = new SocketServerProxy(this, ipAddress, port);
        this.view = view;
        this.miniModel = new MiniModel();
    }

    /**
     * first call the server to set up the player information
     * after start the game mode for the view
     */
    public void runClient() {
        try {
            server.welcomePlayer(this);

            while (this.miniModel.getPlayer() == null) {
                Thread.sleep(100);
            }
            view.run();
        } catch (InterruptedException | IOException e) {
            System.out.print("The connection has been lost");
            close();
            //throw new RuntimeException(e);
        }
    }

    /**
     * @return the client username
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * the command is the action the player want to make during the game.
     *
     * @param command The command to send.
     */
    @Override
    public void sendCommand(Command command) {
        server.receiveCommand(command);
    }

    /**
     * this.miniModel contains all the data the client need to describe the actual
     * state of the game
     *
     * @return this client miniModel
     */
    @Override
    public MiniModel getMiniModel() {
        return this.miniModel;
    }

    /**
     * called by the server or the messages for updates, act as an intermediary
     * to connect with the vies
     *
     * @param message is the string wanted to be showed
     */
    @Override
    public void show(String message) throws RemoteException {
        view.showString(message);
    }

    /**
     * take a String input from the view, usually called in situation with
     * an expected result
     *
     * @return a String
     */
    @Override
    public String read() throws RemoteException {
        return view.read();
    }

    /**
     * modify this.username with the chosen username by the client
     *
     * @param username is going to be put in this.username
     */
    @Override
    public void setUsername(String username) throws RemoteException {
        this.username = username;
        this.show("Welcome " + this.username + "!" + "\nWaiting for other players to join the game...");
    }

    /**
     * take the update from the server and put it in the messages queue
     * @param message The update from the server.
     */
    @Override
    public void update(Message message) {
        message.reportUpdate(this, this.view);
    }

    /**
     * close the process
     */
    @Override
    public void close() {
        System.exit(0);
    }

    /**
     * This method is required by the {@link VirtualView} interface,
     * but it is not used in this implementation.
     */

    @Override
    public void pingFromServer() throws RemoteException {
    }
}
