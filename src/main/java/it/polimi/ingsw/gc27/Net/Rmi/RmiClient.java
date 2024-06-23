package it.polimi.ingsw.gc27.Net.Rmi;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The RmiClient class implements the VirtualView interface and represents the client in the RMI network.
 * It manages the connection to the server, sends commands to it, and checks if the server is alive.
 * It also contains a View instance for handling client view and a MiniModel instance for storing the game state.
 */
public class RmiClient extends UnicastRemoteObject implements VirtualView {

    /**
     * The VirtualServer instance representing the server.
     */
    private VirtualServer server;

    /**
     * The View instance for handling client view.
     */
    private final View view; //this will be or tui or gui, when  a gui is ready is to implement

    /**
     * The MiniModel instance for storing the game state.
     */
    private final MiniModel miniModel;

    /**
     * The queue of messages received from the server.
     */
    private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();

    /**
     * The username of the client.
     */
    private String username = "";

    /**
     * The last time a ping was received from the server.
     */
    private long lastPingFromServer = 0 ;

    // TODO IMPORTANTISSIMO: REIMPOSTARE I TEMPI DI PING A ROBA NORMALE CHE SE NO ZIO PERA è LA FINE
    /**
     * The maximum time in milliseconds a server can be inactive before being considered disconnected.
     */
    private int TIME_COUNT = 5000;

    /**
     * Constructs a new RmiClient with the given IP address, port, and View.
     *
     * @param ipAddress The IP address of the server.
     * @param port The port number of the server.
     * @param view The View instance for handling client view.
     * @throws RemoteException If a remote access error occurs.
     */
    public RmiClient(String ipAddress, int port, View view) throws RemoteException {

        this.view = view;
        this.miniModel = new MiniModel();

        do {
            try{
                locateRegistry(ipAddress, port);
                break;
            }catch(IOException | NotBoundException e){
                show("Server not found, retrying...");
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException ignored){

                }
            }
        } while (true);

    }

    /**
     * * Establishes a connection to the RMI registry at the specified IP address and port.
     *  * It then retrieves the remote object registered under the name "VirtualServer".
     *
     * @param ipAddress the IP address where the RMI registry is hosted.
     * @param port the port number on which the RMI registry accepts connections.
     * @throws RemoteException if there is an issue with network connectivity or
     *         the registry is not available at the specified IP address and port.
     * @throws NotBoundException if no binding for "VirtualServer" is found in the registry,
     *         indicating that the remote object is not registered.
     */
    public void locateRegistry(String ipAddress, int port) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(ipAddress, port);
        this.server = (VirtualServer) registry.lookup("VirtualServer");

    }

    /**
     *Send the Command to the server to have it executed
     * @param command the action the client want to do
     * @throws RemoteException if there is any trouble with the connection
     */
    public void sendCommand(Command command) throws RemoteException {
        this.server.receiveCommand(command);
    }

    /**
     * this.miniModel contains all the data the client need to describe the actual
     * state of the game
     * @return this client miniModel
     * @throws RemoteException if there is any trouble with the connection
     */
    @Override
    public MiniModel getMiniModel() throws RemoteException {
        return this.miniModel;
    }

    /**
     * called by the server or the messages for updates, act as an intermediary
     * to connect with the vies
     * @param message is the string wanted to be showed
     * @throws RemoteException if there is any trouble with the connection
     */
    @Override
    public void show(String message) throws RemoteException {
        view.showString(message);
    }

    /**
     * take a String input from the view, usually called in situation with
     * an expected result
     * @return a String
     * @throws RemoteException if there is any trouble with the connection
     */
    @Override
    public String read() throws RemoteException {
        return view.read();
    }

    /**
     * modify this.username with the chosen username by the client
     * @param username is going to be put in this.username
     * @throws RemoteException if there is any trouble with the connection
     */
    @Override
    public void setUsername(String username) throws RemoteException {
        this.username = username;
    }

    /**
     * Initiates and manages the client's connection and interaction with the remote server.
     * This method handles several key operations:
     * - Attempts to establish a connection with the server.
     * - Starts a continuous ping to the server to ensure the connection remains alive.
     * - Handles incoming messages and dispatches them for processing.
     * - Manages the user interface and game flow after the initial connection.
     * The method employs multiple threads to manage different aspects of client-server communication
     * and user interaction:
     *   A thread to continuously check if the server is alive by pinging it.
     *   A thread to process incoming messages from the server.
     *   The main thread handles initial connection setup and user interaction.
     *
     * If any part of the initial setup or ongoing communication fails, the client will attempt
     * to close its connection
     * and notify the user of the need to restart.
     */
    @Override
    public void runClient() {

        try{
            this.server.connect(this);
        }catch(RemoteException e){
            System.out.println("There is a problem with the connection, please retry");
            close();
        }

        new Thread(this :: checkServerIsAlive).start();

        //keep the client alive
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    pingToServer( this);
                } catch (RemoteException | InterruptedException e) {
                    System.out.println("Probably the server is down");
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    Message mess = messages.take();
                    mess.reportUpdate(this, view);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        try{
            this.server.welcomePlayer(this);
            this.show("Welcome " + this.username + "!" + "\nWaiting for other players to join the game...");
        }catch(IOException e){
            System.out.println("The connection has been lost while setting the player, please try to reconnect ");
            close();
        }

        //wait for the other players to join the game
        while (miniModel.getPlayer() == null) {
            try {
                Thread.sleep(1000);
            }catch(InterruptedException e){
                throw new RuntimeException("Thread problem");
            }
        }

        //start the game
        try{
            view.run();
        }catch(IOException e){
            System.out.println("There has been a problem with the UI, please restart ");
            close();
        }

    }

    /**
     * check if last ping received from server is too old, in that case the connection is considered lost
     * and the process closed
     */
    private void checkServerIsAlive()  {
        if(this.lastPingFromServer == 0){
            this.lastPingFromServer = System.currentTimeMillis();
        }
        while(true){
            if((System.currentTimeMillis() - this.lastPingFromServer) > TIME_COUNT ){
                System.out.println("The connection has been lost, please restart the game");
                close();
            }
            try {
                Thread.sleep(1000);
            }catch(InterruptedException e){
                throw new RuntimeException("Explosion of thread sleep");
            }
        }
    }

    /**
     * @return this.username, the username of the client
     * @throws RemoteException if there is any trouble with the connection
     */
    @Override
    public String getUsername() throws RemoteException {
        return this.getMiniModel().getPlayer().getUsername();
    }

    /**
     * add the messages received from the server in the queue messages
     * @param message is the update received from the server
     * @throws RemoteException if there is any trouble with the connection
     */
    @Override
    public void update(Message message) throws RemoteException {
        messages.add(message);
    }

    /**
     * close the process
     */
    @Override
    public void close(){
        System.exit(0);
    }

    /**
     * set the lastPingFromRemoteServer to the actual current in millis
     * @throws RemoteException if there is any trouble with the connection
     */
    public void pingFromServer() throws RemoteException{
        this.lastPingFromServer = System.currentTimeMillis();
    }

    /**
     * say to the server that connection is working
     * @param client is this class
     * @throws RemoteException if there is any trouble with the connection
     */
    public void pingToServer( VirtualView client) throws RemoteException {
        server.receivePing(client);
    }

}
