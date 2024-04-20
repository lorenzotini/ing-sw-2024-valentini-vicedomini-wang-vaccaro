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

public class EndOfTurnState extends PlayerState{
    public EndOfTurnState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
        getTurnHandler().notifyEndOfTurnState(getPlayer(), getTurnHandler());
    }



    @Override
    public void drawResourceCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) {

    }

    @Override
    public void drawGoldCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) {

    }

    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) {

    }

    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face, int x, int y) {

    }


}
