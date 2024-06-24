package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Queue;

public class ClientTest implements VirtualView{
    private String username;
    private String lastShownMessage;
    private Queue<String> nextReads = new LinkedList<>();
    public Queue<String> nextShows = new LinkedList<>();

    public void setNextRead(String nextRead) {
        nextReads.add(nextRead);
    }

    public String getLastShownMessage() {
        return lastShownMessage;
    }

    public void show(String s) throws IOException {
        nextShows.add(s);
    }


    public String read() {
        return nextReads.poll();
    }


    public void setUsername(String username) throws IOException {

    }


    public void runClient() {

    }


    public String getUsername() throws IOException {
        return null;
    }


    public void sendCommand(Command command) throws RemoteException {

    }


    public MiniModel getMiniModel() throws RemoteException {
        return null;
    }


    public void update(Message message) throws RemoteException {

    }


    public void close() throws RemoteException {

    }


    public void pingFromServer() throws RemoteException {

    }
}
