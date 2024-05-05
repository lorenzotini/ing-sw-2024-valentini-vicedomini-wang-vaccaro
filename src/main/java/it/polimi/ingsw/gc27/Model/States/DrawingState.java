package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Market;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.View.ColourControl;

import java.io.IOException;
import java.util.ArrayList;

public class DrawingState extends PlayerState {
    public DrawingState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
    }

    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) {

    }

    @Override
    public void drawResourceCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) {
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
        player.setPlayerState(new EndOfTurnState(getPlayer(), getTurnHandler()));
        //market.notifyObservers();

    }

    @Override
    public void drawGoldCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) {
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
        player.setPlayerState(new EndOfTurnState(getPlayer(), getTurnHandler()));
    }

    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) {
        System.out.println(ColourControl.RED + "You've already played a card, draw one please\n" + ColourControl.RESET);
    }

    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face) throws IOException, InterruptedException{
        System.out.println("you already have a starter card\n");
    }

}
