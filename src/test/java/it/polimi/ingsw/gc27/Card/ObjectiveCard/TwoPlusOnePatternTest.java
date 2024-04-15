package it.polimi.ingsw.gc27.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Card.GoldCard;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Card.StarterCard;
import it.polimi.ingsw.gc27.Console;
import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Controller.JsonParser;
import it.polimi.ingsw.gc27.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Game.Board;
import it.polimi.ingsw.gc27.Game.Game;
import it.polimi.ingsw.gc27.Game.Manuscript;
import it.polimi.ingsw.gc27.Game.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TwoPlusOnePatternTest {

    @Test
    void calculateObjectivePoints() {
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
        p1.setUsername("Taylor"); //1 player
        p1.setPawnColour(PawnColour.BLUE);
        board.setPointsBluePlayer(0);

        StarterCard starterCard= starterDeck.get(1);
        starterDeck.remove(1);

        ResourceCard resourceCard1= resourceDeck.get(12);
        //resourceDeck.remove(12);
        ResourceCard resourceCard2= resourceDeck.get(0);
        //resourceDeck.remove(0);
        ResourceCard goldCard3 = goldDeck.get(6);
        //goldDeck.remove(6);
        ResourceCard resourceCard4= resourceDeck.get(4);
        //resourceDeck.remove(3);
        ResourceCard goldCard5 = goldDeck.get(15);
        //goldDeck.remove(15);
        ResourceCard goldCard6 = goldDeck.get(11);
        //goldDeck.remove(11);
        ResourceCard resourceCard7= resourceDeck.get(26);
        //resourceDeck.remove(26);
        ResourceCard resourceCard8= resourceDeck.get(27);
        //resourceDeck.remove(27);
        ResourceCard goldCard9 = goldDeck.get(7);
        //goldDeck.remove(8);
        ResourceCard goldCard10 = goldDeck.get(4);
        ResourceCard goldCard11 = goldDeck.get(8);
        ResourceCard resourceCard12= resourceDeck.get(12);
        ResourceCard resourceCard13= resourceDeck.get(32);



        Manuscript manuscript=new Manuscript();
        p1.setManuscript(manuscript);
        /*
        List<ResourceCard> hand=new ArrayList<>();
        p1.getHand().add(resourceCard1);
        p1.getHand().add(resourceCard2);
        p1.getHand().add(goldCard);
        */
        ObjectiveCard obj1=objectiveDeck.get(1); //ladder
        ObjectiveCard obj2= objectiveDeck.get(12); //different
        ObjectiveCard obj3= objectiveDeck.get(15); //double quill
        ObjectiveCard obj4= objectiveDeck.get(13); //double manuscript
        ObjectiveCard obj7= objectiveDeck.get(5); //two plus one green-purple


        gameController.addStarterCard(p1, starterCard, starterCard.getBack() );
        gameController.addCard(p1, resourceCard1, resourceCard1.getFront(), 41,43);
        gameController.addCard(p1, resourceCard2, resourceCard2.getFront(), 43,41);
        gameController.addCard(p1, goldCard3, goldCard3.getFront(), 44,40);
        gameController.addCard(p1, resourceCard4, resourceCard4.getFront(), 41,41);
        gameController.addCard(p1, goldCard5, goldCard5.getFront(), 40,42);
        gameController.addCard(p1,goldCard6, goldCard6.getFront(), 39,41);
        gameController.addCard(p1, resourceCard7, resourceCard7.getFront(), 43,43);
        gameController.addCard(p1, resourceCard8, resourceCard8.getFront(), 44,44);
        gameController.addCard(p1,goldCard9, goldCard9.getFront(), 43,45);
        gameController.addCard(p1,goldCard10, goldCard10.getFront(), 42,44);
        gameController.addCard(p1,goldCard11, goldCard11.getFront(), 41,45);
        gameController.addCard(p1, resourceCard12, resourceCard12.getFront(), 38,43);


        //LadderPattern ladder=new LadderPattern(88,obj1.getFront(), obj1.getBack(), Kingdom.PLANTKINGDOM, false);
        assertEquals(21,board.getPointsBluePlayer());
        //int points= board.getPointsBluePlayer() + obj1.calculateObjectivePoints(manuscript);
        assertEquals(23,board.getPointsBluePlayer() + obj1.calculateObjectivePoints(manuscript));
        assertEquals(24, board.getPointsBluePlayer()+ obj2.calculateObjectivePoints(manuscript));
        assertEquals(23, board.getPointsBluePlayer()+ obj3.calculateObjectivePoints(manuscript));
        assertEquals(23, board.getPointsBluePlayer()+ obj4.calculateObjectivePoints(manuscript));
        //assertEquals(24, board.getPointsBluePlayer()+ obj7.calculateObjectivePoints(manuscript));

        //assertTrue(p1.getManuscript().getField()[42][42].getCorner(1,-1).isHidden());
        //assertTrue(p1.getManuscript().getField()[42][42].getCorner(-1,1).isHidden());
        //assertFalse(p1.getManuscript().getField()[42][42].getCorner(1,1).isHidden());
        //assertFalse(p1.getManuscript().getField()[42][42].getCorner(-1,-1).isHidden());

        assertEquals(4, gameController.getGame().getPlayers().get(0).getManuscript().getFungiCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(0).getManuscript().getAnimalCounter());
        assertEquals(1, gameController.getGame().getPlayers().get(0).getManuscript().getPlantCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(0).getManuscript().getInsectCounter());
        assertEquals(1, gameController.getGame().getPlayers().get(0).getManuscript().getInkwellCounter());
        assertEquals(2, gameController.getGame().getPlayers().get(0).getManuscript().getQuillCounter());
        assertEquals(2, gameController.getGame().getPlayers().get(0).getManuscript().getManuscriptCounter());

    }
}