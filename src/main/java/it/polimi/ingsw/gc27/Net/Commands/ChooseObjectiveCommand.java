package it.polimi.ingsw.gc27.Net.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.io.IOException;

public class ChooseObjectiveCommand implements Command {

    String playerName;
    int  objectiveIndex;

    public ChooseObjectiveCommand(String playerName, int objectiveIndex){
        this.playerName = playerName;
        this.objectiveIndex = objectiveIndex;
    }

    public void execute(GameController gc) throws IOException, InterruptedException {
        Player player = gc.getPlayer(playerName);
        gc.chooseObjectiveCard(player, objectiveIndex);
    }
    @Override
    public String getPlayerName() {
        return this.playerName;
    }

}