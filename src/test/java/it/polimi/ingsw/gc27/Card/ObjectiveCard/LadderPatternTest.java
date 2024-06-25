package it.polimi.ingsw.gc27.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.LadderPattern;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Utils.JsonParser;
import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Game.*;
import it.polimi.ingsw.gc27.Model.Game.*;
import it.polimi.ingsw.gc27.Model.States.InitializingState;
import it.polimi.ingsw.gc27.Model.States.PlayingState;
import it.polimi.ingsw.gc27.View.*;
import it.polimi.ingsw.gc27.View.Tui.Tui;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LadderPatternTest {
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
    private ResourceCard[] faceUpResources = new ResourceCard[2];
    private GoldCard[] faceUpGolds = new GoldCard[2];

    public void initializeGame() {

        players1 = new ArrayList<>();
        g1 = new Game(1, new Board(), players1);
        gc1 = new GameController(g1);


        // generate decks
        JsonParser jsonParser = new JsonParser("codex_cards_collection.json");
        starterDeck = jsonParser.getStarterDeck();
        objectiveDeck = jsonParser.getObjectiveDeck();
        resourceDeck = jsonParser.getResourceDeck();
        goldDeck= jsonParser.getGoldDeck();


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

        faceUpResources[0]= resourceDeck.get(0);
        faceUpResources[1]= resourceDeck.get(1);
        faceUpGolds[0]=goldDeck.get(0);
        faceUpGolds[1]=goldDeck.get(1);
        market=new Market(resourceDeck, goldDeck, faceUpResources,faceUpGolds, objectiveDeck);
        g1.setMarket(market);


        // create game and its controller



        /*
        Collections.shuffle(resourceDeck);
        Collections.shuffle(goldDeck);
        Collections.shuffle(objectiveDeck);
        */
    }

    @Test
    void calculateObjectivePointsTest1() throws IOException, InterruptedException { //test3, plant ladder
        initializeGame();
        p3.addCard(g1, starterDeck.get(0), starterDeck.get(0).getBack(),42,42);

        p3.addCard(g1, resourceDeck.get(15), resourceDeck.get(15).getFront(), 41, 41);
        assertTrue(p3.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());

        p3.addCard(g1, resourceDeck.get(17), resourceDeck.get(17).getFront(), 40, 40);
        assertTrue(p3.getManuscript().getField()[41][41].getCorner(-1, 1).isHidden());

        p3.addCard(g1, resourceDeck.get(14), resourceDeck.get(14).getFront(), 39, 39);
        assertTrue(p3.getManuscript().getField()[40][40].getCorner(-1, 1).isHidden());

        p3.addCard(g1, resourceDeck.get(36), resourceDeck.get(36).getFront(), 43, 43);
        assertTrue(p3.getManuscript().getField()[42][42].getCorner(1, -1).isHidden());

        p3.addCard(g1, resourceDeck.get(0), resourceDeck.get(0).getBack(), 44, 42);
        assertTrue(p3.getManuscript().getField()[43][43].getCorner(1, 1).isHidden());

        p3.addCard(g1, resourceDeck.get(33), resourceDeck.get(33).getFront(), 43, 41);
        assertTrue(p3.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());
        assertTrue(p3.getManuscript().getField()[44][42].getCorner(-1, 1).isHidden());

        p3.addCard(g1, resourceDeck.get(29), resourceDeck.get(29).getFront(), 42, 40);
        assertTrue(p3.getManuscript().getField()[41][41].getCorner(1, 1).isHidden());
        assertTrue(p3.getManuscript().getField()[43][41].getCorner(-1, 1).isHidden());

        p3.addCard(g1, goldDeck.get(33), goldDeck.get(33).getFront(), 41, 39);
        assertTrue(p3.getManuscript().getField()[40][40].getCorner(1, 1).isHidden());
        assertTrue(p3.getManuscript().getField()[42][40].getCorner(-1, 1).isHidden());

        p3.addCard(g1, goldDeck.get(39), goldDeck.get(39).getFront(), 45, 41);
        assertTrue(p3.getManuscript().getField()[44][42].getCorner(1, 1).isHidden());

        p3.addCard(g1, resourceDeck.get(10), resourceDeck.get(10).getBack(), 46, 40);
        assertTrue(p3.getManuscript().getField()[45][41].getCorner(1, 1).isHidden());

        p3.addCard(g1, goldDeck.get(18), goldDeck.get(18).getFront(), 45, 43);
        assertTrue(p3.getManuscript().getField()[44][42].getCorner(1, -1).isHidden());

        p3.addCard(g1, resourceDeck.get(16), resourceDeck.get(16).getFront(), 41, 43);


        assertEquals(2, objectiveDeck.get(1).calculateObjectivePoints(p3.getManuscript()));

    }

    @Test
    void calculateObjectivePointsTest2() throws IOException, InterruptedException { //test2, animal ladder
        initializeGame();
        p2.addCard(g1, starterDeck.get(1), starterDeck.get(1).getFront(),42,42);

        p2.addCard(g1, resourceDeck.get(24), resourceDeck.get(24).getFront(), 41, 43);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(-1, -1).isHidden());

        p2.addCard(g1, resourceDeck.get(25), resourceDeck.get(25).getFront(), 41, 41);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());

        p2.addCard(g1, resourceDeck.get(28), resourceDeck.get(28).getFront(), 40, 44);
        assertTrue(p2.getManuscript().getField()[41][43].getCorner(-1, -1).isHidden());

        p2.addCard(g1, goldDeck.get(26), goldDeck.get(26).getFront(), 39, 45);
        assertTrue(p2.getManuscript().getField()[40][44].getCorner(-1, -1).isHidden());

        p2.addCard(g1, resourceDeck.get(6), resourceDeck.get(6).getFront(), 42, 40);
        assertTrue(p2.getManuscript().getField()[41][41].getCorner(1, 1).isHidden());

        p2.addCard(g1, goldDeck.get(35), goldDeck.get(35).getFront(), 43, 41);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());
        assertTrue(p2.getManuscript().getField()[42][40].getCorner(1, -1).isHidden());

        p2.addCard(g1, goldDeck.get(37), goldDeck.get(37).getFront(), 38, 44);
        assertTrue(p2.getManuscript().getField()[39][45].getCorner(-1, 1).isHidden());

        p2.addCard(g1, resourceDeck.get(16), resourceDeck.get(16).getFront(), 44, 42);
        assertTrue(p2.getManuscript().getField()[43][41].getCorner(1, -1).isHidden());

        p2.addCard(g1, resourceDeck.get(18), resourceDeck.get(18).getBack(), 42, 44);
        assertTrue(p2.getManuscript().getField()[41][43].getCorner(1, -1).isHidden());

        p2.addCard(g1, goldDeck.get(34), goldDeck.get(34).getFront(), 43, 43);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(1, -1).isHidden());
        assertTrue(p2.getManuscript().getField()[44][42].getCorner(-1, -1).isHidden());
        assertTrue(p2.getManuscript().getField()[42][44].getCorner(1, 1).isHidden());

        assertEquals(2, objectiveDeck.get(2).calculateObjectivePoints(p2.getManuscript()));




    }

}