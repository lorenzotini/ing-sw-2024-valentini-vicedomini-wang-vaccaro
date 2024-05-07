package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Listeners.Messages.Message;
import it.polimi.ingsw.gc27.Listeners.Messages.UpdateBoardMessage;
import it.polimi.ingsw.gc27.Model.Listener.Observable;
import it.polimi.ingsw.gc27.Model.Listener.Observer;
import it.polimi.ingsw.gc27.Model.MiniModel;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Board implements Serializable, Observable {
    private int pointsRedPlayer;
    private int pointsYellowPlayer;
    private int pointsGreenPlayer;
    private int pointsBluePlayer;
    public final int END_GAME_THRESHOLD=20;
    public final int MAX_POINTS=29;

    public int getPointsRedPlayer() {
        return pointsRedPlayer;
    }

    public void setPointsRedPlayer(int pointsRedPlayer) throws RemoteException {
        this.pointsRedPlayer = pointsRedPlayer;
        notifyObservers();
    }

    public int getPointsYellowPlayer() {
        return pointsYellowPlayer;
    }

    public void setPointsYellowPlayer(int pointsYellowPlayer) throws RemoteException {
        this.pointsYellowPlayer = pointsYellowPlayer;
        notifyObservers();
    }

    public int getPointsGreenPlayer() {
        return pointsGreenPlayer;
    }

    public void setPointsGreenPlayer(int pointsGreenPlayer) throws RemoteException {
        this.pointsGreenPlayer = pointsGreenPlayer;
        notifyObservers();
    }

    public int getPointsBluePlayer() {
        return pointsBluePlayer;
    }

    public void setPointsBluePlayer(int pointsBluePlayer) throws RemoteException {
        this.pointsBluePlayer = pointsBluePlayer;
        notifyObservers();
    }

    @Override
    public void addObserver(Observer o) {

    }

    @Override
    public void deleteObserver(Observer o) {

    }

    @Override
    public void notifyObservers() throws RemoteException {
        MiniModel miniModel = new MiniModel(this);
        Message message = new UpdateBoardMessage(miniModel);
        for(Observer o : observers){
            o.update(message);
        }
    }

    @Override
    public void notifyObservers(Message notYourTurnMessage) throws RemoteException {

    }
}
