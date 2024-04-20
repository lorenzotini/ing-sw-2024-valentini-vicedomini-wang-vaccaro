package it.polimi.ingsw.gc27.States.PlayerStates;

import it.polimi.ingsw.gc27.Card.Face;
import it.polimi.ingsw.gc27.Card.GoldCard;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Card.StarterCard;
import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Game.Game;
import it.polimi.ingsw.gc27.Game.Market;
import it.polimi.ingsw.gc27.Game.Player;

import java.util.ArrayList;

//aggiungere state e setState in Game

public abstract class PlayerState {
    private Player player;
    private TurnHandler turnHandler;

    public PlayerState(Player player, TurnHandler turnHandler){
        this.player = player;
        this.turnHandler = turnHandler;
    }



    public abstract void drawCard(Market market, Player player, ArrayList<ResourceCard> deck, ResourceCard card, int faceUpCardIndex);
    public abstract void drawCard(Market market, Player player, ArrayList<GoldCard> deck, GoldCard card, int faceUpCardIndex);

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
