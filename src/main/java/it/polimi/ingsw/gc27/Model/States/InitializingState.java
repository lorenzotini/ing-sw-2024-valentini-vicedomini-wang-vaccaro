package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.UpdateMyManuscriptMessage;
import it.polimi.ingsw.gc27.Messages.UpdateOtherManuscriptMessage;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;

public class InitializingState extends PlayerState {

    private final String wrongStateText = "place your starter card first and only then you can start playing";

    /**
     * State in which a player adds the starter card to their manuscript
     * constructor matching super {@link PlayerState}
     */

    public InitializingState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
    }
    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) {
        super.sendError(wrongStateText, this.player, turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void drawCard(Player player, boolean isGold, boolean fromDeck, int faceUpCardIndex) {
        super.sendError(wrongStateText, this.player, turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) {
        super.sendError(wrongStateText, this.player, turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face) {

        this.player.addCard(game, starterCard, face, Manuscript.FIELD_DIM / 2, Manuscript.FIELD_DIM / 2);

        this.player.setPlayerState(new ChooseObjectiveState(this.player, getTurnHandler()));

        Message updateMyManuscriptMessage = new UpdateMyManuscriptMessage(new MiniModel(this.player, game));
        turnHandler.getGame().notifyObservers(updateMyManuscriptMessage);

        Message updateOtherManuscriptMessage = new UpdateOtherManuscriptMessage(new MiniModel(game));
        turnHandler.getGame().notifyObservers(updateOtherManuscriptMessage);

    }

}