package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.States.*;

import java.rmi.RemoteException;

public class TurnHandler {
    private Game game;
    private boolean twentyPointsReached;
    private boolean lastRound;

    public void notifyChooseObjectiveState(Player player) {
        boolean everyoneReady = true;
        for(Player p: game.getPlayers()) {
            if (p.getPlayerState() instanceof ChooseObjectiveState) {
                everyoneReady = false;
                break;
            }
        }
        if(everyoneReady){
            game.getPlayers().getFirst().setPlayerState(new PlayingState(player, this));
        }
    }

    public void notifyInitializingState(Player player) {
        boolean everyoneReady = true;
        for(Player p: game.getPlayers()) {
            if (p.getPlayerState() instanceof InitializingState) {
                everyoneReady = false;
                break;
            }
        }
        if(everyoneReady){
            for(Player p: game.getPlayers()) {
                p.setPlayerState(new ChooseObjectiveState(player, this));
            }
        }
    }

    public void notifyEndOfTurnState(Player player) throws RemoteException {
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
            player.setPlayerState(new WaitingState(player, this));
            if(game.getPlayers().get(index+1) != null) {
                game.getPlayers().get(index+1).setPlayerState(new PlayingState(player, this));
            } else {
                game.getPlayers().getFirst().setPlayerState(new PlayingState(player, this));
                if(twentyPointsReached) {
                    lastRound = true; // once someone gets 20 points, only if the round is finished you can trigger the last round
                }
            }

        } else {
            player.setPlayerState(new EndingState(player, this));
            if(game.getPlayers().get(index+1) != null) {
                game.getPlayers().get(index+1).setPlayerState(new PlayingState(player, this));
            }
        }

    }

    public void notifyCalculateObjectivePoints(Player player) throws RemoteException {
        // verify that every player is in the ending state
        int points = 0;
        boolean everyPlayerEndingState = true;
        for(Player p : game.getPlayers()){
            if (!(p.getPlayerState() instanceof EndingState)) {
                everyPlayerEndingState = false;
                break;
            }
        }
        // if everyone in ending state then start the objective points calculation
        if(everyPlayerEndingState){
            for(Player p : game.getPlayers()){
                points = 0;
                points = p.getSecretObjectives().getFirst().calculateObjectivePoints(player.getManuscript());
                game.addPoints(player, points); // adding the points to the respective player
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
