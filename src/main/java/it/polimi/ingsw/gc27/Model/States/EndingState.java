package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.NotYourTurnMessage;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.MiniModel;

import java.io.IOException;
import java.rmi.RemoteException;

public class EndingState extends PlayerState  {

    String textMessage = "the game is ending... it's too late man";

    public EndingState(Player player, TurnHandler turnHandler) throws RemoteException {
        super(player, turnHandler);
        turnHandler.notifyCalculateObjectivePoints(getPlayer());
    }

    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) throws RemoteException {
        MiniModel currentPlayer = new MiniModel(getPlayer());
        Message genericErrorMessage = new NotYourTurnMessage(textMessage, currentPlayer);
        turnHandler.getGame().notifyObservers(genericErrorMessage);
    }

    @Override
    public void drawCard(Player player, boolean isGold, boolean fromDeck, int faceUpCardIndex) throws RemoteException {
        MiniModel currentPlayer = new MiniModel(getPlayer());
        Message genericErrorMessage = new NotYourTurnMessage(textMessage, currentPlayer);
        turnHandler.getGame().notifyObservers(genericErrorMessage);
    }

    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) throws RemoteException {
        MiniModel currentPlayer = new MiniModel(getPlayer());
        Message genericErrorMessage = new NotYourTurnMessage(textMessage, currentPlayer);
        turnHandler.getGame().notifyObservers(genericErrorMessage);
    }

    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face) throws IOException, InterruptedException{
        MiniModel currentPlayer = new MiniModel(getPlayer());
        Message genericErrorMessage = new NotYourTurnMessage(textMessage, currentPlayer);
        game.notifyObservers(genericErrorMessage);
    }

}
