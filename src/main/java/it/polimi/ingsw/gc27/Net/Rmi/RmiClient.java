package it.polimi.ingsw.gc27.Net.Rmi;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.Tui;
import it.polimi.ingsw.gc27.View.View;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RmiClient extends UnicastRemoteObject implements VirtualView {

    private final MiniModel miniModel;
    private final VirtualServer server;
    private String username;
    private View view; //this will be or tui or gui, when  a gui is ready is to implement

    public RmiClient(String ipAddress, int port) throws IOException, NotBoundException, InterruptedException {
        Registry registry = LocateRegistry.getRegistry(ipAddress, port);
        this.server = (VirtualServer) registry.lookup("VirtualServer");
        this.view = new Tui(this); //when gui is ready there will be a command for this
        this.miniModel = new MiniModel();
    }

    public void sendCommand(Command command) throws RemoteException {
        this.server.receiveCommand(command);
    }

    @Override
    public MiniModel getMiniModel() {
        return this.miniModel;
    }

    @Override
    public void show(String message) throws RemoteException{
        System.out.println(message);
    }

    @Override
    public String read() throws RemoteException{
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    @Override
    public void setUsername(String username) throws RemoteException {
        this.username = username;
    }

    @Override
    public void runClient() throws IOException, InterruptedException {

        this.server.connect(this);
        this.server.welcomePlayer(this);
        this.view.showString("Welcome " + this.username + "!" + "\nWaiting for other players to join the game...");

        //wait for the other players to join the game
        while(miniModel.getPlayer() == null) {
            Thread.sleep(1000);
        }

        //start the game
        this.view.run();

    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void update(Message message) throws RemoteException {
        message.reportUpdate(this, this.view );
    }

}
