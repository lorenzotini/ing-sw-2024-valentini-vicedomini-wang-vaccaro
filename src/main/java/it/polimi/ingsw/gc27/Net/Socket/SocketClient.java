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

            synchronized (this) {
                while(this.miniModel.getPlayer() == null) {
                    this.wait();
                }
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
        String string = scan.nextLine();
        while((string = scan.nextLine()).equals("\n")){};
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
        try{
            message.reportUpdate(this, this.view);
        }catch(RemoteException e){

        }
    }

}
