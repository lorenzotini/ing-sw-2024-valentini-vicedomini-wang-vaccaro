package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientBoard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the scoreboard that tracks players' scores
 */
public class Board implements Serializable, ClientBoard {

    private int pointsRedPlayer;
    private int pointsYellowPlayer;
    private int pointsGreenPlayer;
    private int pointsBluePlayer;
    private String redPlayer;
    private String yellowPlayer;
    private String greenPlayer;
    private String bluePlayer;
    public final static int END_GAME_THRESHOLD = 20;
    public final static int MAX_POINTS = 29;
    public HashMap<String, PawnColour> colourPlayermap = new HashMap<>();

    /**
     * populates the Hashmap colourPlayerMap with players and their pawn colours
     * @param players the list of players
     */
    public void initBoard(List<Player> players){
        for(Player p: players){
            colourPlayermap.put(p.getUsername(), p.getPawnColour());
        }
    }

    /**
     * @return a Map that represents the scoreboard
     * the keys are the players username, whereas the values will be the scored points mapped to the username
     */
    public Map<String, Integer> getScoreBoard(){
        Map<String, Integer> scoreBoard = new HashMap<>();
        scoreBoard.put(this.redPlayer, pointsRedPlayer);
        scoreBoard.put(this.yellowPlayer, pointsYellowPlayer);
        scoreBoard.put(this.greenPlayer, pointsGreenPlayer);
        scoreBoard.put(this.bluePlayer, pointsBluePlayer);

        return scoreBoard;
    }

    /**
     * Gets the points of the red player
     * @return The points of the red player
     */
    @Override
    public int getPointsRedPlayer() {
        return pointsRedPlayer;
    }

    /**
     * Sets the points of the red player.
     * @param pointsRedPlayer The points to set for the red player.
     */
    public void setPointsRedPlayer(int pointsRedPlayer) {
        this.pointsRedPlayer = pointsRedPlayer;
    }

    /**
     * Gets the points of the yellow player
     * @return The points of the yellow player
     */
    @Override
    public int getPointsYellowPlayer() {
        return pointsYellowPlayer;
    }

    /**
     * Sets the points of the yellow player
     * @param pointsYellowPlayer The points to set for the yellow player
     */
    public void setPointsYellowPlayer(int pointsYellowPlayer) {
        this.pointsYellowPlayer = pointsYellowPlayer;
    }

    /**
     * Gets the points of the green player
     * @return The points of the green player
     */
    @Override
    public int getPointsGreenPlayer() {
        return pointsGreenPlayer;
    }

    /**
     * Sets the points of the green player
     * @param pointsGreenPlayer The points to set for the green player
     */
    public void setPointsGreenPlayer(int pointsGreenPlayer) {
        this.pointsGreenPlayer = pointsGreenPlayer;
    }

    /**
     * Gets the points of the blue player
     * @return The points of the blue player
     */
    @Override
    public int getPointsBluePlayer() {
        return pointsBluePlayer;
    }

    /**
     * Sets the points of the blue player
     * @param pointsBluePlayer The points to set for the blue player
     */
    public void setPointsBluePlayer(int pointsBluePlayer) {
        this.pointsBluePlayer = pointsBluePlayer;
    }

    /**
     * Gets the HashMap mapping player usernames to their pawn colours
     * @return The HashMap colourPlayermap
     */
    @Override
    public HashMap<String, PawnColour> getColourPlayermap() {
        return colourPlayermap;
    }

    /**
     * Gets the username of the red player.
     * @return The username of the red player.
     */
    public String getRedPlayer() {
        return redPlayer;
    }

    /**
     * Sets the username of the red player.
     * @param redPlayer The username to set for the red player.
     */
    public void setRedPlayer(String redPlayer) {
        this.redPlayer = redPlayer;
    }

    /**
     * Gets the username of the yellow player.
     * @return The username of the yellow player.
     */
    public String getYellowPlayer() {
        return yellowPlayer;
    }

    /**
     * Sets the username of the yellow player.
     * @param yellowPlayer The username to set for the yellow player.
     */
    public void setYellowPlayer(String yellowPlayer) {
        this.yellowPlayer = yellowPlayer;
    }

    /**
     * Gets the username of the green player.
     * @return The username of the green player.
     */
    public String getGreenPlayer() {
        return greenPlayer;
    }

    /**
     * Sets the username of the green player.
     * @param greenPlayer The username to set for the green player.
     */
    public void setGreenPlayer(String greenPlayer) {
        this.greenPlayer = greenPlayer;
    }

    /**
     * Gets the username of the blue player.
     * @return The username of the blue player.
     */
    public String getBluePlayer() {
        return bluePlayer;
    }

    /**
     * Sets the username of the blue player.
     * @param bluePlayer The username to set for the blue player.
     */
    public void setBluePlayer(String bluePlayer) {
        this.bluePlayer = bluePlayer;
    }
}
