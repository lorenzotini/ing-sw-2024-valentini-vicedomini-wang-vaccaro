package it.polimi.ingsw.gc27.Net.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
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

    public void execute(GameController gc) throws IOException, InterruptedException {
        Player player = gc.getPlayer(playerName);
        // TODO: gestire le eccezioni
        gc.drawCard(player, isGold, fromDeck, faceUpCardIndex);
    }
    @Override
    public String getPlayerName() {
        return this.playerName;
    }

}
