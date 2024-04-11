package it.polimi.ingsw.gc27.Net;

import it.polimi.ingsw.gc27.Card.Face;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Game.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote{
    void connect(VirtualView client) throws RemoteException;
    void addCard(Player player, ResourceCard card, Face face, int x, int y) throws RemoteException;
    void drawResourceCard(Player player, boolean fromDeck, int faceUpCardIndex) throws RemoteException;
    void drawGoldCard(Player player, boolean fromDeck, int faceUpCardIndex) throws RemoteException;
    Player welcomePlayer(VirtualView client) throws RemoteException;
}
