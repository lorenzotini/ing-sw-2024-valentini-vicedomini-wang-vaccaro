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

public class ClientHandler implements VirtualView {


    final ObjectInputStream input;
    final ObjectOutputStream output;
    boolean flag;
    private Player player;
    final GigaController console;
    final SocketServer server;
    final Socket socketClient;
    private boolean disconnected = false;
    private int TIME_COUNT = 100;
    // is for the command received


    public ClientHandler(GigaController console, SocketServer server, Socket socketClient) throws IOException {
        this.console = console;
        this.server = server;
        this.socketClient = socketClient;

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

    @Override
    public void setUsername(String username) throws IOException {
        player = console.getPlayer(username);

        output.writeObject(new SetUsernameMessage(username));
        output.reset();
        output.flush();



    }

    @Override
    public void runClient() throws IOException, InterruptedException {
    }

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

    public void disconnected() {
        if (!disconnected) {
            server.disconnect(this); //rimuove questo handler dalla lista di handler
            disconnected = true;
            console.removeReferences(this);
            System.out.println("disconessione avvenuta");
            //console.disconnected(this.player);
        }
    }

}

