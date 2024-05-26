package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class SocketClient implements VirtualView {

    final SocketServerProxy server;
    private String username;
    private View view;
    final MiniModel miniModel;


    public SocketClient(String ipAddress, int port, View view) throws IOException, InterruptedException {
        this.server = new SocketServerProxy(this, ipAddress, port);
        this.view = view;
        this.miniModel = new MiniModel();
    }

    public void runClient() {
        try {
            server.welcomePlayer(this);

            while(this.miniModel.getPlayer() == null) {
                Thread.sleep(100);
            }
            view.run();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void sendCommand(Command command) {
        server.receiveCommand(command);
    }

    @Override
    public MiniModel getMiniModel() {
        return this.miniModel;
    }


    @Override
    public void pingToServer(VirtualServer virtualServer, VirtualView client) throws RemoteException {

    }

    @Override
    public void show(String s) throws RemoteException {
        System.out.println(s);
    }
    public void showUpdate(String mex) throws RemoteException {
        // TODO Attenzione! Questo può causare data race con il thread dell'interfaccia o un altro thread
        System.out.println(mex);
    }
    @Override
    public String read() throws RemoteException {
        Scanner scan = new Scanner(System.in);
        String string;
        while((string = scan.nextLine()).equals("\n")){};
        return string;
    }

    @Override
    public void setUsername(String username) throws RemoteException {

        this.username = username;
        this.show("Welcome " + this.username + "!" + "\nWaiting for other players to join the game...");
    }


    @Override
    public void update(Message message) {
        message.reportUpdate(this, this.view);
    }

}
