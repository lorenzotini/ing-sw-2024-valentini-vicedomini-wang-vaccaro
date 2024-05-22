package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.UpdateManuscriptMessage;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.MiniModel;

import java.io.IOException;
import java.rmi.RemoteException;

public class InitializingState extends PlayerState {

    private String wrongStateText = "place your starter card first and only then you can start playing";

    public InitializingState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
    }

    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) throws RemoteException {
        super.sendError(wrongStateText, getPlayer(), turnHandler);
    }

    @Override
    public void drawCard(Player player, boolean isGold, boolean fromDeck, int faceUpCardIndex) throws RemoteException {
        super.sendError(wrongStateText, getPlayer(), turnHandler);
    }

    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) throws RemoteException {
        super.sendError(wrongStateText, getPlayer(), turnHandler);
    }

    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face) throws IOException, InterruptedException {

        getPlayer().addCard(game, starterCard, face, Manuscript.FIELD_DIM / 2, Manuscript.FIELD_DIM / 2);

        getPlayer().setPlayerState(new ChooseObjectiveState(getPlayer(), getTurnHandler()));

        Message updateManuscriptMessage = new UpdateManuscriptMessage(new MiniModel(getPlayer(), getPlayer().getManuscript()));

        turnHandler.getGame().notifyObservers(updateManuscriptMessage);

    }


}