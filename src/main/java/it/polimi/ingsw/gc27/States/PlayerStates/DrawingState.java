package it.polimi.ingsw.gc27.States.PlayerStates;

import it.polimi.ingsw.gc27.Card.Face;
import it.polimi.ingsw.gc27.Card.GoldCard;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Card.StarterCard;
import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Game.Game;
import it.polimi.ingsw.gc27.Game.Market;
import it.polimi.ingsw.gc27.Game.Player;
import it.polimi.ingsw.gc27.View.ColourControl;

import java.util.ArrayList;

public class DrawingState extends PlayerState {
    public DrawingState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
    }

    @Override
    public void drawCard(Market market, Player player, ArrayList<ResourceCard> deck, ResourceCard card, int faceUpCardIndex) {

        if(deck != null && card != null){
            throw new IllegalArgumentException("Something went wrong: impossible call to drawCard method");
        }

        player.getHand().add(card);

        //replace card on market
        if(deck == null){ // player drawn a face up card from the market
            market.setFaceUpResources(market.getResourceDeck().removeLast(), faceUpCardIndex);
        }else{  // player drawn card from a deck
            market.getResourceDeck().removeLast();
        }

        //go to next state
        getPlayer().setPlayerState(new EndOfTurnState(getPlayer(), getTurnHandler()));
    }

    @Override
    public void drawCard(Market market, Player player, ArrayList<GoldCard> deck, GoldCard card, int faceUpCardIndex) {
        if(deck != null && card != null){
            throw new IllegalArgumentException("Something went wrong: impossible call to drawCard method");
        }

        player.getHand().add(card);

        //replace card on market
        if(deck == null){ // player drawn a face up card from the market
            market.setFaceUpGolds(market.getGoldDeck().removeLast(), faceUpCardIndex);
        }else{  // player drawn card from a deck
            market.getGoldDeck().removeLast();
        }

        //go to next state
        getPlayer().setPlayerState(new EndOfTurnState(getPlayer(), getTurnHandler()));
    }

    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) {
        System.out.println(ColourControl.RED + "You've already played a card, draw one please\n" + ColourControl.RESET);
    }

    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face, int x, int y) {
        System.out.println("you already have a starter card\n");
    }

}
