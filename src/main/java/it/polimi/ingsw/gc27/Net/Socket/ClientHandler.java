package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Messages.*;
import it.polimi.ingsw.gc27.Messages.SetUsernameMessage;
import it.polimi.ingsw.gc27.Messages.StringMessage;
import it.polimi.ingsw.gc27.Messages.UpdateManuscriptMessage;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.Commands.PingCommand;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientHandler implements VirtualView {


    final ObjectInputStream input;
    final ObjectOutputStream output;
    boolean flag;
    private Player player;
    final GigaController console;
    final SocketServer server;
    final Socket socketClient;
    private boolean disconnected;
    // is for the command received
    final BlockingQueue<Command> commands = new LinkedBlockingQueue<>();


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
                }
            } catch (ClassNotFoundException | InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
            while (true) {
                try {
                    Command command;
                    command = (Command) input.readObject();
                    if (command instanceof PingCommand) {
                        this.flag = true;
                    } else {
                        commands.add(command);
                    }
                } catch (IOException e) {
                    disconnected();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    runVirtualView();
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }

    public void runVirtualView() throws IOException, InterruptedException {

        //this.player = console.welcomePlayer(this);
        Command command;
        // Read message type
        while (true) {
            // Read message and perform action
            command = commands.take();
            try {
                command.execute(console);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void show(String s) {
        try {
            this.output.writeObject(new StringMessage(s));
            output.reset();
            this.output.flush();
        } catch (IOException e) {
            //TODO this.console.disconnected(this);
            //this to do will be implemented when the disconnection problem will be solved
        }
    }
    // TODO CIAO ANDRE, TOGLI QUESTO METODO CHE NON SI PUO' FARE LA SHOW DAL SERVER COME CI HANNO DETTO AL LAB

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
    public void setUsername(String username) throws RemoteException {

        try {
            output.writeObject(new SetUsernameMessage(username));
            output.reset();
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void pingToServer(VirtualServer virtualServer, VirtualView client) throws RemoteException {

    }

    @Override
    public void update(Message message) {
        try {
            if (message instanceof UpdateManuscriptMessage)
                message.getMiniModel().setManuscript(null);
            output.writeObject(message);
            output.reset();
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void verifyPing() throws InterruptedException {
        new Thread(() -> {
            int count = 0;
            while (count < 3) {
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
                    if (count == 3) {
                        disconnected();
                        System.out.println("giocatore socket disconnesso");
                    }
                }

            }
        }).start();
    }

    public synchronized void disconnected() {
        //console.disconnected(this.player);
        if (disconnected == false) {
            disconnected = true;
            System.out.println("disconessione avvenuta");
            //console.disconnected(this.player);
        }
    }

}

