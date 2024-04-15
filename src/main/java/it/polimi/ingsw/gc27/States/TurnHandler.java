package it.polimi.ingsw.gc27.States;

import it.polimi.ingsw.gc27.Game.*;

import java.util.*;

public class TurnHandler {
    private HashMap<Player, State> playerState;

    public TurnHandler(Game game, HashMap<Player, State> statePlayer) {
        this.playerState = statePlayer;
        for(Player p : game.getPlayers()){
            this.playerState.put(p, (new WaitingState(game)));
        } // initializes the hashmap, it maps the players to their current state
    }

    public HashMap<Player, State> getStatePlayer() {
        return playerState;
    }

    public void setPlayerState(HashMap<Player, State> playerState) {
        this.playerState = playerState;
    }

}
