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

    private static GameController gc1;

    private static Game g1;

    private static Player p1;
    private static Player p2;
    private static Player p3;
    private static Player p4;
    private static ArrayList<Player> players1;

    private static ArrayList<StarterCard> starterDeck;
    private static ArrayList<ResourceCard> resourceDeck;
    private static ArrayList<ObjectiveCard> objectiveDeck;
    private static ArrayList<GoldCard> goldDeck;

    public void initializeGame(){

        // generate decks
        starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
        resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
        objectiveDeck = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);
        goldDeck = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);

        // create players and add them to the game.
        p1 = new Player("Giocatore 1", new Manuscript(starterDeck.get(0).getFront()), PawnColour.RED);
        p2 = new Player("Giocatore 2", new Manuscript(starterDeck.get(1).getFront()), PawnColour.GREEN);
        p3 = new Player("Giocatore 3", new Manuscript(starterDeck.get(2).getFront()), PawnColour.BLUE);
        p4 = new Player("Giocatore 4", new Manuscript(starterDeck.get(3).getFront()), PawnColour.YELLOW);
        players1 = new ArrayList<>();
        players1.add(p1);
        players1.add(p2);
        players1.add(p3);
        players1.add(p4);

        // create game and its controller
        g1 = new Game(1, new Board(), players1);
        gc1 = new GameController(g1);

        /*
        Collections.shuffle(resourceDeck);
        Collections.shuffle(goldDeck);
        Collections.shuffle(objectiveDeck);
        */
    }

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
        assertEquals(0, gameController.getGame().getPlayers().get(0).getManuscript().getPlantCounter()); //1 se conta la starter
        assertEquals(0, gameController.getGame().getPlayers().get(0).getManuscript().getInsectCounter()); //2 se conta la starter
        assertEquals(0, gameController.getGame().getPlayers().get(0).getManuscript().getInkwellCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(0).getManuscript().getQuillCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(0).getManuscript().getManuscriptCounter());


        //player2 (manuscript2)
        Player p2= new Player();
        players.add(p2);
        p2.setUsername("Meredith");
        p2.setPawnColour(PawnColour.YELLOW);
        board.setPointsYellowPlayer(0);
        StarterCard starterCard2= starterDeck.get(1);
        ResourceCard resourceCard1_p2= resourceDeck.get(24);
        ResourceCard resourceCard2_p2 = resourceDeck.get(16);
        ResourceCard resourceCard3_p2 = resourceDeck.get(34);
        ResourceCard resourceCard4_p2 = resourceDeck.get(27);
        ResourceCard goldCard_p2 = goldDeck.get(2);

        Manuscript manuscript2=new Manuscript(starterCard.getFront());
        p2.setManuscript(manuscript2);
        /*
        List<ResourceCard> hand2=new ArrayList<>();
        p2.getHand().add(resourceCard1);
        p2.getHand().add(resourceCard2);
        p2.getHand().add(goldCard);
        */
        gameController.addCard(p2, resourceCard1_p2, resourceCard1_p2.getFront(), 43,43);
        gameController.addCard(p2, resourceCard2_p2, resourceCard2_p2.getFront(), 41,41);
        gameController.addCard(p2, resourceCard3_p2, resourceCard3_p2.getFront(), 44,42);
        gameController.addCard(p2, resourceCard4_p2, resourceCard4_p2.getFront(), 45,43);
        assertEquals(1, gameController.getGame().getBoard().getPointsBluePlayer());
        assertEquals(-1, gameController.getGame().getPlayers().get(0).getManuscript().getFungiCounter()); //problema starter
        assertEquals(3, gameController.getGame().getPlayers().get(0).getManuscript().getAnimalCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(0).getManuscript().getPlantCounter()); //1 se conta la starter
        assertEquals(-1, gameController.getGame().getPlayers().get(0).getManuscript().getInsectCounter()); //2 se conta la starter
        assertEquals(0, gameController.getGame().getPlayers().get(0).getManuscript().getInkwellCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(0).getManuscript().getQuillCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(0).getManuscript().getManuscriptCounter());


          /*  //player2
        Player p2= new Player();
        p2.setUsername("Benjamin");
        p2.setPawnColour(PawnColour.YELLOW);
        StarterCard starterCard2_p2= starterDeck.get(2);
        Manuscript manuscript2=new Manuscript(starterCard2_p2.getBack());
        p2.setManuscript(manuscript2);

        //List<ResourceCard> hand2=new ArrayList<>();
        ResourceCard resourceCard1_p2= resourceDeck.get(38);
        ResourceCard resourceCard2_p2 = resourceDeck.get(29);
        ResourceCard resourceCard3_p2 = resourceDeck.get(16);
        ResourceCard resourceCard4_p2 = resourceDeck.get(11);
        ResourceCard goldCard_p2 = goldDeck.get(1);
        //p2.getHand().add(resourceCard1_p2);
        //p2.getHand().add(resourceCard2_p2);
        //p2.getHand().add(goldCard_p2);
        board2.setPointsYellowPlayer(0);
        players2.add(p2);
*/
            //end initializer

            //2nd configuration


        /*
        con.addCard(p2, resourceCard1_p2, resourceCard1_p2.getFront(), 41,43);
        con.addCard(p2, resourceCard2_p2, resourceCard2_p2.getFront(), 43,43);
        con.addCard(p2, resourceCard3_p2, resourceCard3_p2.getFront(), 42,44);
        con.addCard(p2, resourceCard4_p2, resourceCard4_p2.getFront(), 43,45);
        assertEquals(2, con.getGame().getBoard().getPointsYellowPlayer());
        assertTrue(con.getGame().getPlayers().get(1).getManuscript().getField()[43][43].getCorner(1,1).isHidden());
        */
    }
}