package it.polimi.ingsw.gc27.Model.States;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;

public class WaitingState extends PlayerState {
    private Player currentPlayer;

    //private final String waitText = "Patientiam forti est virtute, so please wait";
    private final String waitText = "Waiting State";

    // Use this state to wait for other players to choose starter and objective cards
    public WaitingState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
//        super.sendState("you are not the\n current player \nwait", getPlayer(),turnHandler);

    }

    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) {
        super.sendError(waitText, getPlayer(), turnHandler);
    }

    @Override
    public void drawCard(Player player, boolean isGold, boolean fromDeck, int faceUpCardIndex) {
        super.sendError(waitText, getPlayer(), turnHandler);
    }

    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) {
        super.sendError(waitText, getPlayer(), turnHandler);
    }

    @Override
    public void addStarterCard(Game game, StarterCard starterCard, Face face) {
        super.sendError(waitText, getPlayer(),turnHandler);
    }
    @Override
    public String toStringGUI(){
        if(currentPlayer != null)
            return "Now playing: "+currentPlayer.getUsername();
        else{
            return "Current player not set";
        }
    }
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

}
