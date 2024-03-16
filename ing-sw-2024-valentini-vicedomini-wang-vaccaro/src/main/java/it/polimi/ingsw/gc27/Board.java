package it.polimi.ingsw.gc27;

public class Board {
    private int pointsRedPlayer;
    private int pointsYellowPlayer;
    private int pointsGreenPlayer;
    private int pointsBluePlayer;



    public final int END_GAME_THRESHOLD=20;
    public final int MAX_POINTS=29;
    public int getPointsRedPlayer() {
        return pointsRedPlayer;
    }

    public void setPointsRedPlayer(int pointsRedPlayer) {
        this.pointsRedPlayer = pointsRedPlayer;
    }

    public int getPointsYellowPlayer() {
        return pointsYellowPlayer;
    }

    public void setPointsYellowPlayer(int pointsYellowPlayer) {
        this.pointsYellowPlayer = pointsYellowPlayer;
    }

    public int getPointsGreenPlayer() {
        return pointsGreenPlayer;
    }

    public void setPointsGreenPlayer(int pointsGreenPlayer) {
        this.pointsGreenPlayer = pointsGreenPlayer;
    }

    public int getPointsBluePlayer() {
        return pointsBluePlayer;
    }

    public void setPointsBluePlayer(int pointsBluePlayer) {
        this.pointsBluePlayer = pointsBluePlayer;
    }




}
