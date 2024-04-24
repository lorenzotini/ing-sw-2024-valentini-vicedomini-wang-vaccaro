package it.polimi.ingsw.gc27.Model.States.PlayerStates;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;

public class ChooseObjectiveState extends PlayerState{
    public ChooseObjectiveState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
    }

    @Override
    public void drawResourceCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) {
        System.out.println("You have to choose an objective card first\n");
    }

    @Override
    public void drawGoldCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) {
        System.out.println("You have to choose an objective card first\n");
    }

    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) {
        System.out.println("You have to choose an objective card first\n");
    }

    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face, int x, int y) {
        System.out.println("You have to choose an objective card first\n");
    }
    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) {
        if(objectiveCardIndex == 0){
            objectiveCardIndex = 1;
        } else {
            objectiveCardIndex = 0;
        }
        this.getPlayer().getSecretObjectives().remove(objectiveCardIndex);
        this.getPlayer().setPlayerState(new WaitingState(getPlayer(), getTurnHandler()));
        this.getTurnHandler().notifyChooseObjectiveState(getPlayer(), getTurnHandler());
    }
}
