package it.polimi.ingsw.gc27.Net.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.io.IOException;

public class SuspendPlayerCommand implements Command {

    String player;
    public SuspendPlayerCommand(String username){
        this.player = username;
    }
    @Override
    public void execute(GameController gc)  {
        gc.getGame().removeObserver(player);
        gc.suspendPlayer(gc.getPlayer(player));
    }

    @Override
    public String getPlayerName() {
        return player;
    }
}
