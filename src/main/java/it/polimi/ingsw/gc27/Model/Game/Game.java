
package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.PlayerJoinedMessage;
import it.polimi.ingsw.gc27.Model.Card.Card;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.PlayerListener;
import it.polimi.ingsw.gc27.Net.VirtualView;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Represents the game where players participate and play against each other
 */
public class Game implements Serializable {

    private final transient BlockingQueue<PlayerListener> observers = new LinkedBlockingQueue<>() {};
    private Integer numActualPlayers;
    private Market market;
    private List<Player> players;
    private Board board;
    private ObjectiveCard commonObjective1;
    private ObjectiveCard commonObjective2;
    private ArrayList<StarterCard> starterDeck;
    private ArrayList<ObjectiveCard> objectiveDeck;
    private ArrayList<PawnColour> availablePawns = new ArrayList<>(List.of(PawnColour.GREEN, PawnColour.YELLOW, PawnColour.BLUE, PawnColour.RED));
    private final Chat generalChat = new Chat();
    final private HashMap< Pair<String, String>, Chat> chatMap = new HashMap<>();
    private int readyPlayers = 0;


    /**
     * constructor of the game
     * @param gameID game id
     * @param board the board/scoreboard
     * @param players players participating in the game
     */
    public Game(int gameID, Board board, List<Player> players) {
        this.board = board;
        this.players = players;
    }

    /**
     * constructor of the game
     * @param board the board/scoreboard
     * @param market the common market
     * @param players list of players
     * @param commonObjective1 common objective card
     * @param commonObjective2 common objective card
     * @param starterDeck the deck containing starter cards
     * @param objectiveDeck the deck containing objective cards
     */
    public Game(Board board, Market market, List<Player> players, ObjectiveCard commonObjective1, ObjectiveCard commonObjective2, ArrayList<StarterCard> starterDeck, ArrayList<ObjectiveCard> objectiveDeck) {
        this.board = board;
        this.market = market;
        this.players = players;
        this.commonObjective1 = commonObjective1;
        this.commonObjective2 = commonObjective2;
        this.starterDeck = starterDeck;
        this.objectiveDeck = objectiveDeck;
    }

    /**
     * Updates player's points, as a result of a played card or objectives points
     * @param player which player receives the points
     * @param points amount of points
     */
    public void addPoints(Player player, int points) {
        PawnColour pawncolour = player.getPawnColour();
        switch (pawncolour) {
            case BLUE -> board.setPointsBluePlayer(board.getPointsBluePlayer() + points);
            case RED -> board.setPointsRedPlayer(board.getPointsRedPlayer() + points);
            case GREEN -> board.setPointsGreenPlayer(board.getPointsGreenPlayer() + points);
            case YELLOW -> board.setPointsYellowPlayer(board.getPointsYellowPlayer() + points);
        }
    }

    /**
     * this method returns a boolean indicating if a pawn colour is mapped to an actual player
     * @param pawn pawn colour
     * @return boolean
     */
    public boolean validPawn(String pawn) {
        for (var available : availablePawns) {
            if (available.toString().equalsIgnoreCase(pawn)) {
                return true;
            }
        }
        return false;
    }

    /**
     * adds a player to the game
     * @param p player
     * @param client player's client
     */
    public void addPlayer(Player p, VirtualView client) {

        for(Player player : players){
            Chat chat = new Chat(player, p);
            chatMap.put(new Pair<String, String>(player.getUsername(), p.getUsername()),   chat);
            chatMap.put(new Pair<String, String>(p.getUsername(), player.getUsername()),   chat);
        }
        this.players.add(p);
        board.setColourPlayer(p);

//        //create a listener
        //he will listen the observable and decide if the message has to be sent to his player
        this.addObserver(new PlayerListener(client, p));
        this.notifyObservers(new PlayerJoinedMessage(p.getUsername()));
    }

    /**
     * adds observer to the observers list
     * @param pl PlayerListener
     */
    public void addObserver(PlayerListener pl) {
        observers.add(pl);
    }

    /**
     * removes observer from the observers list
     * @param username player's username
     */
    public void removeObserver(String username) {
        PlayerListener toBeRemoved = observers.stream().filter(u -> u.getPlayerUsername().equals(username)).toList().getFirst();
        observers.remove(toBeRemoved);
    }

    /**
     * notifies all the observers, sending them a specific message
     * @param message message
     */
    public void notifyObservers(Message message) {
        for (PlayerListener o : observers) {
            o.update(message);
        }
    }

    /**
     * @return if the game is suspended
     */
    public boolean isSuspended() {
        int count = 0;
        for(Player p: players){
            if(p.isDisconnected()){
                count++;
            }
        }
        return count >= players.size() - 1;

    }
    /**
     * changes the state of a player to ready
     * @param p player
     * @return the number of players that are ready
     */
    public int ready(Player p) {
        synchronized (board){
            this.readyPlayers++;
            return readyPlayers;
        }
    }

