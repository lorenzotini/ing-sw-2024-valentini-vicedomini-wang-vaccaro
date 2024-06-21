package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;

public class ClientTest implements VirtualView {
    @Override
    public void show(String s) throws IOException {
        
    }

    @Override
    public String read() throws IOException, InterruptedException {
        return null;
    }

    @Override
    public void setUsername(String username) throws IOException {

    }

    @Override
    public void runClient() throws IOException, InterruptedException {

    }

    @Override
    public String getUsername() throws IOException {
        return null;
    }

    @Override
    public void sendCommand(Command command) throws RemoteException {

    }

    @Override
    public MiniModel getMiniModel() throws RemoteException {
        return null;
    }

    @Override
    public void update(Message message) throws RemoteException {

    }

    @Override
    public void close() throws RemoteException {

    }

    @Override
    public void pingFromServer() throws RemoteException {

    }
}
