package it.polimi.ingsw.gc27.Net.Commands;

import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.io.IOException;

public class ChooseObjectiveCommand implements Command {

    String playerName;
    int  objectiveIndex;

    public ChooseObjectiveCommand(String playerName, int objectiveIndex){
        this.playerName = playerName;
        this.objectiveIndex = objectiveIndex;
    }

    public void execute(GigaController console) throws IOException, InterruptedException {
        Player player = console.getPlayer(playerName);
        console.userToGameController(playerName).chooseObjectiveCard(player, objectiveIndex);
    }

}