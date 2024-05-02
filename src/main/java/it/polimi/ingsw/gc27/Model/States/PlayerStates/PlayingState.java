package it.polimi.ingsw.gc27.Model.States.PlayerStates;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Model.Card.*;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.io.IOException;

public class PlayingState extends PlayerState {
    public PlayingState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
    }

    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) {

    }

    @Override
    public void drawResourceCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) {
        System.out.println("you have to play a card first and then draw a card\n");
    }

    @Override
    public void drawGoldCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) {
        System.out.println("you have to play a card first and then draw a card\n");
    }

    @Override
    public void addCard(Game game, ResourceCard card, Face face, int x, int y) {
        if(getPlayer().getManuscript().isValidPlacement(x, y) && ((face instanceof FrontFace && getPlayer().getManuscript().satisfiedRequirement((ResourceCard) card)) || (face instanceof BackFace))){
            getPlayer().addCard(game, card, face, x, y);
            getPlayer().getHand().remove(card);
            //shows on screen that a card was played successfully
            //...
            //go to next state
            getPlayer().setPlayerState(new DrawingState(getPlayer(), getTurnHandler()));
        }else{
            System.err.println("Error: invalid position");
        }
    }

    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face) throws IOException, InterruptedException{
        System.out.println("you already have a starter card\n");
    }


}
