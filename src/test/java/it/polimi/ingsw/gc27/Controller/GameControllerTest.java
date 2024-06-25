package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Controller.ClientTest;
import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientPlayer;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.*;
import it.polimi.ingsw.gc27.Model.PlayerListener;
import it.polimi.ingsw.gc27.Model.States.*;
import it.polimi.ingsw.gc27.Net.Commands.AddStarterCommand;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.Commands.ReconnectPlayerCommand;
import it.polimi.ingsw.gc27.Net.Commands.SuspendPlayerCommand;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.Utils.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private TurnHandler turnHandler;
    private Player p1,p2,p3,p4;

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
        p4 = new Player("Giocatore 4", new Manuscript(), PawnColour.YELLOW);
        p4.setHand(new ArrayList<ResourceCard>());

        players1.add(p1);
        players1.add(p2);
        players1.add(p3);

        ResourceCard[] faceUpResources = new ResourceCard[2];
        GoldCard[] faceUpGolds= new GoldCard[2];
        ArrayList<ObjectiveCard> secretObjectives=new ArrayList<>();
        faceUpResources[0]= resourceDeck.get(0);
        faceUpResources[1]= resourceDeck.get(1);
        faceUpGolds[0]=goldDeck.get(0);
        faceUpGolds[1]=goldDeck.get(1);
        Market market=new Market(resourceDeck, goldDeck, faceUpResources,faceUpGolds,objectiveDeck);
        secretObjectives.add(objectiveDeck.get(0));
        secretObjectives.add(objectiveDeck.get(1));
        p1.setSecretObjectives(secretObjectives);

        ArrayList<GameController> gameControllers=new ArrayList<>();
        game = new Game(new Board(), market,players1,objectiveDeck.get(0),objectiveDeck.get(1), starterDeck,objectiveDeck);
        gameController = new GameController(game, 4, 0, gigaController);
        gameControllers.add(gameController);
        turnHandler=new TurnHandler(game);



        clientTest= new ClientTest();
    }

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
        //gigaController1.getGameControllers().getFirst().suspendGame();
    }

    @Test
    void addCardTest1(){
        initializeGame();
        p1.setPlayerState(new InitializingState(p1, turnHandler));
        gameController.addStarterCard(p1, starterDeck.get(2), starterDeck.get(2).getBack());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(31), resourceDeck.get(31).getFront(), 43, 43);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(15), resourceDeck.get(15).getFront(), 44, 42);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(32), resourceDeck.get(32).getFront(), 44, 44);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(34), goldDeck.get(34).getFront(), 43, 41);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(17), resourceDeck.get(17).getFront(), 44, 40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(29), resourceDeck.get(29).getFront(), 42, 40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(-1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(2), resourceDeck.get(2).getBack(), 41, 39);
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(-1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(36), resourceDeck.get(36).getBack(), 40, 40);
        assertTrue(p1.getManuscript().getField()[41][39].getCorner(-1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(14), goldDeck.get(14).getFront(), 41, 41);
        assertTrue(p1.getManuscript().getField()[40][40].getCorner(1, -1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(-1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(16), goldDeck.get(16).getFront(), 40, 42);
        assertTrue(p1.getManuscript().getField()[41][41].getCorner(-1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(33), goldDeck.get(33).getBack(), 45, 45);
        assertTrue(p1.getManuscript().getField()[44][44].getCorner(1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(1), goldDeck.get(1).getFront(), 42, 38);
        assertTrue(p1.getManuscript().getField()[41][39].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(14), goldDeck.get(14).getFront(), 43, 39);

        assertTrue(p1.getManuscript().getField()[42][38].getCorner(1, -1).isHidden());
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(1, 1).isHidden());
        assertTrue(p1.getManuscript().getField()[44][40].getCorner(-1, 1).isHidden());
        assertEquals(2, objectiveDeck.get(3).calculateObjectivePoints(p1.getManuscript()));
        assertEquals(3, objectiveDeck.get(5).calculateObjectivePoints(p1.getManuscript()));
        assertEquals(2, gameController.getGame().getPlayers().get(0).getManuscript().getFungiCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(0).getManuscript().getAnimalCounter());
        assertEquals(3, gameController.getGame().getPlayers().get(0).getManuscript().getPlantCounter());
        assertEquals(5, gameController.getGame().getPlayers().get(0).getManuscript().getInsectCounter());
        assertEquals(2, gameController.getGame().getPlayers().get(0).getManuscript().getInkwellCounter());
        assertEquals(1, gameController.getGame().getPlayers().get(0).getManuscript().getQuillCounter());
        assertEquals(0, gameController.getGame().getPlayers().get(0).getManuscript().getManuscriptCounter());
        assertEquals(23, gameController.getGame().getBoard().getPointsRedPlayer());
        Placement placement=new Placement(43,43);
        placement.getX();
        placement.getY();
        game.getBoard().getColourPlayerMap();
        game.getBoard().getPointsOf(PawnColour.fromStringToPawnColour("red"));
        game.getBoard().getPointsOf(PawnColour.fromStringToPawnColour("blue"));
        game.getBoard().getPointsOf(PawnColour.fromStringToPawnColour("yellow"));
        game.getBoard().getPointsOf(PawnColour.fromStringToPawnColour("green"));
        Map<String, Integer> map= game.getBoard().getScoreBoard();
        game.getMarket().getCommonObjectives();
        List<CornerSymbol> cornerSymbolList= CornerSymbol.valuesList();
        Chat chat= new Chat();
        chat.addChatMessage(new ChatMessage(p1.getUsername(),p2.getUsername(), "Let's play!"));
        ArrayList<ChatMessage> chatMessages=chat.getChatMessages();
        game.getChat(p1.getUsername(), p2.getUsername());
        game.getGeneralChat();
        game.setPlayers(players1);
        PlayerListener playerListener=new PlayerListener(new ClientTest(), p1);
        playerListener.getPlayerUsername();

    }

    @Test
    void addCardTest2(){
        initializeGame();
        p2.setPlayerState(new InitializingState(p2, turnHandler));
        gameController.addStarterCard(p2, starterDeck.get(1), starterDeck.get(1).getFront());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gameController.addCard(p2, resourceDeck.get(24), resourceDeck.get(24).getFront(), 41, 43);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(-1, -1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gameController.addCard(p2, resourceDeck.get(25), resourceDeck.get(25).getFront(), 41, 41);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gameController.addCard(p2, resourceDeck.get(28), resourceDeck.get(28).getFront(), 40, 44);
        assertTrue(p2.getManuscript().getField()[41][43].getCorner(-1, -1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gameController.addCard(p2, goldDeck.get(26), goldDeck.get(26).getFront(), 39, 45);
        assertTrue(p2.getManuscript().getField()[40][44].getCorner(-1, -1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gameController.addCard(p2, resourceDeck.get(6), resourceDeck.get(6).getFront(), 42, 40);
        assertTrue(p2.getManuscript().getField()[41][41].getCorner(1, 1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gameController.addCard(p2, goldDeck.get(35), goldDeck.get(35).getFront(), 43, 41);
        assertTrue(p2.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());
        assertTrue(p2.getManuscript().getField()[42][40].getCorner(1, -1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gameController.addCard(p2, goldDeck.get(37), goldDeck.get(37).getFront(), 38, 44);
        assertTrue(p2.getManuscript().getField()[39][45].getCorner(-1, 1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gameController.addCard(p2, resourceDeck.get(16), resourceDeck.get(16).getFront(), 44, 42);
        assertTrue(p2.getManuscript().getField()[43][41].getCorner(1, -1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gameController.addCard(p2, resourceDeck.get(18), resourceDeck.get(18).getBack(), 42, 44);
        assertTrue(p2.getManuscript().getField()[41][43].getCorner(1, -1).isHidden());

        p2.setPlayerState(new PlayingState(p2, turnHandler));
        gameController.addCard(p2, goldDeck.get(34), goldDeck.get(34).getFront(), 43, 43);

        assertTrue(p2.getManuscript().getField()[42][42].getCorner(1, -1).isHidden());
        assertTrue(p2.getManuscript().getField()[44][42].getCorner(-1, -1).isHidden());
        assertTrue(p2.getManuscript().getField()[42][44].getCorner(1, 1).isHidden());
        assertEquals(17, gameController.getGame().getBoard().getPointsGreenPlayer());
        assertEquals(4, objectiveDeck.get(13).calculateObjectivePoints(p2.getManuscript()));
        assertEquals(2, objectiveDeck.get(2).calculateObjectivePoints(p2.getManuscript()));
        assertEquals(3, objectiveDeck.get(6).calculateObjectivePoints(p2.getManuscript()));
    }


    @Test
    void addCardTest3(){
        initializeGame();
        p3.setPlayerState(new InitializingState(p3, turnHandler));
        gameController.addStarterCard(p3, starterDeck.get(0), starterDeck.get(0).getBack());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gameController.addCard(p3, resourceDeck.get(15), resourceDeck.get(15).getFront(), 41, 41);
        assertTrue(p3.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gameController.addCard(p3, resourceDeck.get(17), resourceDeck.get(17).getFront(), 40, 40);
        assertTrue(p3.getManuscript().getField()[41][41].getCorner(-1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gameController.addCard(p3, resourceDeck.get(14), resourceDeck.get(14).getFront(), 39, 39);
        assertTrue(p3.getManuscript().getField()[40][40].getCorner(-1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gameController.addCard(p3, resourceDeck.get(36), resourceDeck.get(36).getFront(), 43, 43);
        assertTrue(p3.getManuscript().getField()[42][42].getCorner(1, -1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gameController.addCard(p3, resourceDeck.get(0), resourceDeck.get(0).getBack(), 44, 42);
        assertTrue(p3.getManuscript().getField()[43][43].getCorner(1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gameController.addCard(p3, resourceDeck.get(33), resourceDeck.get(33).getFront(), 43, 41);
        assertTrue(p3.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());
        assertTrue(p3.getManuscript().getField()[44][42].getCorner(-1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gameController.addCard(p3, resourceDeck.get(29), resourceDeck.get(29).getFront(), 42, 40);
        assertTrue(p3.getManuscript().getField()[41][41].getCorner(1, 1).isHidden());
        assertTrue(p3.getManuscript().getField()[43][41].getCorner(-1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gameController.addCard(p3, goldDeck.get(33), goldDeck.get(33).getFront(), 41, 39);
        assertTrue(p3.getManuscript().getField()[40][40].getCorner(1, 1).isHidden());
        assertTrue(p3.getManuscript().getField()[42][40].getCorner(-1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gameController.addCard(p3, goldDeck.get(39), goldDeck.get(39).getFront(), 45, 41);
        assertTrue(p3.getManuscript().getField()[44][42].getCorner(1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gameController.addCard(p3, resourceDeck.get(10), resourceDeck.get(10).getBack(), 46, 40);
        assertTrue(p3.getManuscript().getField()[45][41].getCorner(1, 1).isHidden());

        p3.setPlayerState(new PlayingState(p3, turnHandler));
        gameController.addCard(p3, goldDeck.get(18), goldDeck.get(18).getFront(), 45, 43);

        assertTrue(p3.getManuscript().getField()[44][42].getCorner(1, -1).isHidden());
        assertEquals(14, gameController.getGame().getBoard().getPointsBluePlayer());
        assertEquals(2, objectiveDeck.get(9).calculateObjectivePoints(p3.getManuscript()));
        assertEquals(4, objectiveDeck.get(11).calculateObjectivePoints(p3.getManuscript()));
        assertEquals(2, objectiveDeck.get(1).calculateObjectivePoints(p3.getManuscript()));
        assertEquals(3, objectiveDeck.get(7).calculateObjectivePoints(p3.getManuscript()));
    }

    @Test
    void addCardTest4(){
        initializeGame();
        p4.setPlayerState(new InitializingState(p4, turnHandler));
        gameController.addStarterCard(p4, starterDeck.get(4), starterDeck.get(4).getBack());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gameController.addCard(p4, resourceDeck.get(1), resourceDeck.get(1).getBack(), 43, 41);
        assertTrue(p4.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gameController.addCard(p4, resourceDeck.get(3), resourceDeck.get(3).getBack(), 44, 40);
        assertTrue(p4.getManuscript().getField()[43][41].getCorner(1, 1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gameController.addCard(p4, resourceDeck.get(4), resourceDeck.get(4).getBack(), 45, 39);
        assertTrue(p4.getManuscript().getField()[44][40].getCorner(1, 1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gameController.addCard(p4, resourceDeck.get(11), resourceDeck.get(11).getFront(), 44, 42);
        assertTrue(p4.getManuscript().getField()[43][41].getCorner(1, -1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gameController.addCard(p4, resourceDeck.get(7), resourceDeck.get(7).getFront(), 45, 43);
        assertTrue(p4.getManuscript().getField()[44][42].getCorner(1, -1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gameController.addCard(p4, resourceDeck.get(12), resourceDeck.get(12).getFront(), 44, 44);
        assertTrue(p4.getManuscript().getField()[45][43].getCorner(-1, -1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gameController.addCard(p4, goldDeck.get(30), goldDeck.get(30).getBack(), 43, 45);
        assertTrue(p4.getManuscript().getField()[44][44].getCorner(-1, -1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gameController.addCard(p4, goldDeck.get(2), goldDeck.get(2).getBack(), 42, 40);
        assertTrue(p4.getManuscript().getField()[43][41].getCorner(-1, 1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gameController.addCard(p4, goldDeck.get(1), goldDeck.get(1).getBack(), 43, 39);
        assertTrue(p4.getManuscript().getField()[42][40].getCorner(1, 1).isHidden());
        assertTrue(p4.getManuscript().getField()[44][40].getCorner(-1, 1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gameController.addCard(p4, goldDeck.get(0), goldDeck.get(0).getBack(), 44, 38);
        assertTrue(p4.getManuscript().getField()[43][39].getCorner(1, 1).isHidden());
        assertTrue(p4.getManuscript().getField()[45][39].getCorner(-1, 1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gameController.addCard(p4, goldDeck.get(19), goldDeck.get(19).getBack(), 41, 41);
        assertTrue(p4.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());
        assertTrue(p4.getManuscript().getField()[42][40].getCorner(-1, -1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gameController.addCard(p4, goldDeck.get(17), goldDeck.get(17).getBack(), 41, 39);
        assertTrue(p4.getManuscript().getField()[42][40].getCorner(1, 1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gameController.addCard(p4, goldDeck.get(31), goldDeck.get(31).getFront(), 40, 42);
        assertTrue(p4.getManuscript().getField()[41][41].getCorner(-1, -1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gameController.addCard(p4, resourceDeck.get(9), resourceDeck.get(9).getFront(), 39, 41);
        assertTrue(p4.getManuscript().getField()[40][42].getCorner(-1, 1).isHidden());

        p4.setPlayerState(new PlayingState(p4, turnHandler));
        gameController.addCard(p4, goldDeck.get(15), goldDeck.get(15).getFront(), 42, 38);

        assertTrue(p4.getManuscript().getField()[41][39].getCorner(1, 1).isHidden());
        assertTrue(p4.getManuscript().getField()[43][39].getCorner(-1, 1).isHidden());
        assertEquals(7, gameController.getGame().getBoard().getPointsYellowPlayer());
        assertEquals(4, objectiveDeck.get(0).calculateObjectivePoints(p4.getManuscript()));
        assertEquals(6, objectiveDeck.get(5).calculateObjectivePoints(p4.getManuscript()));
        assertEquals(4, objectiveDeck.get(9).calculateObjectivePoints(p4.getManuscript()));
        assertEquals(4, objectiveDeck.get(8).calculateObjectivePoints(p4.getManuscript()));
    }

    @Test
    void addCardTest5(){
        initializeGame();
        p1.setPlayerState(new InitializingState(p1, turnHandler));
        gameController.addStarterCard(p1, starterDeck.get(0), starterDeck.get(0).getFront());
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(0), resourceDeck.get(0).getBack(), 43, 41);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(1), resourceDeck.get(1).getBack(), 42, 40);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(2), resourceDeck.get(2).getBack(), 43, 39);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(3), resourceDeck.get(3).getBack(), 42, 38);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(4), resourceDeck.get(4).getBack(), 43, 37);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(5), resourceDeck.get(5).getBack(), 42, 36);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(6), resourceDeck.get(6).getBack(), 43, 35);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(7), resourceDeck.get(7).getBack(), 42, 34);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(8), resourceDeck.get(8).getBack(), 43, 33);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(9), resourceDeck.get(9).getBack(), 42, 32);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(10), resourceDeck.get(10).getBack(), 43, 31);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(11), resourceDeck.get(11).getBack(), 42, 30);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(12), resourceDeck.get(12).getBack(), 43, 29);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(13), resourceDeck.get(13).getBack(), 42, 28);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(14), resourceDeck.get(14).getBack(), 43, 27);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(15), resourceDeck.get(15).getBack(), 42, 26);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(16), resourceDeck.get(16).getBack(), 43, 25);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(17), resourceDeck.get(17).getBack(), 42, 24);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(18), resourceDeck.get(18).getBack(), 43, 23);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(19), resourceDeck.get(19).getBack(), 42, 22);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(20), resourceDeck.get(20).getBack(), 43, 21);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(21), resourceDeck.get(21).getBack(), 42, 20);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(22), resourceDeck.get(22).getBack(), 43, 19);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(23), resourceDeck.get(23).getBack(), 42, 18);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(24), resourceDeck.get(24).getBack(), 43, 17);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(25), resourceDeck.get(25).getBack(), 42, 16);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(26), resourceDeck.get(26).getBack(), 43, 15);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(27), resourceDeck.get(27).getBack(), 42, 14);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(28), resourceDeck.get(28).getBack(), 43, 13);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(29), resourceDeck.get(29).getBack(), 42, 12);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(30), resourceDeck.get(30).getBack(), 43, 11);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(31), resourceDeck.get(31).getBack(), 42, 10);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(32), resourceDeck.get(32).getBack(), 43, 9);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(33), resourceDeck.get(33).getBack(), 42, 8);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(34), resourceDeck.get(34).getBack(), 43, 7);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(35), resourceDeck.get(35).getBack(), 42, 6);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(36), resourceDeck.get(36).getBack(), 43, 5);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(37), resourceDeck.get(37).getBack(), 42, 4);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(38), resourceDeck.get(38).getBack(), 43, 3);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(39), resourceDeck.get(39).getBack(), 42, 2);
    }

    @Test
    void addCardTest6(){
        initializeGame();
        p1.setPlayerState(new InitializingState(p1, turnHandler));
        gameController.addStarterCard(p1, starterDeck.get(1), starterDeck.get(1).getFront());
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(0), goldDeck.get(0).getBack(), 43, 41);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(1), goldDeck.get(1).getBack(), 44, 40);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(2), goldDeck.get(2).getBack(), 45, 39);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(3), goldDeck.get(3).getBack(), 46, 38);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(4), goldDeck.get(4).getBack(), 47, 37);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(5), goldDeck.get(5).getBack(), 48, 36);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(6), goldDeck.get(6).getBack(), 49, 35);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(7), goldDeck.get(7).getBack(), 50, 34);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(8), goldDeck.get(8).getBack(), 51, 33);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(9), goldDeck.get(9).getBack(), 52, 32);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(10), goldDeck.get(10).getBack(), 53, 31);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(11), goldDeck.get(11).getBack(), 54, 30);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(12), goldDeck.get(12).getBack(), 55, 29);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(13), goldDeck.get(13).getBack(), 56, 28);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(14), goldDeck.get(14).getBack(), 57, 27);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(15), goldDeck.get(15).getBack(), 58, 26);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(16), goldDeck.get(16).getBack(), 59, 25);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(17), goldDeck.get(17).getBack(), 60, 24);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(18), goldDeck.get(18).getBack(), 61, 23);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(19), goldDeck.get(19).getBack(), 62, 22);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(20), goldDeck.get(20).getBack(), 63, 21);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(21), goldDeck.get(21).getBack(), 64, 20);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(22), goldDeck.get(22).getBack(), 65, 19);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(23), goldDeck.get(23).getBack(), 66, 18);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(24), goldDeck.get(24).getBack(), 67, 17);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(25), goldDeck.get(25).getBack(), 68, 16);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(26), goldDeck.get(26).getBack(), 69, 15);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(27), goldDeck.get(27).getBack(), 70, 14);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(28), goldDeck.get(28).getBack(), 71, 13);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(29), goldDeck.get(29).getBack(), 72, 12);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(30), goldDeck.get(30).getBack(), 73, 11);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(31), goldDeck.get(31).getBack(), 74, 10);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(32), goldDeck.get(32).getBack(), 75, 9);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(33), goldDeck.get(33).getBack(), 76, 8);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(34), goldDeck.get(34).getBack(), 77, 7);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(35), goldDeck.get(35).getBack(), 78, 6);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(36), goldDeck.get(36).getBack(), 79, 5);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(37), goldDeck.get(37).getBack(), 80, 4);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(38), goldDeck.get(38).getBack(), 81, 3);
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(39), goldDeck.get(39).getBack(), 82, 2);
        assertEquals(6, objectiveDeck.get(2).calculateObjectivePoints(p1.getManuscript()));
        assertEquals(6, objectiveDeck.get(0).calculateObjectivePoints(p1.getManuscript()));
    }


    @Test
    void addCardTest7() throws IOException, InterruptedException {
        initializeGame();
        p1.setPlayerState(new InitializingState(p1, turnHandler));
        gameController.addStarterCard(p1, starterDeck.get(2), starterDeck.get(2).getBack());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(10), goldDeck.get(10).getBack(), 41, 41);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(10), resourceDeck.get(10).getBack(), 40, 40);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(11), goldDeck.get(11).getBack(), 39, 39);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(11), resourceDeck.get(11).getBack(), 38, 38);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(0), resourceDeck.get(0).getBack(), 37, 37);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(12), resourceDeck.get(12).getBack(), 38, 36);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(1), resourceDeck.get(1).getBack(), 37, 35);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(2), resourceDeck.get(2).getBack(), 36, 38);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(3), resourceDeck.get(3).getBack(), 35, 39);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(4), resourceDeck.get(4).getBack(), 34, 40);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(5), resourceDeck.get(5).getBack(), 41, 43);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(0), goldDeck.get(0).getBack(), 40, 44);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(6), resourceDeck.get(6).getBack(), 39, 45);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(1), goldDeck.get(1).getBack(), 38, 46);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(20), goldDeck.get(20).getBack(), 37, 47);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(21), goldDeck.get(21).getBack(), 36, 46);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(22), goldDeck.get(22).getBack(), 35, 45);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(23), goldDeck.get(23).getBack(), 34, 44);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(30), resourceDeck.get(30).getBack(), 33, 43);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(31), resourceDeck.get(31).getBack(), 33, 41);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(32), resourceDeck.get(32).getBack(), 32, 42);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(13), resourceDeck.get(13).getBack(), 38, 38);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(24), goldDeck.get(24).getBack(), 37, 39);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(33), resourceDeck.get(33).getBack(), 43, 41);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(34), resourceDeck.get(34).getBack(), 44, 40);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(35), resourceDeck.get(35).getBack(), 45, 39);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(30), goldDeck.get(30).getBack(), 46, 38);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(14), resourceDeck.get(14).getBack(), 47, 37);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(31), goldDeck.get(31).getBack(), 46, 36);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(15), resourceDeck.get(15).getBack(), 47, 35);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(25), goldDeck.get(25).getBack(), 43, 43);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(26), goldDeck.get(26).getBack(), 44, 44);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(20), resourceDeck.get(20).getBack(), 45, 45);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(21), resourceDeck.get(21).getBack(), 46, 46);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(36), resourceDeck.get(36).getBack(), 47, 47);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(22), resourceDeck.get(22).getBack(), 46, 48);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(32), goldDeck.get(32).getBack(), 47, 49);
    }

    @Test
    void addCardTest8(){
        initializeGame();
        p1.setPlayerState(new InitializingState(p1, turnHandler));
        gameController.addStarterCard(p1, starterDeck.get(5), starterDeck.get(5).getFront());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(26), resourceDeck.get(26).getFront(), 41, 41);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(34), resourceDeck.get(34).getFront(), 43, 41);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(29), resourceDeck.get(29).getFront(), 40, 40);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(22), goldDeck.get(22).getFront(), 40, 42);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(33), resourceDeck.get(33).getFront(), 39, 39);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(35), goldDeck.get(35).getFront(), 39, 41);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(10), resourceDeck.get(10).getBack(), 41, 43);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(37), goldDeck.get(37).getFront(), 38, 38);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(22), resourceDeck.get(22).getFront(), 40, 44);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(21), goldDeck.get(21).getFront(), 44, 40);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(8), resourceDeck.get(8).getFront(), 38, 42);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(9), resourceDeck.get(9).getBack(), 37, 43);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(37), goldDeck.get(37).getFront(), 38, 44);

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(33), goldDeck.get(33).getFront(), 39, 43);
    }
    @Test
    void addCardTest9(){
        initializeGame();
        p1.setPlayerState(new InitializingState(p1, turnHandler));
        gameController.addStarterCard(p1, starterDeck.get(0), starterDeck.get(0).getFront());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(21), resourceDeck.get(21).getFront(), 41, 41);
        assertEquals(p1.getPlayerState().toString(), "DrawingState");
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(36), resourceDeck.get(36).getFront(), 43, 41);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(20), resourceDeck.get(20).getBack(), 43, 43);
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(23), resourceDeck.get(23).getBack(), 42, 44);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(-1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(28), resourceDeck.get(28).getFront(), 41, 45);
        assertTrue(p1.getManuscript().getField()[42][44].getCorner(-1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(22), resourceDeck.get(22).getFront(), 40, 44);
        assertTrue(p1.getManuscript().getField()[41][45].getCorner(-1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(29), resourceDeck.get(29).getFront(), 39, 45);
        assertTrue(p1.getManuscript().getField()[40][44].getCorner(-1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(37), resourceDeck.get(37).getFront(), 38, 44);
        assertTrue(p1.getManuscript().getField()[39][45].getCorner(-1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(29), goldDeck.get(29).getFront(), 44, 44);
        assertTrue(p1.getManuscript().getField()[43][43].getCorner(1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(17), resourceDeck.get(17).getFront(), 44, 40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(25), goldDeck.get(25).getFront(), 42, 40);
        assertTrue(p1.getManuscript().getField()[43][41].getCorner(-1, 1).isHidden());
        assertTrue(p1.getManuscript().getField()[41][41].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(4), resourceDeck.get(4).getFront(), 43, 39);
        assertTrue(p1.getManuscript().getField()[42][40].getCorner(1, 1).isHidden());
        assertTrue(p1.getManuscript().getField()[44][40].getCorner(-1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(7), resourceDeck.get(7).getFront(), 45, 43);
        assertTrue(p1.getManuscript().getField()[44][44].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(0), goldDeck.get(0).getFront(), 45, 45);
        assertTrue(p1.getManuscript().getField()[44][44].getCorner(1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(15), resourceDeck.get(15).getFront(), 46, 46);
        assertTrue(p1.getManuscript().getField()[45][45].getCorner(1, -1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, goldDeck.get(14), goldDeck.get(14).getFront(), 44, 38);
        assertTrue(p1.getManuscript().getField()[43][39].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(5), resourceDeck.get(5).getFront(), 43, 37);
        assertTrue(p1.getManuscript().getField()[44][38].getCorner(-1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(8), resourceDeck.get(8).getFront(), 44, 36);
        assertTrue(p1.getManuscript().getField()[43][37].getCorner(1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(9), resourceDeck.get(9).getFront(), 43, 35);
        assertTrue(p1.getManuscript().getField()[44][36].getCorner(-1, 1).isHidden());

        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.addCard(p1, resourceDeck.get(34), resourceDeck.get(34).getFront(), 42, 36);

        assertTrue(p1.getManuscript().getField()[43][35].getCorner(-1, -1).isHidden());
        assertEquals(6, objectiveDeck.get(4).calculateObjectivePoints(p1.getManuscript()));
        assertEquals(3, objectiveDeck.get(5).calculateObjectivePoints(p1.getManuscript()));
    }

    @Test
    void testState(){
        initializeGame();
        p1.setPlayerState(new InitializingState(p1, turnHandler));
        gameController.chooseObjectiveCard(p1,1);
        gameController.addCard(p1,resourceDeck.get(10),resourceDeck.get(10).getFront(),44,44);
        gameController.drawCard(p1,true,false,0);
        gameController.addStarterCard(p1, starterDeck.get(0), starterDeck.get(0).getFront());


        p1.setPlayerState(new ChooseObjectiveState(p1, turnHandler));
        gameController.chooseObjectiveCard(p1,0);
        gameController.addCard(p1,resourceDeck.get(10),resourceDeck.get(10).getFront(),44,44);
        gameController.drawCard(p1,true,false,0);
        gameController.addStarterCard(p1, starterDeck.get(0), starterDeck.get(0).getFront());


        p1.setPlayerState(new PlayingState(p1, turnHandler));
        p2.setPlayerState(new WaitingState(p2, turnHandler));
        p3.setPlayerState(new WaitingState(p3, turnHandler));
        p4.setPlayerState(new WaitingState(p4, turnHandler));
        assertEquals(p1.getPlayerState().toStringGUI(), "It's your turn to play!");
        gameController.addCard(p1, resourceDeck.get(21), resourceDeck.get(21).getFront(), 41, 41);
        assertEquals(p1.getPlayerState().toString(), "DrawingState");
        gameController.chooseObjectiveCard(p1,1);
        gameController.addStarterCard(p1,starterDeck.get(0),starterDeck.get(0).getFront());
        gameController.addCard(p1,resourceDeck.get(0),resourceDeck.get(0).getFront(),43,43);
        assertEquals(p1.getPlayerState().toStringGUI(), "Draw a card!");
        gameController.drawCard(p1,true,false,0);


        p1.setPlayerState(new EndOfTurnState(p1, turnHandler));
        gameController.drawCard(p1,true,true,0);
        gameController.chooseObjectiveCard(p1, 0);
        gameController.addCard(p1,resourceDeck.get(9), resourceDeck.get(9).getFront(),43,43);
        gameController.addStarterCard(p1, starterDeck.get(4), starterDeck.get(4).getFront());


        p1.setPlayerState(new WaitingState(p1, turnHandler));
        assertEquals(p1.getPlayerState().toString(), "WaitingState");
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(-1, 1).isHidden());
        gameController.drawCard(p1,true,true,0);
        gameController.chooseObjectiveCard(p1, 0);
        gameController.addCard(p1,resourceDeck.get(9), resourceDeck.get(9).getFront(),43,43);
        gameController.addStarterCard(p1, starterDeck.get(4), starterDeck.get(4).getFront());
        assertEquals(p1.getPlayerState().toStringGUI(), "Current player not set");

        p2.setPlayerState(new EndingState(p2, turnHandler));
        gameController.chooseObjectiveCard(p2, 0);
        gameController.addCard(p2,resourceDeck.get(8), resourceDeck.get(8).getFront(),43,43);
        gameController.addStarterCard(p2, starterDeck.get(3), starterDeck.get(3).getFront());
        gameController.drawCard(p2,true,false,0);

        assertEquals(p2.getPlayerState().toStringGUI(), "This is the last turn!");
        String pathImage1= p1.getPawnColour().getPathImage();
        String pathImage2= p2.getPawnColour().getPathImage();
        String pathImage3= p3.getPawnColour().getPathImage();
        assertEquals(p1, game.getPlayer(p1.getUsername()));
    }

    @Test
    void minimodelTestGame(){
        MiniModel miniModel=new MiniModel(new Board());
        MiniModel miniModel2=new MiniModel(new Chat(new Player("user", new Manuscript(), PawnColour.fromStringToPawnColour("red")),
                new Player("user2", new Manuscript(), PawnColour.fromStringToPawnColour("yellow"))));
        miniModel2.checkOtherUsername("user");
    }

    @Test
    void sendChatMessageTest(){
        initializeGame();
        Chat chat=new Chat();
        Chat chatPrivate=new Chat(p1,p2);
        gameController.sendChatMessage(new ChatMessage(p1.getUsername(), p2.getUsername(), "Hello"));
        gameController.sendChatMessage(new ChatMessage(p1.getUsername(),"global", "Hello"));
    }

    @Test
    void suspendPlayerTest(){
        initializeGame();
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        gameController.suspendPlayer(p1);
        TurnHandler turnHandlerTest= gameController.getTurnHandler();
    }

    @Test
    void suspendGameTest(){
        initializeGame();
        TurnHandler turnHandler1=new TurnHandler(game);
        gameController.setTurnHandler(turnHandler1);
        ClientTest clientTest1=new ClientTest();
        p1.setPlayerState(new PlayingState(p1, turnHandler));
        p2.setPlayerState(new WaitingState(p2, turnHandler));
        p3.setPlayerState(new WaitingState(p3, turnHandler));
        gameController.getCommands().add(new ReconnectPlayerCommand(clientTest1, p1));
        try {
            gameController.suspendGame();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void cardsFinishedTest(){
        initializeGame();
//        for(ResourceCard r: resourceDeck){
//            resourceDeck.remove(r);
//        }
//        for (int i = list.size() - 1; i >= 0; i--) {
//            list.remove(i);
//        }
        resourceDeck.clear();
        p1.setPlayerState(new DrawingState(p1, turnHandler));
        gameController.drawCard(p1,false, true, 0);


    }



}
