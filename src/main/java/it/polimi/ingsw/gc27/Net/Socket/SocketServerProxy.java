package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;

public class SocketServerProxy implements VirtualServer {
    final PrintWriter output;

    public SocketServerProxy(BufferedWriter output) {
        this.output = new PrintWriter(output);


    }
    @Override
    public void connect(VirtualView client) throws RemoteException {
        output.println("connect");
        output.flush();
    }

    @Override
    public void addCard(String playerName, int handCardIndex, boolean isFrontFace, int x, int y) throws RemoteException {
        output.println("addCard");
        output.println(playerName);
        output.println(handCardIndex);
        if (isFrontFace) {
            output.println(1);
        } else {
            output.println(0);
        }
        output.println(x);
        output.println(y);
        output.flush();
    }

    @Override
    public void drawResourceCard(String playerName, boolean fromDeck, int faceUpCardIndex) throws RemoteException{
        output.println("drawResourceCard");
        output.println(playerName);
        output.println(fromDeck);
        output.println(faceUpCardIndex);
        output.flush();
    }


    @Override
    public void drawGoldCard(String playerName, boolean fromDeck, int faceUpCardIndex) throws RemoteException{
        output.println("drawResourceCard");
        output.println(playerName);
        output.println(fromDeck);
        output.println(faceUpCardIndex);
        output.flush();
    }
    public void askStarter(String playerName) throws IOException, InterruptedException{}
    @Override
    public void welcomePlayer(VirtualView client) throws RemoteException {

        output.println("welcomeplayer");
        output.flush();


    }
    public void sendData(String s){
        output.println(s);
        output.flush();
    }
}
