package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.NotYourTurnMessage;
import it.polimi.ingsw.gc27.Messages.UpdateObjectiveMessage;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.MiniModel;

import java.io.IOException;
import java.rmi.RemoteException;

public class ChooseObjectiveState extends PlayerState {

    private String wrongStateText = "You have to choose an objective card first";
    private String starterText = "You already have a Starter Card";

    public ChooseObjectiveState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
    }

    @Override
    public void drawCard(Player player, boolean isGold, boolean fromDeck, int faceUpCardIndex) throws RemoteException {
        MiniModel currentPlayer = new MiniModel(getPlayer());
        Message genericErrorMessage = new NotYourTurnMessage(wrongStateText, currentPlayer);
        turnHandler.getGame().notifyObservers(genericErrorMessage);
    }

    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) throws RemoteException {
        MiniModel currentPlayer = new MiniModel(getPlayer());
        Message genericErrorMessage = new NotYourTurnMessage(wrongStateText, currentPlayer);
        turnHandler.getGame().notifyObservers(genericErrorMessage);
    }

    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face) throws IOException, InterruptedException {
        MiniModel currentPlayer = new MiniModel(getPlayer());
        Message genericErrorMessage = new NotYourTurnMessage(starterText, currentPlayer);
        turnHandler.getGame().notifyObservers(genericErrorMessage);
    }

    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) throws RemoteException {

        if (objectiveCardIndex == 0) {
            objectiveCardIndex = 1;
        } else {
            objectiveCardIndex = 0;
        }

        this.getPlayer().getSecretObjectives().remove(objectiveCardIndex);
        this.getPlayer().setPlayerState(new WaitingState(getPlayer(), getTurnHandler()));

        Message updateObjectiveMessage = new UpdateObjectiveMessage(new MiniModel(getPlayer(), getPlayer().getSecretObjectives().getFirst()));
        turnHandler.getGame().notifyObservers(updateObjectiveMessage);

        getPlayer().setPlayerState(new WaitingState(getPlayer(), getTurnHandler()));

        this.getTurnHandler().notifyChooseObjectiveState(getPlayer());

    }

}
