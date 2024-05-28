
package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.PlayerJoinedMessage;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Listener.Observer;
import it.polimi.ingsw.gc27.Model.Listener.PlayerListener;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Game implements Serializable {

    private BlockingQueue<Observer> observers = new LinkedBlockingQueue<>() {};
    private Integer numActualPlayers;
    private Board board;
    private Market market;
    private List<Player> players;
    private ObjectiveCard commonObjective1;
    private ObjectiveCard commonObjective2;
    private ArrayList<StarterCard> starterDeck;
    private ArrayList<ObjectiveCard> objectiveDeck;
    private ArrayList<PawnColour> availablePawns = new ArrayList<>(List.of(PawnColour.GREEN, PawnColour.YELLOW, PawnColour.BLUE, PawnColour.RED));


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

    public ArrayList<ObjectiveCard> getObjectiveDeck() {
        return objectiveDeck;
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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
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
        this.players.add(p);
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

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers(Message message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }

}
