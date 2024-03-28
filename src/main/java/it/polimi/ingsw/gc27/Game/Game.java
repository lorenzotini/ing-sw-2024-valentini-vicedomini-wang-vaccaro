package it.polimi.ingsw.gc27.Game;

import it.polimi.ingsw.gc27.Card.Card;
import it.polimi.ingsw.gc27.Enumerations.PawnColour;

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
