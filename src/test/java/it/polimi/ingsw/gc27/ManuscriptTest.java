package it.polimi.ingsw.gc27.Game;

import it.polimi.ingsw.gc27.Card.*;
import it.polimi.ingsw.gc27.Card.ObjectiveCard.DoublePattern;
import it.polimi.ingsw.gc27.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Controller.JsonParser;
import it.polimi.ingsw.gc27.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Enumerations.PawnColour;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManuscriptTest {

    @Test
    void isValidPlacementTest() {
        ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
        ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
        ArrayList<ObjectiveCard> objectiveDeck = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);

        StarterCard starterCard= starterDeck.get(0);
        Manuscript manuscript=new Manuscript();

        Game game= new Game();
        List<Player> players=new ArrayList<>();
        Board board=new Board();
        Player p1= new Player();
        ResourceCard resourceCard1= resourceDeck.get(0);
        p1.setUsername("plauto");
        p1.setPawnColour(PawnColour.BLUE);
        board.setPointsBluePlayer(0);

        game.setGameID(23);
        players.add(p1);
        game.setPlayers(players);
        p1.setManuscript(manuscript);
        game.setBoard(board);


        assertTrue(p1.getManuscript().isValidPlacement(41,41)); //UL corner
        System.out.println("UL OK");
        assertTrue(p1.getManuscript().isValidPlacement(43,43)); //LR corner
        System.out.println("LR OK");
        assertTrue(p1.getManuscript().isValidPlacement(41,43)); //UR corner
        System.out.println("UR OK");
        assertTrue(p1.getManuscript().isValidPlacement(43,41)); //LL corner
        System.out.println("LL OK");

        assertFalse(p1.getManuscript().isValidPlacement(45,45));
        System.out.println("RANDOM NOT VALID");

        assertFalse(p1.getManuscript().isValidPlacement(41,42));
        System.out.println("UP NOT VALID");
        assertFalse(p1.getManuscript().isValidPlacement(42,41));
        System.out.println("LEFT NOT VALID");
        assertFalse(p1.getManuscript().isValidPlacement(42,43));
        System.out.println("RIGHT NOT VALID");
        assertFalse(p1.getManuscript().isValidPlacement(43,42));
        System.out.println("DOWN NOT VALID");




    }

    @Test
    void satisfiedRequirement() {
    }

    @Test
    void getCounterTest() {
        /*
        ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
        ArrayList<ObjectiveCard> objectiveDeck = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);
        ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);

        ResourceCard resourceCard1= resourceDeck.get(0);
        ResourceCard resourceCard2= resourceDeck.get(4);
        ResourceCard resourceCard3= resourceDeck.get(14);


        StarterCard starterCard= starterDeck.get(0);
        ObjectiveCard objectiveCard = objectiveDeck.get(15);

        FrontFace frontface =objectiveCard.getFront();
        BackFace backface =objectiveCard.getBack();

        Manuscript manuscript=new Manuscript(starterCard.getFront());
        Game game= new Game();
        Player player= new Player();
        /*
        Face[][] localField = manuscript.getField();
        localField[41][41]= resourceCard1.getFront();
        localField[43][43]= resourceCard2.getFront();
        localField[42][44]= resourceCard3.getFront();

        //player.addCard();

        int count = manuscript.getCounter(CornerSymbol.FUNGIKINGDOM);
        assertEquals(3,count);
        */

    }

    @Test
    void decreaseCounter() {
    }

    @Test
    void increaseCounter() {

    }
}