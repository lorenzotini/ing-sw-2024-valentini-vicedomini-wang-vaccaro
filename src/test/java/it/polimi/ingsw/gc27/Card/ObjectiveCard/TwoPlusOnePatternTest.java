package it.polimi.ingsw.gc27.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.TwoPlusOnePattern;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Controller.JsonParser;
import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Game.*;
import it.polimi.ingsw.gc27.Model.Game.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TwoPlusOnePatternTest {
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
        starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
        resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
        objectiveDeck = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);
        goldDeck = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);

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
        market=new Market(resourceDeck, goldDeck, faceUpResources,faceUpGolds );
        g1.setMarket(market);


        // create game and its controller



        /*
        Collections.shuffle(resourceDeck);
        Collections.shuffle(goldDeck);
        Collections.shuffle(objectiveDeck);
        */
    }

    @Test
    void calculateObjectivePointsTest1() throws IOException, InterruptedException { //test1, plant+insect
        initializeGame();
        p1.addCard(g1, starterDeck.get(2), starterDeck.get(2).getBack(), 42,42);

        p1.addCard(g1, resourceDeck.get(31), resourceDeck.get(31).getFront(), 43, 43);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1, -1).isHidden());

        p1.addCard(g1, resourceDeck.get(15), resourceDeck.get(15).getFront(), 44, 42);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(1, 1).isHidden());

        p1.addCard(g1, resourceDeck.get(32), resourceDeck.get(32).getFront(), 44, 44);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(1, -1).isHidden());

        p1.addCard(g1, goldDeck.get(34), goldDeck.get(34).getFront(), 43, 41);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());

        p1.addCard(g1, resourceDeck.get(17), resourceDeck.get(17).getFront(), 44, 40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(1, 1).isHidden());

        p1.addCard(g1, resourceDeck.get(29), resourceDeck.get(29).getFront(), 42, 40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(-1, 1).isHidden());

        p1.addCard(g1, resourceDeck.get(2), resourceDeck.get(2).getBack(), 41, 39);
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(-1, 1).isHidden());

        p1.addCard(g1, resourceDeck.get(36), resourceDeck.get(36).getBack(), 40, 40);
        assertTrue(p1.getManuscript().getField()[41][39].getCorner(-1, -1).isHidden());

        p1.addCard(g1, goldDeck.get(14), goldDeck.get(14).getFront(), 41, 41);
        assertTrue(p1.getManuscript().getField()[40][40].getCorner(1, -1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(-1, -1).isHidden());


        p1.addCard(g1, goldDeck.get(16), goldDeck.get(16).getFront(), 40, 42);
        assertTrue(p1.getManuscript().getField()[41][41].getCorner(-1, -1).isHidden());

        p1.addCard(g1, goldDeck.get(33), goldDeck.get(33).getBack(), 45, 45);
        assertTrue(p1.getManuscript().getField()[44][44].getCorner(1, -1).isHidden());

        p1.addCard(g1, goldDeck.get(1), goldDeck.get(1).getFront(), 42, 38);
        assertTrue(p1.getManuscript().getField()[41][39].getCorner(1, 1).isHidden());

        p1.addCard(g1, goldDeck.get(14), goldDeck.get(14).getFront(), 43, 39);
        assertTrue(p1.getManuscript().getField()[42][38].getCorner(1, -1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(1, 1).isHidden());
        assertTrue(p1.getManuscript().getField()[44][40].getCorner(-1, 1).isHidden());

        assertEquals(3, objectiveDeck.get(5).calculateObjectivePoints(p1.getManuscript()));
    }

    @Test
    void calculateObjectivePointsTest2() throws IOException, InterruptedException { //test1, animal+insect
        initializeGame();
        p1.addCard(g1, starterDeck.get(2), starterDeck.get(2).getBack(), 42,42);

        p1.addCard(g1, resourceDeck.get(31), resourceDeck.get(31).getFront(), 43, 43);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1, -1).isHidden());

        p1.addCard(g1, resourceDeck.get(15), resourceDeck.get(15).getFront(), 44, 42);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(1, 1).isHidden());

        p1.addCard(g1, resourceDeck.get(32), resourceDeck.get(32).getFront(), 44, 44);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(1, -1).isHidden());

        p1.addCard(g1, goldDeck.get(34), goldDeck.get(34).getFront(), 43, 41);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());

        p1.addCard(g1, resourceDeck.get(17), resourceDeck.get(17).getFront(), 44, 40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(1, 1).isHidden());

        p1.addCard(g1, resourceDeck.get(29), resourceDeck.get(29).getFront(), 42, 40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(-1, 1).isHidden());

        p1.addCard(g1, resourceDeck.get(2), resourceDeck.get(2).getBack(), 41, 39);
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(-1, 1).isHidden());

        p1.addCard(g1, resourceDeck.get(36), resourceDeck.get(36).getBack(), 40, 40);
        assertTrue(p1.getManuscript().getField()[41][39].getCorner(-1, -1).isHidden());

        p1.addCard(g1, goldDeck.get(14), goldDeck.get(14).getFront(), 41, 41);
        assertTrue(p1.getManuscript().getField()[40][40].getCorner(1, -1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(-1, -1).isHidden());


        p1.addCard(g1, goldDeck.get(16), goldDeck.get(16).getFront(), 40, 42);
        assertTrue(p1.getManuscript().getField()[41][41].getCorner(-1, -1).isHidden());

        p1.addCard(g1, goldDeck.get(33), goldDeck.get(33).getBack(), 45, 45);
        assertTrue(p1.getManuscript().getField()[44][44].getCorner(1, -1).isHidden());

        p1.addCard(g1, goldDeck.get(1), goldDeck.get(1).getFront(), 42, 38);
        assertTrue(p1.getManuscript().getField()[41][39].getCorner(1, 1).isHidden());

        p1.addCard(g1, goldDeck.get(14), goldDeck.get(14).getFront(), 43, 39);
        assertTrue(p1.getManuscript().getField()[42][38].getCorner(1, -1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(1, 1).isHidden());
        assertTrue(p1.getManuscript().getField()[44][40].getCorner(-1, 1).isHidden());

        assertEquals(3, objectiveDeck.get(7).calculateObjectivePoints(p1.getManuscript()));

    }

    @Test
    void calculateObjectivePointsTest3() throws IOException, InterruptedException { //test 2, fungi+animal
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

        assertEquals(3, objectiveDeck.get(6).calculateObjectivePoints(p2.getManuscript()));
    }

    @Test
    void calculateObjectivePointsTest4() throws IOException, InterruptedException {
        initializeGame();
        p4.addCard(g1, starterDeck.get(4), starterDeck.get(4).getBack(),42,42);

        p4.addCard(g1, resourceDeck.get(1), resourceDeck.get(1).getBack(), 43, 41);
        assertTrue(p4.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());

        p4.addCard(g1, resourceDeck.get(3), resourceDeck.get(3).getBack(), 44, 40);
        assertTrue(p4.getManuscript().getField()[43][41].getCorner(1, 1).isHidden());

        p4.addCard(g1, resourceDeck.get(4), resourceDeck.get(4).getBack(), 45, 39);
        assertTrue(p4.getManuscript().getField()[44][40].getCorner(1, 1).isHidden());

        p4.addCard(g1, resourceDeck.get(11), resourceDeck.get(11).getFront(), 44, 42);
        assertTrue(p4.getManuscript().getField()[43][41].getCorner(1, -1).isHidden());

        p4.addCard(g1, resourceDeck.get(7), resourceDeck.get(7).getFront(), 45, 43);
        assertTrue(p4.getManuscript().getField()[44][42].getCorner(1, -1).isHidden());

        p4.addCard(g1, resourceDeck.get(12), resourceDeck.get(12).getFront(), 44, 44);
        assertTrue(p4.getManuscript().getField()[45][43].getCorner(-1, -1).isHidden());

        p4.addCard(g1, goldDeck.get(30), goldDeck.get(30).getBack(), 43, 45);
        assertTrue(p4.getManuscript().getField()[44][44].getCorner(-1, -1).isHidden());

        p4.addCard(g1, goldDeck.get(2), goldDeck.get(2).getBack(), 42, 40);
        assertTrue(p4.getManuscript().getField()[43][41].getCorner(-1, 1).isHidden());

        p4.addCard(g1, goldDeck.get(1), goldDeck.get(1).getBack(), 43, 39);
        assertTrue(p4.getManuscript().getField()[42][40].getCorner(1, 1).isHidden());
        assertTrue(p4.getManuscript().getField()[44][40].getCorner(-1, 1).isHidden());

        p4.addCard(g1, goldDeck.get(0), goldDeck.get(0).getBack(), 44, 38);
        assertTrue(p4.getManuscript().getField()[43][39].getCorner(1, 1).isHidden());
        assertTrue(p4.getManuscript().getField()[45][39].getCorner(-1, 1).isHidden());

        p4.addCard(g1, goldDeck.get(19), goldDeck.get(19).getBack(), 41, 41);
        assertTrue(p4.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());
        assertTrue(p4.getManuscript().getField()[42][40].getCorner(-1, -1).isHidden());

        p4.addCard(g1, goldDeck.get(17), goldDeck.get(17).getBack(), 41, 39);
        assertTrue(p4.getManuscript().getField()[42][40].getCorner(1, 1).isHidden());

        p4.addCard(g1, goldDeck.get(31), goldDeck.get(31).getFront(), 40, 42);
        assertTrue(p4.getManuscript().getField()[41][41].getCorner(-1, -1).isHidden());

        p4.addCard(g1, resourceDeck.get(9), resourceDeck.get(9).getFront(), 39, 41);
        assertTrue(p4.getManuscript().getField()[40][42].getCorner(-1, 1).isHidden());

        p4.addCard(g1, goldDeck.get(15), goldDeck.get(15).getFront(), 42, 38);
        assertTrue(p4.getManuscript().getField()[41][39].getCorner(1, 1).isHidden());
        assertTrue(p4.getManuscript().getField()[43][39].getCorner(-1, 1).isHidden());

        assertEquals(3, objectiveDeck.get(4).calculateObjectivePoints(p4.getManuscript()));
    }
}