package it.polimi.ingsw.gc27.Model.ClientClass;

import java.util.Map;

import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.util.HashMap;

public interface ClientBoard {
    int getPointsYellowPlayer();
    int getPointsGreenPlayer();
    int getPointsRedPlayer();
    int getPointsBluePlayer();
    Map<String, Integer> getScoreBoard();
    HashMap<String, PawnColour> getColourPlayerMap();
    int getPointsOf(PawnColour value);
}
