package it.polimi.ingsw.gc27.Net.Rmi;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.MiniModel;
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

public class RmiClient extends UnicastRemoteObject implements VirtualView {

    private final VirtualServer server;
    private final View view; //this will be or tui or gui, when  a gui is ready is to implement
    private final MiniModel miniModel;
    private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
    private String username = "";
    private long lastPingFromServer = 0 ;

    public RmiClient(String ipAddress, int port, View view) throws IOException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(ipAddress, port);
        this.server = (VirtualServer) registry.lookup("VirtualServer");

        this.view = view;
        this.miniModel = new MiniModel();
    }

    public void sendCommand(Command command) throws RemoteException {
        this.server.receiveCommand(command);
    }

    @Override
    public MiniModel getMiniModel() throws RemoteException {
        return this.miniModel;
    }

    @Override
    public void pingToServer(VirtualServer server, VirtualView client) throws RemoteException {
        server.receivePing(client);
    }

    @Override
    public void show(String message) throws RemoteException {
        System.out.println(message);
        //view.showString(message);
    }

    @Override
    public String read() throws RemoteException {
        return view.read();
    }

    @Override
    public void setUsername(String username) throws RemoteException {
        this.username = username;
    }

    @Override
    public void runClient() throws InterruptedException {
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
                    pingToServer(server, this);
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
            Thread.sleep(1000);
        }
        //start the game
        try{

            view.run();
        }catch(IOException e){
            System.out.println("There has been a problem with the UI, plese restart ");
            close();
        }
    }

    private void checkServerIsAlive()  {
        if(this.lastPingFromServer == 0){
            this.lastPingFromServer = System.currentTimeMillis();
        }
        while(true){
            if((System.currentTimeMillis() - this.lastPingFromServer) >10000){
                System.out.println("The connection has been lost, please restart the game");
                close();
            }
            try {

                Thread.sleep(1000);
            }catch(InterruptedException e){
                //TODO find a thing to do
            }
        }
    }

    @Override
    public String getUsername() throws RemoteException {
        return this.getMiniModel().getPlayer().getUsername();
    }

    @Override
    public void update(Message message) throws RemoteException {
        messages.add(message);
    }
    @Override
    public void close(){
        System.exit(0);
    }
    @Override
    public void pingFromServer(){
        this.lastPingFromServer = System.currentTimeMillis();
    }
}
