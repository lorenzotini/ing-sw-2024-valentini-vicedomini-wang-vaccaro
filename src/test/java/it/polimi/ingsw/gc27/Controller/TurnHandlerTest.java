package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.*;
import it.polimi.ingsw.gc27.Model.States.*;
import it.polimi.ingsw.gc27.Utils.JsonParser;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TurnHandlerTest {
    private GigaController gigaController;
    private GameController gameController;
    private Game game;
    private ClientTest clientTest;


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
    private static ArrayList<ObjectiveCard> secretObjectives;
    private ArrayList<GameController> gameControllers=new ArrayList<>();

    public  void initializeGame() {


        players1 = new ArrayList<>();

        gigaController = new GigaController();

        // generate decks
        JsonParser jsonParser = new JsonParser("codex_cards_collection.json");
        starterDeck = JsonParser.getStarterDeck();
        objectiveDeck = jsonParser.getObjectiveDeck();
        resourceDeck = JsonParser.getResourceDeck();
        goldDeck = jsonParser.getGoldDeck();


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


        game = new Game(new Board(), market,players1,objectiveDeck.get(0),objectiveDeck.get(1), starterDeck,objectiveDeck);
        gameController = new GameController(game, 4, 0, gigaController);
        gameControllers.add(gameController);
        turnHandler=new TurnHandler(game);


        clientTest= new ClientTest();
    }

    @Test
    void notifyChooseObjectiveState() {
        initializeGame();
        p1.setPlayerState(new WaitingState(p1, turnHandler));
        p2.setPlayerState(new WaitingState(p2, turnHandler));
        p3.setPlayerState(new WaitingState(p3, turnHandler));
        turnHandler.notifyChooseObjectiveState();
        assertEquals(p1.getPlayerState().toString(), "PlayingState");
        assertEquals(p2.getPlayerState().toString(), "WaitingState");
        assertEquals(p3.getPlayerState().toString(), "WaitingState");
    }

    //case last round
    @Test
    void notifyEndOfTurnStateTest1() throws RemoteException, InterruptedException {
        initializeGame();
        turnHandler.setLastRound(true);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        p2.setPlayerState(new WaitingState(p2, turnHandler));
        p3.setPlayerState(new WaitingState(p3, turnHandler));

        game.getBoard().setPointsRedPlayer(20);
        try {
            turnHandler.notifyEndOfTurnState(p1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //case disconnection
    @Test
    void notifyEndOfTurnStateTest2() throws RemoteException, InterruptedException {
        initializeGame();
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        p2.setPlayerState(new WaitingState(p2, turnHandler));
        p3.setPlayerState(new WaitingState(p3, turnHandler));
        p2.setDisconnected(true);

        try {
            turnHandler.notifyEndOfTurnState(p1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void notifyEndOfTurnStateTest3() throws RemoteException, InterruptedException {
        initializeGame();
        turnHandler.setLastRound(true);
        game.getBoard().setPointsRedPlayer(20);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        p2.setPlayerState(new WaitingState(p2, turnHandler));
        p3.setPlayerState(new WaitingState(p3, turnHandler));
        p2.setDisconnected(true);


        try {
            turnHandler.notifyEndOfTurnState(p1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    // case disconnected player

    @Test
    void notifyEndOfTurnStateTest4() throws RemoteException, InterruptedException {
        initializeGame();
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        p2.setPlayerState(new WaitingState(p2, turnHandler));
        p3.setPlayerState(new WaitingState(p3, turnHandler));
        game.getBoard().setPointsBluePlayer(20);

        try {
            turnHandler.notifyEndOfTurnState(p3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void notifyCalculateObjectivePointsTest() throws RemoteException {
        initializeGame();
        p1.setPlayerState(new EndingState(p1, turnHandler));
        p1.getSecretObjectives().add(objectiveDeck.get(0));
        p2.setPlayerState(new EndingState(p2, turnHandler));
        p2.getSecretObjectives().add(objectiveDeck.get(1));
        p3.getSecretObjectives().add(objectiveDeck.get(0));
        p3.setPlayerState(new EndingState(p3, turnHandler));
        try {
            turnHandler.notifyCalculateObjectivePoints(p1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals(game.getBoard().getPointsBluePlayer(), 0);
        assertEquals(game.getBoard().getPointsRedPlayer(), 0);
        assertEquals(game.getBoard().getPointsYellowPlayer(), 0);
        assertEquals(game.getBoard().getPointsGreenPlayer(), 0);
    }



    @Test
    void handleDisconnectionTest()  {
        initializeGame();
        p1.setPlayerState(new PlayingState(p1, turnHandler));

        p2.setStarterCard(starterDeck.get(0));
        p2.getSecretObjectives().add(objectiveDeck.get(0));
        p2.getSecretObjectives().add(objectiveDeck.get(1));
        p2.setPlayerState(new InitializingState(p2,turnHandler));

        p3.getSecretObjectives().add(objectiveDeck.get(0));
        p3.getSecretObjectives().add(objectiveDeck.get(1));
        p3.setPlayerState(new ChooseObjectiveState(p3,turnHandler));
        try {
            turnHandler.handleDisconnection(p1, gameController);
            turnHandler.handleDisconnection(p2, gameController);
            turnHandler.handleDisconnection(p3, gameController);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals(p1.getPlayerState().toString(), "EndOfTurnState");
        assertEquals(p2.getPlayerState().toString(), "WaitingState");
        assertEquals(p3.getPlayerState().toString(), "WaitingState");


        p1.setPlayerState(new DrawingState(p1, turnHandler));
        p2.setPlayerState(new WaitingState(p2, turnHandler));
        try {
            turnHandler.handleDisconnection(p1, gameController);
            turnHandler.handleDisconnection(p2, gameController);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals(p1.getPlayerState().toString(), "EndOfTurnState");
        assertEquals(p2.getPlayerState().toString(), "WaitingState");


    }
}