package it.polimi.ingsw.gc27.Model.Game;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Board implements Serializable {

    private int pointsRedPlayer;
    private int pointsYellowPlayer;
    private int pointsGreenPlayer;
    private int pointsBluePlayer;
    public final static int END_GAME_THRESHOLD = 20;
    public final static int MAX_POINTS = 29;

    public int getPointsRedPlayer() {
        return pointsRedPlayer;
    }

    public void setPointsRedPlayer(int pointsRedPlayer) throws RemoteException {
        this.pointsRedPlayer = pointsRedPlayer;

    }

    public int getPointsYellowPlayer() {
        return pointsYellowPlayer;
    }

    public void setPointsYellowPlayer(int pointsYellowPlayer) throws RemoteException {
        this.pointsYellowPlayer = pointsYellowPlayer;

    }

    public int getPointsGreenPlayer() {
        return pointsGreenPlayer;
    }

    public void setPointsGreenPlayer(int pointsGreenPlayer) throws RemoteException {
        this.pointsGreenPlayer = pointsGreenPlayer;

    }

    public int getPointsBluePlayer() {
        return pointsBluePlayer;
    }

    public void setPointsBluePlayer(int pointsBluePlayer) throws RemoteException {
        this.pointsBluePlayer = pointsBluePlayer;

    }

}
