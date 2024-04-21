package it.polimi.ingsw.gc27.States.PlayerStates;

import it.polimi.ingsw.gc27.Card.Face;
import it.polimi.ingsw.gc27.Card.GoldCard;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Card.StarterCard;
import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Game.Game;
import it.polimi.ingsw.gc27.Game.Manuscript;
import it.polimi.ingsw.gc27.Game.Market;
import it.polimi.ingsw.gc27.Game.Player;

import java.util.ArrayList;

public class InitializingState extends PlayerState{

    public InitializingState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
    }

    @Override
    public void drawResourceCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) {
        System.out.println("place your starter card first and only then you can start playing\n");
    }

    @Override
    public void drawGoldCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) {
        System.out.println("place your starter card first and only then you can start playing\n");
    }

    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) {
        System.out.println("place your starter card first and only then you can start playing\n");
    }

    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face, int x, int y) {
        getPlayer().addCard(game, starterCard, face, x, y);

        //go to next state (all the player in waiting state)
        getPlayer().setPlayerState(new WaitingState(getPlayer(), getTurnHandler()));
        // notifica il TurnHandler
        getTurnHandler().notifyInitializingState(getPlayer(), getTurnHandler());

    }
}
