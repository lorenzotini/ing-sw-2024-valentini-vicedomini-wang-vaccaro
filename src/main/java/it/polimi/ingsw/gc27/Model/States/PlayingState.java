package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Messages.*;
import it.polimi.ingsw.gc27.Model.Card.*;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;

public class PlayingState extends PlayerState {

    /**
     * State in which a player can play a card
     * constructor matching super {@link PlayerState}
     */
    public PlayingState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) {
        super.sendError("Just play a ******* card!", player, turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void drawCard(Player player, boolean isGold, boolean fromDeck, int faceUpCardIndex) {
        super.sendError("you have to play a card first and then draw a card", player, turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void addCard(Game game, ResourceCard card, Face face, int x, int y) {

        if (player.getManuscript().isValidPlacement(x, y) && ((face instanceof FrontFace && player.getManuscript().satisfiedRequirement((ResourceCard) card)) || (face instanceof BackFace))) {

            player.addCard(game, card, face, x, y);
            player.getHand().remove(card);

            //shows on screen that a card was played successfully
            //...
            getPlayer().setPlayerState(new DrawingState(getPlayer(), getTurnHandler()));
            Message updatePlayerStateMessage=new UpdatePlayerStateMessage(new MiniModel(getPlayer()));
            //update message
            Message updateHandMessage = new UpdateHandMessage(new MiniModel(player));

            Message updateManuscriptMessage = new UpdateMyManuscriptMessage(new MiniModel(player, game));

            Message updateOtherManuscriptMessage = new UpdateOtherManuscriptMessage(new MiniModel(game));
            turnHandler.getGame().notifyObservers(updatePlayerStateMessage);
            turnHandler.getGame().notifyObservers(updateHandMessage);
            turnHandler.getGame().notifyObservers(updateManuscriptMessage);
            turnHandler.getGame().notifyObservers(updateOtherManuscriptMessage);


        } else if((face instanceof FrontFace) && !(player.getManuscript().satisfiedRequirement((ResourceCard) card))){
            super.sendError("You don't have enough resource, change card or place it in the back.", player, turnHandler);
        }else{
            super.sendError("Invalid Placement, try again.", player, turnHandler);
        }

    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */

    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face) {
        super.sendError("Just play a ******* card!", player, turnHandler);
    }
    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public String toStringGUI(){
        return "It's your turn to play!";
    }
}
