package it.polimi.ingsw.gc27.Game;

import it.polimi.ingsw.gc27.Card.Card;
import it.polimi.ingsw.gc27.Enumerations.PawnColour;

import java.util.List;

public class Game {


    private int GameID;



    private Board board;

    private List<Player> players;

    public void setCommonObjective1(Card commonObjective1) {
        this.commonObjective1 = commonObjective1;
    }

    public void setCommonObjective2(Card commonObjective2) {
        this.commonObjective2 = commonObjective2;
    }

    private Card commonObjective1;
    private Card commonObjective2;

    public Game(int gameID, Board board, List<Player> players) {
        GameID = gameID;
        this.board = board;
        this.players = players;
    }

    public Game() {

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
}
