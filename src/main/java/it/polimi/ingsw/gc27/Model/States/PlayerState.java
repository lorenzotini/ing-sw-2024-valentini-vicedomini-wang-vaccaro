package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Messages.GenericErrorMessage;
import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;

import java.io.Serializable;

public abstract class PlayerState implements Serializable {

    protected Player player;
    protected transient TurnHandler turnHandler;

    /**
     * constructor, abstract class following the State Pattern
     * @param player which player
     * @param turnHandler to notify the turn handler
     */
    public PlayerState(Player player, TurnHandler turnHandler) {
        this.player = player;
        this.turnHandler = turnHandler;
    }

     //abstract methods implemented by concrete State classes
    /**
     * this method allows the player to choose the personal secret objective, this last action
     * is allowed only if the player is in {@link ChooseObjectiveState}
     * @param game which game
     * @param objectiveCardIndex which Objective Card
     */
    public abstract void chooseObjectiveCard(Game game, int objectiveCardIndex);

    /**
     * this method allows the player to draw a card from the market, this last action
     * is allowed only if the player is in {@link DrawingState}
     * @param player which player
     * @param isGold if the card drawn is gold
     * @param fromDeck if the card drawn is from the deck
     * @param faceUpCardIndex which of the two card placed face up in the market
     * @throws InterruptedException exception
     */
    public abstract void drawCard(Player player, boolean isGold, boolean fromDeck, int faceUpCardIndex) throws InterruptedException;

    /**
     * this method allows the player to add a card to the personal manuscript, this last action
     * is allowed only if the player is in {@link PlayingState}
     * @param game which game
     * @param resourceCard the card played (gold or resource card)
     * @param face face up or face down
     * @param x position index
     * @param y position index
     */
    public abstract void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y);

    /**
     * this method allows the player to choose the personal secret objective, this last action
     * is allowed only if the player is in {@link InitializingState}
     * @param game which game
     * @param starterCard the chosen card
     * @param face face up or face down
     */
    public abstract void addStarterCard(Game game, StarterCard starterCard, Face face);

    /**
     * sending to observers an error message
     * @param str which error
     * @param player to which player
     * @param turnHandler to notify the turn handler
     */
    public void sendError(String str, Player player, TurnHandler turnHandler) {

        MiniModel miniWithCurrentPlayer = new MiniModel(player);
        Message errorMessage = new GenericErrorMessage(str, miniWithCurrentPlayer);
        turnHandler.getGame().notifyObservers(errorMessage);

    }
    /**
     * @return the name of the state, to a string
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    /**
     * getters and setters
     */
    public Player getPlayer() {
        return player;
    }
    public TurnHandler getTurnHandler() {
        return turnHandler;
    }

    /**
     * @return the name of the state, to a string
     * if method called from Gui
     */
    public String toStringGUI(){
        return "Player State";
    }

}
