package it.polimi.ingsw.gc27.Model.ClientClass;

import java.util.Map;

public interface ClientBoard {
    int getPointsYellowPlayer();
    int getPointsGreenPlayer();
    int getPointsRedPlayer();
    int getPointsBluePlayer();

    Map<String, Integer> getScoreBoard();
}
