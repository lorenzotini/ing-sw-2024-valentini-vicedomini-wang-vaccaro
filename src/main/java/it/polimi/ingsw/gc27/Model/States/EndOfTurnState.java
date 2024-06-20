package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;

public class EndOfTurnState extends PlayerState {
    private final String waitText = "It's not your turn, wait for the other player.";

    /**
     * End of player's turn
     * constructor matching super {@link PlayerState}
     */
    public EndOfTurnState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) {
        super.sendError(waitText, getPlayer(), turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void drawCard(Player player, boolean isGold, boolean fromDeck, int faceUpCardIndex) {
        super.sendError(waitText, getPlayer(), turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) {
        super.sendError(waitText, getPlayer(), turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face) {
        super.sendError(waitText, getPlayer(), turnHandler);
    }
}
