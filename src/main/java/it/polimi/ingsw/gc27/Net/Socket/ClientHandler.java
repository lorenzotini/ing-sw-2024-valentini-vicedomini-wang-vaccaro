package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.CommandParser;
import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.SendStringMessage;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.Commands.WelcomePlayerCommand;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.*;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientHandler implements VirtualView {

    final ObjectInputStream input;
    final ObjectOutputStream output;

    private Player player ;
    final GigaController console;
    final SocketServer server;

    // here there are two queue, the first is to send Message to client, the second is for the command received
    final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
    final BlockingQueue<Command> commands = new LinkedBlockingQueue<>();


    public ClientHandler(GigaController console, SocketServer server, ObjectInputStream input, ObjectOutputStream output) throws IOException {
        this.console = console;
        this.input = input;
        this.server = server;
        this.output = output;

        new Thread(()->{
            while(true){
                try {
                    commands.put((Command) input.readObject());
                } catch (InterruptedException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }}).start();
        new Thread(()->{
            while(true){
                try {
                    commands.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(commands instanceof WelcomePlayerCommand){
                    try {
                        console.welcomePlayer(this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

    }

    public void runVirtualView() throws IOException, InterruptedException {

        //this.player = console.welcomePlayer(this);
        Command command;

        // Read message type
        while ((command = (Command) commands.take()) != null ) {
            // Read message and perform action
            try {
                commands.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(commands instanceof WelcomePlayerCommand){
                try {
                    console.welcomePlayer(this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @Override
    public void show(String s) {
        try{
            this.output.writeObject(new SendStringMessage(s));
            this.output.flush();
        }catch(IOException e){
            //TODO this.console.disconnected(this);
            //this to do will be implemented when the disconnection problem will be solved
        }
    }
    // TODO CIAO ANDRE, TOGLI QUESTO METODO CHE NON SI PUO' FARE LA SHOW DAL SERVER COME CI HANNO DETTO AL LAB

    @Override
    public String read() throws IOException, InterruptedException {
        Command command = commands.take();

        return str;
    }

    @Override
    public void setUsername(String username) throws RemoteException {
        client.setUsername(username);
    }
    @Override
    public void run() throws IOException, InterruptedException {

    }
    @Override
    public String getUsername() {
        return this.player.getUsername();
    }

    @Override
    public void sendCommand(Command command) {

    }

    @Override
    public void update(Message message) {

    }
}

