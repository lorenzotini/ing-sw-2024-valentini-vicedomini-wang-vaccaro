package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientBoard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public HashMap<String, PawnColour> colourPlayerMap=new HashMap<>();

    public void initBoard(List<Player> players){
        for(Player p: players){
            colourPlayerMap.put(p.getUsername(), p.getPawnColour());
        }
    }

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
    @Override
    public HashMap<String, PawnColour> getColourPlayerMap() {
        return colourPlayerMap;
    }

    public Map<String, Integer> getScoreBoard(){
        Map<String, Integer> scoreBoard = new HashMap<>();
        scoreBoard.put(this.redPlayer, pointsRedPlayer);
        scoreBoard.put(this.yellowPlayer, pointsYellowPlayer);
        scoreBoard.put(this.greenPlayer, pointsGreenPlayer);
        scoreBoard.put(this.bluePlayer, pointsBluePlayer);

        return scoreBoard;
    }
    public void setColourPlayer(Player p){
        switch (p.getPawnColour()){
            case RED:
                redPlayer = p.getUsername();
                break;
            case YELLOW:
                yellowPlayer = p.getUsername();
                break;
            case GREEN:
                greenPlayer = p.getUsername();
                break;
            case BLUE:
                bluePlayer = p.getUsername();
                break;
            default:
                break;
        }
    }
}
