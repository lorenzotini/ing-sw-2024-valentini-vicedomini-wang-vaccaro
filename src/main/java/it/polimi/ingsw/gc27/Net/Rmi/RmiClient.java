package it.polimi.ingsw.gc27.Net.Rmi;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.io.IOException;
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
    private String username;
    private long lastPing = 0;

    public RmiClient(String ipAddress, int port, View view) throws IOException, NotBoundException, InterruptedException {
        Registry registry = LocateRegistry.getRegistry(ipAddress, port);
        this.server = (VirtualServer) registry.lookup("VirtualServer");
        this.view = view;
        this.miniModel = new MiniModel();
    }

    public void sendCommand(Command command) throws RemoteException {
        this.server.receiveCommand(command);
    }

    @Override
    public synchronized long getLastPing() {
        return lastPing;
    }

    @Override
    public synchronized void setLastPing(long lastPing) {
        this.lastPing = lastPing;
    }

    @Override
    public MiniModel getMiniModel() {
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
    public void runClient() throws IOException, InterruptedException {

        this.server.connect(this);

        new Thread(() -> {
            while (true) {
                try {
                    Message mess = messages.take();
                    mess.reportUpdate(this, view);
                } catch (RemoteException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        this.server.welcomePlayer(this);
        this.show("Welcome " + this.username + "!" + "\nWaiting for other players to join the game...");


        //wait for the other players to join the game
        while (miniModel.getPlayer() == null) {
            Thread.sleep(1000);
        }

        //keep the client alive
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    pingToServer(server, this);
                } catch (RemoteException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        //start the game
        view.run();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void update(Message message) throws RemoteException {
        messages.add(message);
    }

}
