package it.polimi.ingsw.gc27.Net.Rmi;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.NotYourTurnMessage;
import it.polimi.ingsw.gc27.Messages.UpdateHandMessage;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
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
    final VirtualServer server;
    private String username;
    private View view; //this will be or tui or gui, when  a gui is ready is to implement

    public RmiClient(String ipAddress, int port) throws IOException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(ipAddress, port);
        this.server = (VirtualServer) registry.lookup("VirtualServer");
        miniModel = new MiniModel();
    }

    @Override
    public void show(String message) throws RemoteException{
        System.out.println(message);
    }

    @Override
    public void showManuscript(Manuscript manuscript) throws RemoteException {
        //Tui.printManuscript(manuscript);
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

    @Override
    public void run() throws IOException, InterruptedException {
        this.server.connect(this);
        server.welcomePlayer(this);
        view = new Tui(this, server); //when gui is ready there will be a command for this
        view.run();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void update(Message message) throws RemoteException {
        message.reportUpdate(this.miniModel, view );
    }

    public void sendCommand(Command command){
        server.receiveCommand(command);
    }
}
