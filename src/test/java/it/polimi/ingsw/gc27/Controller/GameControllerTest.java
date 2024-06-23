package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Controller.ClientTest;
import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.*;
import it.polimi.ingsw.gc27.Model.States.InitializingState;
import it.polimi.ingsw.gc27.Net.Commands.AddStarterCommand;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.Commands.ReconnectPlayerCommand;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.Utils.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    private GameController gameController;
    private GigaController gigaController;
    private ClientTest clientTest;
    private Game game;
    private List<Player> players1= new ArrayList<>();

    private static ArrayList<StarterCard> starterDeck;
    private static ArrayList<ResourceCard> resourceDeck;
    private static ArrayList<ObjectiveCard> objectiveDeck;
    private static ArrayList<GoldCard> goldDeck;
    private final BlockingQueue<Command> commands = new LinkedBlockingQueue<>();
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
        Player p1 = new Player("Giocatore 1", new Manuscript(), PawnColour.RED);
        p1.setHand(new ArrayList<ResourceCard>());
        Player p2 = new Player("Giocatore 2", new Manuscript(), PawnColour.GREEN);
        p2.setHand(new ArrayList<ResourceCard>());
        Player p3 = new Player("Giocatore 3", new Manuscript(), PawnColour.BLUE);
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

        ArrayList<GameController> gameControllers=new ArrayList<>();
        game = new Game(new Board(), market,players1,objectiveDeck.get(0),objectiveDeck.get(1), starterDeck,objectiveDeck);
        gameController = new GameController(game, 4, 0, gigaController);
        gameControllers.add(gameController);


        clientTest= new ClientTest();


    }

    @BeforeEach
    public void setUp() {
//        gigaController = new GigaController();
//        playerList.add(new Player("UserTest", new Manuscript(), PawnColour.fromStringToPawnColour("red")));
//        game=new Game(0, new Board(), playerList);
//        gameController = new GameController(game, 1, 0, gigaController);


    }

//    @Test
//    public void testAddCard() {
//        Player player = new Player("testPlayer", new Manuscript(), PawnColour.RED);
//        ResourceCard card = new ResourceCard();
//        gameController.addCard(player, card, Face.UP, 0, 0);
//        assertTrue(player.getPlayerState().hasCard(card));
//    }
//
//    @Test
//    public void testDrawCard() throws InterruptedException {
//        Player player = new Player("testPlayer", new Manuscript(), PawnColour.RED);
//        gameController.drawCard(player, true, true, 0);
//        assertEquals(1, player.getHand().size());
//    }
//
//    @Test
//    public void testChooseObjectiveCard() {
//        Player player = new Player("testPlayer", new Manuscript(), PawnColour.RED);
//        gameController.chooseObjectiveCard(player, 0);
//        assertNotNull(player.getObjectiveCard());
//    }
//
//    @Test
//    public void testAddStarterCard() {
//        Player player = new Player("testPlayer", new Manuscript(), PawnColour.RED);
//        StarterCard starter = new StarterCard();
//        gameController.addStarterCard(player, starter, Face.UP);
//        assertTrue(player.getPlayerState().hasStarterCard(starter));
//    }
//
//    @Test
//    public void testSuspendPlayer() {
//        Player player = new Player("testPlayer", new Manuscript(), PawnColour.RED);
//        gameController.suspendPlayer(player);
//        assertTrue(player.isDisconnected());
//    }

    @Test
    public void testInitializePlayer() throws InterruptedException, IOException {
        initializeGame();
        clientTest = new ClientTest();
        clientTest.setNextRead("testPlayer");
        clientTest.setNextRead("YELLOW");
        game.ready(players1.get(0));
        game.ready(players1.get(1));
        game.ready(players1.get(2));
        gameController.initializePlayer(clientTest, gigaController);
        assertEquals(4, gameController.getGame().getPlayers().size());

    }

//    @Test
//    public void testSendChatMessage() {
//        ChatMessage chatMessage = new ChatMessage("testPlayer", "Hello World", "global");
//        gameController.sendChatMessage(chatMessage);
//        assertEquals(1, gameController.getGame().getGeneralChat().getMessages().size());
//    }
//
    @Test
    public void testAddCommand() {
        initializeGame();
        Command command = new AddStarterCommand(players1.getFirst().getUsername(), true) {
            @Override
            public void execute(GameController gameController) {
            }
        };
        gameController.addCommand(command);
        gameController.executeCommands();
        assertEquals(1, gameController.getCommands().size());
    }


    @Test
    public void testSuspendGame() throws InterruptedException {
        initializeGame();
        GigaController gigaController1=new GigaController();
        ArrayList<Player> players=new ArrayList<>();
        players.add(new Player("Player1", new Manuscript(), PawnColour.fromStringToPawnColour("blue")));
        players.add(new Player("Player2", new Manuscript(), PawnColour.fromStringToPawnColour("red")));

        gigaController1.getGameControllers().add(new GameController(new Game(1, new Board(),players ), 2, 1, gigaController1));
        gigaController1.getGameControllers().getFirst().getCommands().add(new ReconnectPlayerCommand(new ClientTest(), players.getFirst()));
        gigaController1.getGameControllers().getFirst().getGame().getPlayers().getFirst().setDisconnected(true);
        gigaController1.getGameControllers().getFirst().suspendGame();
        //assertTrue(gigaController1.getGameControllers().getFirst().getGame().isSuspended());
    }
}
