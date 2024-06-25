package it.polimi.ingsw.gc27.Model.ClientClass;

import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MiniModelTest {
    private MiniModel miniModel;
    private ClientManuscript manuscript, manuscript2;
    private ClientBoard board;
    private ClientMarket market;
    private ClientPlayer player, player2;
    private ArrayList<ResourceCard> hand;
    private HashMap<String, ClientManuscript> manuscriptsMap;
    private ArrayList<Player> chatters;
    private ClientChat chat1, chat2;
    private ChatMessage chatMessage;
    @BeforeEach
    public void setUp() {
        miniModel = new MiniModel();
        manuscript = new Manuscript();
        manuscript2 = new Manuscript();
        board = new Board();
        market = new Market();
        player = new Player("player1", (Manuscript) manuscript,PawnColour.fromStringToPawnColour("blue"));
        player2 = new Player("player2", (Manuscript) manuscript2,PawnColour.fromStringToPawnColour("red"));
        hand = new ArrayList<>();
        manuscriptsMap = new HashMap<>();
        manuscriptsMap.put("player1", manuscript);
        chatters=new ArrayList<>();
        chatters.add((Player) player);
        chatters.add((Player) player2);
        chatMessage= new ChatMessage(player.getUsername(), player2.getUsername(), "Let's play!");
    }
    @Test
    void minimodelTest(){
        manuscript=new Manuscript();
        player=new Player("User", (Manuscript) manuscript, PawnColour.fromStringToPawnColour("red"));
        player2= new Player("User2", new Manuscript(), PawnColour.fromStringToPawnColour("blue"));
        miniModel= new MiniModel((Player) player, (Manuscript) manuscript);
        miniModel=new MiniModel();


        chat1=new ClientChatTest();
        chat2=new Chat();
        miniModel=new MiniModel((Chat) chat2);
        miniModel=new MiniModel((Chat) chat2, (Player) player, "global");


    }

    @Test
    void ManuscriptsMapTest() {
        miniModel.getManuscriptsMap();
        miniModel.setManuscriptsMap(manuscriptsMap);
    }

    @Test
    void getPlayer() {
    }

    @Test
    void getMarket() {
    }

    @Test
    void getBoard() {
    }

    @Test
    void getManuscript() {
    }

    @Test
    void getHand() {
    }

    @Test
    void setHand() {
    }

    @Test
    void setManuscript() {
    }

    @Test
    void setMarket() {
    }

    @Test
    void setBoard() {
    }

    @Test
    void setPlayer() {
    }

    @Test
    void copyTest() {
        MiniModel miniModel2=new MiniModel();
        miniModel2.setHand(hand);
        miniModel2.setMarket(market);
        miniModel2.setPlayer(player);
        miniModel2.setBoard(board);
        miniModel2.setManuscript(manuscript);
        miniModel.copy(miniModel2);
        assertEquals(hand, miniModel.getHand());
        assertEquals(market, miniModel.getMarket());
        assertEquals(manuscript, miniModel.getManuscript());
        assertEquals(player, miniModel.getPlayer());
        assertEquals(board, miniModel.getBoard());
    }

    @Test
    void chatTest() {
        Chat chat = new Chat((Player) player, (Player) player2);
        chat.getChatters().add(player2.getUsername());
        chat.getChatters().add(player.getUsername());
        miniModel.getChats().add(chat);
        assertEquals(player.getUsername(), chatMessage.getSender());
        assertEquals(player2.getUsername(), chatMessage.getReceiver());
        assertEquals("Let's play!", chatMessage.getContent());
        assertTrue(chat.contains(player2.getUsername()), player2.getUsername());

    }

    @Test
    void getOtherPlayersUsernames() {
    }

    @Test
    void checkOtherUsername() {
    }

    @Test
    void getChats() {
    }

    @Test
    void testGetChat() {
    }
}