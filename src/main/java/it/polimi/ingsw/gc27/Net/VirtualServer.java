package it.polimi.ingsw.gc27.Net;

import it.polimi.ingsw.gc27.Card.Face;
import it.polimi.ingsw.gc27.Card.GoldCard;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Game.Market;
import it.polimi.ingsw.gc27.Game.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface VirtualServer extends Remote{
    void connect(VirtualView client) throws RemoteException;
    void addCard(Player player, ResourceCard card, Face face, int x, int y);
    void drawCard(Market market, Player player, ArrayList<ResourceCard> deck, ResourceCard card, int faceUpCardIndex);
    void drawCard(Market market, Player player, ArrayList<GoldCard> deck, GoldCard card, int faceUpCardIndex);
}
