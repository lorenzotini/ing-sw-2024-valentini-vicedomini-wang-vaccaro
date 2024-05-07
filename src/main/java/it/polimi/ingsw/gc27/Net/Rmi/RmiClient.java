package it.polimi.ingsw.gc27.Net.Rmi;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.Tui;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RmiClient extends UnicastRemoteObject implements VirtualView {

    final VirtualServer server;
    private String username;

    public RmiClient(String ipAddress, int port) throws IOException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(ipAddress, port);
        this.server = (VirtualServer) registry.lookup("VirtualServer");
    }

    @Override
    public void show(String message) throws RemoteException{
        System.out.println(message);
    }

    @Override
    public void showManuscript(Manuscript manuscript) throws RemoteException {
        Tui.printManuscript(manuscript);
    }

    @Override
    public void showStarter(StarterCard starterCard) throws RemoteException {
        Tui.showStarter(starterCard);
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

    public void run() throws IOException, InterruptedException {
        this.server.connect(this);
        server.welcomePlayer(this);
        Tui tui = new Tui(this, server);
        tui.runCli();
    }

    @Override
    public String getUsername() {
        return this.username;
    }



    @Override
    public void update(Message message) throws RemoteException {

    }
}
