package it.polimi.ingsw.gc27.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ThreeKingdomPattern;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.JsonParser;
import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Game.*;
import it.polimi.ingsw.gc27.Model.Game.*;
import it.polimi.ingsw.gc27.View.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ThreeKingdomPatternTest {
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
        market=new Market(resourceDeck, goldDeck, faceUpResources,faceUpGolds, objectiveDeck );
        g1.setMarket(market);


        // create game and its controller



        /*
        Collections.shuffle(resourceDeck);
        Collections.shuffle(goldDeck);
        Collections.shuffle(objectiveDeck);
        */
    }

    @Test
    void calculateObjectivePoints() throws IOException, InterruptedException { //test 8, tested all three kingdoms
        initializeGame();
        p1.addCard(g1, starterDeck.get(5), starterDeck.get(5).getFront(),42,42);

        p1.addCard(g1, resourceDeck.get(26), resourceDeck.get(26).getFront(), 41, 41);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());

        p1.addCard(g1, resourceDeck.get(34), resourceDeck.get(34).getFront(), 43, 41);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());

        p1.addCard(g1, resourceDeck.get(29), resourceDeck.get(29).getFront(), 40, 40);
        assertTrue(p1.getManuscript().getField()[41][41].getCorner(-1, 1).isHidden());

        p1.addCard(g1, goldDeck.get(22), goldDeck.get(22).getFront(), 40, 42);
        assertTrue(p1.getManuscript().getField()[41][41].getCorner(-1, -1).isHidden());

        p1.addCard(g1, resourceDeck.get(33), resourceDeck.get(33).getFront(), 39, 39);
        assertTrue(p1.getManuscript().getField()[40][40].getCorner(-1, 1).isHidden());

        p1.addCard(g1, goldDeck.get(35), goldDeck.get(35).getFront(), 39, 41);
        assertTrue(p1.getManuscript().getField()[40][40].getCorner(-1, -1).isHidden());
        assertTrue(p1.getManuscript().getField()[40][42].getCorner(-1, 1).isHidden());

        p1.addCard(g1, resourceDeck.get(10), resourceDeck.get(10).getBack(), 41, 43);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(-1, -1).isHidden());

        p1.addCard(g1, goldDeck.get(37), goldDeck.get(37).getFront(), 38, 38);
        assertTrue(p1.getManuscript().getField()[39][39].getCorner(-1, 1).isHidden());

        p1.addCard(g1, resourceDeck.get(22), resourceDeck.get(22).getFront(), 40, 44);
        assertTrue(p1.getManuscript().getField()[41][43].getCorner(-1, -1).isHidden());

        p1.addCard(g1, goldDeck.get(21), goldDeck.get(21).getFront(), 44, 40); //sistema
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(1, 1).isHidden());

        p1.addCard(g1, resourceDeck.get(8), resourceDeck.get(8).getFront(), 38, 42);
        assertTrue(p1.getManuscript().getField()[41][43].getCorner(-1, -1).isHidden());

        p1.addCard(g1, resourceDeck.get(9), resourceDeck.get(9).getBack(), 37, 43);
        assertTrue(p1.getManuscript().getField()[38][42].getCorner(-1, -1).isHidden());

        p1.addCard(g1, goldDeck.get(37), goldDeck.get(37).getFront(), 38, 44);
        assertTrue(p1.getManuscript().getField()[37][43].getCorner(1, -1).isHidden());

        p1.addCard(g1, goldDeck.get(33), goldDeck.get(33).getFront(), 39, 43);
        assertTrue(p1.getManuscript().getField()[38][44].getCorner(1, 1).isHidden());

        //ViewCli view8=new ViewCli();
        //view8.showManuscript(p1.getManuscript());

        View view=new Tui();
        view.show(p1.getManuscript());

        //three fungi
        assertEquals(2, objectiveDeck.get(8).calculateObjectivePoints(p1.getManuscript()));
        //three plant
        assertEquals(0, objectiveDeck.get(9).calculateObjectivePoints(p1.getManuscript()));
        //three animal
        assertEquals(2, objectiveDeck.get(10).calculateObjectivePoints(p1.getManuscript()));
        //three insect
        assertEquals(2, objectiveDeck.get(11).calculateObjectivePoints(p1.getManuscript()));


    }
}