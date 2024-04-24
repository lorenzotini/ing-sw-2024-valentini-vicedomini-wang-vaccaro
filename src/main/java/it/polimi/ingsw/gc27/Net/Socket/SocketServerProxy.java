package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Card.Face;
import it.polimi.ingsw.gc27.Card.FrontFace;
import it.polimi.ingsw.gc27.Card.GoldCard;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Game.Market;
import it.polimi.ingsw.gc27.Game.Player;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
/*
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
    public void addCard(Player player, ResourceCard card, Face face, int x, int y) {
        output.println("addCard");
        output.println(player.getUsername());
        output.println(card.getCardID());
        if(face instanceof FrontFace)
            output.println(1);
        else
            output.println(0);
        output.println(x);
        output.println(y);
        output.flush();
    }

    @Override
    public void drawResourceCard(Player player, boolean fromDeck, int faceUpCardIndex) throws RemoteException {
        output.println("drawResourceCard");
        output.println(player.getUsername());
        output.println(fromDeck);
        output.println(faceUpCardIndex);
        output.flush();
    }


    @Override
    public void drawGoldCard(Player player, boolean fromDeck, int faceUpCardIndex) throws RemoteException {
        output.println("drawResourceCard");
        output.println(player.getUsername());
        output.println(fromDeck);
        output.println(faceUpCardIndex);
        output.flush();
    }

    /*
    @Override
    public Player welcomePlayer(VirtualView client) throws RemoteException {

        output.println("welcomePlayer");
        output.flush();
    }*/

/*
    public void send(String command){
        output.println(command);
        output.flush();
    }
}
*/