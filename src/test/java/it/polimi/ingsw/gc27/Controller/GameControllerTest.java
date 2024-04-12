package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Card.GoldCard;
import it.polimi.ingsw.gc27.Card.ObjectiveCard.*;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Card.StarterCard;
import it.polimi.ingsw.gc27.Console;
import it.polimi.ingsw.gc27.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Game.Board;
import it.polimi.ingsw.gc27.Game.Game;
import it.polimi.ingsw.gc27.Game.Manuscript;
import it.polimi.ingsw.gc27.Game.Player;
import it.polimi.ingsw.gc27.View.ViewCli;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.gc27.Enumerations.Kingdom.*;
import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private static GameController gc1;
    private static GameController gc2;
    private static ArrayList<Game> games;
    private static Player p1;
    private static Player p2;
    private static Player p3;
    private static Player p4;
    private static ArrayList<Player> players1;
    private static ArrayList<Player> players2;

    private static ArrayList<StarterCard> starterDeck;
    private static ArrayList<ResourceCard> resourceDeck;
    private static ArrayList<ObjectiveCard> objectiveDeck;
    private static ArrayList<GoldCard> goldDeck;

    public void initializeGame(){

        players1 = new ArrayList<>();
        games.set(1, new Game(1, new Board(), players1));
        gc1 = new GameController(games.get(1));

        players2 = new ArrayList<>();
        games.set(2, new Game(2, new Board(), players2));
        gc2 = new GameController(games.get(2));

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

        // create game and its controller



        /*
        Collections.shuffle(resourceDeck);
        Collections.shuffle(goldDeck);
        Collections.shuffle(objectiveDeck);
        */
    }

    @Test
    void addCardTest() {
        initializeGame();
        //test 1
        /*
        gc1.addStarterCard(p1,starterDeck.get(2), starterDeck.get(2).getBack());

        gc1.addCard(p1, resourceDeck.get(31), resourceDeck.get(31).getFront(), 43,43);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1,-1).isHidden());

        gc1.addCard(p1, resourceDeck.get(15), resourceDeck.get(15).getFront(), 44,42);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(1,1).isHidden());

        gc1.addCard(p1, resourceDeck.get(32), resourceDeck.get(32).getFront(), 44,44);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(1,-1).isHidden());

        gc1.addCard(p1, goldDeck.get(34), goldDeck.get(34).getFront(), 43,41);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1,1).isHidden());

        gc1.addCard(p1, resourceDeck.get(17), resourceDeck.get(17).getFront(), 44,40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(1,1).isHidden());

        gc1.addCard(p1, resourceDeck.get(29), resourceDeck.get(29).getFront(), 42,40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(-1,1).isHidden());

        gc1.addCard(p1, resourceDeck.get(2), resourceDeck.get(2).getBack(), 41,39);
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(-1,1).isHidden());

        gc1.addCard(p1, resourceDeck.get(36), resourceDeck.get(36).getBack(), 40,40);
        assertTrue(p1.getManuscript().getField()[41][39].getCorner(-1,-1).isHidden());

        gc1.addCard(p1, goldDeck.get(14), goldDeck.get(14).getFront(), 41,41);
        assertTrue(p1.getManuscript().getField()[40][40].getCorner(1,-1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(-1,1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(-1,-1).isHidden());


        gc1.addCard(p1, goldDeck.get(16), goldDeck.get(16).getFront(), 40,42);
        assertTrue(p1.getManuscript().getField()[41][41].getCorner(-1,-1).isHidden());

        gc1.addCard(p1, goldDeck.get(33), goldDeck.get(33).getBack(), 45,45);
        assertTrue(p1.getManuscript().getField()[44][44].getCorner(1,-1).isHidden());

        gc1.addCard(p1, goldDeck.get(1), goldDeck.get(1).getFront(), 42,38);
        assertTrue(p1.getManuscript().getField()[41][39].getCorner(1,1).isHidden());

        gc1.addCard(p1, goldDeck.get(14), goldDeck.get(14).getFront(), 43,39);
        assertTrue(p1.getManuscript().getField()[42][38].getCorner(1,-1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(1,1).isHidden());
        assertTrue(p1.getManuscript().getField()[44][40].getCorner(-1,1).isHidden());


        ObjectiveCard obj1=objectiveDeck.get(3);
        LadderPattern ladder = new LadderPattern(90, obj1.getFront(), obj1.getBack(), Kingdom.ANIMALKINGDOM, true);
        ObjectiveCard obj2=objectiveDeck.get(5);
        TwoPlusOnePattern two=new TwoPlusOnePattern(92, obj2.getFront(), obj2.getBack(), Kingdom.FUNGIKINGDOM, Kingdom.ANIMALKINGDOM, -1, -1);

        assertEquals(2, gc1.getGame().getPlayers().get(0).getManuscript().getFungiCounter());
        assertEquals(0, gc1.getGame().getPlayers().get(0).getManuscript().getAnimalCounter());
        assertEquals(3, gc1.getGame().getPlayers().get(0).getManuscript().getPlantCounter());
        assertEquals(5, gc1.getGame().getPlayers().get(0).getManuscript().getInsectCounter());
        assertEquals(2, gc1.getGame().getPlayers().get(0).getManuscript().getInkwellCounter());
        assertEquals(1, gc1.getGame().getPlayers().get(0).getManuscript().getQuillCounter());
        assertEquals(0, gc1.getGame().getPlayers().get(0).getManuscript().getManuscriptCounter());

        assertEquals(23, gc1.getGame().getBoard().getPointsRedPlayer());

        //ViewCli view=new ViewCli();
        //view.showManuscript(p1.getManuscript());
        //view.zoom(p1.getManuscript(), 41,39);


        //int points =gc1.getGame().getBoard().getPointsRedPlayer() + ladder.calculateObjectivePoints(p1.getManuscript());
        //assertEquals(25, points);
        //int points =gc1.getGame().getBoard().getPointsRedPlayer() + two.calculateObjectivePoints(p1.getManuscript());
        //assertEquals(26, points);
        */

        //test 2
        gc1.addStarterCard(p2,starterDeck.get(1), starterDeck.get(1).getFront());

        gc1.addCard(p2, resourceDeck.get(24), resourceDeck.get(24).getFront(), 41,43);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(-1,-1).isHidden());

        gc1.addCard(p2, resourceDeck.get(25), resourceDeck.get(25).getFront(), 41,41);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(-1,1).isHidden());

        gc1.addCard(p2, resourceDeck.get(28), resourceDeck.get(28).getFront(), 40,44);
        assertTrue(p2.getManuscript().getField()[41][43].getCorner(-1,-1).isHidden());

        gc1.addCard(p2, goldDeck.get(26), goldDeck.get(26).getFront(), 39,45);
        assertTrue(p2.getManuscript().getField()[40][44].getCorner(-1,-1).isHidden());

        gc1.addCard(p2, resourceDeck.get(6), resourceDeck.get(6).getFront(), 42,40);
        assertTrue(p2.getManuscript().getField()[41][41].getCorner(1,1).isHidden());

        gc1.addCard(p2, goldDeck.get(35), goldDeck.get(35).getFront(), 43,41);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(1,1).isHidden());
        assertTrue(p2.getManuscript().getField()[42][40].getCorner(1,-1).isHidden());

        gc1.addCard(p2, goldDeck.get(37), goldDeck.get(37).getFront(), 38,44);
        assertTrue(p2.getManuscript().getField()[39][45].getCorner(-1,1).isHidden());

        gc1.addCard(p2, resourceDeck.get(16), resourceDeck.get(16).getFront(), 44,42);
        assertTrue(p2.getManuscript().getField()[43][41].getCorner(1,-1).isHidden());

        gc1.addCard(p2, resourceDeck.get(18), resourceDeck.get(18).getBack(), 42,44);
        assertTrue(p2.getManuscript().getField()[41][43].getCorner(1,-1).isHidden());

        gc1.addCard(p2, goldDeck.get(34), goldDeck.get(34).getFront(), 43,43);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(1,-1).isHidden());
        assertTrue(p2.getManuscript().getField()[44][42].getCorner(-1,-1).isHidden());
        assertTrue(p2.getManuscript().getField()[42][44].getCorner(1,1).isHidden());

        assertEquals(17, gc1.getGame().getBoard().getPointsGreenPlayer());

        DoublePattern two_manuscript= new DoublePattern(100, objectiveDeck.get(13).getFront(), objectiveDeck.get(13).getBack(), CornerSymbol.MANUSCRIPT );
        assertEquals(21, gc1.getGame().getBoard().getPointsGreenPlayer() + two_manuscript.calculateObjectivePoints(p2.getManuscript()));



        LadderPattern blue_ladder= new LadderPattern(89, objectiveDeck.get(2).getFront(),  objectiveDeck.get(2).getBack(), Kingdom.ANIMALKINGDOM, true);
        assertEquals(19, gc1.getGame().getBoard().getPointsGreenPlayer() + blue_ladder.calculateObjectivePoints(p2.getManuscript()));

        TwoPlusOnePattern blue_plus_red= new TwoPlusOnePattern(93, objectiveDeck.get(6).getFront(), objectiveDeck.get(6).getBack(), Kingdom.FUNGIKINGDOM, Kingdom.ANIMALKINGDOM, 1,1);
        assertEquals(20, gc1.getGame().getBoard().getPointsGreenPlayer() + blue_plus_red.calculateObjectivePoints(p2.getManuscript()));

        //check simboli fatto visivamente

        //ViewCli view=new ViewCli();
        //view.showManuscript(p2.getManuscript());


        //test 3
        gc1.addStarterCard(p3,starterDeck.get(0), starterDeck.get(0).getBack());

        gc1.addCard(p3, resourceDeck.get(15), resourceDeck.get(15).getFront(), 41,41);
        assertTrue(p3.getManuscript().getField()[42][42].getCorner(-1,1).isHidden());

        gc1.addCard(p3, resourceDeck.get(17), resourceDeck.get(17).getFront(), 40,40);
        assertTrue(p3.getManuscript().getField()[41][41].getCorner(-1,1).isHidden());

        gc1.addCard(p3, resourceDeck.get(14), resourceDeck.get(14).getFront(), 39,39);
        assertTrue(p3.getManuscript().getField()[40][40].getCorner(-1,1).isHidden());

        gc1.addCard(p3, resourceDeck.get(36), resourceDeck.get(36).getFront(), 43,43);
        assertTrue(p3.getManuscript().getField()[42][42].getCorner(1,-1).isHidden());

        gc1.addCard(p3, resourceDeck.get(0), resourceDeck.get(0).getBack(), 44,42);
        assertTrue(p3.getManuscript().getField()[43][43].getCorner(1,1).isHidden());

        gc1.addCard(p3, resourceDeck.get(33), resourceDeck.get(33).getFront(), 43,41);
        assertTrue(p3.getManuscript().getField()[42][42].getCorner(1,1).isHidden());
        assertTrue(p3.getManuscript().getField()[44][42].getCorner(-1,1).isHidden());

        gc1.addCard(p3, resourceDeck.get(29), resourceDeck.get(29).getFront(), 42,40);
        assertTrue(p3.getManuscript().getField()[41][41].getCorner(1,1).isHidden());
        assertTrue(p3.getManuscript().getField()[43][41].getCorner(-1,1).isHidden());

        gc1.addCard(p3, goldDeck.get(33), goldDeck.get(33).getFront(), 41,39);
        assertTrue(p3.getManuscript().getField()[40][40].getCorner(1,1).isHidden());
        assertTrue(p3.getManuscript().getField()[42][40].getCorner(-1,1).isHidden());

        gc1.addCard(p3, goldDeck.get(39), goldDeck.get(39).getFront(), 45,41);
        assertTrue(p3.getManuscript().getField()[44][42].getCorner(1,1).isHidden());

        gc1.addCard(p3, resourceDeck.get(10), resourceDeck.get(10).getBack(), 46,40);
        assertTrue(p3.getManuscript().getField()[45][41].getCorner(1,1).isHidden());

        gc1.addCard(p3, goldDeck.get(18), goldDeck.get(18).getFront(), 45,43);
        assertTrue(p3.getManuscript().getField()[44][42].getCorner(1,-1).isHidden());

        assertEquals(14, gc1.getGame().getBoard().getPointsBluePlayer());

        ThreeKingdomPattern three_plants= new ThreeKingdomPattern(96, objectiveDeck.get(9).getFront(), objectiveDeck.get(9).getBack(), Kingdom.PLANTKINGDOM);
        assertEquals(16, gc1.getGame().getBoard().getPointsBluePlayer() + three_plants.calculateObjectivePoints(p3.getManuscript()));

        ThreeKingdomPattern three_insects= new ThreeKingdomPattern(98, objectiveDeck.get(11).getFront(), objectiveDeck.get(11).getBack(), Kingdom.INSECTKINGDOM);
        assertEquals(18, gc1.getGame().getBoard().getPointsBluePlayer() + three_insects.calculateObjectivePoints(p3.getManuscript()));
        //aggiungere punti carte obiettivo

        LadderPattern green_ladder=new LadderPattern(88, objectiveDeck.get(1).getFront(), objectiveDeck.get(1).getBack(), Kingdom.PLANTKINGDOM, false );
        assertEquals(16, gc1.getGame().getBoard().getPointsBluePlayer() + green_ladder.calculateObjectivePoints(p3.getManuscript()));

        TwoPlusOnePattern animal_insect=new TwoPlusOnePattern(94, objectiveDeck.get(7).getFront(), objectiveDeck.get(7).getBack(), Kingdom.ANIMALKINGDOM, Kingdom.INSECTKINGDOM, -1, 1 );
        assertEquals(17, gc1.getGame().getBoard().getPointsBluePlayer() + animal_insect.calculateObjectivePoints(p3.getManuscript()));

        //test 4
        gc1.addStarterCard(p4,starterDeck.get(4), starterDeck.get(4).getBack());

        gc1.addCard(p4, resourceDeck.get(1), resourceDeck.get(1).getBack(), 43,41);
        assertTrue(p4.getManuscript().getField()[42][42].getCorner(1,1).isHidden());

        gc1.addCard(p4, resourceDeck.get(3), resourceDeck.get(3).getBack(), 44,40);
        assertTrue(p4.getManuscript().getField()[43][41].getCorner(1,1).isHidden());

        gc1.addCard(p4, resourceDeck.get(4), resourceDeck.get(4).getBack(), 45,39);
        assertTrue(p4.getManuscript().getField()[44][40].getCorner(1,1).isHidden());

        gc1.addCard(p4, resourceDeck.get(11), resourceDeck.get(11).getFront(), 44,42);
        assertTrue(p4.getManuscript().getField()[43][41].getCorner(1,-1).isHidden());

        gc1.addCard(p4, resourceDeck.get(7), resourceDeck.get(7).getFront(), 45,43);
        assertTrue(p4.getManuscript().getField()[44][42].getCorner(1,-1).isHidden());

        gc1.addCard(p4, resourceDeck.get(12), resourceDeck.get(12).getFront(), 44,44);
        assertTrue(p4.getManuscript().getField()[45][43].getCorner(-1,-1).isHidden());

        gc1.addCard(p4, goldDeck.get(30), goldDeck.get(30).getBack(), 43,45);
        assertTrue(p4.getManuscript().getField()[44][44].getCorner(-1,-1).isHidden());

        gc1.addCard(p4, goldDeck.get(2), goldDeck.get(2).getBack(), 42,40);
        assertTrue(p4.getManuscript().getField()[43][41].getCorner(-1,1).isHidden());

        gc1.addCard(p4, goldDeck.get(1), goldDeck.get(1).getBack(), 43,39);
        assertTrue(p4.getManuscript().getField()[42][40].getCorner(1,1).isHidden());
        assertTrue(p4.getManuscript().getField()[44][40].getCorner(-1,1).isHidden());

        gc1.addCard(p4, goldDeck.get(0), goldDeck.get(0).getBack(), 44,38);
        assertTrue(p4.getManuscript().getField()[43][39].getCorner(1,1).isHidden());
        assertTrue(p4.getManuscript().getField()[45][39].getCorner(-1,1).isHidden());

        gc1.addCard(p4, goldDeck.get(19), goldDeck.get(19).getBack(), 41,41);
        assertTrue(p4.getManuscript().getField()[42][42].getCorner(-1,1).isHidden());
        assertTrue(p4.getManuscript().getField()[42][40].getCorner(-1,-1).isHidden());

        gc1.addCard(p4, goldDeck.get(17), goldDeck.get(17).getBack(), 41,39);
        assertTrue(p4.getManuscript().getField()[42][40].getCorner(1,1).isHidden());

        gc1.addCard(p4, goldDeck.get(31), goldDeck.get(31).getFront(), 40,42);
        assertTrue(p4.getManuscript().getField()[41][41].getCorner(-1,-1).isHidden());

        gc1.addCard(p4, resourceDeck.get(9), resourceDeck.get(9).getFront(), 39,41);
        assertTrue(p4.getManuscript().getField()[40][42].getCorner(-1,1).isHidden());

        gc1.addCard(p4, goldDeck.get(15), goldDeck.get(15).getFront(), 42,38);
        assertTrue(p4.getManuscript().getField()[41][39].getCorner(1,1).isHidden());
        assertTrue(p4.getManuscript().getField()[43][39].getCorner(-1,1).isHidden());

        assertEquals(7, gc1.getGame().getBoard().getPointsYellowPlayer() );

        LadderPattern red_ladder= new LadderPattern(87,objectiveDeck.get(0).getFront(), objectiveDeck.get(0).getBack(), FUNGIKINGDOM, true);
        assertEquals(11, gc1.getGame().getBoard().getPointsYellowPlayer() + red_ladder.calculateObjectivePoints(p4.getManuscript()) );

        TwoPlusOnePattern plant_plus_fungi= new TwoPlusOnePattern(92,objectiveDeck.get(5).getFront(), objectiveDeck.get(5).getBack(), PLANTKINGDOM, INSECTKINGDOM, -1,-1);
        assertEquals(13, gc1.getGame().getBoard().getPointsYellowPlayer() + plant_plus_fungi.calculateObjectivePoints(p4.getManuscript()) );

        ThreeKingdomPattern threeplants=new ThreeKingdomPattern(97, objectiveDeck.get(9).getFront(), objectiveDeck.get(9).getBack(),PLANTKINGDOM);
        assertEquals(11, gc1.getGame().getBoard().getPointsYellowPlayer() + threeplants.calculateObjectivePoints(p4.getManuscript()) );

        ThreeKingdomPattern threetods=new ThreeKingdomPattern(96, objectiveDeck.get(8).getFront(), objectiveDeck.get(8).getBack(),FUNGIKINGDOM);
        assertEquals(11, gc1.getGame().getBoard().getPointsYellowPlayer() + threetods.calculateObjectivePoints(p4.getManuscript()) );






    }


}