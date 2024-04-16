package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;

public class SocketClientProxy implements VirtualView {

    final PrintWriter output;

    public SocketClientProxy(BufferedWriter output) {
        this.output = new PrintWriter(output);
    }


    @Override
    public void showUpdate(String update) {

    }

    @Override
    public void show(String s) throws RemoteException {

    }

    @Override
    public String read() throws RemoteException {
        return null;
    }
}
