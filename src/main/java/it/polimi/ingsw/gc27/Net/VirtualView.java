package it.polimi.ingsw.gc27.Net;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.Commands.Command;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote, Serializable {

    void show(String s) throws IOException;

    String read() throws IOException, InterruptedException;

    void setUsername(String username) throws IOException;

    void runClient() throws IOException, InterruptedException;

    String getUsername() throws IOException, InterruptedException;

    void sendCommand(Command command) throws RemoteException;

    MiniModel getMiniModel() throws RemoteException;

    void update(Message message) throws RemoteException;

    void close() throws RemoteException;

    void pingFromServer() throws  RemoteException;
}
