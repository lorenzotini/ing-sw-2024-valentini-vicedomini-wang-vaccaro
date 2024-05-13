package it.polimi.ingsw.gc27.Net.Commands;

import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.io.IOException;

public class DrawCardCommand implements  Command {

    String playerName;
    boolean fromDeck;
    boolean isGold;
    int  faceUpCardIndex;

    public DrawCardCommand(String playerName, boolean isGold, boolean fromDeck, int faceUpCardIndex) {
        this.playerName = playerName;
        this.isGold = isGold;
        this.fromDeck = fromDeck;
        this.faceUpCardIndex = faceUpCardIndex;
    }

    public void execute(GigaController console) throws IOException, InterruptedException {
        Player player = console.getPlayer(playerName);
        // TODO: gestire le eccezioni
        console.userToGameController(playerName).drawCard(player, isGold, fromDeck, faceUpCardIndex);
    }

}
