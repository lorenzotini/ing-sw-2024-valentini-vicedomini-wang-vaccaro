package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.UpdateHandMessage;
import it.polimi.ingsw.gc27.Messages.UpdateMarketMessage;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Market;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;

import java.util.ArrayList;

public class DrawingState extends PlayerState {

    private final String wrongStateText = "It's time to draw a card!";

    /**
     * State in which a player draws a card from the market
     * constructor matching super {@link PlayerState}
     */
    public DrawingState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) {
        super.sendError(wrongStateText, getPlayer(), turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void drawCard(Player player, boolean isGold, boolean fromDeck, int faceUpCardIndex) throws InterruptedException {

        Market market = turnHandler.getGame().getMarket();
        ArrayList<? extends ResourceCard> deck;
        ResourceCard card;

        deck = isGold ? market.getGoldDeck() : market.getResourceDeck();

        // add card to players hand and replace it on market
        if (fromDeck) { // player drawn card from a deck
            card = deck.removeLast();
        } else { // player drawn a face up card from the market
            card = market.getFaceUp(isGold)[faceUpCardIndex];
            market.setFaceUp(deck.removeLast(), faceUpCardIndex);
        }

        player.getHand().add(card);

        player.setPlayerState(new EndOfTurnState(player, getTurnHandler()));
        getTurnHandler().notifyEndOfTurnState(getPlayer());

        //send messages, one for the updated hand and the other for the updated market
        Message updateHandMessage = new UpdateHandMessage(new MiniModel(player));

        Message updateMarketMessage = new UpdateMarketMessage(new MiniModel(market));
        turnHandler.getGame().notifyObservers(updateHandMessage);
        turnHandler.getGame().notifyObservers(updateMarketMessage);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) {
        super.sendError(wrongStateText, getPlayer(), turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face) {
        super.sendError(wrongStateText, getPlayer(), turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public String toStringGUI(){
        return "Draw a card!";
    }
}
