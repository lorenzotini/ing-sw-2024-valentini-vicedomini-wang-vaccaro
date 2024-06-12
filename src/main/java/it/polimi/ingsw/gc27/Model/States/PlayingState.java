package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Messages.*;
import it.polimi.ingsw.gc27.Model.Card.*;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.MiniModel;

public class PlayingState extends PlayerState {

    public PlayingState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
//        super.sendState("It's your turn, place a card", getPlayer(),turnHandler);
    }

    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) {
        super.sendError("Just play a ******* card!", getPlayer(), turnHandler);
    }

    @Override
    public void drawCard(Player player, boolean isGold, boolean fromDeck, int faceUpCardIndex) {
        super.sendError("you have to play a card first and then draw a card", getPlayer(), turnHandler);
    }

    @Override
    public void addCard(Game game, ResourceCard card, Face face, int x, int y) {

        if (getPlayer().getManuscript().isValidPlacement(x, y) && ((face instanceof FrontFace && getPlayer().getManuscript().satisfiedRequirement((ResourceCard) card)) || (face instanceof BackFace))) {

            getPlayer().addCard(game, card, face, x, y);
            getPlayer().getHand().remove(card);
            //shows on screen that a card was played successfully
            //...
            getPlayer().setPlayerState(new DrawingState(getPlayer(), getTurnHandler()));
            Message updatePlayerStateMessage=new UpdatePlayerStateMessage(new MiniModel(getPlayer()));
            turnHandler.getGame().notifyObservers(updatePlayerStateMessage);
            //update message
            Message updateHandMessage = new UpdateHandMessage(new MiniModel(getPlayer(), getPlayer().getHand()));
            turnHandler.getGame().notifyObservers(updateHandMessage);

            Message updateManuscriptMessage = new UpdateManuscriptMessage(new MiniModel(getPlayer(), getPlayer().getManuscript()));
            turnHandler.getGame().notifyObservers(updateManuscriptMessage);

        } else if((face instanceof FrontFace) && !(getPlayer().getManuscript().satisfiedRequirement((ResourceCard) card))){
            super.sendError("You don't have enough resource, change card or place it in the back.", getPlayer(), turnHandler);
        }else{
            super.sendError("Invalid Placement, try again.", getPlayer(), turnHandler);
        }

    }

    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face) {
        super.sendError("Just play a ******* card!", getPlayer(), turnHandler);
    }
}
