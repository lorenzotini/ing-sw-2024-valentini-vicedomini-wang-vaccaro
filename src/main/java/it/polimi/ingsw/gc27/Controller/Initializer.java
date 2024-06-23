package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Utils.JsonParser;
import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Market;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The Initializer class sets up and initializes components required to start a game.
 * It prepares decks, shuffles them, sets common objectives, and populates the game market.
 */
public class Initializer {

    private  ArrayList<StarterCard> starterDeck;
    private  ArrayList<ResourceCard> resourceDeck;
    private  ArrayList<GoldCard> goldDeck;
    private  ArrayList<ObjectiveCard> objectiveDeck;
    private  Market market;
    private  Board board;

    /**
     * Constructor that initializes the game components by parsing JSON data from a file.
     * It sets up the starter deck, resource deck, gold deck, and objective deck.
     * It also initializes the market and board for the game.
     */
    public Initializer () {
        JsonParser jsonParser = new JsonParser("codex_cards_collection.json");
        starterDeck = jsonParser.getStarterDeck();
        resourceDeck = jsonParser.getResourceDeck();
        goldDeck = jsonParser.getGoldDeck();
        objectiveDeck = jsonParser.getObjectiveDeck();
        market = new Market();
        board = new Board();
    }

    /**
     * creates and initializes the required components in order to play a game successfully
     * shuffles the decks
     * sets common objectives
     * populates the market
     * @return the initialized game
     */
    public  Game initialize(){

        // shuffle decks
        Collections.shuffle(starterDeck);
        Collections.shuffle(resourceDeck);
        Collections.shuffle(goldDeck);
        Collections.shuffle(objectiveDeck);

        for(int i=0 ; i<32; i++){
            resourceDeck.removeFirst();
            goldDeck.removeFirst();
        }

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