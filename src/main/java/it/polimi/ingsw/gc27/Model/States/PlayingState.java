package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.NotYourTurnMessage;
import it.polimi.ingsw.gc27.Messages.UpdateHandMessage;
import it.polimi.ingsw.gc27.Messages.UpdateManuscriptMessage;
import it.polimi.ingsw.gc27.Model.Card.*;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.Listener.Observable;
import it.polimi.ingsw.gc27.Model.Listener.Observer;
import it.polimi.ingsw.gc27.Model.MiniModel;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;

public class PlayingState extends PlayerState {
    public PlayingState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
    }

    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) throws RemoteException {
        super.sendError("Just play a ******* card!", getPlayer(), turnHandler);
    }

    @Override
    public void drawResourceCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) throws RemoteException {
        super.sendError("you have to play a card first and then draw a card", getPlayer(), turnHandler);
    }

    @Override
    public void drawGoldCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) throws RemoteException {

        super.sendError("you have to play a card first and then draw a card", getPlayer(), turnHandler);
    }

    @Override
    public void addCard(Game game, ResourceCard card, Face face, int x, int y) throws RemoteException {
        if(getPlayer().getManuscript().isValidPlacement(x, y) && ((face instanceof FrontFace && getPlayer().getManuscript().satisfiedRequirement((ResourceCard) card)) || (face instanceof BackFace))){
            getPlayer().addCard(game, card, face, x, y);
            getPlayer().getHand().remove(card);

            //shows on screen that a card was played successfully
            //...

            //update message
            Message updateHand = new UpdateHandMessage(new MiniModel(getPlayer(), getPlayer().getHand()));
            turnHandler.getGame().notifyObservers(updateHand);
            Message updateManuscript = new UpdateManuscriptMessage(new MiniModel(getPlayer(),getPlayer().getManuscript()) );
            turnHandler.getGame().notifyObservers(updateManuscript);

            getPlayer().setPlayerState(new DrawingState(getPlayer(), getTurnHandler()));
        }else{
            System.err.println("Error: invalid position");
        }
    }

    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face) throws IOException, InterruptedException{
        super.sendError("Just play a ******* card!", getPlayer(), turnHandler);
    }
}
