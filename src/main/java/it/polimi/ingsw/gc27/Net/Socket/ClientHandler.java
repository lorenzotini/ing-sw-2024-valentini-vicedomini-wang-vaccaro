package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Messages.*;
import it.polimi.ingsw.gc27.Messages.SetUsernameMessage;
import it.polimi.ingsw.gc27.Messages.StringMessage;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.Commands.PingCommand;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * The ClientHandler class handles client connections as server for the client and as the VirtualView for the model.
 * It handles communication with the client (SocketServer) and the controller,
 * handling the reception of commands, sending updates, and maintaining connection status through periodic pings.
 */
public class ClientHandler implements VirtualView {


    private final ObjectInputStream input;
    private final ObjectOutputStream output;
    private boolean flag;
    private Player player;
    private final GigaController console;
    private final SocketServer server;
    private boolean disconnected = false;
    private int TIME_COUNT = 100;
    // is for the command received

    /**
     * Constructs a ClientHandler for managing a client connection. Read the input from the Proxy server, makes the
     * console start the set-up process and from the input takes the command from the client and give it to the GigaController
     *
     * @param console      the GigaController to be used for handling client interactions
     * @param server       the SocketServer managing this handler
     * @param socketClient the socket connected to the client
     * @throws IOException if an I/O error occurs when creating the input or output streams
     */
    public ClientHandler(GigaController console, SocketServer server, Socket socketClient) throws IOException {
        this.console = console;
        this.server = server;


        this.output = new ObjectOutputStream(socketClient.getOutputStream());
        this.input = new ObjectInputStream(socketClient.getInputStream());

        new Thread(() -> {
            try {
                String message = (String) input.readObject();
                if (message.equals("welcomeplayer")) {

                    console.welcomePlayer(this);
                    verifyPing();
                    new Thread(() -> {
                        try {
                            pingFromServer();
                        } catch (RemoteException e) {
                            System.out.println("remote exception lol xd");
                        }
                    }).start();
                }
            } catch (ClassNotFoundException | InterruptedException | IOException e) {
                disconnected();
            }
            while (true) {
                try {
                    Command command;
                    command = (Command) input.readObject();
                    if (command instanceof PingCommand) {
                        this.flag = true;
                    } else {
                        console.addCommandToGameController(command);
                    }
                } catch (IOException e) {
                    disconnected();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }


    /**
     * Sends a message to the client.
     *
     * @param s the message to be sent
     */
    @Override
    public void show(String s) {
        try {
            this.output.writeObject(new StringMessage(s));
            this.output.reset();
            this.output.flush();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO this.console.disconnected(this);
            //this to do will be implemented when the disconnection problem will be solved
        }
    }

    /**
     * Sends a read request to the client and waits for a response.
     *
     * @return the string response from the client
     * @throws IOException if an I/O error occurs
     */
    @Override
    public String read() throws IOException {
        String mex = null;
        output.writeObject(new StringMessage("read"));
        output.reset();
        output.flush();
        try {
            mex = (String) input.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Big connection problem");// this is to be replaced

        }
        return mex;
    }


    /**
     * Sets the username for the player and sends a SetUsernameMessage to the client.
     * @param username the username to set
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void setUsername(String username) throws IOException {
        player = console.getPlayer(username);
        output.writeObject(new SetUsernameMessage(username));
        output.reset();
        output.flush();

    }

    @Override
    public void runClient() {

    }

    /**
     * Returns the username of the player.
     *
     * @return the player's username
     */
    @Override
    public String getUsername() {
        return this.player.getUsername();
    }

    @Override
    public void sendCommand(Command command) {
    }

    @Override
    public MiniModel getMiniModel() {
        return null;
    }

    /**
     * Sends an update message to the client.
     *
     * @param message the message to be sent
     */
    @Override
    public synchronized void update(Message message) {
        try {
            output.writeObject(message);
            output.reset();
            output.flush();
        } catch (IOException e) {
            System.out.println("non funziona");
        }
    }

    @Override
    public void close() {

    }

    /**
     * Continuously sends ping messages to the client to verify connectivity.
     *
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void pingFromServer() throws RemoteException {
        while (!disconnected) {
            update(new PingMessage(""));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * Verifies the reception of ping messages from the client.
     * If for the defined Timeout time doesn't receive the ping, consider the connection lost,
     * disconnect the client.
     *
     * @throws InterruptedException if the thread is interrupted
     */
    private void verifyPing() throws InterruptedException {
        new Thread(() -> {
            int count = 0;
            while (count < 5) {
                if (flag) {
                    count = 0;
                    flag = false;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("ping non ricevuto " + count);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    count++;
                    if (count == TIME_COUNT) {
                        disconnected();
                        System.out.println("giocatore socket disconnesso");
                    }
                }

            }
        }).start();
    }

    /**
     * Handles client disconnection, removing the client handler from the server's list and cleaning up resources.
     */
    public void disconnected() {
        if (!disconnected) {
            server.disconnect(this); //rimuove questo handler dalla lista di handler
            disconnected = true;
            console.removeReferences(this);
            System.out.println("disconessione avvenuta");

        }
    }

}

