package it.polimi.ingsw.gc27.Model.States.PlayerStates;

import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;

public class EndOfTurnState extends PlayerState{
    public EndOfTurnState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
        getTurnHandler().notifyEndOfTurnState(getPlayer(), getTurnHandler());
    }

    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) {

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
