package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Commands.Command;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Queue;
// this class is used to mock a client, all the methods implement the methods of the VirtualView interface
public class ClientTest implements VirtualView{
    private String username;
    private Queue<String> nextReads = new LinkedList<>();
    public Queue<String> nextShows = new LinkedList<>();

    public void setNextRead(String nextRead) {
        nextReads.add(nextRead);
    }

    public void show(String s) {nextShows.add(s);}
    public String read() {
        return nextReads.poll();
    }

    public void setUsername(String username)  {this.username= username;}
    public void runClient() {}
    public String getUsername()  {
        return null;
    }
    public void sendCommand(Command command) throws RemoteException {}
    public MiniModel getMiniModel() throws RemoteException {return null;}
    public void update(Message message)  {}
    public void close() throws RemoteException {}
    public void pingFromServer() throws RemoteException {}
}
