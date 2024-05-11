package it.polimi.ingsw.gc27.Net.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.io.Console;
import java.io.IOException;

public class AddStarterCommand implements Command {

    String playerName;
    boolean  isFront;

    public AddStarterCommand(String playerName, boolean isFront){
        this.playerName = playerName;
        this.isFront = isFront;
    }

    public void execute(GigaController console) throws IOException, InterruptedException {
        Player player = console.getPlayer(playerName);
        StarterCard starter = player.getStarterCard();
        Face face = isFront ? starter.getFront() : starter.getBack();
        // TODO: gestire le eccezioni
        console.userToGameController(playerName).addStarterCard(player, starter, face);
    }

}
