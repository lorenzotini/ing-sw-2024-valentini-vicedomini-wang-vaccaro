package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.States.PlayerStates.EndingState;
import it.polimi.ingsw.gc27.Model.States.PlayerStates.InitializingState;
import it.polimi.ingsw.gc27.Model.States.PlayerStates.PlayingState;
import it.polimi.ingsw.gc27.Model.States.PlayerStates.WaitingState;

public class TurnHandler {
    private Game game;
    private boolean twentyPointsReached;
    private boolean lastRound;



    public void notifyInitializingState(Player player, TurnHandler turnHandler) {
        boolean everyoneReady = true;
        for(Player p: game.getPlayers()) {
            if (p.getPlayerState() instanceof InitializingState) {
                everyoneReady = false;
                break;
            }
        }
        if(everyoneReady){
            game.getPlayers().getFirst().setPlayerState(new PlayingState(player, turnHandler));
        }
    }

    public void notifyEndOfTurnState(Player player, TurnHandler turnHandler) {
        // in case someone triggers the 20 points threshold
        if(game.getBoard().getPointsBluePlayer() >= game.getBoard().END_GAME_THRESHOLD ||
                game.getBoard().getPointsRedPlayer() >= game.getBoard().END_GAME_THRESHOLD ||
                game.getBoard().getPointsYellowPlayer() >= game.getBoard().END_GAME_THRESHOLD ||
                game.getBoard().getPointsGreenPlayer() >= game.getBoard().END_GAME_THRESHOLD)
        {
            this.twentyPointsReached = true;
        }

        // what happens if it's the last round or not
        int index = game.getPlayers().indexOf(player); // index of the player

        if(!lastRound) {
            player.setPlayerState(new WaitingState(player, turnHandler));
            if(game.getPlayers().get(index+1) != null) {
                game.getPlayers().get(index+1).setPlayerState(new PlayingState(player, turnHandler));
            } else {
                game.getPlayers().getFirst().setPlayerState(new PlayingState(player, turnHandler));
                if(twentyPointsReached) {
                    lastRound = true; // once someone gets 20 points, only if the round is finished you can trigger the last round
                }
            }

        } else {
            player.setPlayerState(new EndingState(player, turnHandler));
            if(game.getPlayers().get(index+1) != null) {
                game.getPlayers().get(index+1).setPlayerState(new PlayingState(player, turnHandler));
            }
        }

    }



    //constructor
    public TurnHandler(Game game){
        this.game = game;
        this.twentyPointsReached = false;
        this.lastRound = false;
    }


    // getter and setter

}
