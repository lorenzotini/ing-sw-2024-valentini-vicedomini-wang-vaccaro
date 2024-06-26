package it.polimi.ingsw.gc27.Model.ClientClass;

import java.util.Map;

import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;

import java.util.HashMap;

/**
 * The ClientBoard interface provides methods for accessing player points,
 * scoreboards, and player colors in the Board {@link it.polimi.ingsw.gc27.Model.Game.Board}
 */
public interface ClientBoard {
    /**
     * @return the points of the yellow player
     */
    int getPointsYellowPlayer();

    /**
     * @return the points of the green player
     */
    int getPointsGreenPlayer();

    /**
     * @return the points of the red player
     */
    int getPointsRedPlayer();

    /**
     * @return the points of the blue player
     */
    int getPointsBluePlayer();

    /**
     * Gets the scoreboard, mapping player names to their respective points
     * @return a map representing the scoreboard.
     */
    Map<String, Integer> getScoreBoard();

    /**
     * Gets the mapping of player names to their respective pawn colors.
     * @return a HashMap representing the player colors.
     */
    HashMap<String, PawnColour> getColourPlayerMap();

    /**
     * Gets the points of a player with the specified pawn color
     * @param value the pawn color of the player
     * @return the points of the player with the specified pawn color
     */
    int getPointsOf(PawnColour value);
}
