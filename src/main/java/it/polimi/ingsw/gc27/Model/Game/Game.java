
package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.PlayerJoinedMessage;
import it.polimi.ingsw.gc27.Model.Card.Card;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Exceptions.UserNotFoundException;
import it.polimi.ingsw.gc27.Model.Listener.Observable;
import it.polimi.ingsw.gc27.Model.Listener.Observer;
import it.polimi.ingsw.gc27.Model.Listener.PlayerListener;
import it.polimi.ingsw.gc27.Net.VirtualView;
import javafx.util.Pair;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game implements Serializable, Observable {

    private Integer numActualPlayers;
    private Board board;
    private Market market;
    private List<Player> players;
    private Card commonObjective1;
    private Card commonObjective2;
    private ArrayList<StarterCard> starterDeck;
    private ArrayList<ObjectiveCard> objectiveDeck;
    private ArrayList<PawnColour> availablePawns = new ArrayList<>(List.of(PawnColour.GREEN, PawnColour.YELLOW, PawnColour.BLUE, PawnColour.RED));

    private Chat generalChat = new Chat();
    final private HashMap< Pair<Player, Player>, Chat> chatMap = new HashMap<>();

    public Game() {
    }

    public Game(int gameID, Board board, List<Player> players) {
        this.board = board;
        this.players = players;
    }

    public Game(Board board, Market market, List<Player> players, Card commonObjective1, Card commonObjective2, ArrayList<StarterCard> starterDeck, ArrayList<ObjectiveCard> objectiveDeck) {
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
    public Player getPlayer(String playerName){
        for(Player p : players){
            if(p.getUsername().equals(playerName)){
                return p;
            }
        }
        throw new UserNotFoundException(playerName + "is not in this game");
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
    public void setCommonObjective1(Card commonObjective1) {
        this.commonObjective1 = commonObjective1;
    }
    public void setCommonObjective2(Card commonObjective2) {
        this.commonObjective2 = commonObjective2;
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
     * @param player
     * @param points
     */
    public void addPoints(Player player, int points) throws RemoteException {
        PawnColour pawncolour = player.getPawnColour();
        switch (pawncolour){
            case BLUE -> board.setPointsBluePlayer(board.getPointsBluePlayer() + points);
            case RED -> board.setPointsRedPlayer(board.getPointsRedPlayer() + points);
            case GREEN -> board.setPointsGreenPlayer(board.getPointsGreenPlayer() + points);
            case YELLOW -> board.setPointsYellowPlayer(board.getPointsYellowPlayer() + points);
        }
    }

    public boolean validPawn(String pawn){
        for(var available : availablePawns){
            if(available.toString().equalsIgnoreCase(pawn)){
                return true;
            }
        }
        return false;
    }

    public void addPlayer(Player p, VirtualView client) throws RemoteException {

        for(Player player : players){
            Chat chat = new Chat(player, p);
            chatMap.put(new Pair<Player, Player>(player, p),   chat);
            chatMap.put(new Pair<Player, Player>(p, player),   chat);
        }
        generalChat.addPlayer(p);

        this.players.add(p);
        //create a listener
        //he will listen the observable and decide if the message has to be sent to his player
        this.addObserver(new PlayerListener(client, p));
        this.notifyObservers(new PlayerJoinedMessage(p.getUsername()));
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers(Message message) throws RemoteException {
        for(Observer o : observers){
            o.update(message);
        }
    }
    public Chat getGeneralChat(){
        return this.generalChat;
    }
    public Chat getChat(Player p1, Player p2) {
        return chatMap.get(new Pair<Player,Player>(p1, p2));
    }
    public ArrayList<Chat> getChats(Player player){
        ArrayList<Chat> chats = new ArrayList<>();
        chats.add(this.generalChat);
        System.out.println("aggiunta chat globale");
        for(Pair<Player , Player> p : chatMap.keySet()){
            if(p.getKey().getUsername().equals(player.getUsername())){
                chats.add(chatMap.get(p));
                System.out.println("aggiunta chat singola");
            }
        }
        return chats;
    }

}
