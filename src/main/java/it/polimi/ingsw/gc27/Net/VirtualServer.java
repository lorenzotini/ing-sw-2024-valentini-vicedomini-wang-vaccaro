package it.polimi.ingsw.gc27.Net;

import it.polimi.ingsw.gc27.Game.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote{
    void connect(VirtualView client) throws RemoteException;
    void addCard(String playerName, int handCardIndex, boolean isFrontFace, int x, int y) throws RemoteException;
    void drawResourceCard(String playerName, boolean fromDeck, int faceUpCardIndex) throws RemoteException;
    void drawGoldCard(String playerName, boolean fromDeck, int faceUpCardIndex) throws RemoteException;
    Player welcomePlayer(VirtualView client) throws RemoteException;
}
/*public interface VirtualServer extends Remote{
    void connect(VirtualView client) throws RemoteException;
    void sendMessage(Message message);
}*/