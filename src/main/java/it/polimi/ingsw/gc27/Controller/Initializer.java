package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Card.GoldCard;
import it.polimi.ingsw.gc27.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Card.StarterCard;
import it.polimi.ingsw.gc27.Game.Board;
import it.polimi.ingsw.gc27.Game.Game;
import it.polimi.ingsw.gc27.Game.Market;

import java.util.ArrayList;
import java.util.Collections;

public class Initializer {
    private Game game;
    private  ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
    private  ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
    private  ArrayList<GoldCard> goldDeck = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);
    private  ArrayList<ObjectiveCard> objectiveDeck = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);
    private  int gameId;
    private  Market market = new Market();
    private  Board board = new Board();

    public  Game initialize(){

        // shuffle decks
        Collections.shuffle(starterDeck);
        Collections.shuffle(resourceDeck);
        Collections.shuffle(goldDeck);
        Collections.shuffle(objectiveDeck);

        // populate market
        market.setFaceUpResources(resourceDeck.removeLast(), 0);
        market.setFaceUpResources(resourceDeck.removeLast(), 1);
        market.setFaceUpGolds(goldDeck.removeLast(), 0);
        market.setFaceUpGolds(goldDeck.removeLast(), 1);
        market.setResourceDeck(resourceDeck);
        market.setGoldDeck(goldDeck);

        // set common objectives
        ObjectiveCard commObj1 = objectiveDeck.removeLast();
        ObjectiveCard commObj2 = objectiveDeck.removeLast();

        game = new Game(gameId, board, market, new ArrayList<>(), commObj1, commObj2, starterDeck, objectiveDeck);

        return game;
    }
}