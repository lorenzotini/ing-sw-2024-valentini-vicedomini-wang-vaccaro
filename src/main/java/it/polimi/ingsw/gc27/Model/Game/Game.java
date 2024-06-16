
package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.PlayerJoinedMessage;
import it.polimi.ingsw.gc27.Model.Card.Card;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Observer;
import it.polimi.ingsw.gc27.Model.Listener.PlayerListener;
import it.polimi.ingsw.gc27.Net.VirtualView;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Game implements Serializable {

    private final transient BlockingQueue<Observer> observers = new LinkedBlockingQueue<>() {};
    private Integer numActualPlayers;

    private Market market;
    private List<Player> players;
    private Board board;
    //private Board board= new Board(players);
    private ObjectiveCard commonObjective1;
    private ObjectiveCard commonObjective2;
    private ArrayList<StarterCard> starterDeck;
    private ArrayList<ObjectiveCard> objectiveDeck;
    private ArrayList<PawnColour> availablePawns = new ArrayList<>(List.of(PawnColour.GREEN, PawnColour.YELLOW, PawnColour.BLUE, PawnColour.RED));
    private final Chat generalChat = new Chat();
    final private HashMap< Pair<String, String>, Chat> chatMap = new HashMap<>();
    private int readyPlayers = 0;


    public Game() {
    }

    public Game(int gameID, Board board, List<Player> players) {
        this.board = board;
        this.players = players;
    }

    public Game(Board board, Market market, List<Player> players, ObjectiveCard commonObjective1, ObjectiveCard commonObjective2, ArrayList<StarterCard> starterDeck, ArrayList<ObjectiveCard> objectiveDeck) {

        this.board = board;
        this.market = market;
        this.players = players;
        this.commonObjective1 = commonObjective1;
        this.commonObjective2 = commonObjective2;
        this.starterDeck = starterDeck;
        this.objectiveDeck = objectiveDeck;

    }

    public Integer getNumActualPlayers() {
        return numActualPlayers;
    }

    public void setNumActualPlayers(Integer numActualPlayers) {
        this.numActualPlayers = numActualPlayers;
    }

    public Player getPlayer(String playerName) {
        for (Player p : players) {
            if (p.getUsername().equals(playerName)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<StarterCard> getStarterDeck() {
        return starterDeck;
    }
    public void setStarterDeck(ArrayList<StarterCard> starterDeck) {
        this.starterDeck = starterDeck;
    }
    public ArrayList<ObjectiveCard> getObjectiveDeck() {
        return objectiveDeck;
    }
    public void setObjectiveDeck(ArrayList<ObjectiveCard> objectiveDeck) {
        this.objectiveDeck = objectiveDeck;
    }
    public Market getMarket() {
        return market;
    }
    public void setMarket(Market market) {
        this.market = market;
    }
    public synchronized ArrayList<PawnColour> getAvailablePawns() {
        return availablePawns;
    }
    public void setAvailablePawns(ArrayList<PawnColour> availablePawns) {
        this.availablePawns = availablePawns;
    }
    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }
    public Card getCommonObjective1() {
        return commonObjective1;
    }
    public Card getCommonObjective2() {
        return commonObjective2;
    }
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }


    /**
     * Updates player's points, as a result of a played card or objectives points
     *
     * @param player
     * @param points
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

    public boolean validPawn(String pawn) {
        for (var available : availablePawns) {
            if (available.toString().equalsIgnoreCase(pawn)) {
                return true;
            }
        }
        return false;
    }

    public void addPlayer(Player p, VirtualView client) {

        for(Player player : players){
            Chat chat = new Chat(player, p);
            chatMap.put(new Pair<String, String>(player.getUsername(), p.getUsername()),   chat);
            chatMap.put(new Pair<String, String>(p.getUsername(), player.getUsername()),   chat);
        }
        this.players.add(p);
        switch (p.getPawnColour()){
            case RED:
                board.setRedPlayer(p.getUsername());
                break;
            case YELLOW:
                board.setYellowPlayer(p.getUsername());
                break;
            case GREEN:
                board.setGreenPlayer(p.getUsername());
                break;
            case BLUE:
                board.setBluePlayer(p.getUsername());
                break;
            default:
                break;
        }
        //create a listener
        //he will listen the observable and decide if the message has to be sent to his player
        this.addObserver(new PlayerListener(client, p));
        this.notifyObservers(new PlayerJoinedMessage(p.getUsername()));
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(String username) {
        observers.removeIf(obs -> obs.getPlayerUsername().equals(username));
    }


    public void notifyObservers(Message message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }
    public Chat getGeneralChat(){
        return this.generalChat;
    }
    public Chat getChat(String p1, String p2) {
        return chatMap.get(new Pair<String,String>(p1, p2));
    }
    public ArrayList<Chat> getChats(Player player){
        ArrayList<Chat> chats = new ArrayList<>();
        chats.add(this.generalChat);
        //System.out.println("aggiunta chat globale");
        for(Pair<String , String> p : chatMap.keySet()){
            if(p.getKey().equals(player.getUsername())){
                chats.add(chatMap.get(p));
            //    System.out.println("aggiunta chat singola");
            }
        }
        return chats;
    }

    public boolean isSuspended() {
        int count = 0;
        for(Player p: players){
            if(p.isDisconnected()){
                count++;
            }
        }
        return count >= players.size() - 1;

    }
    public int ready(Player p) {
        synchronized (board){
            this.readyPlayers++;
            return readyPlayers;
        }
    }
}
