package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Market;
import it.polimi.ingsw.gc27.Utils.JsonParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InitializerTest {
    private static Initializer init=new Initializer();
    private static GameController gc;
    private static Game game;
    private static ArrayList<StarterCard> starterDeck;
    private static ArrayList<ResourceCard> resourceDeck;
    private static ArrayList<GoldCard> goldDeck;
    private static ArrayList<ObjectiveCard> objectiveDeck;
    private static int gameId;
    private static Market market = new Market();
    private static Board board = new Board();
    @Test
    void initializeTest() {

        JsonParser jsonParser = new JsonParser("codex_cards_collection.json");
        starterDeck = jsonParser.getStarterDeck();
        resourceDeck = jsonParser.getResourceDeck();
        goldDeck = jsonParser.getGoldDeck();
        objectiveDeck = jsonParser.getObjectiveDeck();

        game = init.initialize();

        assertNotNull(game);

        assertFalse(game.getMarket().getResourceDeck().isEmpty());
        assertFalse(game.getMarket().getGoldDeck().isEmpty());

        assertNotNull(game.getCommonObjective1());
        assertNotNull(game.getCommonObjective2());

        assertNotNull(game.getBoard());

    }
}