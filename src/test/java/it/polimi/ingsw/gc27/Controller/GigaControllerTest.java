package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.*;
import it.polimi.ingsw.gc27.Utils.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GigaControllerTest {

    private GigaController gigaController;
    private ClientTest clientTest;
    private ClientTest clientTest2;
    private ClientTest clientTest3;
    private GameController gameController;

    private Game game1;
    private Game game2;
    private Player p1,p2,p3;

    private List<Player> players1= new ArrayList<>();

    @BeforeEach
    public void setUp() {
        gigaController = new GigaController();
        clientTest = new ClientTest();

    }
    public  void initializeGame() {

        players1 = new ArrayList<>();

        gigaController = new GigaController();

        // generate decks
        JsonParser jsonParser = new JsonParser("codex_cards_collection.json");
        ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck();
        ArrayList<ObjectiveCard> objectiveDeck = jsonParser.getObjectiveDeck();
        ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck();
        ArrayList<GoldCard> goldDeck = jsonParser.getGoldDeck();


        // create players and add them to the game.
        p1 = new Player("Giocatore 1", new Manuscript(), PawnColour.RED);
        p1.setHand(new ArrayList<ResourceCard>());
        p2 = new Player("Giocatore 2", new Manuscript(), PawnColour.GREEN);
        p2.setHand(new ArrayList<ResourceCard>());
        p3 = new Player("Giocatore 3", new Manuscript(), PawnColour.BLUE);
        p3.setHand(new ArrayList<ResourceCard>());


        players1.add(p1);
        players1.add(p2);
        players1.add(p3);


        ResourceCard[] faceUpResources = new ResourceCard[2];
        GoldCard[] faceUpGolds= new GoldCard[2];
        faceUpResources[0]= resourceDeck.get(0);
        faceUpResources[1]= resourceDeck.get(1);
        faceUpGolds[0]=goldDeck.get(0);
        faceUpGolds[1]=goldDeck.get(1);
        Market market=new Market(resourceDeck, goldDeck, faceUpResources,faceUpGolds,objectiveDeck);


        game1 = new Game(new Board(), market,players1,objectiveDeck.get(0),objectiveDeck.get(1), starterDeck,objectiveDeck);
        game2 = new Game(new Board(), market, players1, objectiveDeck.get(0), objectiveDeck.get(1), starterDeck, objectiveDeck);
        gameController = new GameController(game1, 4, 0, gigaController);
        gigaController.getGameControllers().add(gameController);


        clientTest= new ClientTest();


    }

    @Test
    public void testRemoveReferences() {
        String username = "testUser";
        try {
            clientTest.setUsername(username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gigaController.getRegisteredUsernames().put(username, clientTest);
        gigaController.removeReferences(clientTest);
        assertFalse(gigaController.getRegisteredUsernames().containsKey(username));
    }

    @Test
    public void testWelcomePlayerNewGame1() throws IOException, InterruptedException { //da finire
        initializeGame();
        clientTest = new ClientTest();
        clientTest.setNextRead("new");
        clientTest.setNextRead("2");
        clientTest.setNextRead("User");
        clientTest.setNextRead("BLUE");
        gigaController.welcomePlayer(clientTest);
        assertTrue(clientTest.nextShows.poll().contains("\nWelcome to Codex Naturalis\n\nDo you want to start a new game or join an existing one? (enter 'new' or the gameId)"));


        clientTest2=new ClientTest();
        clientTest2.setNextRead("0");
        clientTest2.setNextRead("User2");
        clientTest2.setNextRead("RED");
        gigaController.welcomePlayer(clientTest2);
        assertTrue(clientTest2.nextShows.poll().contains("\nWelcome to Codex Naturalis\n\nDo you want to start a new game or join an existing one? (enter 'new' or the gameId)"));

    }


    //invalid input id
    @Test
    public void testWelcomePlayerNewGame2() throws IOException, InterruptedException { //da finire
        initializeGame();
        clientTest= new ClientTest();
        clientTest.setNextRead("prova");
        clientTest.setNextRead("new");
        clientTest.setNextRead("2");
        clientTest.setNextRead("User");
        clientTest.setNextRead("BLUE");
        gigaController.welcomePlayer(clientTest);
        assertTrue(clientTest.nextShows.poll().contains("\nWelcome to Codex Naturalis\n\nDo you want to start a new game or join an existing one? (enter 'new' or the gameId)"));
        assertTrue(clientTest.nextShows.poll().contains("Invalid input"));
    }

    //game full
    @Test
    public void testWelcomePlayerNewGame3() throws IOException, InterruptedException { //da finire
        initializeGame();

        gameController = new GameController(game1, 2, 0, gigaController);
        gigaController.getGameControllers().add(gameController);
        clientTest = new ClientTest();
        clientTest.setNextRead("new");
        clientTest.setNextRead("2");
        clientTest.setNextRead("User");
        clientTest.setNextRead("BLUE");
        gigaController.welcomePlayer(clientTest);
        assertTrue(clientTest.nextShows.poll().contains("\nWelcome to Codex Naturalis\n\nDo you want to start a new game or join an existing one? (enter 'new' or the gameId)"));

        clientTest2 = new ClientTest();
        clientTest2.setNextRead("0");
        clientTest2.setNextRead("User2");
        clientTest2.setNextRead("GREEN");
        gigaController.welcomePlayer(clientTest2);
        assertTrue(clientTest2.nextShows.poll().contains("\nWelcome to Codex Naturalis\n\nDo you want to start a new game or join an existing one? (enter 'new' or the gameId)"));

        clientTest3 = new ClientTest();
        clientTest3.setNextRead("0");
        clientTest3.setNextRead("new");
        clientTest3.setNextRead("2");
        clientTest3.setNextRead("User3");
        clientTest3.setNextRead("YELLOW");
        gigaController.welcomePlayer(clientTest3);
        assertTrue(clientTest3.nextShows.poll().contains("\nWelcome to Codex Naturalis\n\nDo you want to start a new game or join an existing one? (enter 'new' or the gameId)"));
        assertTrue(clientTest3.nextShows.poll().contains("Joining game"));
        assertTrue(clientTest3.nextShows.poll().contains("Game is full"));
    }


//    @Test
//    public void testWelcomePlayerNewGame4() throws IOException, InterruptedException { //da finire
//        ArrayList<Player> players1 = new ArrayList<>();
//        GigaController gigaController = new GigaController();
//
//        // generate decks
////        JsonParser jsonParser = new JsonParser("codex_cards_collection.json");
////        ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck();
////        ArrayList<ObjectiveCard> objectiveDeck = jsonParser.getObjectiveDeck();
////        ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck();
////        ArrayList<GoldCard> goldDeck = jsonParser.getGoldDeck();
////
////
////        // create players and add them to the game.
////        Player p1 = new Player("Giocatore 1", new Manuscript(), PawnColour.RED);
////        p1.setHand(new ArrayList<ResourceCard>());
////        Player p2 = new Player("Giocatore 2", new Manuscript(), PawnColour.GREEN);
////        p2.setHand(new ArrayList<ResourceCard>());
////        Player p3 = new Player("Giocatore 3", new Manuscript(), PawnColour.BLUE);
////        p3.setHand(new ArrayList<ResourceCard>());
////
////
////        players1.add(p1);
////        players1.add(p2);
////        players1.add(p3);
////
////
////        ResourceCard[] faceUpResources = new ResourceCard[2];
////        GoldCard[] faceUpGolds= new GoldCard[2];
////        faceUpResources[0]= resourceDeck.get(0);
////        faceUpResources[1]= resourceDeck.get(1);
////        faceUpGolds[0]=goldDeck.get(0);
////        faceUpGolds[1]=goldDeck.get(1);
////        Market market=new Market(resourceDeck, goldDeck, faceUpResources,faceUpGolds,objectiveDeck);
////
////
////        Game game1 = new Game(new Board(), market,players1,objectiveDeck.get(0),objectiveDeck.get(1), starterDeck,objectiveDeck);
////        GameController gameController = new GameController(game1, 2, 1, gigaController);
////        gigaController.getGameControllers().add(gameController);
//
//        clientTest = new ClientTest();
//        clientTest.setNextRead("new");
//        clientTest.setNextRead("2");
//        clientTest.setNextRead("User");
//        clientTest.setNextRead("BLUE");
//        gigaController.welcomePlayer(clientTest);
//        //gigaController.getGameControllers().getLast().getGame().getPlayer("User").
//        assertTrue(clientTest.nextShows.poll().contains("\nWelcome to Codex Naturalis\n\nDo you want to start a new game or join an existing one? (enter 'new' or the gameId)"));
//
//        clientTest2 = new ClientTest();
//        clientTest2.setNextRead("0");
//        clientTest2.setNextRead("User2");
//        clientTest2.setNextRead("GREEN");
//        gigaController.welcomePlayer(clientTest2);
//        assertTrue(clientTest2.nextShows.poll().contains("\nWelcome to Codex Naturalis\n\nDo you want to start a new game or join an existing one? (enter 'new' or the gameId)"));
//        assertEquals(gigaController.getGameControllers().getLast().getGame().getNumActualPlayers(), 2);
//
//        Player playerToSuspend= gigaController.getGameControllers().getLast().getGame().getPlayers().get(1);
//        //prolema: prima di testare la riconnessione, la disconnessione dà problemi, testo prima suspend game in game controller
//        gigaController.getGameControllers().getLast().suspendPlayer(playerToSuspend);
//        assertTrue(gigaController.getGameControllers().getLast().getPlayer("User 2").isDisconnected());
////        ClientTest clientTest3;
////        clientTest3 = new ClientTest();
////        clientTest3.setNextRead("0");
////        clientTest3.setNextRead("Giocatore 2");
////        clientTest3.setNextRead("YELLOW");
////        gigaController.welcomePlayer(clientTest3);
//
//        //assertEquals(gigaController.getGameControllers().getLast().getGame().getNumActualPlayers(), 2);
//
//
//    }

    @Test
    public void testCreateNewGame1() throws IOException, InterruptedException {
        clientTest.setNextRead("2");
        clientTest.setNextRead("User");
        clientTest.setNextRead("BLUE");
        gigaController.createNewGame(clientTest);
        assertTrue(clientTest.nextShows.poll().contains("How many play"));
        assertTrue(clientTest.nextShows.poll().contains("Game created with id"));
        assertTrue(clientTest.nextShows.poll().contains("Choose your username"));
        assertTrue(clientTest.nextShows.poll().contains("Choose your color"));
        assertTrue(clientTest.nextShows.poll().contains("GREEN"));
        assertTrue(clientTest.nextShows.poll().contains("YELLOW"));
        assertTrue(clientTest.nextShows.poll().contains("BLUE"));
        assertTrue(clientTest.nextShows.poll().contains("RED"));
    }
//    @Test
//    public void testCreateNewGame2() throws IOException, InterruptedException {
//        clientTest.setNextRead("5");
//        clientTest.setNextRead("User");
//        clientTest.setNextRead("BLUE");
//        gigaController.createNewGame(clientTest);
//        assertTrue(clientTest.nextShows.poll().contains("How many play"));
//        assertTrue(clientTest.nextShows.poll().contains("Game created with id"));
//        assertTrue(clientTest.nextShows.poll().contains("Choose your username"));
//        assertTrue(clientTest.nextShows.poll().contains("Choose your color"));
//        assertTrue(clientTest.nextShows.poll().contains("GREEN"));
//        assertTrue(clientTest.nextShows.poll().contains("YELLOW"));
//        assertTrue(clientTest.nextShows.poll().contains("BLUE"));
//        assertTrue(clientTest.nextShows.poll().contains("RED"));
//    }

    @Test
    public void testTryReconnectPlayer() throws IOException {
       initializeGame();
        clientTest = new ClientTest();
        clientTest.setNextRead("new");
        clientTest.setNextRead("2");
        clientTest.setNextRead("User");
        clientTest.setNextRead("BLUE");
        players1.get(0).setDisconnected(true);
        //assertTrue(gigaController.tryReconnectPlayer(clientTest, gameController, "testUser"));
    }

    @Test
    public void testValidUsername() {
        assertTrue(gigaController.validUsername("newUser", clientTest));
        assertFalse(gigaController.validUsername("global", clientTest));
        assertFalse(gigaController.validUsername("", clientTest));
    }

    @Test
    public void testGetView() {
        gigaController.getRegisteredUsernames().put("testUser", clientTest);
        assertEquals(clientTest, gigaController.getView("testUser"));
    }

    @Test
    public void testGetUsername() {
        String username = "testUser";
        try {
            clientTest.setUsername(username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gigaController.getRegisteredUsernames().put(username, clientTest);
        assertEquals(username, gigaController.getUsername(clientTest));
    }

    @Test
    public void testClose(){
        initializeGame();
        assertEquals(gigaController.getGameControllers().size(), 1);
        this.gigaController.closeGame(this.gameController);
        assertEquals(gigaController.getGameControllers().size(), 0);
    }
}
