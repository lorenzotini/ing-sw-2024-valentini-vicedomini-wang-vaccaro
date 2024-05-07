package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.*;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Game.*;
import it.polimi.ingsw.gc27.Model.Game.*;
import it.polimi.ingsw.gc27.Model.States.InitializingState;
import it.polimi.ingsw.gc27.Model.States.PlayingState;
import it.polimi.ingsw.gc27.View.MyCli;
import it.polimi.ingsw.gc27.View.TUI;
import it.polimi.ingsw.gc27.View.ViewCli;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static it.polimi.ingsw.gc27.Model.Enumerations.Kingdom.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameControllerTest {

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

    private static TurnHandler turnHandler;

    public  void initializeGame() {

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

        faceUpResources = new ResourceCard[2];
        faceUpGolds= new GoldCard[2];
        faceUpResources[0]= resourceDeck.get(0);
        faceUpResources[1]= resourceDeck.get(1);
        faceUpGolds[0]=goldDeck.get(0);
        faceUpGolds[1]=goldDeck.get(1);
        market=new Market(resourceDeck, goldDeck, faceUpResources,faceUpGolds );
        g1.setMarket(market);

        turnHandler=new TurnHandler(g1);



        // create game and its controller



        /*
        Collections.shuffle(resourceDeck);
        Collections.shuffle(goldDeck);
        Collections.shuffle(objectiveDeck);
        */
    }

    @Test
    void addCardTest1() throws IOException, InterruptedException {
        initializeGame();
        p1.setPlayerState(new InitializingState(p1, turnHandler));
        gc1.addStarterCard(p1, starterDeck.get(2), starterDeck.get(2).getBack());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(31), resourceDeck.get(31).getFront(), 43, 43);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(15), resourceDeck.get(15).getFront(), 44, 42);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(32), resourceDeck.get(32).getFront(), 44, 44);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(34), goldDeck.get(34).getFront(), 43, 41);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(17), resourceDeck.get(17).getFront(), 44, 40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(29), resourceDeck.get(29).getFront(), 42, 40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(-1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(2), resourceDeck.get(2).getBack(), 41, 39);
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(-1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(36), resourceDeck.get(36).getBack(), 40, 40);
        assertTrue(p1.getManuscript().getField()[41][39].getCorner(-1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(14), goldDeck.get(14).getFront(), 41, 41);
        assertTrue(p1.getManuscript().getField()[40][40].getCorner(1, -1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(-1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(16), goldDeck.get(16).getFront(), 40, 42);
        assertTrue(p1.getManuscript().getField()[41][41].getCorner(-1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(33), goldDeck.get(33).getBack(), 45, 45);
        assertTrue(p1.getManuscript().getField()[44][44].getCorner(1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(1), goldDeck.get(1).getFront(), 42, 38);
        assertTrue(p1.getManuscript().getField()[41][39].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(14), goldDeck.get(14).getFront(), 43, 39);
        assertTrue(p1.getManuscript().getField()[42][38].getCorner(1, -1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(1, 1).isHidden());
        assertTrue(p1.getManuscript().getField()[44][40].getCorner(-1, 1).isHidden());

        gc1.drawResourceCard(p1,true,0);
        gc1.drawGoldCard(p1,true,0);


        //ObjectiveCard obj1 = objectiveDeck.get(3);
        //LadderPattern ladder = new LadderPattern(90, obj1.getFront(), obj1.getBack(), Kingdom.ANIMALKINGDOM, true);
        assertEquals(2, objectiveDeck.get(3).calculateObjectivePoints(p1.getManuscript()));
        //ObjectiveCard obj2 = objectiveDeck.get(5);
        //TwoPlusOnePattern two = new TwoPlusOnePattern(92, obj2.getFront(), obj2.getBack(), Kingdom.FUNGIKINGDOM, Kingdom.ANIMALKINGDOM, -1, -1);
        assertEquals(2, objectiveDeck.get(5).calculateObjectivePoints(p1.getManuscript()));

        assertEquals(2, gc1.getGame().getPlayers().get(0).getManuscript().getFungiCounter());
        assertEquals(0, gc1.getGame().getPlayers().get(0).getManuscript().getAnimalCounter());
        assertEquals(3, gc1.getGame().getPlayers().get(0).getManuscript().getPlantCounter());
        assertEquals(5, gc1.getGame().getPlayers().get(0).getManuscript().getInsectCounter());
        assertEquals(2, gc1.getGame().getPlayers().get(0).getManuscript().getInkwellCounter());
        assertEquals(1, gc1.getGame().getPlayers().get(0).getManuscript().getQuillCounter());
        assertEquals(0, gc1.getGame().getPlayers().get(0).getManuscript().getManuscriptCounter());

        assertEquals(23, gc1.getGame().getBoard().getPointsRedPlayer());

        ViewCli view = new ViewCli();
        view.showManuscript(p1.getManuscript());
        view.zoom(p1.getManuscript(),42,42);

    }

    @Test
    void addCardTest2() throws IOException, InterruptedException {
        initializeGame();
        //test 2
        p2.setPlayerState(new InitializingState(p2, turnHandler));
        gc1.addStarterCard(p2, starterDeck.get(1), starterDeck.get(1).getFront());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gc1.addCard(p2, resourceDeck.get(24), resourceDeck.get(24).getFront(), 41, 43);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(-1, -1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gc1.addCard(p2, resourceDeck.get(25), resourceDeck.get(25).getFront(), 41, 41);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gc1.addCard(p2, resourceDeck.get(28), resourceDeck.get(28).getFront(), 40, 44);
        assertTrue(p2.getManuscript().getField()[41][43].getCorner(-1, -1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gc1.addCard(p2, goldDeck.get(26), goldDeck.get(26).getFront(), 39, 45);
        assertTrue(p2.getManuscript().getField()[40][44].getCorner(-1, -1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gc1.addCard(p2, resourceDeck.get(6), resourceDeck.get(6).getFront(), 42, 40);
        assertTrue(p2.getManuscript().getField()[41][41].getCorner(1, 1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gc1.addCard(p2, goldDeck.get(35), goldDeck.get(35).getFront(), 43, 41);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());
        assertTrue(p2.getManuscript().getField()[42][40].getCorner(1, -1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gc1.addCard(p2, goldDeck.get(37), goldDeck.get(37).getFront(), 38, 44);
        assertTrue(p2.getManuscript().getField()[39][45].getCorner(-1, 1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gc1.addCard(p2, resourceDeck.get(16), resourceDeck.get(16).getFront(), 44, 42);
        assertTrue(p2.getManuscript().getField()[43][41].getCorner(1, -1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gc1.addCard(p2, resourceDeck.get(18), resourceDeck.get(18).getBack(), 42, 44);
        assertTrue(p2.getManuscript().getField()[41][43].getCorner(1, -1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gc1.addCard(p2, goldDeck.get(34), goldDeck.get(34).getFront(), 43, 43);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(1, -1).isHidden());
        assertTrue(p2.getManuscript().getField()[44][42].getCorner(-1, -1).isHidden());
        assertTrue(p2.getManuscript().getField()[42][44].getCorner(1, 1).isHidden());

        assertEquals(17, gc1.getGame().getBoard().getPointsGreenPlayer());

        //test of double manuscript objective card
        assertEquals(4, objectiveDeck.get(13).calculateObjectivePoints(p2.getManuscript()));

        //test of animal ladder pattern
        assertEquals(2, objectiveDeck.get(2).calculateObjectivePoints(p2.getManuscript()));

        //test of insect+plant two plus one pattern
        assertEquals(3, objectiveDeck.get(6).calculateObjectivePoints(p2.getManuscript()));

        //check simboli fatto visivamente

        ViewCli view2 = new ViewCli();
        view2.showManuscript(p2.getManuscript());
        MyCli view=new MyCli();
        view.printManuscript(p2.getManuscript());
    }


    @Test
    void addCardTest3() throws IOException, InterruptedException {
        initializeGame();
        //test 3
        p3.setPlayerState(new InitializingState(p3, turnHandler));
        gc1.addStarterCard(p3, starterDeck.get(0), starterDeck.get(0).getBack());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gc1.addCard(p3, resourceDeck.get(15), resourceDeck.get(15).getFront(), 41, 41);
        assertTrue(p3.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gc1.addCard(p3, resourceDeck.get(17), resourceDeck.get(17).getFront(), 40, 40);
        assertTrue(p3.getManuscript().getField()[41][41].getCorner(-1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gc1.addCard(p3, resourceDeck.get(14), resourceDeck.get(14).getFront(), 39, 39);
        assertTrue(p3.getManuscript().getField()[40][40].getCorner(-1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gc1.addCard(p3, resourceDeck.get(36), resourceDeck.get(36).getFront(), 43, 43);
        assertTrue(p3.getManuscript().getField()[42][42].getCorner(1, -1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gc1.addCard(p3, resourceDeck.get(0), resourceDeck.get(0).getBack(), 44, 42);
        assertTrue(p3.getManuscript().getField()[43][43].getCorner(1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gc1.addCard(p3, resourceDeck.get(33), resourceDeck.get(33).getFront(), 43, 41);
        assertTrue(p3.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());
        assertTrue(p3.getManuscript().getField()[44][42].getCorner(-1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gc1.addCard(p3, resourceDeck.get(29), resourceDeck.get(29).getFront(), 42, 40);
        assertTrue(p3.getManuscript().getField()[41][41].getCorner(1, 1).isHidden());
        assertTrue(p3.getManuscript().getField()[43][41].getCorner(-1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gc1.addCard(p3, goldDeck.get(33), goldDeck.get(33).getFront(), 41, 39);
        assertTrue(p3.getManuscript().getField()[40][40].getCorner(1, 1).isHidden());
        assertTrue(p3.getManuscript().getField()[42][40].getCorner(-1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gc1.addCard(p3, goldDeck.get(39), goldDeck.get(39).getFront(), 45, 41);
        assertTrue(p3.getManuscript().getField()[44][42].getCorner(1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gc1.addCard(p3, resourceDeck.get(10), resourceDeck.get(10).getBack(), 46, 40);
        assertTrue(p3.getManuscript().getField()[45][41].getCorner(1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gc1.addCard(p3, goldDeck.get(18), goldDeck.get(18).getFront(), 45, 43);
        assertTrue(p3.getManuscript().getField()[44][42].getCorner(1, -1).isHidden());

        assertEquals(14, gc1.getGame().getBoard().getPointsBluePlayer());

        //three plants objective card
        assertEquals(2, objectiveDeck.get(9).calculateObjectivePoints(p3.getManuscript()));
        //three insects objective card
        assertEquals(4, objectiveDeck.get(11).calculateObjectivePoints(p3.getManuscript()));
        //plants ladder
        assertEquals(2, objectiveDeck.get(1).calculateObjectivePoints(p3.getManuscript()));
        //two plus one animial-insect
        assertEquals(3, objectiveDeck.get(7).calculateObjectivePoints(p3.getManuscript()));

        ViewCli view3 = new ViewCli();
        view3.showManuscript(p3.getManuscript());
        MyCli view=new MyCli();
        view.printManuscript(p3.getManuscript());

        //view.printManuscript(p3.getManuscript());
    }

    @Test
    void addCardTest4() throws IOException, InterruptedException {
        initializeGame();
        //test 4
        p4.setPlayerState(new InitializingState(p4, turnHandler));
        gc1.addStarterCard(p4, starterDeck.get(4), starterDeck.get(4).getBack());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gc1.addCard(p4, resourceDeck.get(1), resourceDeck.get(1).getBack(), 43, 41);
        assertTrue(p4.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gc1.addCard(p4, resourceDeck.get(3), resourceDeck.get(3).getBack(), 44, 40);
        assertTrue(p4.getManuscript().getField()[43][41].getCorner(1, 1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gc1.addCard(p4, resourceDeck.get(4), resourceDeck.get(4).getBack(), 45, 39);
        assertTrue(p4.getManuscript().getField()[44][40].getCorner(1, 1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gc1.addCard(p4, resourceDeck.get(11), resourceDeck.get(11).getFront(), 44, 42);
        assertTrue(p4.getManuscript().getField()[43][41].getCorner(1, -1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gc1.addCard(p4, resourceDeck.get(7), resourceDeck.get(7).getFront(), 45, 43);
        assertTrue(p4.getManuscript().getField()[44][42].getCorner(1, -1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gc1.addCard(p4, resourceDeck.get(12), resourceDeck.get(12).getFront(), 44, 44);
        assertTrue(p4.getManuscript().getField()[45][43].getCorner(-1, -1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gc1.addCard(p4, goldDeck.get(30), goldDeck.get(30).getBack(), 43, 45);
        assertTrue(p4.getManuscript().getField()[44][44].getCorner(-1, -1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gc1.addCard(p4, goldDeck.get(2), goldDeck.get(2).getBack(), 42, 40);
        assertTrue(p4.getManuscript().getField()[43][41].getCorner(-1, 1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gc1.addCard(p4, goldDeck.get(1), goldDeck.get(1).getBack(), 43, 39);
        assertTrue(p4.getManuscript().getField()[42][40].getCorner(1, 1).isHidden());
        assertTrue(p4.getManuscript().getField()[44][40].getCorner(-1, 1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gc1.addCard(p4, goldDeck.get(0), goldDeck.get(0).getBack(), 44, 38);
        assertTrue(p4.getManuscript().getField()[43][39].getCorner(1, 1).isHidden());
        assertTrue(p4.getManuscript().getField()[45][39].getCorner(-1, 1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gc1.addCard(p4, goldDeck.get(19), goldDeck.get(19).getBack(), 41, 41);
        assertTrue(p4.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());
        assertTrue(p4.getManuscript().getField()[42][40].getCorner(-1, -1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gc1.addCard(p4, goldDeck.get(17), goldDeck.get(17).getBack(), 41, 39);
        assertTrue(p4.getManuscript().getField()[42][40].getCorner(1, 1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gc1.addCard(p4, goldDeck.get(31), goldDeck.get(31).getFront(), 40, 42);
        assertTrue(p4.getManuscript().getField()[41][41].getCorner(-1, -1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gc1.addCard(p4, resourceDeck.get(9), resourceDeck.get(9).getFront(), 39, 41);
        assertTrue(p4.getManuscript().getField()[40][42].getCorner(-1, 1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gc1.addCard(p4, goldDeck.get(15), goldDeck.get(15).getFront(), 42, 38);
        assertTrue(p4.getManuscript().getField()[41][39].getCorner(1, 1).isHidden());
        assertTrue(p4.getManuscript().getField()[43][39].getCorner(-1, 1).isHidden());

        assertEquals(7, gc1.getGame().getBoard().getPointsYellowPlayer());

        //fungi ladder
        assertEquals(4, objectiveDeck.get(0).calculateObjectivePoints(p4.getManuscript()));
        //two plu one plant-fungi
        assertEquals(6, objectiveDeck.get(5).calculateObjectivePoints(p4.getManuscript()));
        //Three plants
        assertEquals(4, objectiveDeck.get(9).calculateObjectivePoints(p4.getManuscript()));
        //three fungi
        assertEquals(4, objectiveDeck.get(8).calculateObjectivePoints(p4.getManuscript()));

        //ViewCli view4 = new ViewCli();
        //view4.showManuscript(p4.getManuscript());
        MyCli view=new MyCli();
        view.printManuscript(p4.getManuscript());
    }

    @Test
    void addCardTest5() throws IOException, InterruptedException {
        initializeGame();
        p1.setPlayerState(new InitializingState(p1, turnHandler));
        gc1.addStarterCard(p1, starterDeck.get(0), starterDeck.get(0).getFront());
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(0), resourceDeck.get(0).getBack(), 43, 41);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(1), resourceDeck.get(1).getBack(), 42, 40);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(2), resourceDeck.get(2).getBack(), 43, 39);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(3), resourceDeck.get(3).getBack(), 42, 38);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(4), resourceDeck.get(4).getBack(), 43, 37);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(5), resourceDeck.get(5).getBack(), 42, 36);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(6), resourceDeck.get(6).getBack(), 43, 35);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(7), resourceDeck.get(7).getBack(), 42, 34);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(8), resourceDeck.get(8).getBack(), 43, 33);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(9), resourceDeck.get(9).getBack(), 42, 32);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(10), resourceDeck.get(10).getBack(), 43, 31);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(11), resourceDeck.get(11).getBack(), 42, 30);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(12), resourceDeck.get(12).getBack(), 43, 29);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(13), resourceDeck.get(13).getBack(), 42, 28);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(14), resourceDeck.get(14).getBack(), 43, 27);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(15), resourceDeck.get(15).getBack(), 42, 26);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(16), resourceDeck.get(16).getBack(), 43, 25);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(17), resourceDeck.get(17).getBack(), 42, 24);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(18), resourceDeck.get(18).getBack(), 43, 23);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(19), resourceDeck.get(19).getBack(), 42, 22);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(20), resourceDeck.get(20).getBack(), 43, 21);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(21), resourceDeck.get(21).getBack(), 42, 20);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(22), resourceDeck.get(22).getBack(), 43, 19);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(23), resourceDeck.get(23).getBack(), 42, 18);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(24), resourceDeck.get(24).getBack(), 43, 17);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(25), resourceDeck.get(25).getBack(), 42, 16);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(26), resourceDeck.get(26).getBack(), 43, 15);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(27), resourceDeck.get(27).getBack(), 42, 14);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(28), resourceDeck.get(28).getBack(), 43, 13);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(29), resourceDeck.get(29).getBack(), 42, 12);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(30), resourceDeck.get(30).getBack(), 43, 11);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(31), resourceDeck.get(31).getBack(), 42, 10);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(32), resourceDeck.get(32).getBack(), 43, 9);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(33), resourceDeck.get(33).getBack(), 42, 8);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(34), resourceDeck.get(34).getBack(), 43, 7);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(35), resourceDeck.get(35).getBack(), 42, 6);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(36), resourceDeck.get(36).getBack(), 43, 5);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(37), resourceDeck.get(37).getBack(), 42, 4);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(38), resourceDeck.get(38).getBack(), 43, 3);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(39), resourceDeck.get(39).getBack(), 42, 2);

        //ViewCli view5 = new ViewCli();
        //view5.showManuscript(p1.getManuscript());
        MyCli view=new MyCli();
        view.printManuscript(p1.getManuscript());
    }

    @Test
    void addCardTest6() throws IOException, InterruptedException {
        initializeGame();
        p1.setPlayerState(new InitializingState(p1, turnHandler));
        gc1.addStarterCard(p1, starterDeck.get(1), starterDeck.get(1).getFront());
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(0), goldDeck.get(0).getBack(), 43, 41);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(1), goldDeck.get(1).getBack(), 44, 40);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(2), goldDeck.get(2).getBack(), 45, 39);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(3), goldDeck.get(3).getBack(), 46, 38);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(4), goldDeck.get(4).getBack(), 47, 37);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(5), goldDeck.get(5).getBack(), 48, 36);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(6), goldDeck.get(6).getBack(), 49, 35);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(7), goldDeck.get(7).getBack(), 50, 34);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(8), goldDeck.get(8).getBack(), 51, 33);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(9), goldDeck.get(9).getBack(), 52, 32);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(10), goldDeck.get(10).getBack(), 53, 31);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(11), goldDeck.get(11).getBack(), 54, 30);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(12), goldDeck.get(12).getBack(), 55, 29);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(13), goldDeck.get(13).getBack(), 56, 28);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(14), goldDeck.get(14).getBack(), 57, 27);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(15), goldDeck.get(15).getBack(), 58, 26);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(16), goldDeck.get(16).getBack(), 59, 25);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(17), goldDeck.get(17).getBack(), 60, 24);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(18), goldDeck.get(18).getBack(), 61, 23);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(19), goldDeck.get(19).getBack(), 62, 22);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(20), goldDeck.get(20).getBack(), 63, 21);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(21), goldDeck.get(21).getBack(), 64, 20);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(22), goldDeck.get(22).getBack(), 65, 19);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(23), goldDeck.get(23).getBack(), 66, 18);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(24), goldDeck.get(24).getBack(), 67, 17);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(25), goldDeck.get(25).getBack(), 68, 16);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(26), goldDeck.get(26).getBack(), 69, 15);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(27), goldDeck.get(27).getBack(), 70, 14);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(28), goldDeck.get(28).getBack(), 71, 13);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(29), goldDeck.get(29).getBack(), 72, 12);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(30), goldDeck.get(30).getBack(), 73, 11);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(31), goldDeck.get(31).getBack(), 74, 10);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(32), goldDeck.get(32).getBack(), 75, 9);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(33), goldDeck.get(33).getBack(), 76, 8);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(34), goldDeck.get(34).getBack(), 77, 7);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(35), goldDeck.get(35).getBack(), 78, 6);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(36), goldDeck.get(36).getBack(), 79, 5);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(37), goldDeck.get(37).getBack(), 80, 4);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(38), goldDeck.get(38).getBack(), 81, 3);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(39), goldDeck.get(39).getBack(), 82, 2);

        //animal ladder
        assertEquals(6, objectiveDeck.get(2).calculateObjectivePoints(p1.getManuscript()));
        //red ladder
        assertEquals(6, objectiveDeck.get(0).calculateObjectivePoints(p1.getManuscript()));

        //ViewCli view6=new ViewCli();
        //view6.showManuscript(p1.getManuscript());

    }


