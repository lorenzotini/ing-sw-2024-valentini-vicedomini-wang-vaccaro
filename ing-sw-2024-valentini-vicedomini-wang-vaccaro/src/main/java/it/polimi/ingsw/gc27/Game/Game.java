package it.polimi.ingsw.gc27.Game;
import it.polimi.ingsw.gc27.Card.Card;

import java.util.List;

public class Game {
    private int GameID;
    private Board board;

    private List<Player> players;
    private Card commonObjective1;
    private Card commonObjective2;

    public Board getBoard() {
        return board;
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
}
