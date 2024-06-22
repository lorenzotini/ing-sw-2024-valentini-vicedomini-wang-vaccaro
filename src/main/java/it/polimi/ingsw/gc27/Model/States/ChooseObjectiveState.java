package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.UpdateObjectiveMessage;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;

/**
 * Represents the state where a player chooses their personal secret objective card
 * This state allows the player to select an objective card that will contribute to their final score
 */
public class ChooseObjectiveState extends PlayerState {

    private final String wrongStateText = "You have to choose an objective card first";

    /**
     * State in which the player chooses the personal secret objective card
     * constructor matching super {@link PlayerState}
     */
    public ChooseObjectiveState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
    }


    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void drawCard(Player player, boolean isGold, boolean fromDeck, int faceUpCardIndex) {
        super.sendError(wrongStateText, getPlayer(), turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) {
        super.sendError(wrongStateText, getPlayer(), turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face) {
        String starterText = "You already have a Starter Card";
        super.sendError(starterText, getPlayer(), turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) {

        // The index is chosen by the player between 1 and 2, corresponding to 0 and 1 in the list. Then you switch it to remove the other card.
        objectiveCardIndex = objectiveCardIndex == 1 ? 1 : 0;

        this.getPlayer().getSecretObjectives().remove(objectiveCardIndex);


        this.getPlayer().setPlayerState(new WaitingState(getPlayer(), getTurnHandler()));

        Message updateObjectiveMessage = new UpdateObjectiveMessage(new MiniModel(getPlayer()));
        turnHandler.getGame().notifyObservers(updateObjectiveMessage);

        this.getTurnHandler().notifyChooseObjectiveState();

    }
}