@Test
void addCardTest7() throws IOException, InterruptedException {
    initializeGame();
    p1.setPlayerState(new InitializingState(p1, turnHandler));
    gc1.addStarterCard(p1, starterDeck.get(2), starterDeck.get(2).getBack());

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, goldDeck.get(10), goldDeck.get(10).getBack(), 41, 41);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(10), resourceDeck.get(10).getBack(), 40, 40);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, goldDeck.get(11), goldDeck.get(11).getBack(), 39, 39);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(11), resourceDeck.get(11).getBack(), 38, 38);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(0), resourceDeck.get(0).getBack(), 37, 37);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(12), resourceDeck.get(12).getBack(), 38, 36);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(1), resourceDeck.get(1).getBack(), 37, 35);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(2), resourceDeck.get(2).getBack(), 36, 38);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(3), resourceDeck.get(3).getBack(), 35, 39);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(4), resourceDeck.get(4).getBack(), 34, 40);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(5), resourceDeck.get(5).getBack(), 41, 43);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, goldDeck.get(0), goldDeck.get(0).getBack(), 40, 44);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(6), resourceDeck.get(6).getBack(), 39, 45);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, goldDeck.get(1), goldDeck.get(1).getBack(), 38, 46);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, goldDeck.get(20), goldDeck.get(20).getBack(), 37, 47);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, goldDeck.get(21), goldDeck.get(21).getBack(), 36, 46);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, goldDeck.get(22), goldDeck.get(22).getBack(), 35, 45);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, goldDeck.get(23), goldDeck.get(23).getBack(), 34, 44);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(30), resourceDeck.get(30).getBack(), 33, 43);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(31), resourceDeck.get(31).getBack(), 33, 41);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(32), resourceDeck.get(32).getBack(), 32, 42);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(13), resourceDeck.get(13).getBack(), 38, 38);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, goldDeck.get(24), goldDeck.get(24).getBack(), 37, 39);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(33), resourceDeck.get(33).getBack(), 43, 41);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(34), resourceDeck.get(34).getBack(), 44, 40);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(35), resourceDeck.get(35).getBack(), 45, 39);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, goldDeck.get(30), goldDeck.get(30).getBack(), 46, 38);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(14), resourceDeck.get(14).getBack(), 47, 37);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, goldDeck.get(31), goldDeck.get(31).getBack(), 46, 36);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(15), resourceDeck.get(15).getBack(), 47, 35);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, goldDeck.get(25), goldDeck.get(25).getBack(), 43, 43);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, goldDeck.get(26), goldDeck.get(26).getBack(), 44, 44);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(20), resourceDeck.get(20).getBack(), 45, 45);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(21), resourceDeck.get(21).getBack(), 46, 46);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(36), resourceDeck.get(36).getBack(), 47, 47);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, resourceDeck.get(22), resourceDeck.get(22).getBack(), 46, 48);

    p1.setPlayerState(new PlayingState(p1, turnHandler));
    gc1.addCard(p1, goldDeck.get(32), goldDeck.get(32).getBack(), 47, 49);

    //ViewCli view6=new ViewCli();
    //view6.showManuscript(p1.getManuscript());
    MyCli view=new MyCli();
    MyCli.printManuscript(p1.getManuscript());


    }

    @Test
    void addCardTest8() throws IOException, InterruptedException {
        initializeGame();
        p1.setPlayerState(new InitializingState(p1, turnHandler));
        gc1.addStarterCard(p1, starterDeck.get(5), starterDeck.get(5).getFront());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(26), resourceDeck.get(26).getFront(), 41, 41);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(34), resourceDeck.get(34).getFront(), 43, 41);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(29), resourceDeck.get(29).getFront(), 40, 40);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(22), goldDeck.get(22).getFront(), 40, 42);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(33), resourceDeck.get(33).getFront(), 39, 39);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(35), goldDeck.get(35).getFront(), 39, 41);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(10), resourceDeck.get(10).getBack(), 41, 43);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(37), goldDeck.get(37).getFront(), 38, 38);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(22), resourceDeck.get(22).getFront(), 40, 44);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(21), goldDeck.get(21).getFront(), 44, 40);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(8), resourceDeck.get(8).getFront(), 38, 42);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(9), resourceDeck.get(9).getBack(), 37, 43);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(37), goldDeck.get(37).getFront(), 38, 44);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(33), goldDeck.get(33).getFront(), 39, 43);

        //ViewCli view8=new ViewCli();
        //view8.showManuscript(p1.getManuscript());

        MyCli view=new MyCli();
        view.printManuscript(p1.getManuscript());
    }
    @Test
    void addCardTest9() throws IOException, InterruptedException {
        initializeGame();
        p1.setPlayerState(new InitializingState(p1, turnHandler));
        gc1.addStarterCard(p1, starterDeck.get(0), starterDeck.get(0).getFront());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(21), resourceDeck.get(21).getFront(), 41, 41);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(36), resourceDeck.get(36).getFront(), 43, 41);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(20), resourceDeck.get(20).getBack(), 43, 43);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(23), resourceDeck.get(23).getBack(), 42, 44);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(-1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(28), resourceDeck.get(28).getFront(), 41, 45);
        assertTrue(p1.getManuscript().getField()[42][44].getCorner(-1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(22), resourceDeck.get(22).getFront(), 40, 44);
        assertTrue(p1.getManuscript().getField()[41][45].getCorner(-1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(29), resourceDeck.get(29).getFront(), 39, 45);
        assertTrue(p1.getManuscript().getField()[40][44].getCorner(-1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(37), resourceDeck.get(37).getFront(), 38, 44);
        assertTrue(p1.getManuscript().getField()[39][45].getCorner(-1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(29), goldDeck.get(29).getFront(), 44, 44);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(17), resourceDeck.get(17).getFront(), 44, 40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(25), goldDeck.get(25).getFront(), 42, 40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(-1, 1).isHidden());
        assertTrue(p1.getManuscript().getField()[41][41].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(4), resourceDeck.get(4).getFront(), 43, 39);
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(1, 1).isHidden());
        assertTrue(p1.getManuscript().getField()[44][40].getCorner(-1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(7), resourceDeck.get(7).getFront(), 45, 43);
        assertTrue(p1.getManuscript().getField()[44][44].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(0), goldDeck.get(0).getFront(), 45, 45);
        assertTrue(p1.getManuscript().getField()[44][44].getCorner(1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(15), resourceDeck.get(15).getFront(), 46, 46);
        assertTrue(p1.getManuscript().getField()[45][45].getCorner(1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, goldDeck.get(14), goldDeck.get(14).getFront(), 44, 38);
        assertTrue(p1.getManuscript().getField()[43][39].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(5), resourceDeck.get(5).getFront(), 43, 37);
        assertTrue(p1.getManuscript().getField()[44][38].getCorner(-1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(8), resourceDeck.get(8).getFront(), 44, 36);
        assertTrue(p1.getManuscript().getField()[43][37].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(9), resourceDeck.get(9).getFront(), 43, 35);
        assertTrue(p1.getManuscript().getField()[44][36].getCorner(-1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gc1.addCard(p1, resourceDeck.get(34), resourceDeck.get(34).getFront(), 42, 36);
        assertTrue(p1.getManuscript().getField()[43][35].getCorner(-1, -1).isHidden());

        //two plus one fungi+plant
        assertEquals(6, objectiveDeck.get(4).calculateObjectivePoints(p1.getManuscript()));
        //two plus one plant+insect
        assertEquals(3, objectiveDeck.get(5).calculateObjectivePoints(p1.getManuscript()));

        MyCli view=new MyCli();
        view.printManuscript(p1.getManuscript());

    }
    


}