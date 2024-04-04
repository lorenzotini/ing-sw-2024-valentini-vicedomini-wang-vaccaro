package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Card.GoldCard;
import it.polimi.ingsw.gc27.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Card.StarterCard;
import it.polimi.ingsw.gc27.Console;
import it.polimi.ingsw.gc27.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Game.Board;
import it.polimi.ingsw.gc27.Game.Game;
import it.polimi.ingsw.gc27.Game.Manuscript;
import it.polimi.ingsw.gc27.Game.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    @Test
    void addCardTest() {
            //import parser
        ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
        ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
        ArrayList<ObjectiveCard> objectiveDeck = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);
        ArrayList<GoldCard> goldDeck = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);

        Console console= new Console();
        List<Game> games = new ArrayList<>();
        console.setGames(games);


         //game 1
        GameController gameController=new GameController();
        Game game= new Game();
        gameController.setGame(game);
        game.setGameID(13);
        Board board=new Board();
        game.setBoard(board);
        List<Player> players=new ArrayList<>();
        game.setPlayers(players);

        //player 1 (manuscript)
        Player p1= new Player();
        players.add(p1);
        p1.setUsername("Olivia"); //1 player
        p1.setPawnColour(PawnColour.BLUE);
        board.setPointsBluePlayer(0);
        StarterCard starterCard= starterDeck.get(0);
        ResourceCard resourceCard1= resourceDeck.get(0);
        ResourceCard resourceCard2 = resourceDeck.get(9);
        ResourceCard goldCard = goldDeck.get(2);

        Manuscript manuscript=new Manuscript(starterCard.getBack());
        p1.setManuscript(manuscript);

        List<ResourceCard> hand=new ArrayList<>();
        p1.getHand().add(resourceCard1);
        p1.getHand().add(resourceCard2);
        p1.getHand().add(goldCard);


        gameController.addCard(p1, resourceCard1, resourceCard1.getFront(), 43,43);
        gameController.addCard(p1, resourceCard2, resourceCard2.getFront(), 41,41);
        assertEquals(1,board.getPointsBluePlayer());
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1,-1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(-1,1).isHidden());
        assertFalse(p1.getManuscript().getField()[42][42].getCorner(1,1).isHidden());
        assertFalse(p1.getManuscript().getField()[42][42].getCorner(-1,-1).isHidden());

        assertEquals(3, gameController.getGame().getPlayers().get(0).getManuscript().getFungiCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(0).getManuscript().getAnimalCounter());
        assertEquals(1, gameController.getGame().getPlayers().get(0).getManuscript().getPlantCounter());
        assertEquals(2, gameController.getGame().getPlayers().get(0).getManuscript().getInsectCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(0).getManuscript().getInkwellCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(0).getManuscript().getQuillCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(0).getManuscript().getManuscriptCounter());


        //player2 (manuscript2)
        Player p2= new Player();
        players.add(p2);
        p2.setUsername("Meredith");
        p2.setPawnColour(PawnColour.YELLOW);
        board.setPointsYellowPlayer(0);
        StarterCard starterCard2= starterDeck.get(0);
        ResourceCard resourceCard1_p2= resourceDeck.get(24);
        ResourceCard resourceCard2_p2 = resourceDeck.get(16);
        ResourceCard resourceCard3_p2 = resourceDeck.get(34);
        ResourceCard resourceCard4_p2 = resourceDeck.get(27);
        ResourceCard goldCard_p2 = goldDeck.get(2);

        Manuscript manuscript2=new Manuscript(starterCard2.getFront());
        p2.setManuscript(manuscript2);

        //List<ResourceCard> hand2=new ArrayList<>();
        //p2.getHand().add(resourceCard1);
        ///p2.getHand().add(resourceCard2);
        //p2.getHand().add(goldCard);


        gameController.addCard(p2, resourceCard1_p2, resourceCard1_p2.getFront(), 43,43);
        gameController.addCard(p2, resourceCard2_p2, resourceCard2_p2.getFront(), 41,41);
        gameController.addCard(p2, resourceCard3_p2, resourceCard3_p2.getFront(), 44,42);
        gameController.addCard(p2, resourceCard4_p2, resourceCard4_p2.getFront(), 45,43);
        assertEquals(1, gameController.getGame().getBoard().getPointsYellowPlayer());

        assertEquals(0, gameController.getGame().getPlayers().get(1).getManuscript().getFungiCounter());
        assertEquals(4, gameController.getGame().getPlayers().get(1).getManuscript().getAnimalCounter());
        assertEquals(2, gameController.getGame().getPlayers().get(1).getManuscript().getPlantCounter());
        assertEquals(1, gameController.getGame().getPlayers().get(1).getManuscript().getInsectCounter());
        assertEquals(1, gameController.getGame().getPlayers().get(1).getManuscript().getInkwellCounter());
        assertEquals(1, gameController.getGame().getPlayers().get(1).getManuscript().getQuillCounter());
        assertEquals(1, gameController.getGame().getPlayers().get(1).getManuscript().getManuscriptCounter());


        //player 3 (manuscript3)
        Player p3= new Player();
        players.add(p3);
        p3.setUsername("Benjamin");
        p3.setPawnColour(PawnColour.GREEN);
        board.setPointsGreenPlayer(0);
        StarterCard starterCard3= starterDeck.get(2);
        ResourceCard resourceCard1_p3= resourceDeck.get(38);
        ResourceCard resourceCard2_p3 = resourceDeck.get(29);
        ResourceCard resourceCard3_p3 = resourceDeck.get(16);
        ResourceCard resourceCard4_p3 = resourceDeck.get(11);
        ResourceCard goldCard_p3 = goldDeck.get(3);

        Manuscript manuscript3=new Manuscript(starterCard3.getBack());
        p3.setManuscript(manuscript3);

        gameController.addCard(p3, resourceCard1_p3, resourceCard1_p3.getFront(), 43,41);
        gameController.addCard(p3, resourceCard2_p3, resourceCard2_p3.getFront(), 43,43);
        gameController.addCard(p3, resourceCard3_p3, resourceCard3_p3.getFront(), 44,42);
        gameController.addCard(p3, resourceCard4_p3, resourceCard4_p3.getFront(), 45,43);
        assertEquals(2, gameController.getGame().getBoard().getPointsGreenPlayer());

        assertTrue(p3.getManuscript().getField()[42][42].getCorner(1,1).isHidden());
        assertTrue(p3.getManuscript().getField()[42][42].getCorner(1,-1).isHidden());
        assertTrue(p3.getManuscript().getField()[43][41].getCorner(1,-1).isHidden());
        assertTrue(p3.getManuscript().getField()[43][43].getCorner(1,1).isHidden());
        assertTrue(p3.getManuscript().getField()[44][42].getCorner(1,-1).isHidden());

        assertEquals(1, gameController.getGame().getPlayers().get(2).getManuscript().getFungiCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(2).getManuscript().getAnimalCounter());
        assertEquals(4, gameController.getGame().getPlayers().get(2).getManuscript().getPlantCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(2).getManuscript().getInsectCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(2).getManuscript().getInkwellCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(2).getManuscript().getQuillCounter());
        assertEquals(1, gameController.getGame().getPlayers().get(2).getManuscript().getManuscriptCounter());

        //player 4(manuscript4)
        Player p4= new Player();
        players.add(p4);
        p4.setUsername("James");
        p4.setPawnColour(PawnColour.RED);
        board.setPointsRedPlayer(0);
        StarterCard starterCard4= starterDeck.get(5);
        ResourceCard resourceCard1_p4= resourceDeck.get(34);
        ResourceCard goldCard2_p4 = goldDeck.get(50);
        ResourceCard resourceCard3_p4 = resourceDeck.get(32);
        ResourceCard goldCard4_p4 = goldDeck.get(76);
        ResourceCard resourceCard5_p4 = resourceDeck.get(28);
        ResourceCard resourceCard6_p4 = resourceDeck.get(22);
        ResourceCard goldCard7_p4 = goldDeck.get(68);


        Manuscript manuscript4=new Manuscript(starterCard4.getBack());
        p4.setManuscript(manuscript4);

        gameController.addCard(p4, resourceCard1_p4, resourceCard1_p4.getFront(), 43,41);
        gameController.addCard(p4, goldCard2_p4, goldCard2_p4.getFront(), 41,41);
        gameController.addCard(p4, resourceCard3_p4, resourceCard3_p4.getBack(), 44,42);
        gameController.addCard(p4, goldCard4_p4, goldCard4_p4.getFront(), 45,43);
        gameController.addCard(p4, resourceCard5_p4, resourceCard5_p4.getFront(), 44,44);
        gameController.addCard(p4, resourceCard6_p4, resourceCard6_p4.getBack(), 40,42);
        gameController.addCard(p4, goldCard7_p4, goldCard7_p4.getFront(), 39,41);

        assertEquals(9, gameController.getGame().getBoard().getPointsRedPlayer()); //exluding objective cards points

        assertTrue(p4.getManuscript().getField()[42][42].getCorner(1,1).isHidden());
        assertTrue(p4.getManuscript().getField()[42][42].getCorner(-1,1).isHidden());
        assertTrue(p4.getManuscript().getField()[43][41].getCorner(1,-1).isHidden());
        assertTrue(p4.getManuscript().getField()[45][43].getCorner(-1,-1).isHidden());
        assertTrue(p4.getManuscript().getField()[44][42].getCorner(1,-1).isHidden());
        assertTrue(p4.getManuscript().getField()[41][41].getCorner(-1,-1).isHidden());
        assertTrue(p4.getManuscript().getField()[40][42].getCorner(-1,1).isHidden());

        assertEquals(0, gameController.getGame().getPlayers().get(3).getManuscript().getFungiCounter());
        assertEquals(4, gameController.getGame().getPlayers().get(3).getManuscript().getAnimalCounter());
        assertEquals(1, gameController.getGame().getPlayers().get(3).getManuscript().getPlantCounter());
        assertEquals(3, gameController.getGame().getPlayers().get(3).getManuscript().getInsectCounter());
        assertEquals(1, gameController.getGame().getPlayers().get(3).getManuscript().getInkwellCounter());
        assertEquals(3, gameController.getGame().getPlayers().get(3).getManuscript().getQuillCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(3).getManuscript().getManuscriptCounter());



    }
}