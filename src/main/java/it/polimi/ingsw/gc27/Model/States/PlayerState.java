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

//aggiungere state e setState in Game

public abstract class PlayerState implements Serializable {

    protected Player player;
    protected transient TurnHandler turnHandler;

    public PlayerState(){

    }

    public PlayerState(Player player, TurnHandler turnHandler) {
        this.player = player;
        this.turnHandler = turnHandler;
    }

    public abstract void chooseObjectiveCard(Game game, int objectiveCardIndex);

    public abstract void drawCard(Player player, boolean isGold, boolean fromDeck, int faceUpCardIndex) throws InterruptedException;

    public abstract void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y);

    public abstract void addStarterCard(Game game, StarterCard starterCard, Face face);

    public void sendError(String str, Player player, TurnHandler turnHandler) {

        MiniModel miniWithCurrentPlayer = new MiniModel(player);
        Message errorMessage = new GenericErrorMessage(str, miniWithCurrentPlayer);
        turnHandler.getGame().notifyObservers(errorMessage);

    }

//    public void sendState(String str, Player player, TurnHandler turnHandler) {
//
//        MiniModel miniWithCurrentPlayer = new MiniModel(player);
//        Message errorMessage = new GenericErrorMessage(str, miniWithCurrentPlayer);
//        turnHandler.getGame().notifyObservers(errorMessage);
//
//    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    // getter and setter
    public Player getPlayer() {
        return player;
    }

    public TurnHandler getTurnHandler() {
        return turnHandler;
    }

    public String toStringGUI(){
        return "Player State";
    }

}
