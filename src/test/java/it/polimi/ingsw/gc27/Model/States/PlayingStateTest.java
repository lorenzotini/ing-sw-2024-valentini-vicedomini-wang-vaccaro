package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.*;
import it.polimi.ingsw.gc27.Utils.JsonParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayingStateTest {
    private static GameController gc1;
    private static Game g1;
    private static Player p1;
    private static Player p2;
    private static Player p3;
    private static Player p4;
    private static ArrayList<Player> players1;

    private static Market market;

    private static ArrayList<StarterCard> starterDeck;
    private static ArrayList<ResourceCard> resourceDeck;
    private static ArrayList<ObjectiveCard> objectiveDeck;
    private static ArrayList<GoldCard> goldDeck;
    private static ResourceCard[] faceUpResources;
    private static GoldCard[] faceUpGolds;
    public  void initializeGame() {

        players1 = new ArrayList<>();
        g1 = new Game(1, new Board(), players1);
        gc1 = new GameController(g1);


        // generate decks
        JsonParser jsonParser = new JsonParser("codex_cards_collection.json");
        starterDeck = jsonParser.getStarterDeck();
        resourceDeck = jsonParser.getResourceDeck();
        goldDeck = jsonParser.getGoldDeck();
        objectiveDeck = jsonParser.getObjectiveDeck();

        // create players and add them to the game.
        p1 = new Player("Giocatore 1", new Manuscript(), PawnColour.RED);
        p1.setHand(new ArrayList<ResourceCard>());
        p2 = new Player("Giocatore 2", new Manuscript(), PawnColour.GREEN);
        p2.setHand(new ArrayList<ResourceCard>());
        p3 = new Player("Giocatore 3", new Manuscript(), PawnColour.BLUE);
        p3.setHand(new ArrayList<ResourceCard>());
        p4 = new Player("Giocatore 4", new Manuscript(), PawnColour.YELLOW);
        p4.setHand(new ArrayList<ResourceCard>());

        players1.add(p1);
        players1.add(p2);
        players1.add(p3);
        players1.add(p4);

        faceUpResources = new ResourceCard[2];
        faceUpGolds= new GoldCard[2];
        faceUpResources[0]= resourceDeck.get(0);
        faceUpResources[1]= resourceDeck.get(1);
        faceUpGolds[0]=goldDeck.get(0);
        faceUpGolds[1]=goldDeck.get(1);
        market=new Market(resourceDeck, goldDeck, faceUpResources,faceUpGolds,objectiveDeck );
        g1.setMarket(market);
        // create game and its controller
        /*
        Collections.shuffle(resourceDeck);
        Collections.shuffle(goldDeck);
        Collections.shuffle(objectiveDeck);
        */
    }

    @Test
    void chooseObjectiveCard() {
        initializeGame();
        p1.setPlayerState(new PlayingState(p1,new TurnHandler(g1)));
        p1.getPlayerState().chooseObjectiveCard(g1, 0);
        assertEquals(p1.getPlayerState().toString(), "PlayingState");
    }

    @Test
    void drawCard() {
        initializeGame();
        p1.setPlayerState(new PlayingState(p1,new TurnHandler(g1)));
        try {
            p1.getPlayerState().drawCard(p1, true,true,0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals(p1.getPlayerState().toString(), "PlayingState");

    }

    @Test
    void addCard() {
        initializeGame();
        p1.setPlayerState(new PlayingState(p1,new TurnHandler(g1)));
        //p1.getManuscript().getField()[42][42].
        p1.addCard(g1, resourceDeck.getFirst(),resourceDeck.getFirst().getFront(), 42,42);


    }

    @Test
    void addStarterCard() {
        initializeGame();
        p1.setPlayerState(new PlayingState(p1,new TurnHandler(g1)));
        p1.getPlayerState().addStarterCard(g1, starterDeck.getFirst(), starterDeck.getFirst().getFront());
        assertEquals(p1.getPlayerState().toString(), "PlayingState");
    }

    @Test
    void toStringGUI() {
    }
}