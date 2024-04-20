package it.polimi.ingsw.gc27.States.PlayerStates;

import it.polimi.ingsw.gc27.Card.Face;
import it.polimi.ingsw.gc27.Card.GoldCard;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Card.StarterCard;
import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Game.Game;
import it.polimi.ingsw.gc27.Game.Market;
import it.polimi.ingsw.gc27.Game.Player;
import it.polimi.ingsw.gc27.States.PlayerStates.PlayerState;

import java.util.ArrayList;

public class WaitingState extends PlayerState {

    public WaitingState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
    }

    @Override
    public void drawCard(Market market, Player player, ArrayList<ResourceCard> deck, ResourceCard card, int faceUpCardIndex) {
        System.out.println("please wait for your turn...\n");
    }

    @Override
    public void drawCard(Market market, Player player, ArrayList<GoldCard> deck, GoldCard card, int faceUpCardIndex) {
        System.out.println("please wait for your turn...\n");
    }

    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) {
        System.out.println("please wait for your turn...\n");
    }

    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face, int x, int y) {
        System.out.println("please wait for your turn...\n");
    }

}
