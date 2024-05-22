package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.SetUsernameMessage;
import it.polimi.ingsw.gc27.Messages.StringMessage;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.Commands.Command;
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

    private Player player ;
    final GigaController console;
    final SocketServer server;
    final Socket socketClient;

    // here there are two queue, the first is to send Message to client, the second is for the command received
    final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
    final BlockingQueue<Command> commands = new LinkedBlockingQueue<>();


    public ClientHandler(GigaController console, SocketServer server, Socket socketClient) throws IOException {
        this.console = console;
        this.server = server;
        this.socketClient = socketClient;

        this.output = new ObjectOutputStream(socketClient.getOutputStream());
        this.input = new ObjectInputStream(socketClient.getInputStream());

        new Thread(()->{

                try {
                    String message = (String) input.readObject();
                    if(message.equals("welcomeplayer")){
                        console.welcomePlayer(this);
                    }
                } catch (ClassNotFoundException | InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        new Thread(()->{
            while(true){
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
        while ((command = (Command) commands.take()) != null ) {
            // Read message and perform action
            try {
                command.execute(console);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void show(String s) {
        try{
            this.output.writeObject(new StringMessage(s));
            this.output.flush();
        }catch(IOException e){
            //TODO this.console.disconnected(this);
            //this to do will be implemented when the disconnection problem will be solved
        }
    }
    // TODO CIAO ANDRE, TOGLI QUESTO METODO CHE NON SI PUO' FARE LA SHOW DAL SERVER COME CI HANNO DETTO AL LAB

    @Override
    public String read() throws IOException {
        String mex = null;
        output.writeObject(new StringMessage("read"));
        output.flush();
        try {
            mex = (String) input.readObject();
        }catch(ClassNotFoundException e){
            System.out.println("Big connection problem");// this is to be replaced

        }
        return mex;
    }

    @Override
    public void setUsername(String username) throws RemoteException {

        try{
            output.writeObject(new SetUsernameMessage(username));
            output.flush();
        }catch( IOException e){
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
    public long getLastPing() {
        return 0;
    }

    @Override
    public void setLastPing(long l) {

    }

    @Override
    public void pingToServer(VirtualServer virtualServer, VirtualView client) throws RemoteException {

    }

    @Override
    public void update(Message message) {
        try{
            output.writeObject(message);
            output.flush();
        }catch( IOException e){
            e.printStackTrace();
        }
    }

}

