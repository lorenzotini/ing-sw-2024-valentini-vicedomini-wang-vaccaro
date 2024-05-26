package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.io.IOException;
import java.rmi.RemoteException;

public class EndingState extends PlayerState  {

    // This state is reached when the player has finished his last turn, since someone reached the victory points limit

    String textMessage = "the game is ending... it's too late man";

    public EndingState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
        turnHandler.notifyCalculateObjectivePoints(getPlayer());
    }

    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) throws RemoteException {
        super.sendError(textMessage, getPlayer(), turnHandler);

    }

    @Override
    public void drawCard(Player player, boolean isGold, boolean fromDeck, int faceUpCardIndex) throws RemoteException {
        super.sendError(textMessage, getPlayer(), turnHandler);
    }

    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) throws RemoteException {
        super.sendError(textMessage, getPlayer(), turnHandler);
    }

    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face) throws IOException, InterruptedException{
        super.sendError(textMessage, getPlayer(), turnHandler);
    }

}
