package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.JsonParser;
import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Market;

import java.util.ArrayList;
import java.util.Collections;

public class Initializer {
    private  Game game;
    private  ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
    private  ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
    private  ArrayList<GoldCard> goldDeck = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);
    private  ArrayList<ObjectiveCard> objectiveDeck = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);
    private  Market market = new Market();
    //private  Board board = new Board(game.getPlayers());
    private  Board board = new Board();
    public  Game initialize(){

        // shuffle decks
        Collections.shuffle(starterDeck);
        Collections.shuffle(resourceDeck);
        Collections.shuffle(goldDeck);
        Collections.shuffle(objectiveDeck);

        // set common objectives
        ObjectiveCard commObj1 = objectiveDeck.removeLast();
        ObjectiveCard commObj2 = objectiveDeck.removeLast();
        ArrayList<ObjectiveCard> commonObjectives = new ArrayList<>();
        commonObjectives.add(commObj1);
        commonObjectives.add(commObj2);

        // populate market
        market.setFaceUp(resourceDeck.removeLast(), 0);
        market.setFaceUp(resourceDeck.removeLast(), 1);
        market.setFaceUp(goldDeck.removeLast(), 0);
        market.setFaceUp(goldDeck.removeLast(), 1);
        market.setResourceDeck(resourceDeck);
        market.setGoldDeck(goldDeck);
        market.setCommonObjectives(commonObjectives);

        return new Game(board, market, new ArrayList<>(), commObj1, commObj2, starterDeck, objectiveDeck);

    }
}