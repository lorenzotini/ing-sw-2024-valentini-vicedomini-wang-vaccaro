package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;

/**
 * Represents the waiting state of a player in the game
 * State in which players wait for other players to add a starter card, choose an objective or play their turn
 */
public class WaitingState extends PlayerState {
    private Player currentPlayer;
    private final String waitText = "Waiting State";

    /**
     * State in which players wait for other players to add a starter card, choose an objective or play their turn
     * constructor matching super {@link PlayerState}
     */
    public WaitingState(Player player, TurnHandler turnHandler) {
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
        super.sendError(waitText, getPlayer(),turnHandler);
    }

    /**
     * method implemented from {@link PlayerState}, according to the State Pattern principle
     */
    @Override
    public String toStringGUI(){
        if(currentPlayer != null)
            return "Now playing: "+currentPlayer.getUsername();
        else{
            return "Current player not set";
        }
    }

    /**
     * sets the current player
     * @param currentPlayer the current player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

}
