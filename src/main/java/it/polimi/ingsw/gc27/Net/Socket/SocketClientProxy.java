package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;

public class SocketClientProxy implements VirtualView {

    final PrintWriter output;

    public SocketClientProxy(BufferedWriter output) throws IOException {
        this.output = new PrintWriter(output);

    }

    public void runCli(){
        this.output.println("runCli");
        this.output.flush();
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public void showUpdate(String update) {

    }

    @Override
    public void show(String s) throws RemoteException {
        String ss[] = s.split("\n");


        for(String phrase : ss){
            output.println("show "+phrase);
            output.flush();
        }

    }

    @Override
    public void showManuscript(Manuscript manuscript) throws RemoteException {
        output.println("send Manuscript"+ manuscript);
        output.flush();
    }

    @Override
    public void showStarter(StarterCard starterCard) throws RemoteException {

    }

    @Override
    public String read() throws RemoteException {
        output.println("read");
        output.flush();
        return null;
    }

    @Override
    public void setUsername(String username) throws RemoteException {
        output.println("setUsername "+ username);
        output.flush();
    }


    @Override
    public void update(String mex) {

    }
}
