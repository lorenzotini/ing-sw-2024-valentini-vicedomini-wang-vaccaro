package it.polimi.ingsw.gc27.Model.States.PlayerStates;

import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;

//aggiungere state e setState in Game

public abstract class PlayerState {
    private Player player;
    private TurnHandler turnHandler;

    public PlayerState(Player player, TurnHandler turnHandler){
        this.player = player;
        this.turnHandler = turnHandler;
    }

    public abstract void chooseObjectiveCard(Game game, int objectiveCardIndex);
    public abstract void drawResourceCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game);
    public abstract void drawGoldCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game);

    public abstract void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y);

    public abstract void addStarterCard(Game game, StarterCard starterCard, Face face, int x, int y);

    // getter and setter
    public Player getPlayer() {
        return player;
    }

    public TurnHandler getTurnHandler() {
        return turnHandler;
    }
}
