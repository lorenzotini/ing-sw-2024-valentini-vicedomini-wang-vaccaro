package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.Tui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class SocketClient implements VirtualView, Runnable {

    final SocketServerProxy server;
    private String username;
    private Tui tui;


    public SocketClient(String ipAddress, int port) {
        this.server = new SocketServerProxy(this, ipAddress, port);

    }

    public void run() {
        try {
            server.welcomePlayer(this);
            synchronized (this) {
                while(username == null) {
                    this.wait();
                }
            }
            tui = new Tui(this, server);
            tui.run();
        } catch (RemoteException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getUsername() {
        return this.username;
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
    public void showManuscript(Manuscript manuscript) throws RemoteException {

    }

    @Override
    public void showStarter(StarterCard starterCard) throws RemoteException {

    }

    @Override
    public String read() throws RemoteException {
        Scanner scan = new Scanner(System.in);
        String string;
        while((string = scan.nextLine())=="\n"){};
        return string;
    }

    @Override
    public void setUsername(String username) throws RemoteException {
        synchronized (this) {
            this.username = username;
            this.notifyAll();
        }
    }


    @Override
    public void update(Message message) {

    }
}
