package it.polimi.ingsw.gc27.Game;

import it.polimi.ingsw.gc27.Card.Card;
import it.polimi.ingsw.gc27.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Card.StarterCard;
import it.polimi.ingsw.gc27.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Exceptions.UserNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {


    private int GameID;
    private Board board;
    private Market market;
    private List<Player> players;
    private int numberOfPlayers;
    private Card commonObjective1;
    private Card commonObjective2;
    private ArrayList<StarterCard> starterDeck;
    private ArrayList<ObjectiveCard> objectiveDeck;
    private ArrayList<PawnColour> availablePawns = new ArrayList<>(List.of(PawnColour.GREEN, PawnColour.YELLOW, PawnColour.BLUE, PawnColour.RED));


    public Game(int gameID, Board board, List<Player> players) {
        GameID = gameID;
        this.board = board;
        this.players = players;
    }


    public Game(int gameID, Board board, Market market, List<Player> players, Card commonObjective1, Card commonObjective2, ArrayList<StarterCard> starterDeck, ArrayList<ObjectiveCard> objectiveDeck) {
        GameID = gameID;
        this.board = board;
        this.market = market;
        this.players = players;
        this.commonObjective1 = commonObjective1;
        this.commonObjective2 = commonObjective2;
        this.starterDeck = starterDeck;
        this.objectiveDeck = objectiveDeck;
    }

    public Player getPlayer(String playerName){
        for(Player p : players){
            if(p.getUsername().equals(playerName)){
                return p;
            }
        }
        throw new UserNotFoundException(playerName + "is not in this game: " + this.getGameID());
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
    public ArrayList<PawnColour> getAvailablePawns() {
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

    public int getGameID() {
        return GameID;
    }

    public List<Player> getPlayers() {
        return players;
    }
    public void setGameID(int gameID) {
        GameID = gameID;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Updates player's points, as a result of a played card or objectives points
     * @param player
     * @param points
     */
    public void addPoints(Player player, int points){
        PawnColour pawncolour = player.getPawnColour();
        switch (pawncolour){
            case BLUE -> board.setPointsBluePlayer(board.getPointsBluePlayer() + points);
            case RED -> board.setPointsRedPlayer(board.getPointsRedPlayer() + points);
            case GREEN -> board.setPointsGreenPlayer(board.getPointsGreenPlayer() + points);
            case YELLOW -> board.setPointsYellowPlayer(board.getPointsYellowPlayer() + points);
        }
    }
    public boolean validUsername(String u){
        for(var p : players){
            if(p.getUsername().equals(u)){
                return false;
            }
        }
        return true;
    }
    public boolean validPawn(String pawn){
        for(var available : availablePawns){
            if(available.toString().equalsIgnoreCase(pawn)){
                return true;
            }
        }
        return false;
    }
}
