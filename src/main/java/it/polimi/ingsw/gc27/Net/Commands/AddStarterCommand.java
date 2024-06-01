package it.polimi.ingsw.gc27.Net.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.io.IOException;

public class AddStarterCommand implements Command {

    String playerName;
    boolean  isFront;

    public AddStarterCommand(String playerName, boolean isFront){
        this.playerName = playerName;
        this.isFront = isFront;
    }

    @Override
    public void execute(GameController gc) {
        Player player = gc.getPlayer(playerName);
        StarterCard starter = player.getStarterCard();
        Face face = isFront ? starter.getFront() : starter.getBack();
        // TODO: gestire le eccezioni
        gc.addStarterCard(player, starter, face);
    }
    @Override
    public String getPlayerName() {
        return this.playerName;
    }

}
