package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientBoard;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Board implements Serializable, ClientBoard {

    private int pointsRedPlayer;
    private int pointsYellowPlayer;
    private int pointsGreenPlayer;
    private int pointsBluePlayer;
    private String redPlayer;
    private String yellowPlayer;
    private String greenPlayer;
    private String bluePlayer;
    public final static int END_GAME_THRESHOLD = 2;
    public final static int MAX_POINTS = 29;
    @Override
    public int getPointsRedPlayer() {
        return pointsRedPlayer;
    }

    public void setPointsRedPlayer(int pointsRedPlayer) {
        this.pointsRedPlayer = pointsRedPlayer;
    }
    @Override
    public int getPointsYellowPlayer() {
        return pointsYellowPlayer;
    }

    public void setPointsYellowPlayer(int pointsYellowPlayer) {
        this.pointsYellowPlayer = pointsYellowPlayer;
    }
    @Override
    public int getPointsGreenPlayer() {
        return pointsGreenPlayer;
    }

    public void setPointsGreenPlayer(int pointsGreenPlayer) {
        this.pointsGreenPlayer = pointsGreenPlayer;
    }
    @Override
    public int getPointsBluePlayer() {
        return pointsBluePlayer;
    }

    public void setPointsBluePlayer(int pointsBluePlayer) {
        this.pointsBluePlayer = pointsBluePlayer;
    }

    public String getRedPlayer() {
        return redPlayer;
    }

    public void setRedPlayer(String redPlayer) {
        this.redPlayer = redPlayer;
    }

    public String getYellowPlayer() {
        return yellowPlayer;
    }

    public void setYellowPlayer(String yellowPlayer) {
        this.yellowPlayer = yellowPlayer;
    }

    public String getGreenPlayer() {
        return greenPlayer;
    }

    public void setGreenPlayer(String greenPlayer) {
        this.greenPlayer = greenPlayer;
    }

    public String getBluePlayer() {
        return bluePlayer;
    }

    public void setBluePlayer(String bluePlayer) {
        this.bluePlayer = bluePlayer;
    }

    public Map<String, Integer> getScoreBoard(){
        Map<String, Integer> scoreBoard = new HashMap<>();
        scoreBoard.put(this.redPlayer, pointsRedPlayer);
        scoreBoard.put(this.yellowPlayer, pointsYellowPlayer);
        scoreBoard.put(this.greenPlayer, pointsGreenPlayer);
        scoreBoard.put(this.bluePlayer, pointsBluePlayer);

        return scoreBoard;
    }
}
