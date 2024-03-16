package it.polimi.ingsw.gc27;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private int GameID;
    private Manuscript manuscript;
    private Board board;

    private List<Player> players;
    private Card commonObjective1;
    private Card commonObjective2;

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

    public Card getCommonObjective1() {
        return commonObjective1;
    }

    public void setCommonObjective1(Card commonObjectives1) {
        this.commonObjective1 = commonObjectives1;
    }

    public Card getCommonObjective2() {
        return commonObjective2;
    }

    public void setCommonObjective2(Card commonObjectives2) {
        this.commonObjective2 = commonObjectives2;
    }




    public int getGameID() {
        return GameID;
    }

    public void setGameID(int gameID) {
        GameID = gameID;
    }

    public Manuscript getManuscript() {
        return manuscript;
    }

    public void setManuscript(Manuscript manuscript) {
        this.manuscript = manuscript;
    }

    public Board getMarket() {
        return board;
    }

    public void setMarket(Board board) {
        this.board = board;
    }

}
