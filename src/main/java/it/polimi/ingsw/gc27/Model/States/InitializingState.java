package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.NotYourTurnMessage;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.Listener.Observable;
import it.polimi.ingsw.gc27.Model.Listener.Observer;
import it.polimi.ingsw.gc27.Model.MiniModel;

import java.io.IOException;
import java.rmi.RemoteException;

public class InitializingState extends PlayerState implements Observable {

    public InitializingState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
    }

    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) throws RemoteException {
        MiniModel currentPlayer = new MiniModel(getPlayer());
        Message genericErrorMessage = new NotYourTurnMessage("place your starter card first and only then you can start playing", currentPlayer);
        notifyObservers(genericErrorMessage);
    }

    @Override
    public void drawResourceCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) throws RemoteException {
        MiniModel currentPlayer = new MiniModel(getPlayer());
        Message genericErrorMessage = new NotYourTurnMessage("place your starter card first and only then you can start playing", currentPlayer);
        notifyObservers(genericErrorMessage);
    }

    @Override
    public void drawGoldCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) throws RemoteException {
        MiniModel currentPlayer = new MiniModel(getPlayer());
        Message genericErrorMessage = new NotYourTurnMessage("place your starter card first and only then you can start playing", currentPlayer);
        notifyObservers(genericErrorMessage);
    }

    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) throws RemoteException {
        MiniModel currentPlayer = new MiniModel(getPlayer());
        Message genericErrorMessage = new NotYourTurnMessage("place your starter card first and only then you can start playing", currentPlayer);
        notifyObservers(genericErrorMessage);
    }

    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face) throws IOException, InterruptedException {
        getPlayer().addCard(game, starterCard, face, Manuscript.FIELD_DIM/2, Manuscript.FIELD_DIM/2);
        getPlayer().setPlayerState(new WaitingState(getPlayer(), getTurnHandler()));
        // notifica il TurnHandler
        getTurnHandler().notifyInitializingState(getPlayer());
    }

    @Override
    public void addObserver(Observer o) {

    }

    @Override
    public void deleteObserver(Observer o) {

    }

    @Override
    public void notifyObservers() throws RemoteException {

    }

    @Override
    public void notifyObservers(Message notYourTurnMessage) throws RemoteException {
        for(Observer o: observers){
            o.update(notYourTurnMessage);
        }
    }

}