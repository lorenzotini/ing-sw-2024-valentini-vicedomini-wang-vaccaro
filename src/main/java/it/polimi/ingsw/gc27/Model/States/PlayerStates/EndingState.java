package it.polimi.ingsw.gc27.Model.States.PlayerStates;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;

public class EndingState extends PlayerState {
    public EndingState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
        turnHandler.notifyCalculateObjectivePoints(getPlayer());
    }

    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) {

    }

    @Override
    public void drawResourceCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) {
        System.out.println("the game is ending... it's too late man\n");
    }

    @Override
    public void drawGoldCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) {
        System.out.println("the game is ending... it's too late man\n");
    }

    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) {
        System.out.println("the game is ending... it's too late man\n");
    }

    @Override
    public void askStarterCard(Game game, Player player, VirtualView client) throws IOException, InterruptedException {
        System.out.println("the game is ending... it's too late man\n");
    }



}
