package it.polimi.ingsw.gc27.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Card.GoldCard;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Card.StarterCard;
import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Controller.JsonParser;
import it.polimi.ingsw.gc27.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Game.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DoublePatternTest {
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
    void calculateObjectivePointsTest1() { //test1 double inkwell
        initializeGame();
        gc1.addStarterCard(p1, starterDeck.get(2), starterDeck.get(2).getBack());

        gc1.addCard(p1, resourceDeck.get(31), resourceDeck.get(31).getFront(), 43, 43);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1, -1).isHidden());

        gc1.addCard(p1, resourceDeck.get(15), resourceDeck.get(15).getFront(), 44, 42);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(1, 1).isHidden());

        gc1.addCard(p1, resourceDeck.get(32), resourceDeck.get(32).getFront(), 44, 44);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(1, -1).isHidden());

        gc1.addCard(p1, goldDeck.get(34), goldDeck.get(34).getFront(), 43, 41);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());

        gc1.addCard(p1, resourceDeck.get(17), resourceDeck.get(17).getFront(), 44, 40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(1, 1).isHidden());

        gc1.addCard(p1, resourceDeck.get(29), resourceDeck.get(29).getFront(), 42, 40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(-1, 1).isHidden());

        gc1.addCard(p1, resourceDeck.get(2), resourceDeck.get(2).getBack(), 41, 39);
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(-1, 1).isHidden());

        gc1.addCard(p1, resourceDeck.get(36), resourceDeck.get(36).getBack(), 40, 40);
        assertTrue(p1.getManuscript().getField()[41][39].getCorner(-1, -1).isHidden());

        gc1.addCard(p1, goldDeck.get(14), goldDeck.get(14).getFront(), 41, 41);
        assertTrue(p1.getManuscript().getField()[40][40].getCorner(1, -1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(-1, -1).isHidden());


        gc1.addCard(p1, goldDeck.get(16), goldDeck.get(16).getFront(), 40, 42);
        assertTrue(p1.getManuscript().getField()[41][41].getCorner(-1, -1).isHidden());

        gc1.addCard(p1, goldDeck.get(33), goldDeck.get(33).getBack(), 45, 45);
        assertTrue(p1.getManuscript().getField()[44][44].getCorner(1, -1).isHidden());

        gc1.addCard(p1, goldDeck.get(1), goldDeck.get(1).getFront(), 42, 38);
        assertTrue(p1.getManuscript().getField()[41][39].getCorner(1, 1).isHidden());

        gc1.addCard(p1, goldDeck.get(14), goldDeck.get(14).getFront(), 43, 39);
        assertTrue(p1.getManuscript().getField()[42][38].getCorner(1, -1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(1, 1).isHidden());
        assertTrue(p1.getManuscript().getField()[44][40].getCorner(-1, 1).isHidden());

        DoublePattern double_inkwell=new DoublePattern(101, objectiveDeck.get(13).getFront(), objectiveDeck.get(13).getBack(), CornerSymbol.INKWELL);
        assertEquals(2, double_inkwell.calculateObjectivePoints(p1.getManuscript()));
    }
    @Test
    void calculateObjectivePointsTest2() { //test2 double manuscript
        initializeGame();
        gc1.addStarterCard(p2, starterDeck.get(1), starterDeck.get(1).getFront());

        gc1.addCard(p2, resourceDeck.get(24), resourceDeck.get(24).getFront(), 41, 43);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(-1, -1).isHidden());

        gc1.addCard(p2, resourceDeck.get(25), resourceDeck.get(25).getFront(), 41, 41);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());

        gc1.addCard(p2, resourceDeck.get(28), resourceDeck.get(28).getFront(), 40, 44);
        assertTrue(p2.getManuscript().getField()[41][43].getCorner(-1, -1).isHidden());

        gc1.addCard(p2, goldDeck.get(26), goldDeck.get(26).getFront(), 39, 45);
        assertTrue(p2.getManuscript().getField()[40][44].getCorner(-1, -1).isHidden());

        gc1.addCard(p2, resourceDeck.get(6), resourceDeck.get(6).getFront(), 42, 40);
        assertTrue(p2.getManuscript().getField()[41][41].getCorner(1, 1).isHidden());

        gc1.addCard(p2, goldDeck.get(35), goldDeck.get(35).getFront(), 43, 41);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());
        assertTrue(p2.getManuscript().getField()[42][40].getCorner(1, -1).isHidden());

        gc1.addCard(p2, goldDeck.get(37), goldDeck.get(37).getFront(), 38, 44);
        assertTrue(p2.getManuscript().getField()[39][45].getCorner(-1, 1).isHidden());

        gc1.addCard(p2, resourceDeck.get(16), resourceDeck.get(16).getFront(), 44, 42);
        assertTrue(p2.getManuscript().getField()[43][41].getCorner(1, -1).isHidden());

        gc1.addCard(p2, resourceDeck.get(18), resourceDeck.get(18).getBack(), 42, 44);
        assertTrue(p2.getManuscript().getField()[41][43].getCorner(1, -1).isHidden());

        gc1.addCard(p2, goldDeck.get(34), goldDeck.get(34).getFront(), 43, 43);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(1, -1).isHidden());
        assertTrue(p2.getManuscript().getField()[44][42].getCorner(-1, -1).isHidden());
        assertTrue(p2.getManuscript().getField()[42][44].getCorner(1, 1).isHidden());

        DoublePattern double_inkwell=new DoublePattern(100, objectiveDeck.get(12).getFront(), objectiveDeck.get(12).getBack(), CornerSymbol.MANUSCRIPT);
        assertEquals(4, double_inkwell.calculateObjectivePoints(p2.getManuscript()));
    }
    @Test
    void calculateObjectivePointsTest3() { //test1 double quill (added card 5 in position 46,46 not displayed in the example shared)
        initializeGame();
        gc1.addStarterCard(p1, starterDeck.get(2), starterDeck.get(2).getBack());

        gc1.addCard(p1, resourceDeck.get(31), resourceDeck.get(31).getFront(), 43, 43);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1, -1).isHidden());

        gc1.addCard(p1, resourceDeck.get(15), resourceDeck.get(15).getFront(), 44, 42);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(1, 1).isHidden());

        gc1.addCard(p1, resourceDeck.get(32), resourceDeck.get(32).getFront(), 44, 44);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(1, -1).isHidden());

        gc1.addCard(p1, goldDeck.get(34), goldDeck.get(34).getFront(), 43, 41);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());

        gc1.addCard(p1, resourceDeck.get(17), resourceDeck.get(17).getFront(), 44, 40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(1, 1).isHidden());

        gc1.addCard(p1, resourceDeck.get(29), resourceDeck.get(29).getFront(), 42, 40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(-1, 1).isHidden());

        gc1.addCard(p1, resourceDeck.get(2), resourceDeck.get(2).getBack(), 41, 39);
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(-1, 1).isHidden());

        gc1.addCard(p1, resourceDeck.get(36), resourceDeck.get(36).getBack(), 40, 40);
        assertTrue(p1.getManuscript().getField()[41][39].getCorner(-1, -1).isHidden());

        gc1.addCard(p1, goldDeck.get(14), goldDeck.get(14).getFront(), 41, 41);
        assertTrue(p1.getManuscript().getField()[40][40].getCorner(1, -1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(-1, -1).isHidden());


        gc1.addCard(p1, goldDeck.get(16), goldDeck.get(16).getFront(), 40, 42);
        assertTrue(p1.getManuscript().getField()[41][41].getCorner(-1, -1).isHidden());

        gc1.addCard(p1, goldDeck.get(33), goldDeck.get(33).getBack(), 45, 45);
        assertTrue(p1.getManuscript().getField()[44][44].getCorner(1, -1).isHidden());

        gc1.addCard(p1, goldDeck.get(1), goldDeck.get(1).getFront(), 42, 38);
        assertTrue(p1.getManuscript().getField()[41][39].getCorner(1, 1).isHidden());

        gc1.addCard(p1, goldDeck.get(14), goldDeck.get(14).getFront(), 43, 39);
        assertTrue(p1.getManuscript().getField()[42][38].getCorner(1, -1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(1, 1).isHidden());
        assertTrue(p1.getManuscript().getField()[44][40].getCorner(-1, 1).isHidden());

        gc1.addCard(p1, resourceDeck.get(4), resourceDeck.get(4).getFront(), 46, 46);



        DoublePattern double_quill=new DoublePattern(102, objectiveDeck.get(14).getFront(), objectiveDeck.get(14).getBack(), CornerSymbol.QUILL);
        assertEquals(2, double_quill.calculateObjectivePoints(p1.getManuscript()));
    }

}