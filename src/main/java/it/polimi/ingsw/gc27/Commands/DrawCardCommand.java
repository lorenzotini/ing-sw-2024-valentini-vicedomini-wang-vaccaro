package it.polimi.ingsw.gc27.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Model.Game.Player;

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
    @Override
    public void execute(GameController gc) throws InterruptedException {

        Player player = gc.getPlayer(playerName);
        // TODO: gestire le eccezioni
        gc.drawCard(player, isGold, fromDeck, faceUpCardIndex);
    }
    @Override
    public String getPlayerName() {
        return this.playerName;
    }

}