    /**
     * Gets the general chat instance associated with the game
     * @return The general Chat instance
     */
    public Chat getGeneralChat(){
        return this.generalChat;
    }

    /**
     * Gets the chat between two specific players
     * @param p1 Username of the first player
     * @param p2 Username of the second player
     * @return The Chat instance representing the chat between the two players
     */
    public Chat getChat(String p1, String p2) {
        return chatMap.get(new Pair<>(p1, p2));
    }

    /**
     * Gets all chats associated with a specific player
     * @param player The player
     * @return List of Chat instances including the general chat and chats with other players
     */
    public ArrayList<Chat> getChats(Player player){
        ArrayList<Chat> chats = new ArrayList<>();
        chats.add(this.generalChat);
        for(Pair<String , String> p : chatMap.keySet()){
            if(p.getKey().equals(player.getUsername())){
                chats.add(chatMap.get(p));
            }
        }
        return chats;
    }


    /**
     * Gets the number of actual players in the game
     * @return The number of actual players
     */
    public Integer getNumActualPlayers() {
        return numActualPlayers;
    }

    /**
     * Sets the number of actual players in the game
     * @param numActualPlayers The number of actual players to set
     */
    public void setNumActualPlayers(Integer numActualPlayers) {
        this.numActualPlayers = numActualPlayers;
    }

    /**
     * Gets a player by their username
     * @param playerName The username of the player
     * @return The Player instance corresponding to the given username, or null if not found
     */
    public Player getPlayer(String playerName) {
        for (Player p : players) {
            if (p.getUsername().equals(playerName)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Gets the deck containing starter cards
     * @return The ArrayList of StarterCard instances representing the starter deck
     */
    public ArrayList<StarterCard> getStarterDeck() {
        return starterDeck;
    }
//    /**
//     * Sets the deck containing starter cards
//     * @param starterDeck The ArrayList of StarterCard instances to set as the starter deck
//     */
//    public void setStarterDeck(ArrayList<StarterCard> starterDeck) {
//        this.starterDeck = starterDeck;
//    } no usages
    /**
     * Gets the deck containing objective cards
     * @return The ArrayList of ObjectiveCard instances representing the objective deck
     */
    public ArrayList<ObjectiveCard> getObjectiveDeck() {
        return objectiveDeck;
    }

//    /**
//     * Sets the deck containing objective cards
//     * @param objectiveDeck The ArrayList of ObjectiveCard instances to set as the objective deck
//     */
//    public void setObjectiveDeck(ArrayList<ObjectiveCard> objectiveDeck) {
//        this.objectiveDeck = objectiveDeck;
//    } no usages
    /**
     * Gets the market
     * @return The Market instance representing the game's market
     */
    public Market getMarket() {
        return market;
    }

    /**
     * Sets the market
     * @param market The Market instance to set as the game's market
     */
    public void setMarket(Market market) {
        this.market = market;
    }

    /**
     * Gets the full list of available pawn colors
     * @return The ArrayList available pawn colors
     */
    public synchronized ArrayList<PawnColour> getAvailablePawns() {
        return availablePawns;
    }

//    /**
//     * Sets the list of available pawn colors
//     * @param availablePawns The ArrayList of available pawn colors
//     */
//    public void setAvailablePawns(ArrayList<PawnColour> availablePawns) {
//        this.availablePawns = availablePawns;
//    } da togliere no usages

    /**
     * Gets the board
     * @return the game's board
     */
    public Board getBoard() {
        return board;
    }

//    /**
//     * Sets the board
//     * @param board The Board instance to set as the game's board
//     */
//    public void setBoard(Board board) {
//        this.board = board;
//    }  no usages

    /**
     * Gets the first common objective card
     * @return the first common objective card
     */
    public ObjectiveCard getCommonObjective1() {
        return commonObjective1;
    }

    /**
     * Gets the second common objective card
     * @return the second common objective card
     */
    public ObjectiveCard getCommonObjective2() {
        return commonObjective2;
    }

    /**
     * Gets the list of players participating in the game
     * @return The List of Player instances representing the players in the game
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Sets the list of players participating in the game
     * @param players The List of Player instances to set as the players in the game
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    // check only decks, so that when they are empty there are 4 more cards to draw in the last round triggered by this method
    public boolean isMarketEmpty(){
        if(market.getGoldDeck().isEmpty() &&
                market.getResourceDeck().isEmpty())
//                checkIfNoMoreFaceUp(market.getFaceUp(true)) &&
//                checkIfNoMoreFaceUp(market.getFaceUp(false)))
        {
            return true;
        }
        return false;
    }

//    private boolean checkIfNoMoreFaceUp(ResourceCard[] faceUps){
//        for(int i = 0; i < faceUps.length; i++){
//            if(faceUps[i] != null){
//                return false;
//            }
//        }
//        return true;
//    }    no usages
}
