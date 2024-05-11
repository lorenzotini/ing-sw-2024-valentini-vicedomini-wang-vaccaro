package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.NotYourTurnMessage;
import it.polimi.ingsw.gc27.Messages.UpdateHandMessage;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Market;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.Listener.Observable;
import it.polimi.ingsw.gc27.Model.Listener.Observer;
import it.polimi.ingsw.gc27.Model.MiniModel;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class DrawingState extends PlayerState  {
    public DrawingState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
    }

    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) throws RemoteException {
        MiniModel miniWithCurrentP = new MiniModel(getPlayer());
        Message genericErrorMessage = new NotYourTurnMessage("It's time to draw a card!", miniWithCurrentP);
        turnHandler.getGame().notifyObservers(genericErrorMessage);
    }

    @Override
    public void drawResourceCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) throws RemoteException {
        Market market = game.getMarket();
        ArrayList<ResourceCard> deck = market.getResourceDeck();
        ResourceCard card;

        // add card to players hand and replace it on market
        if(fromDeck){ // player drawn card from a deck
            card = deck.removeLast();
        }
        else{ // player drawn a face up card from the market
            card = market.getFaceUpResources()[faceUpCardIndex];
            market.setFaceUpResources(deck.removeLast(), faceUpCardIndex);
        }

        player.getHand().add(card);

        //send messages, one for the updated hand and the other for the updated market
        Message updateHandMessage = new UpdateHandMessage(new MiniModel(player, player.getHand()));
        turnHandler.getGame().notifyObservers(updateHandMessage);

        Message updateMarketMessage = new UpdateHandMessage(new MiniModel(market));
        turnHandler.getGame().notifyObservers(updateMarketMessage);

        player.setPlayerState(new EndOfTurnState(getPlayer(), getTurnHandler()));
    }

    @Override
    public void drawGoldCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) throws RemoteException {
        Market market = game.getMarket();
        ArrayList<GoldCard> deck = market.getGoldDeck();
        GoldCard card;

        // add card to players hand and replace it on market
        if(fromDeck){ // player drawn card from a deck
            card = deck.removeLast();
        }
        else{ // player drawn a face up card from the market
            card = market.getFaceUpGolds()[faceUpCardIndex];
            market.setFaceUpGolds(deck.removeLast(), faceUpCardIndex);
        }
        player.getHand().add(card);

        //send messages, one for the updated hand and the other for the updated market
        Message updateHandMessage = new UpdateHandMessage(new MiniModel(player, player.getHand()));
        turnHandler.getGame().notifyObservers(updateHandMessage);

        Message updateMarketMessage = new UpdateHandMessage(new MiniModel(market));
        turnHandler.getGame().notifyObservers(updateMarketMessage);
        player.setPlayerState(new EndOfTurnState(getPlayer(), getTurnHandler()));
    }

    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) throws RemoteException {
        MiniModel miniWithCurrentP = new MiniModel(getPlayer());
        Message genericErrorMessage = new NotYourTurnMessage("It's time to draw a card!",  miniWithCurrentP);
        turnHandler.getGame().notifyObservers(genericErrorMessage);
    }

    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face) throws IOException, InterruptedException {
        MiniModel currentPlayer = new MiniModel(getPlayer());
        Message genericErrorMessage = new NotYourTurnMessage("it's too late man", currentPlayer);
        turnHandler.getGame().notifyObservers(genericErrorMessage);
    }
}
