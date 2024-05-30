package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.JsonParser;
import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Market;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InitializerTest {
    private static Initializer init=new Initializer();
    private static GameController gc;
    private static Game game;
    private static ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
    private  static ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
    private  static ArrayList<GoldCard> goldDeck = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);
    private  static ArrayList<ObjectiveCard> objectiveDeck = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);
    private  static int gameId;
    private static Market market = new Market();
    private static Board board = new Board();
    @Test
    void initializeTest() {

        game = init.initialize();

        assertNotNull(game);

assertFalse(game.getMarket().getResourceDeck().isEmpty());
        assertFalse(game.getMarket().getGoldDeck().isEmpty());

        assertNotNull(game.getCommonObjective1());
        assertNotNull(game.getCommonObjective2());

        assertNotNull(game.getBoard());

    }
}